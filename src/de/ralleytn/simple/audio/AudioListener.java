package de.ralleytn.simple.audio;

/**
 * Listens to audio events.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.1.0
 */
@FunctionalInterface
public interface AudioListener {

	/**
	 * Is called everytime an event happened.
	 * @param event the event data
	 * @since 1.1.0
	 */
	public void update(AudioEvent event);
}
