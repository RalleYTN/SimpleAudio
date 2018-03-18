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
package de.ralleytn.simple.audio.tests;

import static org.junit.jupiter.api.Assertions.fail;

import javax.sound.sampled.Mixer.Info;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.AbstractAudio;

class StaticMethodTest {

	@Test
	public void testGenerateSound() {
		
		try {
			
			int length = 500; // 1/2 sec
			
			// WITHOUT HARMONY
			AbstractAudio.generateSound(10, length, 100, false);
			Thread.sleep(length);
			AbstractAudio.generateSound(50, length, 100, false);
			Thread.sleep(length);
			AbstractAudio.generateSound(100, length, 100, false);
			Thread.sleep(length);
			AbstractAudio.generateSound(150, length, 100, false);
			Thread.sleep(length);
			AbstractAudio.generateSound(200, length, 100, false);
			Thread.sleep(length);
			
			// WITH HARMONY
			AbstractAudio.generateSound(10, length, 100, true);
			Thread.sleep(length);
			AbstractAudio.generateSound(50, length, 100, true);
			Thread.sleep(length);
			AbstractAudio.generateSound(100, length, 100, true);
			Thread.sleep(length);
			AbstractAudio.generateSound(150, length, 100, true);
			Thread.sleep(length);
			AbstractAudio.generateSound(200, length, 100, true);
			Thread.sleep(length);
			
		} catch(Exception exception) {
			
			fail(exception.getMessage());
		}
	}
	
	@Test
	public void testGetPorts() {
		
		try {
			
			Info[] ports = AbstractAudio.getPorts();
			
			System.out.println("Print all audio ports...");
			System.out.println();
			System.out.println("===========");
			
			for(int index = 0; index < ports.length; index++) {
				
				if(index != 0) {
					
					System.out.println("----");
				}
				
				System.out.println("Name: " + ports[index].getName());
				System.out.println("Vendor: " + ports[index].getVendor());
				System.out.println("Version: " + ports[index].getVersion());
				System.out.println("Description: " + ports[index].getDescription());
			}
			
			System.out.println("===========");
			System.out.println();
			
		} catch(Exception exception) {
			
			fail(exception.getMessage());
		}
	}
}
