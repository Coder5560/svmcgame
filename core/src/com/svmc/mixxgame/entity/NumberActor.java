package com.svmc.mixxgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;

public class NumberActor {
	private String preText = "Score : ";
	String text = "";
	TextButton[] listChar;
	FloatArray post, advances;
	static final float time = 0.5f, delay = 0.2f;
	Vector2 position;
	BitmapFont font;
	boolean visible = true;

	public NumberActor() {
		super();
		init();
	}

	private void init() {
		advances = new FloatArray();
		post = new FloatArray();
		listChar = new TextButton[text.length()];
		font = Assets.instance.fontFactory.getLight20();
		font.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		font.computeGlyphAdvancesAndPositions(text, advances, post);

		final TextButtonStyle style = new TextButtonStyle();
		style.font = font;
		position = new Vector2(Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2);

		/*-------- List Text --------*/
		for (int i = 0; i < text.length(); i++) {
			listChar[i] = new TextButton(String.valueOf(text.charAt(i)), style);
			listChar[i].setTransform(true);
			listChar[i].setPosition(position.x + post.get(i), position.y);
			listChar[i].setOrigin(advances.get(i) / 2,
					listChar[i].getHeight() / 4);
		}
	}

	public void render(Batch batch) {
		if (visible) {
			for (int i = 0; i < listChar.length; i++) {
				listChar[i].act(Gdx.graphics.getDeltaTime());
				listChar[i].draw(batch, 1f);
			}
			font.draw(batch, preText, position.x
					- font.getBounds(preText).width,
					position.y + 1.5f * font.getBounds(preText).height - 5);
		}
	}

	public void resetText(String txt) {
		
		for (int i = 0; i < this.text.length(); i++) {
			listChar[i].remove();
		}
		this.text = txt;

		listChar = new TextButton[this.text.length()];
		font.computeGlyphAdvancesAndPositions(text, advances, post);

		final TextButtonStyle style = new TextButtonStyle();
		style.font = font;

		/*-------- List Text --------*/
		for (int i = 0; i < text.length(); i++) {
			listChar[i] = new TextButton(String.valueOf(text.charAt(i)), style);
			listChar[i].setTransform(true);
			listChar[i].setPosition(position.x + post.get(i), position.y);
			listChar[i].setOrigin(advances.get(i) / 2,
					listChar[i].getHeight() / 4);
			// stage.addActor(listChar[i]);

		}
	}

	public void setPosition(Vector2 pos) {
		float distance = 0;
		for (int i = 0; i < listChar.length; i++) {
			distance += listChar[i].getWidth();
		}
		this.position.y = pos.y;
		this.position.x = pos.x - distance / 2;

		for (int i = 0; i < text.length(); i++) {
			listChar[i].setPosition(position.x + post.get(i), position.y);
		}
	}

	public Vector2 getPosition() {
		return position;
	}

	public void removeAllActions() {
		for (int i = 0; i < listChar.length; i++) {
			Array<Action> actions = listChar[i].getActions();
			for (Action action : actions) {
				listChar[i].removeAction(action);
			}
		}
	}

	public void show() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listChar[i].setScale(0f);
			listChar[i].setColor(0, 0, 0, 0);
			listChar[i].addAction(Actions.delay(delay * i, Actions.parallel(
					Actions.alpha(1, time),
					Actions.scaleTo(1, 1, time, Interpolation.swingOut))));
		}
	}

	public void inVisible() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listChar[i].setScale(0f);
			listChar[i].setColor(0, 0, 0, 0);
		}
		removeAllActions();
	}

	public void fadeOut() {
		for (int i = 0; i < text.length(); i++) {
			listChar[i].addAction(Actions.sequence(Actions.scaleBy(0.1f, 0.1f,
					time, Interpolation.swingOut), Actions.parallel(
					Actions.alpha(0, 2 * time),
					Actions.scaleTo(0, 0, 2 * time, Interpolation.swingIn))));
		}
	}

	public void flash() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listChar[i].setScale(0f);
			listChar[i].setColor(0, 0, 0, 0);
			listChar[i].addAction(Actions.sequence(Actions
					.delay(delay * i / 2,
							Actions.parallel(Actions.alpha(1, time),
									Actions.scaleTo(1, 1, time,
											Interpolation.swingOut))), Actions
					.sequence(Actions.scaleBy(0.1f, 0.1f, time,
							Interpolation.swingOut), Actions.parallel(Actions
							.alpha(0, 2 * time), Actions.scaleTo(0, 0,
							2 * time, Interpolation.swingIn)))));
		}
	}

	private void resetText() {
		for (int i = 0; i < text.length(); i++) {
			listChar[i].setPosition(position.x + post.get(i), position.y);
			listChar[i].setOrigin(advances.get(i) / 2,
					listChar[i].getHeight() / 4);
			listChar[i].setColor(0, 0, 0, 1);
			listChar[i].setScale(1f);
		}
	}

	public void showStart() {
		resetText();
		for (int i = 0; i < text.length(); i++) {
			listChar[i].setScale(0f);
			listChar[i].setColor(0, 0, 0, 0);
		}
		removeAllActions();

		for (int i = 0; i < text.length(); i++) {
			listChar[i].addAction(Actions.sequence(Actions
					.delay(delay * i,
							Actions.parallel(Actions.alpha(1, time),
									Actions.scaleTo(1, 1, time,
											Interpolation.swingOut))), Actions
					.delay(time), Actions.sequence(Actions.scaleBy(0.1f, 0.1f,
					time, Interpolation.swingOut), Actions.parallel(
					Actions.alpha(0, 2 * time),
					Actions.scaleTo(0, 0, 2 * time, Interpolation.swingIn)))));
		}

	}

	public void showLevel() {
		Vector2 origin = new Vector2(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);

		for (int i = 0; i < text.length(); i++) {
			listChar[i].setScale(0f);
			listChar[i].setColor(0, 0, 0, 0);

			Action action1 = Actions.delay(time * i,
					Actions.moveTo(0, origin.y));
			Action action2 = Actions.parallel(Actions.moveTo(origin.x - 40 + i
					* listChar[0].getWidth(), origin.y, 2 * time), Actions
					.alpha(1, 2 * time), Actions.scaleTo(1, 1, 2 * time));

			Action action3 = Actions.moveTo(
					origin.x + i * listChar[0].getWidth(), origin.y, 5 * time);
			Action action4 = Actions.parallel(Actions.moveTo(2 * origin.x,
					origin.y, 1f, Interpolation.swingOut), Actions.alpha(0,
					2 * time), Actions.scaleTo(0, 0, 2 * time,
					Interpolation.swingOut));

			SequenceAction sequenceAction = new SequenceAction(action1,
					action2, action3, action4);
			listChar[i].addAction(sequenceAction);
		}
	}

	public float getWidth() {
		float distance = 0;
		for (int i = 0; i < listChar.length; i++) {
			distance += listChar[i].getWidth();
		}
		return distance;
	}

	public float getHeight() {
		for (int i = 0; i < listChar.length; i++) {
			if (!listChar[i].getText().equals("")) {
				return listChar[i].getHeight();
			}
		}
		return 0;
	}

	public void plusScore(int score) {
		int currentScore = Integer.parseInt(text);
		final int newScore = currentScore + score;

		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(Actions.delay(1.2f),
					Actions.scaleTo(1.4f, 1.4f, .2f),
					Actions.scaleTo(1f, 1f, .15f)));
		}
		listChar[0].addAction(Actions.sequence(Actions.delay(1.5f),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						resetText("" + newScore);
					}
				})));
	}

	public void setScore(int score) {
		resetText("" + score);
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(
					Actions.scaleTo(1.4f, 1.4f, .2f),
					Actions.scaleTo(1f, 1f, .15f)));
		}
	}

	public void setPretext(String string) {
		this.preText = string;

	}

	public void setSale(float amount) {
		font.scale(amount);
	}

	public void moveToScoreActor(NumberActor desActor) {
		
		
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(Actions.scaleTo(1.3f, 1.3f, 0.2f), Actions.parallel(
					Actions.moveBy(-position.x + desActor.position.x, -position.y
							+ desActor.position.y, 1.0f, Interpolation.swingIn)),
					Actions.scaleTo(0, 0)));
		}
	}

	public void visibleIn() {
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions
					.scaleTo(0, 0, 1f, Interpolation.swing));
		}
	}

	public void visibleOut() {
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(Actions.delay(1),
					Actions.scaleTo(1, 1, 1f, Interpolation.swing)));
		}
	}
	public void scale(float scaleX, float scaleY, float duration, Interpolation interpolation) {
		if(interpolation == null){
			for (int i = 0; i < listChar.length; i++) {
				listChar[i].addAction(Actions
						.scaleTo(scaleX, scaleY,duration));
			}
			return;
		}
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions
					.scaleTo(scaleX, scaleY,duration, interpolation));
		}
	}

	public String getText() {
		return text;
	}

	public void changeText(String string) {
		resetText(string);
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(
					Actions.scaleTo(0f, 0f, .1f),
					Actions.scaleTo(1.5f, 1.5f, .3f),
					Actions.scaleTo(1, 1, .2f)));
		}
	}

	public void changeText(String string, float startScale, float endScale,
			float totalTime) {
		resetText(string);
		for (int i = 0; i < listChar.length; i++) {
			listChar[i].addAction(Actions.sequence(
					Actions.scaleTo(startScale, startScale, totalTime / 6),
					Actions.scaleTo(endScale, endScale, totalTime / 2),
					Actions.scaleTo(1, 1, totalTime / 3)));
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;

	}

	public float getHeightBound() {
		return font.getBounds("aaaa").height;
	}

}
