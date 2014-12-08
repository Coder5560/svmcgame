package com.svmc.mixxgame.entity;

import utils.listener.OnDoneListener;
import utils.ui.Img;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.EntityType;
import com.svmc.mixxgame.attribute.MoveType;

public class UserDataRectangle extends UserData {
	public Img	image;
	Rectangle	rectangle;

	@Override
	public void update(float delta) {
		if (image != null)
			image.act(delta);
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		if (image != null) {
			image.draw(batch, 1f);
		}
	}

	public void setUserData(Rectangle rectangle) {
		this.rectangle = rectangle;
		if (image == null) {
			image = new Img(Assets.instance.game.getRectangle(rectangle));
		}
		image.setOrigin(Align.center);
		image.setPosition(rectangle.x, rectangle.y);
		image.setWidth(rectangle.width);
		image.setHeight(rectangle.height);
	}

	@Override
	public void createMove(MoveType moveType) {

	}

	@Override
	public void createEntity(EntityType entityType) {

	}

	@Override
	public void show(final OnDoneListener listener) {
		show = true;
		image.addAction(Actions.sequence(
				Actions.scaleTo(1, 1, .2f, Interpolation.swingOut),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (listener != null)
							listener.done();
					}
				})));
	}

	@Override
	public void hide(final OnDoneListener listener) {
		show = false;
		image.addAction(Actions.sequence(
				Actions.scaleTo(0, 0, .2f, Interpolation.swingIn),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (listener != null)
							listener.done();
					}
				})));
	}
}
