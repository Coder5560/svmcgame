package com.svmc.mixxgame.entity;


import utils.factory.OverlapSystem;

import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.svmc.mixxgame.attribute.Constants;

public class GeomatricObject {
	public Rectangle		rectangle	= null;
	public Ellipse			ellipse		= null;
	public Polygon			polygon		= null;
	public Polyline			polyline	= null;
	public String			name		= "";

	public enum GeomatricObjectType {
		RECTANGLE, ELLIPSE, POLYGON, POLYLINE
	}

	public GeomatricObjectType	type;

	public GeomatricObject(GeomatricObject object) {
		this.rectangle = object.rectangle;
		this.ellipse = object.ellipse;
		this.polygon = object.polygon;
		this.polyline = object.polyline;
		this.type = object.type;
	}

	public GeomatricObject buid(Rectangle rectangle) {
		this.rectangle = rectangle;
		type = GeomatricObjectType.RECTANGLE;
		return this;
	}

	public GeomatricObject buid(Polygon polygon) {
		this.polygon = polygon;
		type = GeomatricObjectType.POLYGON;
		return this;
	}

	public GeomatricObject buid(Polyline polyline) {
		this.polyline = polyline;
		type = GeomatricObjectType.POLYLINE;
		return this;
	}

	public GeomatricObject build(Ellipse ellipse) {
		this.ellipse = ellipse;
		type = GeomatricObjectType.ELLIPSE;
		return this;
	}

	public GeomatricObject(Rectangle rectangle) {
		this.rectangle = rectangle;
		type = GeomatricObjectType.RECTANGLE;
	}

	public GeomatricObject(Polygon polygon) {
		this.polygon = polygon;
		type = GeomatricObjectType.POLYGON;
	}

	public GeomatricObject(Polyline polyline) {
		this.polyline = polyline;
		this.polygon = new Polygon(polyline.getVertices());
		type = GeomatricObjectType.POLYLINE;
	}

	public GeomatricObject(Ellipse ellipse) {
		this.ellipse = ellipse;
		type = GeomatricObjectType.ELLIPSE;
	}

	public boolean hit(float x, float y, Vector2 offset) {
		if (type == GeomatricObjectType.RECTANGLE) {
			if (OverlapSystem.pointInRectangle(rectangle, new Vector2(x, y), offset))
				return true;
		}
		if (type == GeomatricObjectType.ELLIPSE) {
			Rectangle rectangle = new Rectangle(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
			if (OverlapSystem.pointInRectangle(rectangle, new Vector2(x, y), offset))
				return true;
		}
		if (type == GeomatricObjectType.POLYGON) {
			if (OverlapSystem.pointInRectangle(polygon.getBoundingRectangle(), new Vector2(x, y), offset)) {
				return true;
			}
		}

		if (type == GeomatricObjectType.POLYLINE) {
			Polygon pg = new Polygon(polyline.getTransformedVertices());
			if (OverlapSystem.pointInRectangle(pg.getBoundingRectangle(), new Vector2(x, y), offset)) {
				return true;
			}
		}
		return false;
	}

	public void setPosition(float x, float y, float ofsetX, float ofsetY) {
		if (type == GeomatricObjectType.RECTANGLE) {
			rectangle.setPosition(x + ofsetX - rectangle.width / 2, y + ofsetY - rectangle.height / 2);
		}
		if (type == GeomatricObjectType.ELLIPSE) {
			ellipse.setPosition(x + ofsetX - ellipse.width / 2, y + ofsetY - ellipse.height / 2);
		}
		if (type == GeomatricObjectType.POLYGON) {
			polygon.setPosition(x + ofsetX + polygon.getX() - polygon.getBoundingRectangle().x - polygon.getBoundingRectangle().width / 2,
					y + ofsetY + polygon.getY() - polygon.getBoundingRectangle().y - polygon.getBoundingRectangle().height / 2);
		}
		if (type == GeomatricObjectType.POLYLINE) {
			Polygon rect = new Polygon(polyline.getTransformedVertices());
			polyline.setPosition(x + ofsetX + polyline.getX() - rect.getBoundingRectangle().x - rect.getBoundingRectangle().width / 2,
					y + ofsetY + polyline.getY() - rect.getBoundingRectangle().y - rect.getBoundingRectangle().height / 2);
		}
	}

	public void setPosition(float x, float y) {
		if (type == GeomatricObjectType.RECTANGLE) {
			rectangle.setPosition(x - rectangle.width / 2, y - rectangle.height / 2);
		}
		if (type == GeomatricObjectType.ELLIPSE) {
			ellipse.setPosition(x - ellipse.width / 2, y - ellipse.height / 2);
		}
		if (type == GeomatricObjectType.POLYGON) {
			polygon.setPosition(x + polygon.getX() - polygon.getBoundingRectangle().x - polygon.getBoundingRectangle().width / 2,
					y + polygon.getY() - polygon.getBoundingRectangle().y - polygon.getBoundingRectangle().height / 2);
		}
		if (type == GeomatricObjectType.POLYLINE) {
			Polygon rect = new Polygon(polyline.getTransformedVertices());
			polyline.setPosition(x + polyline.getX() - rect.getBoundingRectangle().x - rect.getBoundingRectangle().width / 2,
					y + polyline.getY() - rect.getBoundingRectangle().y - rect.getBoundingRectangle().height / 2);
		}
	}

	public Vector2 getPosition() {
		return getCenter();
	}

	public float[] getPolylineVertice() {
		float[] vertice = polyline.getVertices();
		for (int k = 0; k < vertice.length; k++) {
			if (k % 2 == 0) {
				vertice[k] = (polyline.getX() + vertice[k]);
			} else {
				vertice[k] = (polyline.getY() + vertice[k]);
			}
		}
		return vertice;
	}

	public GeomatricObject clone() {
		switch (type) {
			case ELLIPSE:
				return new GeomatricObject(ellipse);
			case RECTANGLE:
				return new GeomatricObject(rectangle);
			case POLYGON:
				float[] vertice = polygon.getVertices();
				for (int i = 0; i < vertice.length; i++) {
					vertice[i] *= Constants.WORLD_TO_BOX;
				}
				Polygon newPolygon = new Polygon(vertice);
				newPolygon.setPosition(polygon.getX() * Constants.WORLD_TO_BOX, polygon.getY() * Constants.WORLD_TO_BOX);
				return new GeomatricObject(newPolygon);

			case POLYLINE:
				float[] vertices = polyline.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					vertices[i] *= Constants.WORLD_TO_BOX;
				}
				Polyline newPolyline = new Polyline(vertices);
				newPolyline.setPosition(polyline.getX() * Constants.WORLD_TO_BOX, polyline.getY() * Constants.WORLD_TO_BOX);
				return new GeomatricObject(newPolyline);
			default:
				return null;
		}
	}

	public Rectangle getBound() {
		switch (type) {
			case ELLIPSE:
				if (ellipse != null)
					return new Rectangle(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
			case RECTANGLE:
				if (rectangle != null)
					return rectangle;
			case POLYGON:
				if (polygon != null)
					return polygon.getBoundingRectangle();
			case POLYLINE:
				if (polyline != null) {
					Polygon polygon = new Polygon(polyline.getTransformedVertices());
					return polygon.getBoundingRectangle();
				}
			default:
				return null;
		}
	}

	public Vector2 getCenter() {
		Rectangle bound = getBound();
		return new Vector2(bound.x + bound.width / 2, bound.y + bound.height / 2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
