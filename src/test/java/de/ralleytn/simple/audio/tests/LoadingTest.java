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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.Audio;
import de.ralleytn.simple.audio.AudioException;
import de.ralleytn.simple.audio.BufferedAudio;
import de.ralleytn.simple.audio.StreamedAudio;

class LoadingTest {
	
	private static final void testAudio(Audio audio) throws AudioException {
		
		audio.open();
		audio.close();
	}
	
	// ==== BUFFERED
	
	private static final void loadBufferedFromURL(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getURL()));
		System.out.println(String.format("Name: %s, Source: URL, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromURI(Sources sources) throws AudioException, URISyntaxException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getURI()));
		System.out.println(String.format("Name: %s, Source: URI, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromFile(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getFile()));
		System.out.println(String.format("Name: %s, Source: File, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromFileName(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getFileName()));
		System.out.println(String.format("Name: %s, Source: File Name, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromPath(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getPath()));
		System.out.println(String.format("Name: %s, Source: Path, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromZipFile(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getZipFile(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP File, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromZipFileName(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getZipFileName(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP File Name, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadBufferedFromZipPath(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new BufferedAudio(sources.getZipPath(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP Path, Mode: buffered, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void testBuffered(Sources sources) throws AudioException, URISyntaxException, IOException {

		loadBufferedFromURL(sources);
		loadBufferedFromURI(sources);
		loadBufferedFromFile(sources);
		loadBufferedFromFileName(sources);
		loadBufferedFromPath(sources);
		loadBufferedFromZipFile(sources);
		loadBufferedFromZipFileName(sources);
		loadBufferedFromZipPath(sources);
	}
	
	// ==== STREAMED
	
	private static final void loadStreamedFromURL(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getURL()));
		System.out.println(String.format("Name: %s, Source: URL, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromURI(Sources sources) throws AudioException, URISyntaxException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getURI()));
		System.out.println(String.format("Name: %s, Source: URI, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromFile(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getFile()));
		System.out.println(String.format("Name: %s, Source: File, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromFileName(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getFileName()));
		System.out.println(String.format("Name: %s, Source: File Name, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromPath(Sources sources) throws AudioException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getPath()));
		System.out.println(String.format("Name: %s, Source: Path, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromZipFile(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getZipFile(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP File, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromZipFileName(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getZipFileName(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP File Name, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void loadStreamedFromZipPath(Sources sources) throws AudioException, IOException {
		
		long startTime = System.currentTimeMillis();
		testAudio(new StreamedAudio(sources.getZipPath(), sources.getName()));
		System.out.println(String.format("Name: %s, Source: ZIP Path, Mode: streamed, Time: %s ms", sources.getName(), System.currentTimeMillis() - startTime));
	}
	
	private static final void testStreamed(Sources sources) throws AudioException, URISyntaxException, IOException {

		loadStreamedFromURL(sources);
		loadStreamedFromURI(sources);
		loadStreamedFromFile(sources);
		loadStreamedFromFileName(sources);
		loadStreamedFromPath(sources);
		loadStreamedFromZipFile(sources);
		loadStreamedFromZipFileName(sources);
		loadStreamedFromZipPath(sources);
	}
	
	private static final void test(String name, boolean expectFailure) {
		
		boolean failure = false;
		
		try {
			
			Sources sources = new Sources(name);
			testBuffered(sources);
			testStreamed(sources);
			
		} catch(Exception exception) {
			
			failure = true;
			
			if(!expectFailure) {
				
				exception.printStackTrace();
				fail(exception.getMessage());
			}
		}
		
		if(expectFailure) {
			
			assertTrue(failure);
		}
	}
	
	@Test
	public void test() {
		
		System.out.println("Start loading tests...");
		System.out.println();
		System.out.println("==========");
		
		test("audio.aifc", false);
		test("audio.aiff", false);
		test("audio.au", false);
		test("audio.wav", false);
		test("audio.mp3", false);
		test("audio.ogg", false);
		test("audio.snd", false);
		test("audio.aac", true);
		test("audio.wma", true);
		test("audio.flac", true);
		
		System.out.println("==========");
		System.out.println();
	}
}
