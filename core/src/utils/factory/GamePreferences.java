package utils.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {
	private static GamePreferences	instance;
	private Preferences				prefs;
	private static final String		HIGHSCORE	= "highscore";
	private static final String		SOUND		= "sound";
	private static final String		MUSIC		= "music";
	private int						level		= 1;
	private int						levelOpen	= 1;

	public static GamePreferences getInstance() {
		if (instance == null)
			instance = new GamePreferences();
		return instance;
	}

	private GamePreferences() {
		prefs = Gdx.app.getPreferences("data");
	}

	public boolean isMusicOn() {
		return prefs.getBoolean(MUSIC, true);
	}

	public void setMusic(boolean on, boolean flush) {
		prefs.putBoolean(MUSIC, on);
		if (flush)
			flush();
	}

	public boolean isSoundOn() {
		return prefs.getBoolean(SOUND, true);
	}

	public void setSound(boolean on, boolean flush) {
		prefs.putBoolean(SOUND, on);
		if (flush)
			flush();
	}

	public int getHighScore() {
		return prefs.getInteger(HIGHSCORE, 0);
	}

	public void setHighScore(int score, boolean flush) {
		prefs.putInteger(HIGHSCORE, score);
		if (flush)
			flush();
	}

	public void flush() {
		prefs.flush();
	}

	public int getLevelOpen() {
		return levelOpen;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level, boolean flush) {
		this.level = level;
		if (flush)
			flush();
	}
	public void setLevelOpen(int level, boolean flush) {
		this.levelOpen = level;
		if (flush)
			flush();
	}

}
