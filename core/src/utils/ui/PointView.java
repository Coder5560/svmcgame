package utils.ui;

import java.util.ArrayList;

import utils.listener.FocusListener;
import utils.listener.OnDoneListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Direct;

public class PointView {

	ArrayList<Img>	list;
	public int		index;
	public Img		imgCursor;
	public int		size;
	public Table	root;
	public Direct	direct;
	public Color	color;
	float			height	= 20;
	FocusListener	_listener;
	boolean			show	= false;

	public PointView(Table root, int size, Direct direct, FocusListener listener) {
		super();
		this.root = root;
		this.size = size;
		this.direct = direct;
		this._listener = listener;
	}

	public void build() {
		if (list != null) {
			for (Img img : list) {
				root.removeActor(img);
			}
			list.clear();
		}

		list = new ArrayList<Img>();
		for (int i = 0; i < size; i++) {
			Img element = new Img(Assets.instance.game.getCursorNormal());
			element.setSize(24, 24);
			element.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					_listener.touchIndex(index);
				}
			});
			list.add(element);
			root.addActor(element);
		}
		validatePosition();
		imgCursor = new Img(Assets.instance.game.getCursorHighlight());
		imgCursor.setSize(24, 24);
		imgCursor.setPosition(list.get(0).getPosition().x, list.get(0)
				.getPosition().y);
		setIndex(0);
		root.addActor(imgCursor);

	}

	public void build(Color cirle, Color cursor) {
		if (list != null) {
			for (Img img : list) {
				root.removeActor(img);
			}
			list.clear();
		}

		list = new ArrayList<Img>();
		for (int i = 0; i < size; i++) {
			final int index = i;
			Img element = new Img(Assets.instance.game.getCursorNormal());
			element.setColor(cirle);
			element.setSize(24, 24);
			element.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					_listener.touchIndex(index);
				}
			});
			list.add(element);
			root.addActor(element);
		}
		validatePosition();
		imgCursor = new Img(Assets.instance.game.getCursorHighlight());
		imgCursor.setSize(24, 24);
		imgCursor.setColor(cursor);
		if (list.size() > 0) {
			imgCursor.setPosition(list.get(0).getPosition().x, list.get(0)
					.getPosition().y);
			setIndex(0);
		}
		root.addActor(imgCursor);

	}

	public void setHeight(float height) {
		this.height = height;
		validatePosition();
	}

	public void validatePosition() {
		float pad = 10;
		float elementWidth = 16;
		Vector2 center = new Vector2(root.getWidth() / 2, height);
		Vector2 rootLeft = new Vector2(root.getWidth() / 2, height);
		int inHalf = size / 2;
		if (size % 2 == 0) {
			rootLeft.y = center.y;
			rootLeft.x = center.x
					- (inHalf * elementWidth + (inHalf - 0.5f) * pad);
		} else {
			rootLeft.y = center.y;
			rootLeft.x = center.x
					- ((inHalf + 0.5f) * elementWidth + (inHalf) * pad);
		}

		for (int i = 0; i < size; i++) {
			list.get(i).setPosition(rootLeft.x + i * (elementWidth + pad),
					rootLeft.y);
		}
	}

	public void setSize(int size) {
		this.size = size;
		build();
	}

	public void update() {
		if (list != null && list.size() > index)
			imgCursor.setPosition(new Vector2(list.get(index).getPosition().x,
					list.get(index).getPosition().y));
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setColor(Color color) {
		imgCursor.setColor(color);
	}

	public void show(OnDoneListener listener) {
		show = true;
		for (Img img : list) {
			img.setVisible(show);
		}
		imgCursor.setVisible(show);
		if (listener != null)
			listener.done();
	}

	public void hide(OnDoneListener listener) {
		show = false;
		for (Img img : list) {
			img.setVisible(show);
		}
		imgCursor.setVisible(show);
		if (listener != null)
			listener.done();
	}

	public boolean isShow() {
		return show;
	}
}
