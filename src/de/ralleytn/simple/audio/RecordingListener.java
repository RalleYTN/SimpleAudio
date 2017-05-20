package de.ralleytn.simple.audio;

/**
 * Listens to a {@linkplain Recorder}.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public interface RecordingListener {

	/**
	 * Is called everytime a small amount of data was recorded.
	 * @param recordedData the recorded data
	 * @param length length of the actual recorded data
	 * @since 1.0.0
	 */
	public void recorded(byte[] recordedData, int length);
}
