package utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.svmc.mixxgame.Assets;

public class BackgroundManager {
	public Image	background;

	public void create() {
		background = new Image(Assets.instance.game.getBackground());
	}

}
