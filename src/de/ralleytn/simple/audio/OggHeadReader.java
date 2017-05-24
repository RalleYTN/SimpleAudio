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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads the head informations from an Ogg Vorbis file as described <a href="https://xiph.org/vorbis/doc/Vorbis_I_spec.html">here</a>.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.2.0
 * @since 1.2.0
 */
public class OggHeadReader implements HeadReader {

	@Override
	public Map<String, ?> read(URL resource) throws IOException {
		
		Map<String, Object> headers = new HashMap<>();
		
		try(InputStream in = resource.openStream()) {
			
			int data = -1;
			boolean currentlyInHeader = false;
			int header = -1;
			int identificationHeaderReadBytes = 0;
			int vendorLengthBytesRead = 0;
			int userCommentListLengthBytesRead = 0;
			int userCommentListEntryLengthBytesRead = 0;
			long vendorLength = -1;
			long userCommentListLength = -1;
			long userCommentListIndex = 0;
			long userCommentListEntryLength = -1;
			List<Integer> readData = new ArrayList<>();
			List<Integer> vendor = new ArrayList<>();
			List<Integer> userCommentListEntry = new ArrayList<>();
			boolean vendorRead = false;
			int buffer = 1;
			int buffer2 = 1;
			
			while((data = in.read()) != -1) {
				
				if(!currentlyInHeader && readData.size() > 4) {
					
					int b1 = readData.get(readData.size() - 5);
					int b2 = readData.get(readData.size() - 4);
					int b3 = readData.get(readData.size() - 3);
					int b4 = readData.get(readData.size() - 2);
					int b5 = readData.get(readData.size() - 1);
					int b6 = data;
					
					if(b1 == 0x76 && b2 == 0x6F && b3 == 0x72 && b4 == 0x62 && b5 == 0x69 && b6 == 0x73) {
						
						currentlyInHeader = true;
						header++;
					}
					
				} else {
					
					if(header == 0) {
						
						identificationHeaderReadBytes++;
						
						if(identificationHeaderReadBytes == 23) {
							
							headers.put("ogg.framing_flag", ((data >> 7) & 0b1) == 1);
							headers.put("ogg.blocksize_1", readData.get(readData.size() - 1) & 0b1111);
							headers.put("ogg.blocksize_0", (readData.get(readData.size() - 1) >> 4) & 0b1111);
							headers.put("ogg.bitrate_minimum", BinaryUtils.getSignedInteger(readData.get(readData.size() - 2), readData.get(readData.size() - 3), readData.get(readData.size() - 4), readData.get(readData.size() - 5)));
							headers.put("ogg.bitrate_nominal", BinaryUtils.getSignedInteger(readData.get(readData.size() - 6), readData.get(readData.size() - 7), readData.get(readData.size() - 8), readData.get(readData.size() - 9)));
							headers.put("ogg.bitrate_maximum", BinaryUtils.getSignedInteger(readData.get(readData.size() - 10), readData.get(readData.size() - 11), readData.get(readData.size() - 12), readData.get(readData.size() - 13)));
							headers.put("ogg.audio_sample_rate", BinaryUtils.getUnsignedInteger(readData.get(readData.size() - 14), readData.get(readData.size() - 15), readData.get(readData.size() - 16), readData.get(readData.size() - 17)));
							headers.put("ogg.audio_channels", readData.get(readData.size() - 18));
							headers.put("ogg.vorbis_version", BinaryUtils.getUnsignedInteger(readData.get(readData.size() - 19), readData.get(readData.size() - 20), readData.get(readData.size() - 21), readData.get(readData.size() - 22)));
							currentlyInHeader = false;
						}
						
					} else if(header == 1) {
						
						if(!vendorRead) {
							
							if(vendorLengthBytesRead < 4) {
								
								vendorLengthBytesRead++;
								
							} else {
								
								if(vendorLength == -1) {
									
									vendorLength = BinaryUtils.getUnsignedInteger(readData.get(readData.size() - buffer), readData.get(readData.size() - (buffer + 1)), readData.get(readData.size() - (buffer + 2)), readData.get(readData.size() - (buffer + 3)));
									buffer++;
								}
								
								if(vendor.size() < vendorLength) {
									
									vendor.add(data);
									
								} else {
									
									headers.put("ogg.vendor", new String(BinaryUtils.toByteArray(vendor), "UTF-8"));
									vendorRead = true;
								}
							}
							
						} else {

							if(userCommentListLengthBytesRead < 4) {
								
								userCommentListLengthBytesRead++;
								
							} else {
								
								if(userCommentListLength == -1) {

									userCommentListLength = BinaryUtils.getUnsignedInteger(readData.get(readData.size() - buffer), readData.get(readData.size() - (buffer + 1)), readData.get(readData.size() - (buffer + 2)), readData.get(readData.size() - (buffer + 3)));
								}
								
								if(userCommentListIndex < userCommentListLength) {
									
									if(userCommentListEntryLengthBytesRead < 4) {
										
										userCommentListEntryLengthBytesRead++;
										
									} else {
										
										if(userCommentListEntryLength == -1) {

											userCommentListEntryLength = BinaryUtils.getUnsignedInteger(readData.get(readData.size() - buffer), readData.get(readData.size() - (buffer + 1)), readData.get(readData.size() - (buffer + 2)), readData.get(readData.size() - (buffer + 3)));
											buffer++;
										}
				
										if(userCommentListEntry.size() < userCommentListEntryLength) {
											
											userCommentListEntry.add(readData.get(readData.size() - buffer2));
											
										} else {
											
											String comment = new String(BinaryUtils.toByteArray(userCommentListEntry), "UTF-8");
											String[] parts = comment.split("=");
											headers.put(parts[0], parts[1]);
											
											userCommentListEntryLengthBytesRead = 0;
											userCommentListEntryLength = -1;
											userCommentListEntry.clear();
											userCommentListIndex++;
											buffer2++;
										}
									}
								}
							}
						}
					}
				}
				
				readData.add(data);
			}
		}
		
		return headers;
	}
}
