package com.svmc.mixxgame;

import utils.factory.FontFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.svmc.mixxgame.attribute.Constants;

public class Assets implements Disposable, AssetErrorListener {
	public static final String	TAG			= Assets.class.getName();
	public static final Assets	instance	= new Assets();

	public AssetManager			assetManager;
	public AssetUI				ui;
	public AssetGame			game;
	public AssetString			strings;
	public AssetMap				assetMap;

	public FontFactory			fontFactory;

	private Assets() {
		assetManager = new AssetManager();
		assetManager.setErrorListener(this);
		load();
	}

	public void load() {
		assetManager
				.load(Constants.TEXTURE_ATLAS_NINEPATCH, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_UI, TextureAtlas.class);
		assetManager.finishLoading();
	}

	public void init() {
		TextureAtlas atlasNinePatch = assetManager
				.get(Constants.TEXTURE_ATLAS_NINEPATCH);
		TextureAtlas atlasUI = assetManager.get(Constants.TEXTURE_ATLAS_UI);

		for (Texture t : atlasNinePatch.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		for (Texture t : atlasUI.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}

		ui = new AssetUI(atlasNinePatch);
		game = new AssetGame(atlasUI);
		strings = new AssetString(atlasUI);
		fontFactory = new FontFactory(assetManager);
		assetMap = new AssetMap();
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
				(Exception) throwable);
	}
}
