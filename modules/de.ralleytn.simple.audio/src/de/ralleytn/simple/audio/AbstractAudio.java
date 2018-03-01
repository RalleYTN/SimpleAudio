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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.EnumControl;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;

import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * Implements the {@linkplain Audio} and should be extended by all classes representing a form of playable audio.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.0.0
 */
public abstract class AbstractAudio implements Audio {

	protected volatile URL resource;
	protected volatile FileFormat fileFormat;
	protected volatile AudioInputStream audioInputStream;
	protected volatile HashMap<String, Control> controls;
	protected volatile boolean open;
	protected volatile boolean paused;
	protected volatile List<AudioListener> listeners = new ArrayList<>();
	
	/**
	 * @param file name of the resource file
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(String file) throws AudioException {
		
		try {
			
			this.resource = new File(file).toURI().toURL();
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param file the resource as {@linkplain File}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(File file) throws AudioException {
		
		try {
			
			this.resource = file.toURI().toURL();
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param file the resource as {@linkplain Path}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(Path file) throws AudioException {
		
		try {
			
			this.resource = file.toUri().toURL();
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(String zip, String entry) throws AudioException {
		
		try(ZipFile zipFile = new ZipFile(zip)) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(File zip, String entry) throws AudioException {
		
		try(ZipFile zipFile = new ZipFile(zip)) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(Path zip, String entry) throws AudioException {
		
		try(ZipFile zipFile = new ZipFile(zip.toFile())) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	/**
	 * @param url the resource
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(URL url) throws AudioException {
		
		this.resource = url;
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
		
		if(this.fileFormat == null) {
			
			throw new AudioException("Unsupported file format!");
		}
	}
	
	/**
	 * @param uri the resource as {@linkplain URI}
	 * @throws AudioException if something is wrong with the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(URI uri) throws AudioException {
		
		try {
			
			this.resource = uri.toURL();
			this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
			
			if(this.fileFormat == null) {
				
				throw new AudioException("Unsupported file format!");
			}
			
		} catch(Exception exception) {
			
			throw new AudioException(exception);
		}
	}
	
	@Override
	public void setVolume(float volume) {
		
		FloatControl control = (FloatControl)this.controls.get("Master Gain");
		float min = control.getMinimum();
		float max = control.getMaximum();
		float oldVal = control.getValue();
		float newVal = volume < min ? min : (volume > max ? max : volume);
		control.setValue(newVal);
		this.trigger(AudioEvent.Type.VOLUME_CHANGED, oldVal, newVal);
	}

	@Override
	public void setMute(boolean mute) {
		
		BooleanControl control = (BooleanControl)this.controls.get("Mute");
		boolean oldVal = control.getValue();
		control.setValue(mute);
		this.trigger(AudioEvent.Type.MUTE_CHANGED, oldVal, mute);
	}
	
	@Override
	public void setBalance(float balance) {
		
		FloatControl balanceControl = (FloatControl)this.controls.get("Balance");
		float max = balanceControl.getMaximum();
		float min = balanceControl.getMinimum();
		balanceControl.setValue(balance < min ? min : (balance > max ? max : balance));
	}
	
	@Override
	public boolean isMuted() {
		
		return ((BooleanControl)this.controls.get("Mute")).getValue();
	}
	
	@Override
	public float getVolume() {
		
		return ((FloatControl)this.controls.get("Master Gain")).getValue();
	}
	
	@Override
	public float getBalance() {
		
		return ((FloatControl)this.controls.get("Balance")).getValue();
	}
	
	@Override
	public FileFormat getFileFormat() {
		
		return this.fileFormat;
	}

	@Override
	public Map<String, Control> getControls() {
		
		@SuppressWarnings("unchecked")
		Map<String, Control> controls = (Map<String, Control>)this.controls.clone();
		return controls;
	}
	
	@Override
	public URL getResource() {
		
		return this.resource;
	}
	
	@Override
	public boolean isOpen() {
		
		return this.open;
	}
	
	@Override
	public void addAudioListener(AudioListener listener) {
		
		this.listeners.add(listener);
	}
	
	@Override
	public void removeAudioListener(AudioListener listener) {
		
		List<AudioListener> newList = new ArrayList<>();
		this.listeners.forEach(element -> {
			
			if(element != listener) {
				
				newList.add(element);
			}
		});
		
		this.listeners = newList;
	}
	
	@Override
	public List<AudioListener> getAudioListeners() {
		
		return this.listeners;
	}
	
	@Override
	public boolean isPaused() {
		
		return this.paused;
	}
	
	@Override
	public Map<?, ?> getHeaders() {
		
		Map<?, ?> headers = null;
		
		switch(this.fileFormat) {
			case AIFC:
			case AIFF:
			case AU:
			case SND:
			case WAV:
				try {
				
					headers = AudioSystem.getAudioFileFormat(this.resource).properties();
					
				} catch(Exception exception) {
					
					exception.printStackTrace();
				}
			
				break;
				
			case MP3:
				try {
					
					AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(this.resource);
					
					if(audioFileFormat instanceof TAudioFileFormat) {
						
						headers = ((TAudioFileFormat)audioFileFormat).properties();
					}
					
				} catch(Exception exception) {
					
					exception.printStackTrace();
				}
				
				break;
				
			case OGG:
				try {
					
					headers = new OggHeadReader().read(this.resource);
					
				} catch(IOException exception) {
					
					exception.printStackTrace();
				}
				break;
		}
		
		return headers;
	}
	
	/**
	 * Triggers a new {@linkplain AudioEvent}.
	 * @param type the event type
	 * @since 1.1.0
	 */
	protected void trigger(AudioEvent.Type type) {
		
		AudioEvent event = new AudioEvent(this, type);
		this.listeners.forEach(listener -> listener.update(event));
	}
	
	/**
	 * Triggers a new {@linkplain AudioEvent}.
	 * @param type the event type
	 * @param oldVal the old value
	 * @param newVal the new value
	 * @since 1.1.0
	 */
	protected void trigger(AudioEvent.Type type, Object oldVal, Object newVal) {
		
		AudioEvent event = new AudioEvent(this, type, oldVal, newVal);
		this.listeners.forEach(listener -> listener.update(event));
	}
	
	/**
	 * Extracts the controls from a {@linkplain Line} based on an old control map.
	 * @param line the {@linkplain Line}
	 * @param old the old control map
	 * @return a new control map based on an old one
	 * @since 1.0.0
	 */
	protected static final HashMap<String, Control> extractControls(Line line, Map<String, Control> old) {
		
		HashMap<String, Control> controls = new HashMap<>();
		
		for(Control control : line.getControls()) {
			
			String key = control.getType().toString();
			
			if(old != null && old.containsKey(key)) {
				
				Control oldControl = old.get(key);
				
				if(control instanceof FloatControl && oldControl instanceof FloatControl) {
					
					((FloatControl)control).setValue(((FloatControl)oldControl).getValue());
					
				} else if(control instanceof BooleanControl && oldControl instanceof BooleanControl) {
					
					((BooleanControl)control).setValue(((BooleanControl)oldControl).getValue());
					
				} else if(control instanceof EnumControl && oldControl instanceof EnumControl) {
					
					((EnumControl)control).setValue(((EnumControl)oldControl).getValue());
				}
			}
			
			controls.put(control.getType().toString(), control);
		}
		
		return controls;
	}
	
	private static final URL extractZipEntry(ZipFile zip, String entry) throws IOException {
		
		ZipEntry zipEntry = zip.getEntry(entry);
		byte[] buffer = new byte[1024];
		String extension = entry.substring(entry.lastIndexOf('.')).toLowerCase();
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File extracted = new File(tempDir, "simple-audio_buffered-" + System.nanoTime() + extension);
		
		try(InputStream input = zip.getInputStream(zipEntry); FileOutputStream output = new FileOutputStream(extracted)) {
			
			int readBytes = 0;
			
			while((readBytes = input.read(buffer)) > -1) {
				
				output.write(buffer, 0, readBytes);
			}
		}
		
		extracted.deleteOnExit();
		return extracted.toURI().toURL();
	}
}
