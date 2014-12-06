package utils.factory;

import java.util.Vector;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class OverlapSystem {
	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		if (r1.x < r2.x + r2.width && r1.x + r1.width > r2.x
				&& r1.y < r2.y + r2.height && r1.y + r1.height > r2.y)
			return true;
		else
			return false;
	}

	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.x <= p.x && r.x + r.width >= p.x && r.y <= p.y
				&& r.y + r.height >= p.y;
	}

	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.x <= x && r.x + r.width >= x && r.y <= y
				&& r.y + r.height >= y;
	}

	public static boolean overlapListRectangles(Vector<Rectangle> vectorRec1,
			Vector<Rectangle> vectorRec2) {
		boolean result = false;
		for (int i = 0; i < vectorRec1.size(); i++) {
			for (int j = 0; j < vectorRec2.size(); j++) {
				if (overlapRectangles(vectorRec1.get(i), vectorRec2.get(j)))
					result = true;
			}
		}
		return result;
	}

	public static boolean pointInRectangle(Rectangle r, Vector2 p, Vector2 o) {
		Rectangle r2 = new Rectangle(r.x - o.x / 2, r.y - o.y / 2, r.width
				+ o.x, r.height + o.y);

		return r2.x <= p.x && r2.x + r2.width >= p.x && r2.y <= p.y
				&& r2.y + r2.height >= p.y;
	}
}
