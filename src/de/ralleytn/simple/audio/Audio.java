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

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.OnDemandUrlStream;
import de.jarnbjo.vorbis.VorbisStream;
import de.ralleytn.simple.audio.internal.VorbisInputStream;

/**
 * Interface containing all the methods a good audio implementation should have.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.0.0
 */
public interface Audio extends Playable {

	/**
	 * @since 1.0.0
	 */
	public static final int LOOP_ENDLESS = -1;
	
	/**
	 * Loops the audio for {@code n} times.
	 * @param repetitions the number of repetitions
	 * @since 1.0.0
	 */
	public void loop(int repetitions);
	
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
	 * @return {@code true} if the audio resource is currently open, else {@code false}
	 * @since 1.1.0
	 */
	public boolean isOpen();
	
	/**
	 * @return the audio file's headers
	 * @since 1.1.0
	 */
	public Map<?, ?> getHeaders();
	
	/**
	 * Adds an {@linkplain AudioListener} to the audio.
	 * @param listener the listener to add
	 * @since 1.1.0
	 */
	public void addAudioListener(AudioListener listener);
	
	/**
	 * Removes an {@linkplain AudioListener} from the audio.
	 * @param listener the listener to remove
	 * @since 1.1.0
	 */
	public void removeAudioListener(AudioListener listener);
	
	/**
	 * @return a list with all the {@linkplain AudioListener}s fo this audio
	 * @since 1.1.0
	 */
	public List<AudioListener> getAudioListeners();
	
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
	 * @return The default audio format used in the Java Sound API
	 * @since 1.2.2
	 */
	public static AudioFormat getDefaultAudioFormat() {
		
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	}
	
	/**
	 * @param resource the resource from which you want the {@linkplain AudioInputStream} from
	 * @return the {@linkplain AudioInputStream} from the resource
	 * @throws AudioException if something went wrong while retrieving the {@linkplain AudioInputStream}
	 * @since 1.1.0
	 */
	public static AudioInputStream getAudioInputStream(URL resource) throws AudioException {
		
		AudioInputStream audioInputStream = null;
		FileFormat fileFormat = FileFormat.getFormatByName(resource.toExternalForm());
		
		try {
			
			switch(fileFormat) {
				case MP3:
					audioInputStream = AudioSystem.getAudioInputStream(resource);
					AudioFormat baseFormat = audioInputStream.getFormat();
					AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
					audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
					break;
	
				case OGG:
					LogicalOggStream loggs = (LogicalOggStream)new OnDemandUrlStream(resource).getLogicalStreams().iterator().next();
					
					if(!loggs.getFormat().equals(LogicalOggStream.FORMAT_VORBIS)) {
						
						throw new AudioException("Not a plain Ogg/Vorbis audio file!");
					}
					
					VorbisInputStream vis = new VorbisInputStream(new VorbisStream(loggs));
					audioInputStream = new AudioInputStream(vis, vis.getAudioFormat(), -1L);
					break;
	
				case AU:
				case AIFC:
				case SND:
				case AIFF:
				case WAV:
					audioInputStream = AudioSystem.getAudioInputStream(resource);
					break;
					
				default:
					throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
		
		return audioInputStream;
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
