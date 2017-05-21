import de.ralleytn.simple.audio.Playlist;
import de.ralleytn.simple.audio.StreamedAudio;

public class Test {

	public static void main(String[] args) {
		
		try {
			
			Playlist playlist = new Playlist();
			playlist.add(new StreamedAudio("piano2.mp3"));
		//	playlist.add(new StreamedAudio("test1.mp3"));
			//playlist.add(new StreamedAudio("test2.mp3"));
			playlist.play();
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
		}
	}

}
