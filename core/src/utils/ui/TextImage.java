package utils.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class TextImage extends Image {
	public Label	lbText;
	public float	orginTextX, orginTextY;

	public TextImage(NinePatch patch, Label text) {
		super(patch);
		this.lbText = text;
		setOrigin(Align.center);
		orginTextX = 1 / 2f;
		orginTextY = 1 / 2f;
	}

	public TextImage(TextureRegion region, Label text) {
		super(region);
		this.lbText = text;
		setOrigin(Align.center);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		lbText.act(delta);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		lbText.setVisible(visible);
	}

	@Override
	public void setTouchable(Touchable touchable) {
		super.setTouchable(touchable);
		lbText.setTouchable(touchable);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validateText();
		super.draw(batch, parentAlpha);
		lbText.draw(batch, parentAlpha);
	}

	public void setText(String text) {
		lbText.setText(text);
		validateText();
	}

	public void validateText() {
		lbText.setWrap(true);
		lbText.setColor(lbText.getColor().r, lbText.getColor().g,
				lbText.getColor().b, getColor().a);
		lbText.setBounds(getX(), getY(), getWidth(), getHeight());
		lbText.setScale(getScaleX(), getScaleY());
		lbText.setPosition(getX() + orginTextX * getWidth(), getY()
				+ orginTextY * getHeight(), Align.center);
	}

	public void setOriginText(float originX, float originY) {
		this.orginTextX = originX;
		this.orginTextY = originY;
	}

}
