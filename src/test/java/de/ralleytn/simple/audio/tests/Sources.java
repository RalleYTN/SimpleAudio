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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

class Sources {

	private static final String RESOURCE_LOCATION = "de/ralleytn/simple/audio/tests/";
	private static final String ZIP_FILE_NAME = "audio.zip";
	
	private final String name;
	
	public Sources(String name) {
		
		this.name = name;
	}
	
	private static final File getDirectory() {
		
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File directory = new File(tempDir, "simple-audio-test");
		
		if(!directory.exists()) {
			
			directory.mkdirs();
		}
		
		return directory;
	}
	
	public final String getName() {
		
		return this.name;
	}
	
	public final Path getZipPath() {
		
		return new File(getDirectory(), ZIP_FILE_NAME).toPath();
	}
	
	public final String getZipFileName() {
		
		return new File(getDirectory(), ZIP_FILE_NAME).getAbsolutePath();
	}
	
	public final File getZipFile() throws IOException {
		
		return copy(ZIP_FILE_NAME, getDirectory());
	}
	
	public final File getFile() throws IOException {

		return copy(this.name, getDirectory());
	}
	
	public final Path getPath() {
		
		return new File(getDirectory(), this.name).toPath();
	}
	
	public final String getFileName() {
		
		return new File(getDirectory(), this.name).getAbsolutePath();
	}
	
	public final URI getURI() throws URISyntaxException {
		
		return this.getURL().toURI();
	}
	
	public final URL getURL() {
		
		return getResource(this.name);
	}
	
	public static final URL getResource(String name) {
		
		return Sources.class.getClassLoader().getResource(String.format("%s%s", RESOURCE_LOCATION, name));
	}
	
	public static final File copy(String name, File targetDir) throws IOException {
		
		File file = new File(targetDir, name);
		
		try(InputStream in = Sources.class.getClassLoader().getResourceAsStream(String.format("%s%s", RESOURCE_LOCATION, name));
			OutputStream out = new FileOutputStream(file)) {
			
			int read = 0;
			byte[] buffer = new byte[4096];
			
			while((read = in.read(buffer)) != -1) {
				
				out.write(buffer, 0, read);
			}
		}
		
		file.deleteOnExit();
		return file;
	}
}
