package com.svmc.mixxgame.entity;

import utils.listener.OnDoneListener;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.svmc.mixxgame.attribute.EntityType;
import com.svmc.mixxgame.attribute.MoveType;

public abstract class UserData {

	public EntityType	entityType;

	public EntityMove	entityMove;

	public Body			body;
	boolean				show	= true;

	public abstract void update(float delta);

	public abstract void render(SpriteBatch batch, float delta);

	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	public void drawShapeLine(ShapeRenderer shapeRenderer) {

	}

	public abstract void createMove(MoveType moveType);

	public abstract void createEntity(EntityType entityType);

	public abstract void hide(OnDoneListener listener);

	public abstract void show(OnDoneListener listener);
}
