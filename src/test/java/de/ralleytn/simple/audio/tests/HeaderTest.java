package de.ralleytn.simple.audio.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.Audio;
import de.ralleytn.simple.audio.AudioException;
import de.ralleytn.simple.audio.BufferedAudio;

class HeaderTest {

	private static final Map<?, ?> getHeaders(String name) {
		
		try {
			
			URL resource = TestUtil.getResource(name);
			Audio audio = new BufferedAudio(resource);
			return audio.getHeaders();
			
		} catch(AudioException exception) {
			
			TestUtil.fail(exception);
		}
		
		return null;
	}
	
	@Test
	void testMp3Header() {
		
		Map<?, ?> headers = getHeaders("audio.mp3");
		
		assertTrue(headers.containsKey("mp3.copyright"));
		assertTrue(headers.containsKey("mp3.framesize.bytes"));
		assertTrue(headers.containsKey("mp3.vbr"));
		assertTrue(headers.containsKey("mp3.frequency.hz"));
		assertTrue(headers.containsKey("mp3.framerate.fps"));
		assertTrue(headers.containsKey("mp3.channels"));
		assertTrue(headers.containsKey("mp3.vbr.scale"));
		assertTrue(headers.containsKey("mp3.version.encoding"));
		assertTrue(headers.containsKey("mp3.bitrate.nominal.bps"));
		assertTrue(headers.containsKey("mp3.version.layer"));
		assertTrue(headers.containsKey("mp3.padding"));
		assertTrue(headers.containsKey("mp3.header.pos"));
		assertTrue(headers.containsKey("mp3.version.mpeg"));
		assertTrue(headers.containsKey("mp3.mode"));
		assertTrue(headers.containsKey("mp3.crc"));
		assertTrue(headers.containsKey("mp3.original"));
		
		assertEquals(false, headers.get("mp3.copyright"));
		assertEquals(205, headers.get("mp3.framesize.bytes"));
		assertEquals(false, headers.get("mp3.vbr"));
		assertEquals(22050, headers.get("mp3.frequency.hz"));
		assertEquals(38.28125F, headers.get("mp3.framerate.fps"));
		assertEquals(1, headers.get("mp3.channels"));
		assertEquals(0, headers.get("mp3.vbr.scale"));
		assertEquals("MPEG2L3", headers.get("mp3.version.encoding"));
		assertEquals(64000, headers.get("mp3.bitrate.nominal.bps"));
		assertEquals("3", headers.get("mp3.version.layer"));
		assertEquals(true, headers.get("mp3.padding"));
		assertEquals(0, headers.get("mp3.header.pos"));
		assertEquals("2", headers.get("mp3.version.mpeg"));
		assertEquals(3, headers.get("mp3.mode"));
		assertEquals(false, headers.get("mp3.crc"));
		assertEquals(true, headers.get("mp3.original"));
	}
	
	@Test
	void testOggHeader() {
		
		Map<?, ?> headers = getHeaders("audio.ogg");
		
		assertTrue(headers.containsKey("ogg.vorbis_version"));
		assertTrue(headers.containsKey("ogg.vendor"));
		assertTrue(headers.containsKey("ogg.blocksize_0"));
		assertTrue(headers.containsKey("ogg.blocksize_1"));
		assertTrue(headers.containsKey("ogg.bitrate_nominal"));
		assertTrue(headers.containsKey("ogg.bitrate_minimum"));
		assertTrue(headers.containsKey("ogg.bitrate_maximum"));
		assertTrue(headers.containsKey("ogg.audio_sample_rate"));
		assertTrue(headers.containsKey("ogg.audio_channels"));
		assertTrue(headers.containsKey("ogg.framing_flag"));
		assertTrue(headers.containsKey("ogg.comment.encoder"));
		
		assertEquals(0L, headers.get("ogg.vorbis_version"));
		assertEquals("Lavf58.10.100", headers.get("ogg.vendor"));
		assertEquals(10, headers.get("ogg.blocksize_0"));
		assertEquals(9, headers.get("ogg.blocksize_1"));
		assertEquals(40222, headers.get("ogg.bitrate_nominal"));
		assertEquals(0, headers.get("ogg.bitrate_minimum"));
		assertEquals(0, headers.get("ogg.bitrate_maximum"));
		assertEquals(22050L, headers.get("ogg.audio_sample_rate"));
		assertEquals(1, headers.get("ogg.audio_channels"));
		assertEquals(false, headers.get("ogg.framing_flag"));
		assertEquals("Lavc58.13.100 libvorbis", headers.get("ogg.comment.encoder"));
	}
}
