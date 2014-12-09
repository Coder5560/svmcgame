package com.svmc.mixxgame.screens;

import utils.factory.GamePreferences;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.attribute.GameState;
import com.svmc.mixxgame.attribute.Level;
import com.svmc.mixxgame.entity.NumberActor;

public class UIManager {

	private Stage		stage;
	public Image		btnRefresh, btnNext, btnBack;
	public NumberActor	countTime;
	boolean				show	= false;

	GameScreen			gameScreen;

	public UIManager(Stage stage, GameScreen gameScreen) {
		super();
		this.stage = stage;
		this.gameScreen = gameScreen;
		buildButton();
		disable();
	}

	void buildButton() {
		TextureRegionDrawable reg_btn_back = new TextureRegionDrawable(
				Assets.instance.ui.reg_btn[0]);
		TextureRegionDrawable reg_btn_refresh = new TextureRegionDrawable(
				Assets.instance.ui.reg_btn[1]);
		TextureRegionDrawable reg_btn_next = new TextureRegionDrawable(
				Assets.instance.ui.reg_btn[2]);

		btnNext = new Image(reg_btn_next);
		btnBack = new Image(reg_btn_back);
		btnRefresh = new Image(reg_btn_refresh);

		btnBack.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				btnBack.addAction(Actions.alpha(.5f));
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				btnBack.addAction(Actions.alpha(1f));
				Level.setLevel(Level.getLevel() - 1);
				Assets.instance.assetMap.loadLevel(Level.getLevel());
				btnRefresh.addAction(Actions.alpha(1f));
				disable();
				gameScreen.reset();

			}

		});
		btnRefresh.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				btnRefresh.addAction(Actions.alpha(.5f));
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				Assets.instance.assetMap.loadLevel(Level.getLevel());
				btnRefresh.addAction(Actions.alpha(1f));
				disable();
				gameScreen.reset();
			}

		});

		btnNext.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				btnNext.addAction(Actions.alpha(.5f));
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				btnNext.addAction(Actions.alpha(1f));
				Level.setLevel(Level.getLevel() + 1);
				Assets.instance.assetMap.loadLevel(Level.getLevel());
				disable();
				gameScreen.reset();
			}

		});
		Vector2 centerScreen = new Vector2(Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2);

		btnRefresh.setSize(150, 150);
		btnRefresh.setOrigin(btnRefresh.getWidth() / 2,
				btnRefresh.getHeight() / 2);
		btnRefresh.setPosition(centerScreen.x - btnRefresh.getWidth() / 2,
				centerScreen.y - btnRefresh.getHeight() / 2);

		btnBack.setSize(100, 100);
		btnBack.setOrigin(btnBack.getWidth() / 2, btnBack.getHeight() / 2);
		btnBack.setPosition(btnRefresh.getX() - btnBack.getWidth() - 20,
				centerScreen.y - btnRefresh.getHeight() / 2);

		btnNext.setSize(100, 100);
		btnNext.setOrigin(btnNext.getWidth() / 2, btnNext.getHeight() / 2);
		btnNext.setPosition(btnRefresh.getX() + btnRefresh.getWidth() + 20,
				centerScreen.y - btnRefresh.getHeight() / 2);

		countTime = new NumberActor();
		countTime.resetText("" + 5);
		countTime.setPretext("");
		countTime.setPosition(new Vector2(centerScreen.x, centerScreen.y + 80));
		countTime.setVisible(false);

		stage.addActor(btnBack);
		stage.addActor(btnRefresh);
		stage.addActor(btnNext);
		disable();
	}

	public void show() {
		this.show = true;
		if (Level.getLevel() != 1) {
			btnBack.addAction(Actions.sequence(Actions.alpha(1f),
					Actions.scaleTo(0, 0), Actions.scaleTo(1f, 1f, .5f),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							btnBack.setTouchable(Touchable.enabled);
						}
					})));
		} else {
			btnBack.addAction(Actions.sequence(Actions.alpha(.4f),
					Actions.scaleTo(0, 0), Actions.scaleTo(1f, 1f, .5f),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							btnBack.setTouchable(Touchable.disabled);
						}
					})));
		}
		btnRefresh.addAction(Actions.sequence(Actions.scaleTo(0, 0),
				Actions.scaleTo(1f, 1f, .5f), Actions.run(new Runnable() {

					@Override
					public void run() {
						btnNext.setTouchable(Touchable.enabled);
						btnRefresh.setTouchable(Touchable.enabled);
					}
				})));
		if (Level.getLevel() != Level.getMaxLevel()
				&& gameScreen.getGameState() == GameState.GAME_COMPLETE) {
			int level = Level.getLevel() + 1;
			if (level > Level.getLevel()) {
				level = Level.getMaxLevel();
			}
			if (level > GamePreferences.getInstance().getLevelOpen()) {
				GamePreferences.getInstance().setLevelOpen(level, true);
			}
			btnNext.addAction(Actions.sequence(Actions.alpha(1f),
					Actions.scaleTo(0, 0), Actions.scaleTo(1f, 1f, .5f),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							btnNext.setTouchable(Touchable.enabled);
						}
					})));
		} else {
			btnNext.addAction(Actions.sequence(Actions.alpha(.4f),
					Actions.scaleTo(0, 0), Actions.scaleTo(1f, 1f, .5f),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							btnNext.setTouchable(Touchable.disabled);
						}
					})));
		}
	}

	public void disable() {
		this.show = false;
		btnBack.addAction(Actions.sequence(Actions.scaleTo(0, 0),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						btnBack.setTouchable(Touchable.disabled);
					}
				})));
		btnRefresh.addAction(Actions.sequence(Actions.scaleTo(0, 0),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						btnRefresh.setTouchable(Touchable.disabled);
					}
				})));
		btnNext.addAction(Actions.sequence(Actions.scaleTo(0, 0),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						btnNext.setTouchable(Touchable.disabled);
					}
				})));
	}
	
}
