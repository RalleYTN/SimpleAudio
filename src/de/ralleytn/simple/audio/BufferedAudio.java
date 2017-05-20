package de.ralleytn.simple.audio;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Reads the entire audio data into the RAM. Good for small sound effects.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class BufferedAudio extends AbstractAudio {

	private Clip clip;
	
	/**
	 * @param file name of the resource file
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public BufferedAudio(String file) throws MalformedURLException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain File}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public BufferedAudio(File file) throws MalformedURLException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain Path}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public BufferedAudio(Path file) throws MalformedURLException {
		
		super(file);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(String zip, String entry) throws IOException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(File zip, String entry) throws IOException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(Path zip, String entry) throws IOException {
		
		super(zip, entry);
	}
	
	/**
	 * @param url the resource
	 * @since 1.0.0
	 */
	public BufferedAudio(URL url) {
		
		super(url);
	}
	
	/**
	 * @param uri the resource as {@linkplain URI}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public BufferedAudio(URI uri) throws MalformedURLException {
		
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
	}

	@Override
	public void pause() {
		
		this.clip.stop();
	}

	@Override
	public void resume() {
		
		this.clip.start();
	}

	@Override
	public void stop() {
		
		this.clip.stop();
		this.clip.setMicrosecondPosition(0);
	}

	@Override
	public void loop(int repetitions) {
		
		if(this.isPlaying()) {
			
			this.stop();
		}
		
		this.clip.loop(repetitions);
	}

	@Override
	public void setPosition(long millisecond) {
		
		this.clip.setMicrosecondPosition(millisecond * 1000);
	}

	@Override
	public void open() throws AudioException {

		try {
			
			this.audioInputStream = AbstractAudio.getAudioInputStream(this.resource, this.fileFormat);
			this.clip = AudioSystem.getClip();
			this.clip.open(this.audioInputStream);
			this.controls = AbstractAudio.extractControls(this.clip);
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}

	@Override
	public void close() {
		
		this.stop();
		this.clip.flush();
		this.clip.close();
		this.controls.clear();
		
		try {
			
			this.audioInputStream.close();
			
		} catch(IOException exception) {}
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
	public boolean ends() {
		
		return this.clip.getMicrosecondPosition() >= this.clip.getMicrosecondLength();
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
