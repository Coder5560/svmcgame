package com.svmc.mixxgame.move;

import com.badlogic.gdx.math.Vector2;

public class MoveHarmonic extends Entity {

	public void build(Vector2 position, Vector2 velocity, Vector2 acceleration) {
		this.position.set(position);
		this.velocity.set(velocity);
		this.acceleration.set(acceleration);
	}

	public void update(float deltaTime) {
	}

	public void move(float deltaTime) {
		float x = position.x;
		float y = position.y;
		float sX = x + velocity.x * deltaTime + acceleration.x * deltaTime
				* deltaTime / 2;
		velocity.x += acceleration.x * deltaTime;

		float sY = y + velocity.y * deltaTime + acceleration.y * deltaTime
				* deltaTime / 2;
		velocity.y += acceleration.y * deltaTime;
		position.set(sX, sY);
	}

	public void moveTo(Vector2 target, float alpha) {
		position.lerp(target, alpha);
	}

	public void flipX() {
		acceleration.x *= -1;
	}

	public void flipY() {
		acceleration.y *= -1;
	}

	public void active() {
	}

	public void deactive() {
	}
}
