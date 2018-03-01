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
 * Represents an audio event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.1.0
 */
public class AudioEvent {

	private Audio audio;
	private Type type;
	private Object oldVal;
	private Object newVal;
	
	/**
	 * @param audio the audio on which the event happened
	 * @param type the type of event
	 * @param oldVal value the audio had before this event happened
	 * @param newVal value after this event happened
	 * @since 1.2.0
	 */
	public AudioEvent(Audio audio, Type type, Object oldVal, Object newVal) {
		
		this.audio = audio;
		this.type = type;
		this.oldVal = oldVal;
		this.newVal = newVal;
	}
	
	/**
	 * @param audio the audio on which the event happened
	 * @param type the type of event
	 * @since 1.1.0
	 */
	public AudioEvent(Audio audio, Type type) {
		
		this.audio = audio;
		this.type = type;
	}
	
	/**
	 * @return the audio on which the event happened
	 * @since 1.1.0
	 */
	public Audio getAudio() {
		
		return this.audio;
	}
	
	/**
	 * @return the type of event
	 * @since 1.1.0
	 */
	public Type getType() {
		
		return this.type;
	}
	
	/**
	 * @return value the audio had before this event happened
	 * @since 1.2.0
	 */
	public Object getOldValue() {
		
		return this.oldVal;
	}
	
	/**
	 * @return value after this event happened
	 * @since 1.2.0
	 */
	public Object getNewValue() {
		
		return this.newVal;
	}
	
	/**
	 * Represents a type that an audio event can be.
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 2.0.0
	 * @since 1.1.0
	 */
	public static enum Type {

		/** 
		 * When the audio was started
		 * @since 1.1.0
		 */
		STARTED,
		
		/**
		 * When the audio was stopped after being started
		 * @since 1.1.0
		 */
		STOPPED,
		
		/**
		 * When the audio was paused after being started
		 * @since 1.1.0
		 */
		PAUSED,
		
		/**
		 * When the audio was resumed after being paused
		 * @since 1.1.0
		 */
		RESUMED,
		
		/**
		 * When the audio reached it's end
		 * @since 1.1.0
		 */
		REACHED_END,
		
		/**
		 * When the audio was opened
		 * @since 1.1.0
		 */
		OPENED,
		
		/**
		 * When the audio was closed
		 * @since 1.1.0
		 */
		CLOSED,
		
		/**
		 * When the volume of the audio changed
		 * @since 1.2.0
		 */
		VOLUME_CHANGED,
		
		/**
		 * When the position of the audio changed
		 * @since 1.2.0
		 */
		POSITION_CHANGED,
		
		/**
		 * When the method {@link Audio#setMute(boolean)} was called
		 * @since 1.2.0
		 */
		MUTE_CHANGED;
	}
}
