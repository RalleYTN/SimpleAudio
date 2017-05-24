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
import java.util.List;

final class BinaryUtils {

	private BinaryUtils() {}
	
	static final long getUnsignedInteger(int o1, int o2, int o3, int o4) {
		
		return BinaryUtils.getSignedInteger(o1, o2, o3, o4) & 0xFFFFFFFFL;
	}
	
	static final int getSignedInteger(int o1, int o2, int o3, int o4) {
		
		return ((o1 & 0xFF) << 24) | ((o2 & 0xFF) << 16) | ((o3 & 0xFF) << 8) | (o4 & 0xFF);
	}
	
	static final byte[] toByteArray(List<Integer> integers) throws IOException {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			integers.forEach(buffer::write);
			return buffer.toByteArray();
		}
	}
}
