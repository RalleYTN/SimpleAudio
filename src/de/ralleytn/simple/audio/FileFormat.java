package de.ralleytn.simple.audio;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;

/**
 * Represents a file format for audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.1.0
 * @since 1.0.0
 */
public enum FileFormat {

	/**
	 * Wavesound(.wav)
	 * @since 1.0.0
	 */
	WAV(true, AudioFileFormat.Type.WAVE, "wav"),
	
	/**
	 *  MPEG-1 Audio Layer III or MPEG-2 Audio Layer III(.mp3)
	 * @since 1.0.0
	 */
	MP3(false, null, "mp3"),
	
	/**
	 * Ogg Vorbis(.ogg, .oga)
	 * @since 1.0.0
	 */
	OGG(false, null, "ogg", "oga"),
	
	/**
	 * Audio Interchange File Format(.aiff)
	 * @since 1.0.0
	 */
	AIFF(true, AudioFileFormat.Type.AIFF, "aif", "aiff"),
	
	/**
	 * Au Sound File(.au)
	 * @since 1.0.0
	 */
	AU(true, AudioFileFormat.Type.AU, "au"),
	
	/**
	 * Audio Interchange File Format(.aifc)<br>
	 * <i>WARNING:</i> The format is not supported on all systems!
	 * @since 1.1.0
	 */
	AIFC(true, AudioFileFormat.Type.AIFC, "aifc"),
	
	/**
	 * Audio Interchange File Format(.snd)<br>
	 * <i>WARNING:</i> The format is not supported on all systems!
	 * @since 1.1.0
	 */
	SND(true, AudioFileFormat.Type.SND, "snd");
	
	private List<String> associatedFileExtensions;
	private boolean writingSupported;
	private AudioFileFormat.Type type;
	
	private FileFormat(boolean writingSupported, AudioFileFormat.Type type, String... associatedFileExtensions) {
		
		this.associatedFileExtensions = new ArrayList<>();
		this.writingSupported = writingSupported;
		this.type = type;
		
		for(String extension : associatedFileExtensions) {
			
			this.associatedFileExtensions.add(extension);
		}
	}
	
	/**
	 * @return the {@linkplain AudioFileFormat.Type} equivalent to this {@linkplain FileFormat} instance
	 * @since 1.1.0
	 */
	public AudioFileFormat.Type getType() {

		return this.type;
	}

	/**
	 * @return {@code true} if writing for the file format is supported, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isWritingSupported() {
		
		return this.writingSupported;
	}

	/**
	 * @return a list with all associated file extensions
	 * @since 1.0.0
	 */
	public List<String> getAssociatedExtensions() {
		
		return this.associatedFileExtensions;
	}
	
	/**
	 * @param name name of the file
	 * @return the {@linkplain FileFormat} instance which fits or {@code null} if no file format could be found for that name.
	 * @since 1.0.0
	 */
	public static FileFormat getFormatByName(String name) {
		
		FileFormat returnValue = null;
		
		if(name.contains(".") && !name.endsWith(".")) {
			
			String extension = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
			
			for(FileFormat format : FileFormat.values()) {
				
				if(format.getAssociatedExtensions().contains(extension)) {
					
					returnValue = format;
					break;
				}
			}
		}
		
		return returnValue;
	}
}
