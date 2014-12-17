package com.svmc.mixxgame.screens;

import utils.screen.AbstractGameScreen;
import utils.screen.GameCore;
import utils.ui.Laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TestScreen extends AbstractGameScreen {
	LineHandler		handler;

	Array<Laser>	lasers	= new Array<Laser>();

	public TestScreen(GameCore game) {
		super(game);
//		createLaser();
		
		Array<Vector2> vertice= new Array<Vector2>(); 
		vertice.add(new Vector2(100, 100));
		vertice.add(new Vector2(300, 100));
		vertice.add(new Vector2(300, 300));
		vertice.add(new Vector2(100, 300));
		createLaser(vertice);
	}

	void createLaser() {
		lasers.clear();
		Vector2 point1 = new Vector2(100, 100);
		Vector2 point2 = new Vector2(100, 200);
		Vector2 point3 = new Vector2(200, 200);
		Vector2 point4 = new Vector2(200, 100);
		Laser laser, laser2, laser3, laser4;
		laser = new Laser();
		laser.startPoint.set(point1);
		laser.endPoint.set(point2);
		laser.drawVertice = false;

		laser3 = new Laser();
		laser3.startPoint.set(point3);
		laser3.endPoint.set(point4);
		laser3.drawVertice = false;

		laser2 = new Laser();
		laser2.startPoint.set(point2);
		laser2.endPoint.set(point3);
		laser2.drawVertice = false;

		laser4 = new Laser();
		laser4.startPoint.set(point4);
		laser4.endPoint.set(point1);
		laser4.drawVertice = false;

		lasers.add(laser);
		lasers.add(laser2);
		lasers.add(laser3);
		lasers.add(laser4);
	}

	void createLaser(Array<Vector2> vertice) {
		lasers.clear();
		if (vertice.size <= 1)
			return;
		for (int i = 0; i < vertice.size - 1; i++) {
			Laser laser = new Laser(vertice.get(i), vertice.get(i+1), false);
			lasers.add(laser);
		}
		Laser laser = new Laser(vertice.get(vertice.size-1), vertice.get(0), false);
		lasers.add(laser);
	}

	@Override
	public void show() {
		super.show();
		handler = new LineHandler();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void drawBatch(SpriteBatch batch) {
		float delta = Gdx.graphics.getDeltaTime();
		for (Laser laser : lasers) {
			laser.render(batch, delta);
		}

	}

	@Override
	public void drawShapeFill(ShapeRenderer shapeRenderer) {
		handler.fill(shapeRenderer);

	}

	@Override
	public void drawShapeLine(ShapeRenderer shapeRenderer) {
		handler.line(shapeRenderer);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		viewport.unproject(touchPoint.set(screenX, screenY));
		handler.addPoint(new Vector2(touchPoint));
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Keys.E:
				handler.points.clear();
				break;

			default:
				break;
		}

		return true;
	}

	public class LineHandler {
		Array<Vector2>	points;

		float			r		= 10;
		float			smooth	= 2;

		Color			colorFill;
		Color			colorLine;

		public LineHandler() {
			super();
			points = new Array<Vector2>();
			colorFill = new Color(0 / 255f, 255 / 255f, 0 / 255f, 1f);
			colorLine = new Color(0 / 255f, 0 / 255f, 255 / 255f, 1f);
		}

		public void fill(ShapeRenderer render) {
			if (!avaiable())
				return;
			render.setColor(colorFill);
			for (Vector2 point : points) {
				render.circle(point.x, point.y, r);
			}

		}

		public void line(ShapeRenderer render) {
			if (!avaiable() && points.size < 2)
				return;
			render.setColor(colorLine);
			for (int i = 0; i < points.size - 1; i++) {
				render.line(points.get(i), points.get(i + 1));
			}
		}

		public boolean addPoint(Vector2 point) {
			if (avaiable()) {
				if (points.size == 0) {
					points.add(point);
					return true;
				}

				if (point.dst(points.get(points.size - 1)) >= smooth) {
					points.add(point);
					return true;
				}
			}
			return false;
		}

		public boolean avaiable() {
			return points != null;
		}

		public void renderSmoothLine(ShapeRenderer shaperender,
				Array<Vector2> controlPoints) {

			if (controlPoints.size < 4)
				return;
			CatmullRomSpline<Vector2> path = new CatmullRomSpline<Vector2>();
			Vector2 point1 = new Vector2(), point2 = new Vector2();
			Gdx.gl.glClearDepthf(1f);
			Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
			Gdx.gl.glDepthFunc(GL20.GL_LESS);
			Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
			Gdx.gl.glDepthMask(true);
			Gdx.gl.glColorMask(false, false, false, false);
			shaperender.begin(ShapeType.Filled);
			shaperender.setColor(0f, 0f, 0f, 0f);

			Vector2[] array = new Vector2[controlPoints.size];
			System.arraycopy(controlPoints.items, 0, array, 0, array.length);
			path.set(array, false);
			float t = 0;
			float step = 0.01f;
			float begin = -Float.MAX_VALUE;
			while (t < 1) {
				point1 = path.valueAt(point1, t);
				t += step;
				point2 = path.valueAt(point2, t);
				if (point1.x > point2.x || point1.x < begin) {
					// break;
					continue;
				}
				begin = point1.x;
				if (t < 1) {
					shaperender.triangle(point1.x, point1.y, point2.x,
							point2.y, point1.x, camera.position.y
									- camera.viewportHeight / 2);
					shaperender.triangle(point2.x, point2.y, point1.x,
							camera.position.y - camera.viewportHeight / 2,
							point2.x, camera.position.y - camera.viewportHeight
									/ 2);
				}
			}
			shaperender.end();
			Gdx.gl.glColorMask(true, true, true, true);
			Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
			shaperender.begin(ShapeType.Filled);
			shaperender.setColor(Color.WHITE);
			shaperender.rect(camera.position.x - camera.viewportWidth / 2,
					camera.position.y - camera.viewportHeight / 2,
					camera.viewportWidth, camera.viewportHeight);
			shaperender.end();
			Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

			shaperender.begin(ShapeType.Line);
			shaperender.setColor(Color.RED);
			t = 0;
			step = 0.01f;
			begin = -Float.MAX_VALUE;
			while (t < 1) {
				point1 = path.valueAt(point1, t);
				t += step;
				point2 = path.valueAt(point2, t);
				if (point1.x > point2.x || point1.x < begin) {
					break;
				}
				begin = point1.x;
				if (t < 1) {
					shaperender.line(point1, point2);
					for (int i = 0; i < 2; i++) {
						point1.y += 0.5f;
						point2.y += 0.5f;
						shaperender.line(point1, point2);
					}
				}
			}
			shaperender.end();
		}

	}
}
