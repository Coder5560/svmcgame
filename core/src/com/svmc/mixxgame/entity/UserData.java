package com.svmc.mixxgame.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.svmc.mixxgame.attribute.EntityType;
import com.svmc.mixxgame.attribute.MoveType;

public class UserData {

	public EntityType	entityType;

	public EntityMove	entityMove;

	public Body			body;

	public void update(float delta) {

	}

	public void render(SpriteBatch batch, float delta) {

	}

	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	public void drawShapeLine(ShapeRenderer shapeRenderer) {

	}
	public void createMove(MoveType moveType) {

	}

	public void createEntity(EntityType entityType) {

	}
}
