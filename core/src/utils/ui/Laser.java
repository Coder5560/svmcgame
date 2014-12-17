package utils.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Coder5560
 */
public class Laser {

	public Vector2	startPoint	= new Vector2();
	public Vector2	endPoint	= new Vector2();
	public float	degrees;
	public Color	color		= new Color(220 / 255f, 0 / 255f, 0 / 255f, 1f);
	// public Color rayColor = new Color(Color.WHITE);
	public Color	rayColor	= new Color(Color.BLUE);

	public Image	begin, beginRay, mid, midRay, end, endRay;
	public boolean	drawVertice	= true;
	public boolean	isGlow		= true;

	public Laser() {
		super();
		setUp();
	}

	public Laser(Vector2 startPoint, Vector2 endPoint) {
		super();

		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Laser(Vector2 startPoint, Vector2 endPoint, boolean drawVertice) {
		super();
		setUp();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.drawVertice = drawVertice;
	}

	void setUp() {
		Texture texLaserS1 = new Texture(
				Gdx.files.internal("data/beamstart1.png"));
		Texture texLaserS2 = new Texture(
				Gdx.files.internal("data/beamstart2.png"));
		Texture texLaserM1 = new Texture(
				Gdx.files.internal("data/beammid1.png"));
		Texture texLaserM2 = new Texture(
				Gdx.files.internal("data/beammid2.png"));
		// Texture texLaserE1 = new Texture(
		// Gdx.files.internal("data/beamend1.png"));
		// Texture texLaserE2 = new Texture(
		// Gdx.files.internal("data/beamend2.png"));
		beginRay = new Image(texLaserS2);
		begin = new Image(texLaserS1);
		midRay = new Image(texLaserM2);
		mid = new Image(texLaserM1);
		endRay = new Image(texLaserS2);
		end = new Image(texLaserS1);
	}

	public void render(SpriteBatch batch, float delta) {
		float distance = Vector2.dst(startPoint.x, startPoint.y, endPoint.x,
				endPoint.y);
		degrees = getAngle(startPoint, endPoint);
		begin.setColor(color);
		beginRay.setColor(rayColor);
		mid.setColor(color);
		midRay.setColor(rayColor);
		end.setColor(color);
		endRay.setColor(rayColor);

		if (drawVertice) {
			mid.setSize(mid.getWidth(), distance - begin.getWidth());
			midRay.setSize(midRay.getWidth(), distance - beginRay.getWidth());
		} else {
			mid.setSize(mid.getWidth(), distance);
			midRay.setSize(midRay.getWidth(), distance);
		}

		begin.setPosition(startPoint.x, startPoint.y, Align.center);
		beginRay.setPosition(startPoint.x, startPoint.y, Align.center);
		end.setPosition(endPoint.x, endPoint.y, Align.center);
		endRay.setPosition(endPoint.x, endPoint.y, Align.center);
		mid.setPosition(startPoint.x / 2 + endPoint.x / 2, startPoint.y / 2
				+ endPoint.y / 2, Align.center);
		midRay.setPosition(startPoint.x / 2 + endPoint.x / 2, startPoint.y / 2
				+ endPoint.y / 2, Align.center);

		mid.setOrigin(Align.center);
		midRay.setOrigin(Align.center);
		begin.setOrigin(Align.center);
		beginRay.setOrigin(Align.center);
		end.setOrigin(Align.center);
		endRay.setOrigin(Align.center);

		begin.setRotation(degrees);
		beginRay.setRotation(degrees);
		mid.setRotation(degrees);
		midRay.setRotation(degrees);
		end.setRotation(degrees + 180);
		endRay.setRotation(degrees + 180);

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

		if (drawVertice) {
			if (isGlow)
				begin.draw(batch, 1f);
			beginRay.draw(batch, 1f);
		}
		if (isGlow)
			mid.draw(batch, 1f);
		midRay.draw(batch, 1f);
		if (drawVertice) {
			if (isGlow)
				end.draw(batch, 1f);
			endRay.draw(batch, 1f);
		}
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	public float getAngle(Vector2 start, Vector2 end) {
		float angle = (float) Math.toDegrees(Math.atan2(-end.x + start.x, end.y
				- start.y));
		System.out.println("Angle : " + angle);
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	// public void render(SpriteBatch batch, float delta) {
	// begin1.setColor(color);
	// begin2.setColor(rayColor);
	// mid1.setColor(color);
	// mid2.setColor(rayColor);
	// end1.setColor(color);
	// end2.setColor(rayColor);
	//
	// mid1.setSize(mid1.getWidth(), distance);
	// mid2.setSize(mid1.getWidth(), distance);
	//
	// begin1.setPosition(position.x, position.y);
	// begin2.setPosition(position.x, position.y);
	//
	// mid1.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight());
	// mid2.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight());
	//
	// end1.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight()
	// + mid1.getHeight());
	// end2.setPosition(begin1.getX(), begin1.getY() + begin1.getHeight()
	// + mid1.getHeight());
	//
	// begin1.setOrigin(begin1.getWidth() / 2, 0);
	// begin2.setOrigin(begin1.getWidth() / 2, 0);
	//
	// mid1.setOrigin(mid1.getWidth() / 2, -begin1.getHeight());
	// mid2.setOrigin(mid2.getWidth() / 2, -begin1.getHeight());
	//
	// end1.setOrigin(mid1.getWidth() / 2,
	// -begin1.getHeight() - mid1.getHeight());
	// end2.setOrigin(mid2.getWidth() / 2,
	// -begin1.getHeight() - mid2.getHeight());
	//
	// begin1.setRotation(degrees);
	// begin2.setRotation(degrees);
	// mid1.setRotation(degrees);
	// mid2.setRotation(degrees);
	// end1.setRotation(-degrees);
	// end2.setRotation(-degrees);
	//
	// batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	//
	// begin1.draw(batch, 1f);
	// begin2.draw(batch, 1f);
	// mid1.draw(batch, 1f);
	//
	// mid2.draw(batch, 1f);
	//
	// end1.draw(batch, 1f);
	// end2.draw(batch, 1f);
	// batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	// }
}
