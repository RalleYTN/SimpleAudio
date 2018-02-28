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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains some utility methods.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.2.0
 */
public final class Utils {

	private Utils() {}
	
	/**
	 * Skips bytes in an {@linkplain InputStream} until it finds the given sequence.
	 * @param inputStream the {@linkplain InputStream}
	 * @param sequence the sequence that should be found
	 * @throws IOException if something went wrong while reading from the {@linkplain InputStream}
	 * @since 1.2.0
	 */
	public static final void readUntil(InputStream inputStream, int[] sequence) throws IOException {
		
		List<Integer> readBytes = new ArrayList<>();
		int readByte = -1;
		
		while((readByte = inputStream.read()) != -1) {
			
			if(readBytes.size() >= sequence.length - 1) {
				
				boolean correct = true;
				int current = 1;
				
				for(int index = sequence.length - 2; index >= 0; index--) {
					
					if(readBytes.get(readBytes.size() - current) != sequence[index]) {
						
						correct = false;
						break;
					}
					
					current++;
				}
				
				if(correct && readByte == sequence[sequence.length - 1]) {
					
					return;
				}
			}
			
			readBytes.add(readByte);
		}
		
		throw new IOException("Could not find the given sequence!");
	}
	
	/**
	 * Reads a {@linkplain String} from an {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream}
	 * @param length the length of the {@linkplain String} in bytes
	 * @param charset the charset of the {@linkplain String}
	 * @return the read {@linkplain String}
	 * @throws IOException if something went wrong while reading from the {@linkplain InputStream}
	 * @since 1.2.0
	 */
	public static final String readString(InputStream inputStream, int length, Charset charset) throws IOException {
		
		int[] octets = Utils.read(inputStream, length);
		return new String(Utils.toByteArray(octets), charset.name());
	}

	/**
	 * Reads an unsigned {@code int} from an {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream}
	 * @return the read {@code int}
	 * @throws IOException if something went wrong while reading from the {@linkplain InputStream}
	 * @since 1.2.0
	 */
	public static final long readUnsignedInt(InputStream inputStream) throws IOException {
		
		int[] octets = Utils.read(inputStream, 4);
		return Utils.getUnsignedInteger(octets[3], octets[2], octets[1], octets[0]);
	}
	
	/**
	 * Reads a signed {@code int} from an {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream}
	 * @return the read {@code int}
	 * @throws IOException if something went wrong while reading from the {@linkplain InputStream}
	 * @since 1.2.0
	 */
	public static final int readSignedInt(InputStream inputStream) throws IOException {
		
		int[] octets = Utils.read(inputStream, 4);
		return Utils.getSignedInteger(octets[3], octets[2], octets[1], octets[0]);
	}
	
	/**
	 * Reads a certain amount of bytes from an {@linkplain InputStream}.
	 * @param inputStream the {@linkplain InputStream}
	 * @param length the amount of bytes that should be read
	 * @return the read bytes as an {@code int} array
	 * @throws IOException if something went wrong while reading from the {@linkplain InputStream}
	 * @since 1.2.0
	 */
	public static final int[] read(InputStream inputStream, int length) throws IOException {
		
		int[] data = new int[length];
		
		for(int index = 0; index < data.length; index++) {
			
			data[index] = inputStream.read();
		}
		
		return data;
	}
	
	/**
	 * Builds an unsigned {@code int} with 4 bytes.
	 * @param o1 first byte
	 * @param o2 second byte
	 * @param o3 third byte
	 * @param o4 fourth byte
	 * @return the built {@code int}
	 * @since 1.2.0
	 */
	public static final long getUnsignedInteger(int o1, int o2, int o3, int o4) {
		
		return Utils.getSignedInteger(o1, o2, o3, o4) & 0xFFFFFFFFL;
	}
	
	/**
	 * Builds a signed {@code int} with 4 bytes.
	 * @param o1 first byte
	 * @param o2 second byte
	 * @param o3 third byte
	 * @param o4 fourth byte
	 * @return the built {@code int}
	 * @since 1.2.0
	 */
	public static final int getSignedInteger(int o1, int o2, int o3, int o4) {
		
		return ((o1 & 0xFF) << 24) | ((o2 & 0xFF) << 16) | ((o3 & 0xFF) << 8) | (o4 & 0xFF);
	}
	
	/**
	 * @param integer the bit chain
	 * @param position the position of the bit that you want (starting on the right at index 0)
	 * @return {@code true} = 1, {@code false} = 0
	 * @since 1.2.0
	 */
	public static final boolean getBit(int integer, int position) {
		
		return ((integer >> position) & 1) == 1;
	}
	
	/**
	 * Converts a list of {@code int}s into a {@code byte} array.
	 * @param integers the {@code int} list
	 * @return the resulting {@code byte} array
	 * @since 1.2.0
	 */
	public static final byte[] toByteArray(List<Integer> integers) {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			integers.forEach(buffer::write);
			return buffer.toByteArray();
			
		} catch(IOException exception) {
			
			// SHOULD NEVER HAPPEN!
			throw new RuntimeException(exception);
		}
	}
	
	/**
	 * Converts an array of {@code int}s into a {@code byte} array.
	 * @param integers the {@code int} array
	 * @return the resulting {@code byte} array
	 * @since 1.2.0
	 */
	public static final byte[] toByteArray(int[] integers) {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			for(int integer : integers) {
				
				buffer.write(integer);
			}
			
			return buffer.toByteArray();
			
		} catch(IOException exception) {
			
			// SHOULD NEVER HAPPEN!
			throw new RuntimeException(exception);
		}
	}
}
