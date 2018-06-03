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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.BufferedAudio;
import de.ralleytn.simple.audio.Recorder;

class RecorderTest {

	@Test
	void testRecording() {
		
		try {
			
			File output = new File("recorded.au");
			Recorder recorder = new Recorder();
			assertFalse(recorder.isRunning());
			recorder.start(output);
			assertTrue(recorder.isRunning());
			Thread.sleep(5000);
			assertTrue(recorder.getRecordingTime() >= 5000 && recorder.getRecordingTime() <= 5500);
			recorder.stop();
			assertFalse(recorder.isRunning());
			
			BufferedAudio audio = new BufferedAudio(output);
			audio.open();
			audio.close();
			
			output.delete();
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
			fail(exception.getMessage());
		}
	}
}
