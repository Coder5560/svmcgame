package com.svmc.mixxgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class AssetGame implements Disposable {
	private TextureAtlas	textureAtlas;

	private TextureRegion	background;
	private TextureRegion	force_background;
	private TextureRegion	logo;
	private TextureRegion	menu;
	private TextureRegion	menu_ball;
	private TextureRegion	menu_level;
	private TextureRegion	rect;
	private TextureRegion	reg_hex_tile;

	private TextureRegion	reg_cursor_normal;
	private TextureRegion	reg_cursor_highlight;

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

	public TextureRegion getCursorNormal() {
		if (reg_cursor_normal == null)
			reg_cursor_normal = textureAtlas.findRegion("ui/cursor_normal");
		return reg_cursor_normal;
	}

	public TextureRegion getCursorHighlight() {
		if (reg_cursor_highlight == null)
			reg_cursor_highlight = textureAtlas
					.findRegion("ui/cursor_higlight");
		return reg_cursor_highlight;
	}

	public void resetAll() {
		background = null;
	}

	@Override
	public void dispose() {
		background.getTexture().dispose();
	}

	public TextureRegion getHexTile() {
		if (reg_hex_tile == null)
			reg_hex_tile = textureAtlas.findRegion("ui/hex-tile");
		return reg_hex_tile;
	}
	public TextureRegion getLogo() {
		if (logo == null)
			logo = textureAtlas.findRegion("ui/logo");
		return logo;
	}

	public TextureRegion getForceBackground() {
		if (force_background == null)
			force_background = textureAtlas.findRegion("ui/force");
		return force_background;
	}
}
