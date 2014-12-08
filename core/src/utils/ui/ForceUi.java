package utils.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class ForceUi {
	Img	bg;
	public ForceUi(Stage stage) {
		create(stage);
	}

	public void create(Stage stage) {
		bg = new Img(Assets.instance.game.getForceBackground());
		bg.setPosition(44, Constants.HEIGHT_SCREEN - 4 - bg.getHeight());
		stage.addActor(bg);
	}

	public void update(float delta) {

	}
	
}
