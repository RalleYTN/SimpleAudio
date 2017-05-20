package de.ralleytn.simple.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * Records audio from an input device.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class Recorder {

	private AudioFormat audioFormat;
	private FileFormat fileFormat;
	private TargetDataLine line;
	private AudioInputStream audioInputStream;
	private boolean running;
	private List<RecordingListener> recordingListeners;
	
	/**
	 * Initializes the {@linkplain Recorder} with {@link FileFormat#WAV} and an audio format with
	 * {@link AudioFormat.Encoding#PCM_SIGNED}, a sample rate of {@code 44100.0F}, a sample size
	 * of {@code 16}, {@code 2} channels, a frame size of {@code 4}, a frame rate of {@code 44100}
	 * and little endian.
	 * @throws AudioException if something went wrong while initializing the recorder
	 * @since 1.0.0
	 */
	public Recorder() throws AudioException {
		
		this(FileFormat.WAV, new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false));
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
					this.recordingListeners = new ArrayList<>();
					
				} catch(Exception exception) {
					
					throw new AudioException(exception);
				}
			}
			
		} else {
			
			throw new AudioException("The given file format does not support writing!");
		}
	}
	
	/**
	 * Adds a {@linkplain RecordingListener}.
	 * @param listener the listener to add
	 * @since 1.0.0
	 */
	public void addRecordingListener(RecordingListener listener) {
		
		this.recordingListeners.add(listener);
	}
	
	/**
	 * Removes the given {@linkplain RecordingListener}.
	 * @param listener the listener to remove
	 * @since 1.0.0
	 */
	public void removeRecordingListener(RecordingListener listener) {
		
		this.recordingListeners.remove(listener);
	}
	
	/**
	 * Starts the recording.
	 * @throws AudioException if the line cannot be opened due to resource restrictions
	 * @since 1.0.0
	 */
	public void start() throws AudioException {
		
		if(!this.running) {
			
			try {
				
				this.line.open();
				this.line.start();
				this.running = true;
				
				new Thread(() -> {
					
					Recorder.this.audioInputStream = new AudioInputStream(Recorder.this.line);
					byte[] data = new byte[Recorder.this.line.getBufferSize()];
					
					while(Recorder.this.line.isOpen()) {
						
						int readBytes = Recorder.this.line.read(data, 0, data.length);
						Recorder.this.recordingListeners.forEach(element -> element.recorded(data, readBytes));
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
		this.line.stop();
		this.line.close();
		this.running = false;
	}
	
	/**
	 * Gives all used resources free.
	 * @since 1.0.0
	 */
	public void dispose() {
		
		this.stop();
		this.line = null;
		this.audioFormat = null;
		this.fileFormat = null;
		
		try {
			
			this.audioInputStream.close();
			this.audioInputStream = null;
			
		} catch(IOException exception) {}
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
	 * @return the list with all the {@linkplain RecordingListener}s
	 * @since 1.0.0
	 */
	public List<RecordingListener> getRecordingListeners() {
		
		return this.recordingListeners;
	}
}
