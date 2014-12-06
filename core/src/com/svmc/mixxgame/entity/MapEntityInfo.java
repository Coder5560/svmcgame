package com.svmc.mixxgame.entity;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.attribute.Level;
import com.svmc.mixxgame.attribute.StringSystem;

public class MapEntityInfo {
	public String		name;
	public String		type;
	public float		x;
	public float		y;
	public float		width;
	public float		height;

	public float		density;
	public float		restitution;
	public float		friction;
	public boolean		isSensor;
	public MapObject	object;

	public MapEntityInfo(MapObject mapObject) {
		super();
		this.object = mapObject;
		restitution = getDensity(object);
		density = getDensity(object);
		friction = getFriction(object);
		isSensor = getSensor(object);

	}

	public String getName(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.NAME))
			return Level.DEFAULT_NAME;
		String name = (String) object.getProperties().get(StringSystem.NAME);
		return name;
	}

	public BodyType getType(MapObject object) {
		String type;
		if (!object.getProperties().containsKey(StringSystem.TYPE))
			type = Level.DEFAULT_TYPE;
		else
			type = (String) object.getProperties().get(StringSystem.TYPE);

		return (type.equalsIgnoreCase(StringSystem.BODY_KINEMATIC) ? BodyType.KinematicBody
				: (type.equalsIgnoreCase(StringSystem.BODY_DYNAMIC) ? BodyType.DynamicBody
						: BodyType.StaticBody));
	}

	public float getRestitution(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.F_RESTITUTION))
			return Level.DEFAULT_RESTITUTION;
		String value = (String) object.getProperties().get(
				StringSystem.F_RESTITUTION);
		return Float.parseFloat(value);
	}

	public float getDensity(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.F_DENSITY))
			return Level.DEFAULT_DENSITY;
		String value = (String) object.getProperties().get(
				StringSystem.F_DENSITY);
		return Float.parseFloat(value);
	}

	public float getFriction(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.F_FRICTION))
			return Level.DEFAULT_FRICTION;
		String value = (String) object.getProperties().get(
				StringSystem.F_FRICTION);
		return Float.parseFloat(value);
	}

	public boolean getSensor(MapObject object) {
		if (object.getProperties().containsKey(StringSystem.F_IS_SENSOR)) {
			String value = (String) object.getProperties().get(
					StringSystem.F_IS_SENSOR);
			if (value.equalsIgnoreCase("true"))
				return true;
			if (value.equalsIgnoreCase("false"))
				return false;
		}
		return Level.DEFAULT_SENSOR;
	}

	/* Don't forget to dispose shape when you use it */
	public Shape getShape(MapObject object) {
		if (object instanceof PolygonMapObject) {
			Polygon polygone = ((PolygonMapObject) object).getPolygon();
			float[] vertice = polygone.getVertices();
			for (int k = 0; k < vertice.length; k++) {
				if (k % 2 == 0) {
					vertice[k] = Constants.WORLD_TO_BOX
							* (polygone.getX() + vertice[k]);
				} else {
					vertice[k] = Constants.WORLD_TO_BOX
							* (polygone.getY() + vertice[k]);
				}
			}
			PolygonShape polygoneShape = new PolygonShape();
			polygoneShape.set(vertice);
			return polygoneShape;
		}

		if (object instanceof PolylineMapObject) {
			Polyline polyline = ((PolylineMapObject) object).getPolyline();

			float[] vertice = polyline.getVertices();

			for (int k = 0; k < vertice.length; k++) {
				if (k % 2 == 0) {
					vertice[k] = Constants.WORLD_TO_BOX
							* (polyline.getX() + vertice[k]);
				} else {
					vertice[k] = Constants.WORLD_TO_BOX
							* (polyline.getY() + vertice[k]);
				}
			}
			ChainShape chain = new ChainShape();
			chain.createChain(vertice);
			return chain;
		}

		if (object instanceof EllipseMapObject) {
			Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(Constants.WORLD_TO_BOX * ellipse.width / 2);
			return circleShape;
		}
		if (object instanceof RectangleMapObject) {
			Rectangle rectagle = ((RectangleMapObject) object).getRectangle();
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(rectagle.width * Constants.WORLD_TO_BOX / 2,
					rectagle.height * Constants.WORLD_TO_BOX / 2);
			return polygonShape;
		}
		return null;
	}

	public float getAngle(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.F_ANGLE))
			return Level.DEFAULT_ANGLE;
		String value = (String) object.getProperties()
				.get(StringSystem.F_ANGLE);
		return Float.parseFloat(value);
	}

	public boolean getActive(MapObject object) {
		if (!object.getProperties().containsKey(StringSystem.F_ACTIVE))
			return Level.DEFAULT_ACTIVE;
		return object.getProperties().get(StringSystem.F_ACTIVE, false,
				Boolean.class);
	}

	public Vector2 getPosition(MapObject object) {
		Vector2 position = new Vector2();
		if (object instanceof EllipseMapObject) {
			Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
			position.set((ellipse.x + ellipse.width / 2)
					* Constants.WORLD_TO_BOX, (ellipse.y + ellipse.height / 2)
					* Constants.WORLD_TO_BOX);
		}
		if (object instanceof RectangleMapObject) {
			Rectangle rectagle = ((RectangleMapObject) object).getRectangle();
			position.set((rectagle.x + rectagle.width / 2)
					* Constants.WORLD_TO_BOX,
					(rectagle.y + rectagle.height / 2) * Constants.WORLD_TO_BOX);
		}
		if (object instanceof PolygonMapObject) {
			// =============== add in fixtureDef ===========
		}

		if (object instanceof PolylineMapObject) {
			// =============== add in fixtureDef ===========
		}

		return position;
	}

	public FixtureDef getDefaultFixtureDef(MapObject object) {
		FixtureDef fixtureDef = new FixtureDef();
		Shape shape = getShape(object);
		fixtureDef.shape = shape;
		fixtureDef.density = getDensity(object);
		fixtureDef.restitution = getRestitution(object);
		fixtureDef.friction = getFriction(object);
		fixtureDef.isSensor = getSensor(object);
		shape.dispose();
		return fixtureDef;
	}

	public BodyDef getDefaultBodyDef(MapObject object) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.angle = getAngle(object);
		bodyDef.type = getType(object);
		bodyDef.position.set(getPosition(object));
		bodyDef.active = getActive(object);
		return bodyDef;
	}

	public Body createBodyBydefault(World world, MapObject object) {
		Body body = world.createBody(getDefaultBodyDef(object));
		body.createFixture(getDefaultFixtureDef(object));
		return body;
	}
}
