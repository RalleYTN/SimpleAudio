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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import de.ralleytn.simple.audio.internal.Utils;

/**
 * Reads the head informations from an Ogg Vorbis file as described <a href="https://xiph.org/vorbis/doc/Vorbis_I_spec.html">here</a>.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.2.0
 */
public class OggHeadReader implements HeadReader {

	@Override
	public Map<String, ?> read(URL resource) throws IOException {
		
		Map<String, Object> headers = new HashMap<>();
		int[] sequence = {0x76, 0x6F, 0x72, 0x62, 0x69, 0x73};
		
		try(InputStream inputStream = resource.openStream()) {
			
			Utils.readUntil(inputStream, sequence);
			headers.put("ogg.vorbis_version", Utils.readUnsignedInt(inputStream));
			headers.put("ogg.audio_channels", inputStream.read());
			headers.put("ogg.audio_sample_rate", Utils.readUnsignedInt(inputStream));
			headers.put("ogg.bitrate_maximum", Utils.readSignedInt(inputStream));
			headers.put("ogg.bitrate_nominal", Utils.readSignedInt(inputStream));
			headers.put("ogg.bitrate_minimum", Utils.readSignedInt(inputStream));
			int blocksize = inputStream.read();
			headers.put("ogg.blocksize_0", (blocksize >> 4) & 0b1111);
			headers.put("ogg.blocksize_1", blocksize & 0b1111);
			headers.put("ogg.framing_flag", ((inputStream.read() >> 7) & 0b1) == 1);
			Utils.readUntil(inputStream, sequence);
			headers.put("ogg.vendor", Utils.readString(inputStream, (int)Utils.readUnsignedInt(inputStream), StandardCharsets.UTF_8));
			long userCommentListLength = Utils.readUnsignedInt(inputStream);
			
			for(long index = 0; index < userCommentListLength; index++) {
				
				String[] comment = Utils.readString(inputStream, (int)Utils.readUnsignedInt(inputStream), StandardCharsets.UTF_8).split("=");
				headers.put("ogg.comment." + comment[0].toLowerCase(), comment[1]);
			}
		}
		
		return headers;
	}
}
