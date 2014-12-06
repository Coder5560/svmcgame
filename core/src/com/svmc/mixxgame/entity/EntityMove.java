package com.svmc.mixxgame.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class EntityMove {
	public Body		body;
	Vector2			velocity		= new Vector2(100, 0);
	public Vector2	position		= new Vector2(200, 200);
	Vector2			dimesion		= new Vector2(20, 20);
	Vector2			acceleration	= new Vector2(-100, 0);
	float			time			= 0;
	float			minX, maxX;
	boolean			active			= false;

	public EntityMove(float minX, float maxX) {
		super();
		this.minX = minX;
		this.maxX = maxX;
	}

	public void update(float delta) {
		move(delta);
		boundX(delta);
	}

	public void move(float delta) {
		velocity.x += acceleration.x * delta;
		velocity.y += acceleration.y * delta;
		float sX = position.x + velocity.x * delta + acceleration.x * delta
				* delta / 2;
		float sY = position.y + velocity.y * delta + acceleration.y * delta
				* delta / 2;
		position.set(sX, sY);
	}

	public void boundX(float delta) {
		float vX = velocity.x + acceleration.x * delta;
		// float vY = velocity.y + acceleration.y * delta;

		float sX = position.x + vX * delta + acceleration.x * delta * delta / 2;
		// float sY = position.y + vY * delta + acceleration.y * delta * delta /
		// 2;
		if (sX < minX) {
			position.x = minX;
			acceleration.x *= -1;
		}
		if (sX > minX) {
			position.x = maxX;
			acceleration.x *= -1;
			velocity.set(100, 0);
		}
	}

	public void boundY(float delta) {
		float vX = velocity.x + acceleration.x * delta;
		// float vY = velocity.y + acceleration.y * delta;

		float sX = position.x + vX * delta + acceleration.x * delta * delta / 2;
		// float sY = position.y + vY * delta + acceleration.y * delta * delta /
		// 2;

		if (sX < minX) {
			position.x = minX;
			acceleration.x *= -1;
		}
		if (sX > minX) {
			position.x = maxX;
			acceleration.x *= -1;
			velocity.set(100, 0);
		}
	}

	public void active() {
		this.active = true;
	}

	public void deactive() {
		this.active = false;
	}
}
