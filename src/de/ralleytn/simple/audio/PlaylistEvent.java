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
 * Represents a playlist event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.2.0
 */
public class PlaylistEvent {

	private Playlist playlist;
	private Object oldVal;
	private Object newVal;
	private Type type;
	
	/**
	 * @param playlist the {@linkplain Playlist} on which the event happened
	 * @param type the type of event this is
	 * @since 1.2.0
	 */
	public PlaylistEvent(Playlist playlist, Type type) {
		
		this.playlist = playlist;
		this.type = type;
	}
	
	/**
	 * @param playlist the {@linkplain Playlist} on which the event happened
	 * @param type the type of event this is
	 * @param oldVal the value before the event happened
	 * @param newVal the value after the event happened
	 * @since 1.2.0
	 */
	public PlaylistEvent(Playlist playlist, Type type, Object oldVal, Object newVal) {
		
		this.playlist = playlist;
		this.type = type;
		this.oldVal = oldVal;
		this.newVal = newVal;
	}
	
	/**
	 * @return the {@linkplain Playlist} on which the event happened
	 * @since 1.2.0
	 */
	public Playlist getPlaylist() {
		
		return this.playlist;
	}
	
	/**
	 * @return the type of event this is
	 * @since 1.2.0
	 */
	public Type getType() {
		
		return this.type;
	}
	
	/**
	 * @return the value before the event happened
	 * @since 1.2.0
	 */
	public Object getOldValue() {
		
		return this.oldVal;
	}
	
	/**
	 * @return the value after the event happened
	 * @since 1.2.0
	 */
	public Object getNewValue() {
		
		return this.newVal;
	}
	
	/**
	 * Represents a type that a playlist event can be.
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 1.2.0
	 * @since 1.2.0
	 */
	public static enum Type {
		
		/**
		 * When the currently selected track in a {@linkplain Playlist} reached its end
		 * @since 1.2.0
		 */
		TRACK_REACHED_END,
		
		/**
		 * When the track in a {@linkplain Playlist} changed
		 * @since 1.2.0
		 */
		TRACK_CHANGED,
		
		/**
		 * When the volume of a {@linkplain Playlist} changed
		 * @since 1.2.0
		 */
		VOLUME_CHANGED,
		
		/**
		 * When a currently not playing {@linkplain Playlist} was started
		 * @since 1.2.0
		 */
		STARTED,
		
		/**
		 * When a currently playing {@linkplain Playlist} was stopped
		 * @since 1.2.0
		 */
		STOPPED,
		
		/**
		 * When a playing {@linkplain Playlist} was paused
		 * @since 1.2.0
		 */
		PAUSED,
		
		/**
		 * When a paused {@linkplain Playlist} was resumed
		 * @since 1.2.0
		 */
		RESUMED,
		
		/**
		 * When the method {@link Playlist#setMute(boolean)}
		 * @since 1.2.0
		 */
		MUTE_CHANGED;
	}
}
