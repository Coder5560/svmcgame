package com.svmc.mixxgame.screens;

import utils.listener.OnClickListener;
import utils.listener.OnDoneListener;
import utils.ui.TextImage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.attribute.GameState;
import com.svmc.mixxgame.entity.UserData;

public class UISystem {
	private Stage			stage;

	public Image			background;
	public Image			menu;
	public TextImage		menuBall;
	public TextImage		menuLevel;
	private LabelStyle		style;
	public Array<UserData>	userDatas	= new Array<UserData>();
	GameState				gameState;
	boolean					show		= false;
	public boolean					menuClick	= false;
	OnClickListener			onMenuClick;

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
		buildListener();
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

	public void show(OnDoneListener listener) {
		System.out.println("show");
		show = true;
		menu.setVisible(show);
		menuBall.setVisible(show);
		menuLevel.setVisible(show);
		if (listener != null)
			listener.done();
	}

	public void hide(OnDoneListener listener) {
		System.out.println("hide");
		show = false;
		menu.setVisible(show);
		menuBall.setVisible(show);
		menuLevel.setVisible(show);
		if (listener != null)
			listener.done();
	}

	public boolean isShow() {
		return show;
	}

	public boolean isShowMenu() {
		return menuClick;
	}

	public void setOnMenuClick(OnClickListener listener) {
		this.onMenuClick = listener;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;

	}

	public void buildListener() {
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				menu.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .1f),
						Actions.scaleTo(1f, 1f, .1f, Interpolation.swingOut),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								if (onMenuClick != null) {
									if (isShowMenu()) {
										onMenuClick
												.onClick(OnClickListener.DOWN);
										menuClick = false;

									} else {
										onMenuClick.onClick(OnClickListener.UP);
										menuClick = true;
									}
								}
							}
						})));
			}
		});
	}

	public void reset(OnDoneListener listener) {
		
	}
}