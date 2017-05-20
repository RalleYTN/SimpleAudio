package de.ralleytn.simple.audio;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a file format for audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum FileFormat {

	/**
	 * Wavesound(.wav)
	 * @since 1.0.0
	 */
	WAV(true, "wav"),
	
	/**
	 *  MPEG-1 Audio Layer III or MPEG-2 Audio Layer III(.mp3)
	 * @since 1.0.0
	 */
	MP3(false, "mp3"),
	
	/**
	 * Ogg Vorbis(.ogg, .oga)
	 * @since 1.0.0
	 */
	OGG(false, "ogg", "oga"),
	
	/**
	 * Audio Interchange File Format(.aiff)
	 * @since 1.0.0
	 */
	AIFF(true, "aif", "aiff"),
	
	/**
	 * Au Sound File(.au)
	 * @since 1.0.0
	 */
	AU(true, "au");
	
	private List<String> associatedFileExtensions;
	private boolean writingSupported;
	
	private FileFormat(boolean writingSupported, String... associatedFileExtensions) {
		
		this.associatedFileExtensions = new ArrayList<>();
		this.writingSupported = writingSupported;
		
		for(String extension : associatedFileExtensions) {
			
			this.associatedFileExtensions.add(extension);
		}
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
