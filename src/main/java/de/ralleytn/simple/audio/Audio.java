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
import javax.sound.sampled.Control;

// ==== 15.03.2018 | Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// -	Moved everything static to AbstractAudio
// ====

/**
 * Interface containing all the methods a good audio implementation should have.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.1
 * @since 1.0.0
 */
public interface Audio extends Playable {
	
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
		
		this.loop(AbstractAudio.LOOP_ENDLESS);
	}
}
