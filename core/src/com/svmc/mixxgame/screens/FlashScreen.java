package com.svmc.mixxgame.screens;

import utils.screen.AbstractGameScreen;
import utils.screen.GameCore;
import utils.ui.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class FlashScreen extends AbstractGameScreen {
	Image	imBg;
	Image	imLogo;

	boolean	loaded		= false;
	boolean	showMessage	= false;

	public FlashScreen(GameCore game) {
		super(game);
		imBg = new Image(new Texture(Gdx.files.internal("Img/background.png")));
		imBg.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		imLogo = new Image(new Texture(Gdx.files.internal("Img/logo.png")));
		imLogo.setPosition(Constants.WIDTH_SCREEN / 2 - imLogo.getWidth() / 2,
				Constants.HEIGHT_SCREEN / 2 - imLogo.getHeight() / 2);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height, true);
	}

	float	time	= 0;

	@Override
	public void update(float delta) {
		if (time <= 0.1f)
			time += delta;
		if (!loaded && time > 0.1f) {
			if (Assets.instance.assetManager.update()) {
				Assets.instance.init();
				buildComponent(stage);
				loaded = true;
			}
		}

		if (loaded && !showMessage) {
			Toast.makeText(stage, "Assets Loaded", Toast.LENGTH_SHORT);
			showMessage = true;
		}
	}

	boolean	switchScreen	= false;

	void switchScreen() {
		if (!switchScreen) {
			// parent.setScreen(new GameScreen(parent));
			parent.setScreen(new TestScreen(parent));
			switchScreen = true;
		}
	}

	@Override
	public void render(float delta) {
		if (showMessage && imBg.getActions().size == 0) {
			switchScreen();
		}
		super.render(delta);
	}

	@Override
	public void drawBatch(SpriteBatch batch) {
		float delta = Gdx.graphics.getDeltaTime();
		imBg.act(delta);
		imLogo.act(delta);
		imBg.draw(batch, 1f);
		imLogo.draw(batch, 1f);
	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	void buildComponent(Stage stage) {
		Action act1 = Actions.alpha(0f, 1f);
		imLogo.addAction(Actions.sequence(act1));
	}
}
