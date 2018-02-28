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

import java.util.ArrayList;
import java.util.List;

/**
 * Stores multiple {@linkplain Audio}s and plays them in a batch.
 * Handy if you want to program an audio player.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 2.0.0
 * @since 1.1.0
 */
public class Playlist implements Playable {

	private List<Audio> tracks = new ArrayList<>();
	private List<PlaylistListener> listeners = new ArrayList<>();
	private int currentTrack = -1;
	private boolean shuffling;
	private boolean looping;
	private boolean muted;
	private boolean playing;
	private float volume;
	private AudioListener listener = (event) -> {

		if(event.getType().equals(AudioEvent.Type.REACHED_END)) {
			
			this.trigger(PlaylistEvent.Type.TRACK_REACHED_END);
			this.next();
		}
	};
	
	/**
	 * Adds a new track to the track list.
	 * @param audio the audio of the track
	 * @since 1.1.0
	 */
	public void add(Audio audio) {
		
		this.tracks.add(audio);
	}
	
	/**
	 * Adds a {@linkplain PlaylistListener} to this {@linkplain Playlist}.
	 * @param listener the {@linkplain PlaylistListener} to add
	 * @since 1.2.0
	 */
	public void addPlaylistListener(PlaylistListener listener) {
		
		this.listeners.add(listener);
	}
	
	/**
	 * Remove the given {@linkplain PlaylistListener} from this {@linkplain Playlist}.
	 * @param listener the {@linkplain PlaylistListener} to remove
	 * @since 1.2.0
	 */
	public void removePlaylistListener(PlaylistListener listener) {
		
		List<PlaylistListener> newList = new ArrayList<>();
		this.listeners.forEach(element -> {
			
			if(element != listener) {
				
				newList.add(element);
			}
		});
		
		this.listeners = newList;
	}
	
	/**
	 * @return the list of all the {@linkplain PlaylistListener}s
	 * @since 1.2.0
	 */
	public List<PlaylistListener> getPlaylistListeners() {
		
		return this.listeners;
	}
	
	/**
	 * Removes a track from the track list.
	 * @param index index of the track which should be removed
	 * @since 1.1.0
	 */
	public void remove(int index) {
		
		Audio audio = this.tracks.get(index);
		
		if(audio != null) {
			
			if(audio.isPlaying()) {
				
				audio.stop();
			}
			
			if(audio.isOpen()) {
				
				audio.close();
			}
			
			this.tracks.remove(index);
			
			if(this.currentTrack == index) {
				
				this.next();
			}
		}
	}
	
	/**
	 * @return the number of tracks on the track list
	 * @since 1.1.0
	 */
	public int getNumberOfTracks() {
		
		return this.tracks.size();
	}
	
	/**
	 * Sets whether the next track to play should be picked randomly or not.
	 * @param shuffle {@code true} if tracks should be picked randomly, else {@code false}
	 * @since 1.1.0
	 */
	public void setShuffle(boolean shuffle) {
		
		this.shuffling = shuffle;
	}
	
	/**
	 * Sets whether the {@linkplain Playlist} should restart once it reached the end or not.
	 * @param loop {@code true} if the {@linkplain Playlist} should loop, else {@code false}
	 * @since 1.1.0
	 */
	public void setLoop(boolean loop) {
		
		this.looping = loop;
	}

	@Override
	public void play() {

		if(this.playing) {

			this.stop();
		}
		
		if(this.currentTrack == -1) {
			
			this.currentTrack = this.getNextTrackIndex();
		}
		
		Audio audio = this.tracks.get(this.currentTrack);
		
		if(!audio.isOpen()) {
			
			try {
				
				audio.open();
				audio.setMute(this.muted);
				audio.setVolume(this.volume);
				audio.addAudioListener(this.listener);
				
			} catch(AudioException exception) {
				
				exception.printStackTrace();
			}
		}
		
		audio.play();
		this.trigger(PlaylistEvent.Type.STARTED);
		this.playing = true;
	}

	@Override
	public void stop() {
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			
			if(audio != null && audio.isOpen()) {
				
				audio.stop();
				audio.removeAudioListener(this.listener);
				audio.close();
				this.playing = false;
				this.trigger(PlaylistEvent.Type.STOPPED);
			}
		}
	}

	@Override
	public void pause() {
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			
			if(audio != null && audio.isOpen()) {
				
				audio.pause();
				this.playing = false;
				this.trigger(PlaylistEvent.Type.PAUSED);
			}
		}
	}

	@Override
	public void resume() {
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			
			if(audio != null && audio.isOpen()) {
				
				audio.resume();
				this.playing = true;
				this.trigger(PlaylistEvent.Type.RESUMED);
			}
		}
	}
	
	@Override
	public void setVolume(float volume) {
		
		this.volume = volume;
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			
			if(audio != null && audio.isOpen()) {
				
				float oldVal = audio.getVolume();
				audio.setVolume(volume);
				this.trigger(PlaylistEvent.Type.VOLUME_CHANGED, oldVal, volume);
			}
		}
	}

	@Override
	public void setMute(boolean mute) {
		
		this.muted = mute;
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			
			if(audio != null && audio.isOpen()) {
				
				boolean oldVal = audio.isMuted();
				audio.setMute(mute);
				this.trigger(PlaylistEvent.Type.MUTE_CHANGED, oldVal, volume);
			}
		}
	}
	
	/**
	 * @return {@code true} if the {@linkplain Playlist} is looping, else {@code false}
	 * @since 1.1.0
	 */
	public boolean isLooping() {
		
		return this.looping;
	}
	
	/**
	 * @return {@code true} if the {@linkplain Playlist} is shuffling, else {@code false}
	 * @since 1.1.0
	 */
	public boolean isShuffling() {
		
		return this.shuffling;
	}

	@Override
	public boolean isMuted() {
		
		return this.muted;
	}

	@Override
	public boolean isPlaying() {
		
		return this.playing;
	}

	@Override
	public float getVolume() {
		
		return this.volume;
	}
	
	/**
	 * Starts the next track.
	 * @since 1.1.0
	 */
	public void next() {
		
		this.stop();
		Audio oldVal = this.tracks.get(this.currentTrack);
		this.currentTrack = this.getNextTrackIndex();
		Audio newVal = this.tracks.get(this.currentTrack);
		
		if(this.currentTrack != -1) {
			
			this.play();
		}
		
		this.trigger(PlaylistEvent.Type.TRACK_CHANGED, oldVal, newVal);
	}
	
	/**
	 * Starts the previous track.
	 * @since 1.1.0
	 */
	public void previous() {
		
		this.stop();
		Audio oldVal = this.tracks.get(this.currentTrack);
		this.currentTrack = this.getPreviousTrackIndex();
		Audio newVal = this.tracks.get(this.currentTrack);
		
		if(this.currentTrack != -1) {
			
			this.play();
		}
		
		this.trigger(PlaylistEvent.Type.TRACK_CHANGED, oldVal, newVal);
	}
	
	private int getPreviousTrackIndex() {
		
		int previousTrack = -1;
		
		if(this.shuffling) {
			
			previousTrack = (int)(Math.random() * (this.tracks.size() - 1));
			
		} else {
			
			if(this.currentTrack != -1) {
				
				for(int index = 0; index < this.tracks.size(); index++) {
					
					if(index == this.currentTrack) {
						
						if(index == 0) {
							
							if(this.looping) {
								
								previousTrack = this.tracks.size() - 1;
							}
							
						} else {
							
							previousTrack = index - 1;
						}
					}
				}
				
			} else {
				
				previousTrack = this.tracks.size() - 1;
			}
		}
		
		return previousTrack;
	}
	
	private int getNextTrackIndex() {
		
		int nextTrack = -1;
		
		if(this.shuffling) {
			
			nextTrack = (int)(Math.random() * (this.tracks.size() - 1));
			
		} else {
			
			if(this.currentTrack != -1) {
				
				for(int index = 0; index < this.tracks.size(); index++) {
					
					if(index == this.currentTrack) {
						
						if(index == this.tracks.size() - 1) {
							
							if(this.looping) {
								
								nextTrack = 0;
							}
							
						} else {
							
							nextTrack = index + 1;
						}
					}
				}
				
			} else {
				
				nextTrack = 0;
			}
		}
		
		return nextTrack;
	}
	
	/**
	 * @return the index of the track which is currently selected or {@code -1} if no track is selected
	 * @since 1.1.0
	 */
	public int getCurrentTrackIndex() {
		
		return this.currentTrack;
	}
	
	/**
	 * @param index index of the track
	 * @return the track with the given index
	 * @since 1.1.0
	 */
	public Audio getTrack(int index) {
		
		return this.tracks.get(index);
	}
	
	/**
	 * @return a list with all tracks of the {@linkplain Playlist}
	 * @since 1.1.0
	 */
	public List<Audio> getTracks() {
		
		return this.tracks;
	}
	
	@Override
	public boolean isPaused() {
		
		if(this.currentTrack != -1) {
			
			Audio audio = this.tracks.get(this.currentTrack);
			return audio != null && audio.isOpen() && audio.isPaused();
		}
		
		return false;
	}
	
	protected void trigger(PlaylistEvent.Type type) {
		
		PlaylistEvent event = new PlaylistEvent(this, type);
		this.listeners.forEach(listener -> listener.update(event));
	}
	
	protected void trigger(PlaylistEvent.Type type, Object oldVal, Object newVal) {
		
		PlaylistEvent event = new PlaylistEvent(this, type, oldVal, newVal);
		this.listeners.forEach(listener -> listener.update(event));
	}
}
