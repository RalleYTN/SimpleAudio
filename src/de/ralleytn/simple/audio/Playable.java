package de.ralleytn.simple.audio;

/**
 * Implemented by everything that can be played.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
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
	 * Sets the volume.
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
	 * @return the current volume of the {@linkplain Playable}
	 * @since 1.1.0
	 */
	public float getVolume();
}
