/*
 * MIT License
 * 
 * Copyright (c) 2017 Ralph Niemitz
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.ralleytn.simple.audio;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 * Reads the entire audio data into the RAM. Good for small sound effects.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.0.0
 */
public class BufferedAudio extends AbstractAudio {

	private Clip clip;
	
	/**
	 * @param file name of the resource file
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(String file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain File}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(File file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain Path}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(Path file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(String zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(File zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(Path zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param url the resource
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(URL url) throws AudioException {
		
		super(url);
	}
	
	/**
	 * @param uri the resource as {@linkplain URI}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(URI uri) throws AudioException {
		
		super(uri);
	}
	
	@Override
	public void setFramePosition(long frame) {
		
		long oldVal = this.clip.getLongFramePosition();
		this.clip.setFramePosition((int)frame);
		this.trigger(AudioEvent.Type.POSITION_CHANGED, oldVal, frame);
	}
	
	@Override
	public void play() {
		
		if(this.isPlaying()) {
			
			this.stop();
		}
		
		this.clip.start();
		this.paused = false;
		this.trigger(AudioEvent.Type.STARTED);
	}

	@Override
	public void pause() {
		
		if(this.isPlaying()) {
			
			this.clip.stop();
			this.paused = true;
			this.trigger(AudioEvent.Type.PAUSED);
		}
	}

	@Override
	public void resume() {
		
		if(this.paused) {
			
			this.clip.start();
			this.paused = false;
			this.trigger(AudioEvent.Type.RESUMED);
			
		} else {
			
			this.play();
		}
	}

	@Override
	public void stop() {
		
		this.clip.stop();
		this.clip.setMicrosecondPosition(0);
		this.paused = false;
		this.trigger(AudioEvent.Type.STOPPED);
	}

	@Override
	public void loop(int repetitions) {
		
		if(this.isPlaying()) {
			
			this.stop();
		}
		
		this.paused = false;
		this.clip.loop(repetitions);
	}

	@Override
	public void setPosition(long millisecond) {
		
		float frameRate = this.audioInputStream.getFormat().getFrameRate() / 1000.0F;
		long newVal = (long)(frameRate * millisecond);
		long oldVal = this.clip.getLongFramePosition();
		this.clip.setMicrosecondPosition(millisecond * 1000);
		this.trigger(AudioEvent.Type.POSITION_CHANGED, oldVal, newVal);
	}

	@Override
	public void open() throws AudioException {

		try {
			
			this.audioInputStream = Audio.getAudioInputStream(this.resource);
			this.clip = AudioSystem.getClip();
			this.clip.open(this.audioInputStream);
			this.clip.addLineListener(event -> {
				
				if(event.getType().equals(LineEvent.Type.STOP) && this.clip.getMicrosecondPosition() >= this.clip.getMicrosecondLength()) {
					
					this.trigger(AudioEvent.Type.REACHED_END);
				}
			});
			this.controls = AbstractAudio.extractControls(this.clip, this.controls);
			this.open = true;
			this.trigger(AudioEvent.Type.OPENED);
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}

	@Override
	public void close() {
		
		if(this.isPlaying()) {
			
			this.stop();
		}
		
		this.clip.flush();
		this.clip.close();
		this.controls.clear();
		
		try {
			
			this.audioInputStream.close();
			
		} catch(IOException exception) {}
		
		this.open = false;
		this.trigger(AudioEvent.Type.CLOSED);
	}
	
	@Override
	public long getFrameLength() {
		
		return this.clip.getFrameLength();
	}
	
	@Override
	public long getLength() {
		
		return this.clip.getMicrosecondLength() / 1000;
	}

	@Override
	public long getPosition() {
		
		return this.clip.getMicrosecondPosition() / 1000;
	}

	@Override
	public boolean isPlaying() {
		
		return this.clip.isRunning();
	}

	@Override
	public float getLevel() {
	
		return this.clip.getLevel();
	}
	
	@Override
	public int getBufferSize() {
		
		return this.clip.getBufferSize();
	}
	
	@Override
	public AudioFormat getAudioFormat() {
		
		return this.clip.getFormat();
	}
	
	@Override
	public long getFramePosition() {
		
		return this.clip.getFramePosition();
	}
}
