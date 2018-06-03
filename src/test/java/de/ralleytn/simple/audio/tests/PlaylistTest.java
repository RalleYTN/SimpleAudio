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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.ralleytn.simple.audio.BufferedAudio;
import de.ralleytn.simple.audio.Playlist;
import de.ralleytn.simple.audio.PlaylistListener;
import de.ralleytn.simple.audio.StreamedAudio;

class PlaylistTest {
	
	private static final Playlist createPlaylist() {
		
		Playlist playlist = new Playlist();
		
		try {
			
			playlist.add(new BufferedAudio(Sources.getResource("audio.ogg")));
			playlist.add(new StreamedAudio(Sources.getResource("audio.mp3")));
			playlist.add(new StreamedAudio(Sources.getResource("audio.au")));
			playlist.add(new BufferedAudio(Sources.getResource("audio.aiff")));
			
			assertEquals(4, playlist.getNumberOfTracks());
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
			fail(exception.getMessage());
		}
		
		return playlist;
	}
	
	@Test
	public void testSetTrack() {
		
		try {
			
			Playlist playlist = createPlaylist();
			playlist.setTrack(2);
			playlist.play();
			assertEquals(2, playlist.getCurrentTrackIndex());
			Thread.sleep(3000);
			playlist.setTrack(0);
			assertEquals(0, playlist.getCurrentTrackIndex());
			Thread.sleep(3000);
			
		} catch(InterruptedException exception) {}
	}
	
	@Test
	public void testAddAndRemoveListeners() {
		
		PlaylistListener listener = event -> {};
		Playlist playlist = createPlaylist();
		playlist.addPlaylistListener(listener);
		assertEquals(1, playlist.getPlaylistListeners().size());
		playlist.play();
		playlist.removePlaylistListener(listener);
		playlist.close();
		assertEquals(0, playlist.getPlaylistListeners().size());
	}

	@Test
	public void testPlay() {
		
		class Data {
			boolean started;
			boolean stopped;
			boolean trackChanged;
			boolean trackReachedEnd;
			boolean volumeChanged;
			
			void test() {
				
				assertTrue(started);
				assertTrue(stopped);
				assertTrue(trackChanged);
				assertTrue(trackReachedEnd);
				assertTrue(volumeChanged);
			}
		}
		
		Data data = new Data();
		Playlist playlist = createPlaylist();
		playlist.addPlaylistListener(event -> {
			
			switch(event.getType()) {
			
				case MUTE_CHANGED: break;
				case PAUSED: break;
				case RESUMED: break;
				case STARTED:
					data.started = true;
					break;
				case STOPPED:
					data.stopped = true;
					break;
				case TRACK_CHANGED:
					data.trackChanged = true;
					break;
				case TRACK_REACHED_END:
					data.trackReachedEnd = true;
					break;
				case VOLUME_CHANGED:
					data.volumeChanged = true;
					break;
				default: break;
			}
		});
		playlist.play();
		
		try {
			
			assertEquals(0, playlist.getCurrentTrackIndex());
			Thread.sleep(1100);
			playlist.next();
			assertEquals(1, playlist.getCurrentTrackIndex());
			Thread.sleep(5100);
			playlist.setVolume(-20.0F);
			assertEquals(2, playlist.getCurrentTrackIndex());
			Thread.sleep(5100);
			assertEquals(3, playlist.getCurrentTrackIndex());
			Thread.sleep(5100);
			
		} catch(InterruptedException exception) {}
		
		playlist.close();
		assertEquals(-1, playlist.getCurrentTrackIndex());
		data.test();
	}
	
	@Test
	public void testShuffle() {
		
		int[] order = {0, 1, 2, 3};
		int index = 0;
		
		Playlist playlist = createPlaylist();
		playlist.setLoop(true);
		playlist.setShuffle(true);
		playlist.play();
		
		assertTrue(playlist.isLooping());
		assertTrue(playlist.isShuffling());
		assertTrue(playlist.isPlaying());
		
		boolean allEqual = true;
		
		for(int i = 0; i < 200; i++) {
			
			if(i % 4 == 0) {
				
				index = 0;
			}
			
			if(order[index] != playlist.getCurrentTrackIndex()) {
				
				allEqual = false;
			}
			
			playlist.next();
		}
		
		playlist.close();
		assertFalse(allEqual);
	}
	
	@Test
	public void testPause() {
		
		Playlist playlist = createPlaylist();
		playlist.play();
		
		try {
			
			Thread.sleep(2500);
			assertTrue(playlist.isPlaying());
			assertFalse(playlist.isPaused());
			playlist.pause();
			assertFalse(playlist.isPlaying());
			assertTrue(playlist.isPaused());
			Thread.sleep(1000);
			playlist.resume();
			assertTrue(playlist.isPlaying());
			assertFalse(playlist.isPaused());
			Thread.sleep(5000);
			playlist.stop();
			
		} catch(InterruptedException exception) {}
	
		playlist.close();
	}
}
