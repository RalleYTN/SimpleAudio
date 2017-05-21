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
 * @version 1.1.0
 * @since 1.0.0
 */
public class BufferedAudio extends AbstractAudio {

	private Clip clip;
	private boolean paused;
	
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
		
		this.clip.setFramePosition((int)frame);
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
		
		this.clip.setMicrosecondPosition(millisecond * 1000);
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
