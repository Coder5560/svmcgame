package utils.factory;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private static SoundManager instance;
	
	public static SoundManager getInstance() {
		if(instance == null) instance = new SoundManager();
		return instance;
	}
	
	public static enum MusicName {
		MAGIC_DREAM("sound/magic_dream.mp3", 0.5f);
		
		String path;
		float volumn;
		
		private MusicName(String path, float vol) {
			this.path = path;
			this.volumn = vol;
		}
	}
	
	public static enum SoundName {
		BUTTON_CLICK("sound/button.mp3", 0.4f),
		FINISH("sound/finish.mp3", 1f),
		NEW_HIGHSCORE("sound/newhighscore.mp3", 1f),
		SCORE("sound/score.mp3", 1f),
		COLLAPSE("sound/collapse.mp3", 1f),
		PLOOOP("sound/plooop.mp3",1f);
		
		String path;
		float volumn;
		
		private SoundName(String path, float vol) {
			this.path = path;
			this.volumn = vol;
		}
	}
	
	HashMap<SoundName, Sound> sounds = new HashMap<SoundManager.SoundName, Sound>();
	HashMap<MusicName, Music> musics = new HashMap<SoundManager.MusicName, Music>();
	
	public boolean sound = true, music = true;
	private Music currentMusic;
	
	private SoundManager() {
		initComponents();
	}

	private void initComponents() {
		for(SoundName name : SoundName.values()) {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(name.path));
			sounds.put(name, sound);
		}
		for(MusicName name : MusicName.values()) {
			Music music = Gdx.audio.newMusic(Gdx.files.internal(name.path));
			music.setLooping(true);
			music.setVolume(name.volumn);
			musics.put(name, music);
		}
		sound = GamePreferences.getInstance().isSoundOn();
		music = GamePreferences.getInstance().isMusicOn();
	}
	
	public void dispose() {
		for(Sound sound : sounds.values()) {
			sound.dispose();
		}
		for(Music music : musics.values()) {
			music.dispose();
		}
		sounds.clear();
		musics.clear();
	}
	
	public void turnSound() {
		sound = !sound;
		GamePreferences.getInstance().setSound(sound, true);
	}
	
	public void turnMusic() {
		if(music) {
			music = false;
			for(MusicName mn : MusicName.values()) {
				if(musics.get(mn).isPlaying()) {
					musics.get(mn).stop();
					currentMusic = musics.get(mn);
				}
			}
		} else {
			music = true;
			if(currentMusic == null) {
				playMusic(MusicName.MAGIC_DREAM);
			} else {
				currentMusic.play();
			}
		}
		GamePreferences.getInstance().setMusic(music, true);
	}
	
	public void playMusic(MusicName name) {
		if(!music) return;
		
		if(musics.get(name).isPlaying()) return;
		
		for(MusicName mn : MusicName.values()) {
			if(musics.get(mn).isPlaying()) musics.get(mn).stop();
		}
		
		musics.get(name).play();
		
		currentMusic = musics.get(name);
	}
	
	public void playSound(SoundName name) {
		if(!sound) return;
		
		try {
			sounds.get(name).play(name.volumn);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
