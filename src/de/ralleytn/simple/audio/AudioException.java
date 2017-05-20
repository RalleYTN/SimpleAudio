package de.ralleytn.simple.audio;

/**
 * Is thrown when an error inside of SimpleAudio happens.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class AudioException extends Exception {

	private static final long serialVersionUID = -1269054597333986867L;

	/**
	 * @param message message
	 * @since 1.0.0
	 */
	public AudioException(String message) {
		
		super(message);
	}
	
	/**
	 * Transfers the message and stack trace from the given exception to this exception.
	 * @param exception parent exception
	 * @since 1.0.0
	 */
	public AudioException(Exception exception) {
		
		super(exception.getMessage());
		
		this.setStackTrace(exception.getStackTrace());
	}
}
