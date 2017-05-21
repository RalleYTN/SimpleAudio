package de.ralleytn.simple.audio;

/**
 * Represents an audio event.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.1.0
 */
public class AudioEvent {

	private Audio audio;
	private Type type;
	
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
	 * Represents a type that an audio event can be.
	 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
	 * @version 1.1.0
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
		CLOSED;
	}
}
