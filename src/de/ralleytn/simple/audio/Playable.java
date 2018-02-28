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

/**
 * Implemented by everything that can be played.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.1.0
 */
public interface Playable {

	/**
	 * Plays from the beginning.
	 * @since 1.1.0
	 */
	public void play();
	
	/**
	 * Stops the {@linkplain Playable}.
	 * @since 1.1.0
	 */
	public void stop();
	
	/**
	 * Pauses the {@linkplain Playable}.
	 * @since 1.1.0
	 */
	public void pause();
	
	/**
	 * Resumes when paused.
	 * @since 1.1.0
	 */
	public void resume();
	
	/**
	 * Sets the volume. The given value is in decibel and is more like an amplifier to the volume
	 * and not the volume itself.
	 * @param volume the new volume
	 * @since 1.1.0
	 */
	public void setVolume(float volume);
	
	/**
	 * Sets wherever the {@linkplain Playable} should be muted or not.
	 * @param mute {@code true} if it should be muted, else {@code false}
	 * @since 1.1.0
	 */
	public void setMute(boolean mute);
	
	/**
	 * @return {@code true} if the {@linkplain Playable} is muted, else {@code false}
	 * @since 1.1.0
	 */
	public boolean isMuted();
	
	/**
	 * @return {@code true} if the {@linkplain Playable} is currently playing, else {@code false}
	 * @since 1.1.0
	 */
	public boolean isPlaying();
	
	/**
	 * @return {@code true} if the {@linkplain Playable} is currently paused, else {@code false}
	 * @since 1.2.0
	 */
	public boolean isPaused();
	
	/**
	 * @return the current volume of the {@linkplain Playable}
	 * @since 1.1.0
	 */
	public float getVolume();
}
