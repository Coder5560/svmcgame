package com.svmc.mixxgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class AssetGame implements Disposable {
	private TextureAtlas	textureAtlas;

	private TextureRegion	background;
	private TextureRegion	menu;
	private TextureRegion	menu_ball;
	private TextureRegion	menu_level;
	private TextureRegion	rect;

	public AssetGame(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	public TextureRegion getBackground() {
		if (background == null)
			background = textureAtlas.findRegion("ui/background");
		return background;
	}

	public TextureRegion getMenuButton() {
		if (menu == null)
			menu = textureAtlas.findRegion("ui/ui_menu");
		return menu;
	}

	public TextureRegion getMenuBall() {
		if (menu_ball == null)
			menu_ball = textureAtlas.findRegion("ui/ui_ball");
		return menu_ball;
	}

	public TextureRegion getMenuLevel() {
		if (menu_level == null)
			menu_level = textureAtlas.findRegion("ui/ui_level");
		return menu_level;
	}

	public TextureRegion getRectangle(Rectangle rectangle) {
		if (rect == null)
			rect = textureAtlas.findRegion("ui/rect1");
		return rect;
	}
	
	public void resetAll() {
		background = null;
	}
 
	@Override
	public void dispose() {
		background.getTexture().dispose();
	}
	
}
