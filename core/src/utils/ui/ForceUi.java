package utils.ui;

import utils.listener.OnDoneListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class ForceUi {
	Img		bg;
	boolean	show	= false;

	public ForceUi(Stage stage) {
		create(stage);
	}

	public void create(Stage stage) {
		bg = new Img(Assets.instance.game.getForceBackground());
		bg.setPosition(44, Constants.HEIGHT_SCREEN - 4 - bg.getHeight());
		stage.addActor(bg);
		bg.setColor(bg.getColor().r, bg.getColor().g, bg.getColor().b, 0);
	}

	public void show(final OnDoneListener listener) {
		show = true;
		bg.addAction(Actions.sequence(Actions.alpha(1f, .1f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (listener != null)
							listener.done();
					}
				})));
	}

	public void hide(final OnDoneListener listener) {
		show = false;
		bg.addAction(Actions.sequence(Actions.alpha(0f, .1f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (listener != null)
							listener.done();
					}
				})));

	}

	public boolean isShowing() {
		return show;
	}

	public void update(float delta) {

	}

}
