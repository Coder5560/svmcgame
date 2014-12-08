package utils.ui;

import utils.listener.OnClickListener;
import utils.listener.OnDoneListener;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.sun.javafx.geom.Vec2d;

public class ImgLevel extends Group {
	public Img		background;
	public Img		level;
	public boolean	show		= false;
	private int		index;
	OnClickListener	listener;
	public Vector2	scaleText	= new Vector2(0.5f, 0.5f);

	public ImgLevel(Img background, Img level) {
		super();
		this.background = background;
		this.level = level;
		background.setOrigin(Align.center);
		level.setOrigin(Align.center);
		addActor(level);
		addActor(background);
		buildListener();
	}

	public void buildListener() {
		level.setTouchable(Touchable.disabled);
		background.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				background.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .1f),
						Actions.scaleTo(1f, 1f, .2f, Interpolation.swingOut),
						Actions.run(new Runnable() {

							@Override
							public void run() {
								if (listener != null) {
									listener.onClick(OnClickListener.NONE);
									background.addAction(Actions.sequence(
											Actions.touchable(Touchable.disabled),
											Actions.delay(
													.1f,
													Actions.touchable(Touchable.enabled))));
								}
							}
						})));
				level.addAction(Actions.sequence(
						Actions.scaleTo(1.2f, 1.2f, .1f),
						Actions.scaleTo(1f, 1f, .2f, Interpolation.swingOut)));
				super.clicked(event, x, y);
			}
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validatePosition();
		super.draw(batch, parentAlpha);
	}

	private void validatePosition() {
		level.setSize(background.getWidth() * scaleText.x,
				background.getHeight() * scaleText.y);
		level.setPosition(background.getX() + background.getWidth() / 2,
				background.getY() + background.getHeight() / 2, Align.center);
	}

	public void show(OnDoneListener listener) {
		show = true;
		background.setVisible(show);
		level.setVisible(show);
		setVisible(show);
		listener.done();
	}

	public void hide(final OnDoneListener listener) {
		show = false;
		background.addAction(Actions.sequence(Actions.scaleTo(1f, 1f),
				Actions.scaleTo(0f, 0f, .5f, Interpolation.swingIn),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						if (listener != null)
							listener.done();
					}
				})));
		level.addAction(Actions.sequence(Actions.scaleTo(1f, 1f),
				Actions.scaleTo(0f, 0f, .5f, Interpolation.swingIn)));

	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	public int getIndex() {
		return index;
	}

	public void reset(boolean show) {
		this.show = show;
		background.clearActions();
		level.clearActions();
		background.setVisible(show);
		level.setVisible(show);
		setVisible(show);
		background.addAction(Actions.scaleTo(1f, 1f));
		level.addAction(Actions.scaleTo(1f, 1f));
		if (show) {
			background.setTouchable(Touchable.enabled);
		} else {
			background.setTouchable(Touchable.disabled);
		}
	}
}
