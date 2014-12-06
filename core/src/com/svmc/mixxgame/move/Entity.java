package com.svmc.mixxgame.move;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	public enum State {
		LIVE, DIE
	}

	public State	state			= State.LIVE;
	public Vector2	velocity		= new Vector2();
	public Vector2	acceleration	= new Vector2();
	public Vector2	position		= new Vector2();
	public Vector2	dimesion		= new Vector2();

	public Rectangle getBound() {
		return new Rectangle(position.x - dimesion.x / 2, position.y
				- dimesion.y / 2, dimesion.x, dimesion.y);
	}
}
