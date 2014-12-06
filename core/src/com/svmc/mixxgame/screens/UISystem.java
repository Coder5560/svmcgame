package com.svmc.mixxgame.screens;

import utils.listener.OnDoneListener;
import utils.ui.TextImage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class UISystem {
	private Stage		stage;

	public Image		background;
	public Image		menu;
	public TextImage	menuBall;
	public TextImage	menuLevel;

	private LabelStyle	style;

	public UISystem(Stage stage) {
		this.stage = stage;
		style = new LabelStyle();
		style.font = Assets.instance.fontFactory.getLight20();
		style.fontColor = Color.WHITE;
	}

	public void create() {
		createBackground();
		createMenuButton();
		createMenuBall();
		createMenuLevel();
		buildPosition();
	}

	// ===================== Initial ========================
	private void createBackground() {
		background = new Image(Assets.instance.game.getBackground());
		background.setOrigin(Align.center);
		background.setSize(Constants.WIDTH_SCREEN, Constants.HEIGHT_SCREEN);
		stage.addActor(background);
	}

	private void createMenuButton() {
		menu = new Image(Assets.instance.game.getMenuButton());
		menu.setOrigin(Align.center);
		menu.setSize(70, 70);
		menu.setVisible(false);
		stage.addActor(menu);
	}

	private void createMenuBall() {
		menuBall = new TextImage(Assets.instance.game.getMenuBall(), new Label(
				"5", style));
		menuBall.setOriginText(9 / 10f, 1 / 3f);
		menuBall.setOrigin(Align.center);
		menuBall.setSize(70, 70);
		menuBall.setVisible(false);
		stage.addActor(menuBall);
	}

	private void createMenuLevel() {
		menuLevel = new TextImage(Assets.instance.game.getMenuLevel(),
				new Label("1", style));
		menuLevel.setOriginText(9 / 10f, 1 / 3f);
		menuLevel.setOrigin(Align.center);
		menuLevel.setSize(70, 70);
		menuLevel.setVisible(false);
		stage.addActor(menuLevel);
	}

	private void buildPosition() {
		float offset = 12;
		menu.setPosition(Constants.WIDTH_SCREEN - 10 - menu.getWidth(),
				Constants.HEIGHT_SCREEN - 10 - menu.getHeight());
		menuLevel.setPosition(menu.getX() - menuLevel.getWidth() - offset,
				menu.getY());
		menuBall.setPosition(menuLevel.getX() - menuBall.getWidth() - offset,
				menu.getY());
	}

	public void showMenu(OnDoneListener listener) {
		menu.setVisible(true);
		menuBall.setVisible(true);
		menuLevel.setVisible(true);
		listener.done();
	}
}
