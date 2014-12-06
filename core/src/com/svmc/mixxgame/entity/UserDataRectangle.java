package com.svmc.mixxgame.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.svmc.mixxgame.Assets;

public class UserDataRectangle extends UserData {
	public Image	image;
	Rectangle		rectangle;

	@Override
	public void update(float delta) {
		super.update(delta);
		if (image != null)
			image.act(delta);
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
		if (image != null) {
			image.draw(batch, 1f);
		}
	}

	public void setUserData(Rectangle rectangle) {
		this.rectangle = rectangle;
		if (image == null) {
			image = new Image(Assets.instance.game.getRectangle(rectangle));
		}
		image.setOrigin(Align.center);
		image.setPosition(rectangle.x, rectangle.y);
		image.setWidth(rectangle.width);
		image.setHeight(rectangle.height);
	}
}
