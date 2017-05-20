package de.ralleytn.simple.audio;

import java.net.URL;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

/**
 * Interface containing all the methods a good audio implementation should have.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Audio {

	/**
	 * @since 1.0.0
	 */
	public static final int LOOP_ENDLESS = -1;
	
	/**
	 * Plays the audio from the beginning.
	 * @since 1.0.0
	 */
	public void play();
	
	/**
	 * Pauses the audio.
	 * @since 1.0.0
	 */
	public void pause();
	
	/**
	 * Resumes the audio when paused.
	 * @since 1.0.0
	 */
	public void resume();
	
	/**
	 * Stops the audio.
	 * @since 1.0.0
	 */
	public void stop();
	
	/**
	 * Loops the audio for {@code n} times.
	 * @param repetitions the number of repetitions
	 * @since 1.0.0
	 */
	public void loop(int repetitions);
	
	/**
	 * Sets the volume of the audio.
	 * @param volume new volume
	 * @since 1.0.0
	 */
	public void setVolume(float volume);
	
	/**
	 * Sets wherever the audio should be muted or not.
	 * @param mute {@code true} if it should be muted, else {@code false}
	 * @since 1.0.0
	 */
	public void setMute(boolean mute);
	
	/**
	 * Sets the position at which the audio should play.
	 * @param millisecond position in milliseconds.
	 * @since 1.0.0
	 */
	public void setPosition(long millisecond);
	
	/**
	 * Sets the position at which the audio should play.
	 * @param frame position in frames
	 * @since 1.0.0
	 */
	public void setFramePosition(long frame);
	
	/**
	 * Sets the balance.
	 * @param balance {@code -1.0F} = left speaker, {@code 1.0F} = right speaker.
	 * @since 1.0.0
	 */
	public void setBalance(float balance);
	
	/**
	 * Opens the audio.
	 * @throws AudioException if something went wrong while opening the audio
	 * @since 1.0.0
	 */
	public void open() throws AudioException;
	
	/**
	 * Closes the audio.
	 * @since 1.0.0
	 */
	public void close();
	
	/**
	 * @return the current position of the audio in milliseconds
	 * @since 1.0.0
	 */
	public long getPosition();
	
	/**
	 * @return {@code true} if the audio is muted, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isMuted();
	
	/**
	 * @return {@code true} if the audio is currently playing, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isPlaying();
	
	/**
	 * @return the current volume
	 * @since 1.0.0
	 */
	public float getVolume();
	
	/**
	 * @return the length of the audio in milliseconds
	 * @since 1.0.0
	 */
	public long getLength();
	
	/**
	 * @return the audio's balance
	 * @since 1.0.0
	 */
	public float getBalance();
	
	/**
	 * Should be invoked in a loop.
	 * @return {@code true} if the audio reached its end, else {@code false}
	 * @since 1.0.0
	 */
	public boolean ends();
	
	/**
	 * @return the current level of volume.
	 * @since 1.0.0
	 */
	public float getLevel();
	
	/**
	 * @return the file format
	 * @since 1.0.0
	 */
	public FileFormat getFileFormat();
	
	/**
	 * @return a copy of the map with all the controls
	 * @since 1.0.0
	 */
	public Map<String, Control> getControls();
	
	/**
	 * @return the audio format
	 * @since 1.0.0
	 */
	public AudioFormat getAudioFormat();
	
	/**
	 * @return the buffer size
	 * @since 1.0.0
	 */
	public int getBufferSize();
	
	/**
	 * @return the location of the resource
	 * @since 1.0.0
	 */
	public URL getResource();
	
	/**
	 * @return the length of the audio in frames
	 * @since 1.0.0
	 */
	public long getFrameLength();
	
	/**
	 * @return the current position of the audio in frames.
	 * @since 1.0.0
	 */
	public long getFramePosition();
	
	/**
	 * Loops the audio endlessly.
	 * @since 1.0.0
	 */
	default public void loop() {
		
		this.loop(Audio.LOOP_ENDLESS);
	}
	
	/**
	 * Obtains an array of mixer info objects that represents the set of audio mixers that are
	 * currently installed on the system.
	 * @return an array of info objects for the currently installed mixers. If no mixers are available on the system, an array of length 0 is returned
	 * @since 1.0.0
	 */
	public static Mixer.Info[] getPorts() {

		return AudioSystem.getMixerInfo();
	}
	
	/**
	 * Generates a single sound
	 * @param hz the sound's frequency
	 * @param msecs the length of the sound in milliseconds
	 * @param volume the volume of the sound (0 - 100)
	 * @param addHarmonic adds a sinus curve to the sound
	 * @since 1.0.0
	 */
	public static void generateSound(int hz, int msecs, int volume, boolean addHarmonic) {

    	new Thread(() -> {
    		
 			try {
				
				float frequency = 44100;
    	    	byte[] buffer;
    	    	AudioFormat format;
    	    	    
    	    	if(addHarmonic) {
    	    	    	
    	    		buffer = new byte[2];
    	    	    format = new AudioFormat(frequency, 8, 2, true, false);
    	    	      
    	    	} else {
    	    	    	
    	    	    buffer = new byte[1];
    	    	    format = new AudioFormat(frequency, 8, 1, true, false);
    	    	}
    	    	    
    	    	SourceDataLine source = AudioSystem.getSourceDataLine(format);
    	    	source = AudioSystem.getSourceDataLine(format);
    	    	source.open(format);
    	    	source.start();
    	    	    
    	    	for(int index = 0; index < msecs * frequency / 1000; index++){
    	    	    	
    	    	    double angle = index / (frequency / hz) * 2.0 * Math.PI;
    	    	    buffer[0] = (byte)(Math.sin(angle) * volume);

    	    	    if(addHarmonic) {
    	    	    	  
    	    	    	double angle2 = index / (frequency / hz) * 2.0 * Math.PI;
    	    	        buffer[1] = (byte)(Math.sin(2 * angle2) * volume * 0.6);
    	    	        source.write(buffer, 0, 2);
    	    	        
    	    	    } else {
    	    	    	  
    	    	    	source.write(buffer, 0, 1);
    	    	    }
    	    	}
    	    	    
    	    	source.drain();
    	    	source.stop();
    	    	source.close();
				
			} catch(LineUnavailableException exception) {
				
				exception.printStackTrace();
			}
    		
    	}).start();
    }
}
