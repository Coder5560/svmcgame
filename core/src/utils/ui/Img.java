package utils.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Img extends Image {
	
	
	public Img(Texture texture) {
		super(texture);
		setOriginCenter();
	}

	public Img(TextureRegion region) {
		super(region);
		setOriginCenter();
	}

	public Img(TextureRegion region, float width, float height) {
		super(region);
		setSize(width, height);
		setOriginCenter();
	}

	public Vector2 getPosition() {
		return new Vector2(getX(), getY());
	}

	public void setPosition(Vector2 position) {
		setX(position.x);
		setY(position.y);
	}

	public Rectangle getBound() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public void setOriginCenter() {
		setOrigin(getWidth() / 2, getHeight() / 2);
	}

	public Vector2 getCenter() {
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	public float getRight() {
		return getX() + getWidth();
	}
}
