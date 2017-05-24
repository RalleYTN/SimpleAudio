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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class Utils {

	private Utils() {}
	
	static final void readUntil(InputStream inputStream, int[] sequence) throws IOException {
		
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
	
	static final String readString(InputStream inputStream, int length, Charset charset) throws IOException {
		
		int[] octets = Utils.read(inputStream, length);
		return new String(Utils.toByteArray(octets), charset.name());
	}

	static final long readUnsignedInt(InputStream inputStream) throws IOException {
		
		int[] octets = Utils.read(inputStream, 4);
		return Utils.getUnsignedInteger(octets[3], octets[2], octets[1], octets[0]);
	}
	
	static final int readSignedInt(InputStream inputStream) throws IOException {
		
		int[] octets = Utils.read(inputStream, 4);
		return Utils.getSignedInteger(octets[3], octets[2], octets[1], octets[0]);
	}
	
	static final int[] read(InputStream inputStream, int length) throws IOException {
		
		int[] data = new int[length];
		
		for(int index = 0; index < data.length; index++) {
			
			data[index] = inputStream.read();
		}
		
		return data;
	}
	
	static final long getUnsignedInteger(int o1, int o2, int o3, int o4) {
		
		return Utils.getSignedInteger(o1, o2, o3, o4) & 0xFFFFFFFFL;
	}
	
	static final int getSignedInteger(int o1, int o2, int o3, int o4) {
		
		return ((o1 & 0xFF) << 24) | ((o2 & 0xFF) << 16) | ((o3 & 0xFF) << 8) | (o4 & 0xFF);
	}
	
	static final boolean getBit(int integer, int position) {
		
		return ((integer >> position) & 1) == 1;
	}
	
	static final byte[] toByteArray(List<Integer> integers) throws IOException {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			integers.forEach(buffer::write);
			return buffer.toByteArray();
		}
	}
	
	static final byte[] toByteArray(int[] integers) {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			for(int integer : integers) {
				
				buffer.write(integer);
			}
			
			return buffer.toByteArray();
			
		} catch(IOException exception) {
			
			exception.printStackTrace();
		}
		
		return null;
	}
}
