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
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * Never loads the entire audio data into the RAM. Good for music and long audio.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.0.0
 */
public class StreamedAudio extends AbstractAudio {

	private SourceDataLine line;
	private boolean playing;
	private boolean looping;
	private boolean pausedWhileLooping;
	private ExecutorService servicePlay;
	private Runnable actionPlay = new ActionPlay();
	private long microsecondLength;
	private long frameLength;
	private Object object = new Object();
	
	/**
	 * @param file name of the resource file
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(String file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain File}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(File file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param file the resource as {@linkplain Path}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(Path file) throws AudioException {
		
		super(file);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(String zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(File zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(Path zip, String entry) throws AudioException {
		
		super(zip, entry);
	}
	
	/**
	 * @param url the resource
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(URL url) throws AudioException {
		
		super(url);
	}
	
	/**
	 * @param uri the resource as {@linkplain URI}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public StreamedAudio(URI uri) throws AudioException {
		
		super(uri);
	}
	
	@Override
	public void play() {
		
		if(this.playing || this.paused) {
			
			this.stop();
		}
		
		this.servicePlay = Executors.newSingleThreadExecutor();
		this.servicePlay.execute(this.actionPlay);
		this.playing = true;
		this.trigger(AudioEvent.Type.STARTED);
	}

	@Override
	public void pause() {
		
		if(this.playing && !this.paused) {
			
			this.paused = true;
			this.playing = false;

			if(this.looping) {
				
				this.pausedWhileLooping = true;
			}
			
			this.trigger(AudioEvent.Type.PAUSED);
		}
	}

	@Override
	public void resume() {
		
		if(this.paused) {
			
			this.playing = true;
			
			if(this.pausedWhileLooping) {
				
				this.pausedWhileLooping = false;
				this.looping = true;
			}
			
	        this.paused = false;
	        
	        synchronized(this.object) {
	        	
	            this.object.notifyAll();  
	        }
	        
	        this.trigger(AudioEvent.Type.RESUMED);
	        
		} else {
			
			this.play();
		}
	}

	@Override
	public void stop() {
		
		this.playing = false;
		this.looping = false;
		this.pausedWhileLooping = false;
		
		if(this.paused) {
			
	        synchronized(this.object) {
	        	
	            this.object.notifyAll();  
	        }
	        
	        this.paused = false;
		}
		
		this.setPosition(0);
		this.trigger(AudioEvent.Type.STOPPED);
	}

	@Override
	public void loop(int repetitions) {
		
		if(this.playing || this.paused) {
			
			this.stop();
		}
		
		this.servicePlay = Executors.newSingleThreadExecutor();
		this.servicePlay.execute(new ActionLoop(repetitions));
		this.playing = true;
		this.looping = true;
	}
	
	@Override
	public void setFramePosition(long frame) {
		
		try {

			long oldVal = this.line.getLongFramePosition();
			
			if(this.playing) {
				
				this.pause();
			}
			
			if(frame < this.frameLength) {
				
				this.reset();
			}
			
			byte[] buffer = new byte[this.audioInputStream.getFormat().getFrameSize()];
			long currentFrame = 0;
			
			while(currentFrame < frame) {

				this.audioInputStream.read(buffer);
				currentFrame++;
			}
			
			if(this.paused) {
				
				StreamedAudio.this.line.start();
				this.resume();
			}
			
			this.trigger(AudioEvent.Type.POSITION_CHANGED, oldVal, frame);
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
		}
	}

	@Override
	public void setPosition(long millisecond) {

		float frameRate = this.audioInputStream.getFormat().getFrameRate() / 1000.0F;
		long framePosition = (long)(frameRate * millisecond);
		this.setFramePosition(framePosition);
	}

	@Override
	public void open() throws AudioException {
		
		try {
			
			this.audioInputStream = Audio.getAudioInputStream(this.resource);
			this.microsecondLength = (long)(1000000 * (this.audioInputStream.getFrameLength() / this.audioInputStream.getFormat().getFrameRate()));
			this.frameLength = this.audioInputStream.getFrameLength();
			
			if(this.microsecondLength < 0) {
				
				byte[] buffer = new byte[4096];
				int readBytes = 0;

				while((readBytes = this.audioInputStream.read(buffer)) != -1) {
					
					this.frameLength += readBytes;
				}

				this.frameLength /= this.audioInputStream.getFormat().getFrameSize();
				this.audioInputStream.close();
				this.audioInputStream = Audio.getAudioInputStream(this.resource);
				this.microsecondLength = (long)(1000000 * (frameLength / this.audioInputStream.getFormat().getFrameRate()));
			}
			
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.audioInputStream.getFormat());
			this.line = (SourceDataLine)AudioSystem.getLine(info);
			this.line.open(this.audioInputStream.getFormat());
			this.controls = AbstractAudio.extractControls(this.line, this.controls);
			this.open = true;
			this.trigger(AudioEvent.Type.OPENED);
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}

	@Override
	public void close() {
		
		this.playing = false;
		this.paused = false;
		this.pausedWhileLooping = false;
		
		if(this.line != null) {
			
			this.line.flush();
			this.line.close();
			this.line = null;
		}
		
		try {
			
			if(this.audioInputStream != null) {
				
				this.audioInputStream.close();
				this.audioInputStream = null;
			}
			
		} catch(IOException exception) {}
		
		this.open = false;
		this.trigger(AudioEvent.Type.CLOSED);
	}
	
	@Override
	public long getFrameLength() {
		
		return this.frameLength;
	}
	
	@Override
	public long getLength() {
		
		return this.microsecondLength / 1000;
	}

	@Override
	public long getPosition() {
		
		return this.line.getMicrosecondPosition() / 1000;
	}

	@Override
	public boolean isPlaying() {
		
		return this.playing;
	}

	@Override
	public float getLevel() {
		
		return this.line.getLevel();
	}

	@Override
	public AudioFormat getAudioFormat() {
		
		return this.audioInputStream.getFormat();
	}

	@Override
	public int getBufferSize() {
		
		return this.line.getBufferSize();
	}
	
	@Override
	public long getFramePosition() {
		
		return this.line.getLongFramePosition();
	}
	
	private void reset() throws AudioException {
		
		this.close();
		this.open();
	}
	
	private class ActionLoop implements Runnable {

		private int repetitions;
		
		public ActionLoop(int repetitions) {
			
			this.repetitions = repetitions;
		}
		
		@Override
		public void run() {
			
			int currentRepetition = 0;
			
			try {
				
				while((currentRepetition < this.repetitions || this.repetitions == Audio.LOOP_ENDLESS) && StreamedAudio.this.looping) {
					
					StreamedAudio.this.line.start();
					byte[] buffer = new byte[StreamedAudio.this.audioInputStream.getFormat().getFrameSize()];
					int read = 0;
					
					while(!StreamedAudio.this.servicePlay.isShutdown()) {
						
						if(!StreamedAudio.this.paused) {
							
							while((read = StreamedAudio.this.audioInputStream.read(buffer)) > -1 && StreamedAudio.this.looping && !StreamedAudio.this.paused) {
								
								StreamedAudio.this.line.write(buffer, 0, read);
							}
							
							if(!StreamedAudio.this.paused) {
								
								StreamedAudio.this.trigger(AudioEvent.Type.REACHED_END);
								StreamedAudio.this.reset();
								currentRepetition++;
								break;
							}
							
						} else {
							
							while(StreamedAudio.this.paused) {
								
		                        synchronized(StreamedAudio.this.object){
		                            
		                        	try {
		                        		
										StreamedAudio.this.object.wait();
										
									} catch(InterruptedException exception) {}
		                        }                           
		                    }
						}
					}
				}
				
				StreamedAudio.this.servicePlay.shutdown();
				
			} catch(Exception exception) {
				
				exception.printStackTrace();
			}
		}
	}
	
	private class ActionPlay implements Runnable {

		@Override
		public void run() {
			
			StreamedAudio.this.line.start();
			byte[] buffer = new byte[StreamedAudio.this.audioInputStream.getFormat().getFrameSize()];
			int read = 0;
			
			while(!StreamedAudio.this.servicePlay.isShutdown()) {
				
				if(!StreamedAudio.this.paused) {
					
					try {

						while(StreamedAudio.this.playing && !StreamedAudio.this.paused && (read = StreamedAudio.this.audioInputStream.read(buffer)) > -1) {
							
							StreamedAudio.this.line.write(buffer, 0, read);
						}
						
						if(!StreamedAudio.this.paused) {
							
							StreamedAudio.this.trigger(AudioEvent.Type.REACHED_END);
							StreamedAudio.this.reset();
							StreamedAudio.this.servicePlay.shutdown();
						}
						
					} catch(Exception exception) {
						
						exception.printStackTrace();
					}
					
				} else {
					
					while(StreamedAudio.this.paused) {
						
                        synchronized(StreamedAudio.this.object){
                            
                        	try {
                        		
								StreamedAudio.this.object.wait();
								
							} catch(InterruptedException exception) {}
                        }                           
                    }
				}
			}
		}
	}
}
