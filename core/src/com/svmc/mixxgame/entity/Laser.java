package com.svmc.mixxgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Laser {
	public Vector2	position	= new Vector2();
	public float	distance;
	public Color	color		= new Color(0 / 255f, 0 / 255f, 220 / 255f, 1f);
	public Color	rayColor	= new Color(Color.WHITE);
	public float	degrees;
	public Sprite	mid1, mid2;

	public Laser() {
		super();
		Texture texLaserM1 = new Texture(
				Gdx.files.internal("data/beammid1.png"));
		Texture texLaserM2 = new Texture(
				Gdx.files.internal("data/beammid2.png"));
		mid1 = new Sprite(texLaserM1);
		mid2 = new Sprite(texLaserM2);
		distance = 100;
		position.set(200, 200);
		degrees = 0;

	}

	public void render(SpriteBatch batch, float delta) {
		mid1.setColor(color);
		mid2.setColor(rayColor);
		mid1.setSize(mid1.getWidth(), distance);
		mid2.setSize(mid1.getWidth(), distance);
		mid1.setPosition(position.x, position.y);
		mid2.setPosition(position.x, position.y);
		mid1.setOrigin(mid1.getWidth() / 2, mid1.getHeight() / 2);
		mid2.setOrigin(mid2.getWidth() / 2, mid2.getHeight() / 2);
		mid1.setRotation(degrees);
		mid2.setRotation(degrees);

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		mid1.draw(batch);
		mid2.draw(batch);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void render(Vector2 startPoint, Vector2 endpoint, SpriteBatch batch,
			float delta) {
		distance = startPoint.dst(endpoint);
		degrees = getAngle(startPoint, endpoint);
		degrees = -90;
		mid1.setColor(color);
		mid2.setColor(rayColor);
		mid1.setSize(mid1.getWidth(), distance);
		mid2.setSize(mid1.getWidth(), distance);
		mid1.setPosition(startPoint.x, startPoint.y);
		mid2.setPosition(startPoint.x, startPoint.y);
		mid1.setOrigin(mid1.getWidth() / 2, 0);
		mid2.setOrigin(mid2.getWidth() / 2, 0);
		mid1.setRotation(degrees);
		mid2.setRotation(degrees);

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		mid1.draw(batch);
		mid2.draw(batch);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	public float getAngle(Vector2 start, Vector2 end) {
		float angle = (float) Math.toDegrees(Math.atan2(end.y - start.y, end.x
				- start.x));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}
}
