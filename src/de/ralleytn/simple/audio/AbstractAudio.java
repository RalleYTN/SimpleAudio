package de.ralleytn.simple.audio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;

import de.john.jarnbjo.JOgg;

/**
 * Implements the {@linkplain Audio} and should be extended by all classes representing a form of playable audio.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractAudio implements Audio {

	protected URL resource;
	protected FileFormat fileFormat;
	protected AudioInputStream audioInputStream;
	protected HashMap<String, Control> controls;
	
	/**
	 * @param file name of the resource file
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public AbstractAudio(String file) throws MalformedURLException {
		
		this.resource = new File(file).toURI().toURL();
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param file the resource as {@linkplain File}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public AbstractAudio(File file) throws MalformedURLException {
		
		this.resource = file.toURI().toURL();
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param file the resource as {@linkplain Path}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public AbstractAudio(Path file) throws MalformedURLException {
		
		this.resource = file.toUri().toURL();
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(String zip, String entry) throws IOException {
		
		try(ZipFile zipFile = new ZipFile(zip)) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
		}
		
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(File zip, String entry) throws IOException {
		
		try(ZipFile zipFile = new ZipFile(zip)) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
		}
		
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param zip zip file containing the resource
	 * @param entry name of the resource entry
	 * @throws IOException if somethiong went wrong while extracting the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(Path zip, String entry) throws IOException {
		
		try(ZipFile zipFile = new ZipFile(zip.toFile())) {
			
			this.resource = AbstractAudio.extractZipEntry(zipFile, entry);
		}
		
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param url the resource
	 * @since 1.0.0
	 */
	public AbstractAudio(URL url) {
		
		this.resource = url;
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	/**
	 * @param uri the resource as {@linkplain URI}
	 * @throws MalformedURLException if a protocol handler for the URL could not be found, or if some other error occurred while constructing the URL
	 * @since 1.0.0
	 */
	public AbstractAudio(URI uri) throws MalformedURLException {
		
		this.resource = uri.toURL();
		this.fileFormat = FileFormat.getFormatByName(this.resource.toExternalForm());
	}
	
	@Override
	public void setVolume(float volume) {
		
		FloatControl control = (FloatControl)this.controls.get("Master Gain");
		float min = control.getMinimum();
		float max = control.getMaximum();
		control.setValue(volume < min ? min : (volume > max ? max : volume));
	}

	@Override
	public void setMute(boolean mute) {
		
		((BooleanControl)this.controls.get("Mute")).setValue(mute);
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
	
	protected static final HashMap<String, Control> extractControls(Line line) {
		
		HashMap<String, Control> controls = new HashMap<>();
		
		for(Control control : line.getControls()) {
			
			controls.put(control.getType().toString(), control);
		}
		
		return controls;
	}
	
	protected static final AudioInputStream getAudioInputStream(URL resource, FileFormat fileFormat) throws Exception {
		
		AudioInputStream audioInputStream = null;
		
		switch(fileFormat) {
			case MP3:
				audioInputStream = AudioSystem.getAudioInputStream(resource);
				AudioFormat baseFormat = audioInputStream.getFormat();
				AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
				audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
				break;
	
			case OGG:
				audioInputStream = JOgg.getAudioInputStream(resource);
				break;
	
			case AU:
			case AIFF:
			case WAV:
				audioInputStream = AudioSystem.getAudioInputStream(resource);
				break;
		}
		
		return audioInputStream;
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
