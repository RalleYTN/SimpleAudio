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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Records audio from an input device.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.0.0
 */
public class Recorder {

	private AudioFormat audioFormat;
	private FileFormat fileFormat;
	private TargetDataLine line;
	private AudioInputStream input;
	private boolean running;
	private long recordingStartTime;
	
	/**
	 * Initializes the {@linkplain Recorder} with {@link FileFormat#WAV} and an audio format with
	 * {@link AudioFormat.Encoding#PCM_SIGNED}, a sample rate of {@code 44100.0F}, a sample size
	 * of {@code 16}, {@code 2} channels, a frame size of {@code 4}, a frame rate of {@code 44100}
	 * and little endian.
	 * @throws AudioException if something went wrong while initializing the recorder
	 * @since 1.0.0
	 */
	public Recorder() throws AudioException {
		
		this(FileFormat.AU, new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false));
	}
	
	/**
	 * @param fileFormat the file format to use
	 * @param audioFormat the audio format to use
	 * @throws AudioException if something went wrong while initializing the recorder
	 * @since 1.0.0
	 */
	public Recorder(FileFormat fileFormat, AudioFormat audioFormat) throws AudioException {
		
		if(fileFormat.isWritingSupported()) {
			
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.audioFormat);
			
			if(AudioSystem.isLineSupported(info)) {
				
				try {
					
					this.line = (TargetDataLine)AudioSystem.getLine(info);
					this.fileFormat = fileFormat;
					this.audioFormat = audioFormat;
					
				} catch(Exception exception) {
					
					throw new AudioException(exception);
				}
			}
			
		} else {
			
			throw new AudioException("The given file format does not support writing!");
		}
	}
	
	/**
	 * Starts the recording and writes the data in the given file.
	 * @param output the file to which the data will be written
	 * @throws AudioException if the line cannot be opened due to resource restrictions
	 * @since 1.2.2
	 */
	public void start(File output) throws AudioException {
		
		if(!this.running) {
			
			try {
				
				this.line.open();
				this.line.start();
				this.recordingStartTime = System.currentTimeMillis();
				this.running = true;
				
				new Thread(() -> {
					
					try {
						
						Recorder.this.input = new AudioInputStream(Recorder.this.line);
						AudioSystem.write(Recorder.this.input, Recorder.this.fileFormat.getType(), output);
					
					} catch(IOException exception) {
						
						exception.printStackTrace();
					}

				}).start();
				
			} catch(Exception exception) {
				
				throw new AudioException(exception);
			}
		}
	}

	/**
	 * Starts the recording and writes the data on the given output stream.
	 * @param output the output stream to write the data on
	 * @throws AudioException if the line cannot be opened due to resource restrictions
	 * @since 1.1.0
	 */
	public void start(OutputStream output) throws AudioException {
		
		if(!this.running) {
			
			try {
				
				this.line.open();
				this.line.start();
				this.recordingStartTime = System.currentTimeMillis();
				this.running = true;
				
				new Thread(() -> {
					
					try {
						
						Recorder.this.input = new AudioInputStream(Recorder.this.line);
						AudioSystem.write(Recorder.this.input, Recorder.this.fileFormat.getType(), output);
					
					} catch(IOException exception) {
						
						exception.printStackTrace();
					}

				}).start();
				
			} catch(Exception exception) {
				
				throw new AudioException(exception);
			}
		}
	}
	
	/**
	 * Stops the recording.
	 * @since 1.0.0
	 */
	public void stop() {
		
		this.line.flush();
		this.running = false;
		this.line.stop();
		this.line.close();
		
		if(this.input != null) {
			
			try {
				
				this.input.close();
				
			} catch(IOException exception) {}
		}
	}
	
	/**
	 * @return the used audio format
	 * @since 1.0.0
	 */
	public AudioFormat getAudioFormat() {
		
		return this.audioFormat;
	}
	
	/**
	 * @return the used file format
	 * @since 1.0.0
	 */
	public FileFormat getFileFormat() {
		
		return this.fileFormat;
	}
	
	/**
	 * @return {@code true} if the recording is running, else {@code false}
	 * @since 1.0.0
	 */
	public boolean isRunning() {
		
		return this.running;
	}
	
	/**
	 * @return the time passes since the recording started in milliseconds or {@code 0} if the recorder isn't recording
	 * @since 1.1.0
	 */
	public long getRecordingTime() {
		
		return this.running ? System.currentTimeMillis() - this.recordingStartTime : 0;
	}
}
