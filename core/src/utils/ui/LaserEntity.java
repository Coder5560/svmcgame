package utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LaserEntity {
	public Array<Vector2>	vertices;
	public Color			rayColor;
	public Color			glowColor;
	public boolean			isGlow;

	public LaserEntity(Array<Vector2> vertices, Color rayColor,
			Color glowColor, boolean isGlow) {
		super();
		this.vertices = vertices;
		this.rayColor = rayColor;
		this.glowColor = glowColor;
		this.isGlow = isGlow;
	}

	public void update() {

	}

	public void draw(SpriteBatch batch, float delta) {

	}

}
