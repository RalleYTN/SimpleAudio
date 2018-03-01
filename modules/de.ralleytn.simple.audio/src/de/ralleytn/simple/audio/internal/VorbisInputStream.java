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

package de.ralleytn.simple.audio.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;

import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.vorbis.VorbisStream;

/**
 * Wraps a {@linkplain VorbisStream}.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
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
			
		// catch is empty because this code was copied and throwing a RuntimeException could break it
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
