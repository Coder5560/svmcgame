package com.coder5560.svmcgame.desktop;

import utils.screen.GameCore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.screens.FlashScreen;

public class MixxGameDesktop {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		GameCore game = new GameCore() {
			@Override
			public void create() {
				super.create();
				// float scale = Gdx.graphics.getWidth() /
				// Constants.WIDTH_SCREEN;
				// Constants.HEIGHT_SCREEN = (int) (scale * Gdx.graphics
				// .getHeight());

				Constants.WIDTH_SCREEN = Gdx.graphics.getWidth();
				Constants.HEIGHT_SCREEN = Gdx.graphics.getHeight();

				setScreen(new FlashScreen(this));
			}
		};

		// config.width = 800;
		// config.height = 600;

		config.width = Constants.WIDTH_SCREEN;
		config.height = Constants.HEIGHT_SCREEN;
		config.title = "SVMC Game";

		new LwjglApplication(game, config);
	}
}
