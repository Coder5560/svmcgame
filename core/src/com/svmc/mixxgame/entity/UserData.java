package com.svmc.mixxgame.entity;

import utils.listener.OnDoneListener;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.svmc.mixxgame.attribute.EntityType;
import com.svmc.mixxgame.attribute.MoveType;
import com.svmc.mixxgame.attribute.State;

public abstract class UserData {

	public EntityType	entityType;
	public EntityMove	entityMove;
	public Body			body;
	boolean				show	= true;
	private State		state	= State.RUNNING;

	public abstract void update(float delta);

	public abstract void render(SpriteBatch batch, float delta);

	public void drawShapeFill(ShapeRenderer shapeRenderer) {

	}

	public void drawShapeLine(ShapeRenderer shapeRenderer) {

	}
	public State getState(){
		return state;
	}
	public void setState(State state){
		this.state = state;
	}
	public abstract void createMove(MoveType moveType);

	public abstract void createEntity(EntityType entityType);

	public abstract void hide(OnDoneListener listener);

	public abstract void show(OnDoneListener listener);
	
}
