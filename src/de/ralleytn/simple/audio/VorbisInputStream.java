package de.ralleytn.simple.audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.vorbis.VorbisStream;

/**
 * Wraps a {@linkplain VorbisStream}.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.2.2
 * @since 1.2.2
 */
public class VorbisInputStream extends InputStream {

	private VorbisStream source;
	
	/**
	 * @param source the instance of {@linkplain VorbisStream} to wrap
	 * @since 1.2.2
	 */
	public VorbisInputStream(VorbisStream source) {
		
		this.source = source;
	}
	
	@Override
	public int read(byte[] buffer) throws IOException {

		return this.read(buffer, 0, buffer.length);
	}
	
	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException {

		try {
			
			return this.source.readPcm(buffer, offset, length);
			
		} catch(EndOfOggStreamException exception) {}
		
		return -1;
	}
	
	@Override
	public int read() throws IOException {
		
		return 0;
	}
	
	/**
	 * @return the {@linkplain AudioFormat} of the wrapped {@linkplain VorbisStream}.
	 * @since 1.2.2
	 */
	public AudioFormat getAudioFormat() {
		
		return new AudioFormat(this.source.getIdentificationHeader().getSampleRate(), 16, this.source.getIdentificationHeader().getChannels(), true, true);
	}
}
