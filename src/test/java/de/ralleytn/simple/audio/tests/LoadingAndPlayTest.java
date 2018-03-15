package de.ralleytn.simple.audio.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.Audio;
import de.ralleytn.simple.audio.AudioEvent;
import de.ralleytn.simple.audio.AudioException;
import de.ralleytn.simple.audio.BufferedAudio;
import de.ralleytn.simple.audio.StreamedAudio;

class LoadingAndPlayTest {

	private static final String RESOURCE_LOCATION = "de/ralleytn/simple/audio/tests/";
	
	private static final URL getResource(String name) {
		
		return LoadingAndPlayTest.class.getClassLoader().getResource(String.format("%s%s", RESOURCE_LOCATION, name));
	}
	
	private static final void fail(Exception exception) {
		
		Assertions.fail(String.format("%s: %s", exception.getClass().getName(), exception.getMessage()));
	}
	
	private static final void testAudio(Audio audio) throws AudioException {
		
		audio.open();
		audio.addAudioListener(event -> {

			if(event.getType() == AudioEvent.Type.REACHED_END) {

				assertTrue(audio.isOpen());
				audio.close();
			}
		});
		audio.play();
		
		try {
			
			Thread.sleep(audio.getLength() + 200);
			
		} catch(InterruptedException exception) {
			
			// DO NOTHING!
		}
		
		assertFalse(audio.isOpen());
	}
	
	@Test
	void testAiff() {
		
		try {
			
			URL resource = getResource("audio.aiff");
			testAudio(new BufferedAudio(resource));
			testAudio(new StreamedAudio(resource));
			
		} catch(AudioException exception) {
			
			fail(exception);
		}
	}
	
	@Test
	void testOgg() {
		
		try {
			
			URL resource = getResource("audio.ogg");
			testAudio(new BufferedAudio(resource));
			testAudio(new StreamedAudio(resource));
			
		} catch(AudioException exception) {
			
			fail(exception);
		}
	}
	
	@Test
	void testAu() {
		
		try {
			
			URL resource = getResource("audio.au");
			testAudio(new BufferedAudio(resource));
			testAudio(new StreamedAudio(resource));
			
		} catch(AudioException exception) {
			
			fail(exception);
		}
	}
	
	@Test
	void testWave() {
		
		try {
			
			URL resource = getResource("audio.wav");
			testAudio(new BufferedAudio(resource));
			testAudio(new StreamedAudio(resource));
			
		} catch(AudioException exception) {
			
			fail(exception);
		}
	}
	
	@Test
	void testMp3() {
		
		try {
			
			URL resource = getResource("audio.mp3");
			testAudio(new BufferedAudio(resource));
			testAudio(new StreamedAudio(resource));
			
		} catch(AudioException exception) {
			
			fail(exception);
		}
	}
}
