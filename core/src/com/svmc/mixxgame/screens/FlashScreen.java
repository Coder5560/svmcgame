package com.svmc.mixxgame.screens;

import utils.screen.AbstractGameScreen;
import utils.screen.GameCore;
import utils.ui.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class FlashScreen extends AbstractGameScreen {
	Image	splash;
	Sprite	sprite;

	boolean	loaded		= false;
	boolean	showMessage	= false;

	public FlashScreen(GameCore game) {
		super(game);
		sprite = new Sprite(new Texture(Gdx.files.internal("Img/images.jpeg")));
		sprite.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
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
//			 parent.setScreen(new GameScreen(parent));
			parent.setScreen(new TestScreen(parent));
			switchScreen = true;
		}
	}

	@Override
	public void render(float delta) {
		if (showMessage && splash.getActions().size == 0) {
			switchScreen();
		}
		super.render(delta);
	}

	@Override
	public void drawBatch(SpriteBatch batch) {
		if (!loaded) {
			sprite.draw(batch);
		}
	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	void buildComponent(Stage stage) {
		splash = new Image(new Texture(Gdx.files.internal("Img/images.jpeg")));
		splash.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		Action act0 = Actions.alpha(0f, 1f);
		splash.addAction(Actions.sequence(act0));
		stage.addActor(splash);
	}
}
