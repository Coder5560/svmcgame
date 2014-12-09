package com.svmc.mixxgame;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.svmc.mixxgame.attribute.Level;

public class AssetMap {
	TiledMap	map;

	public TiledMap getMap() {
		loadLevel(3);
		return map;
	}

	public void loadLevel(int level) {
		level = MathUtils.clamp(level, 1, Level.getMaxLevel());
		map = new TmxMapLoader().load(getTexureByleve(Level.getLevel()));
	}

	public static boolean isContainLayer(TiledMap map, String layerName) {
		for (int i = 0; i < map.getLayers().getCount(); i++) {
			if (map.getLayers().get(i).getName().equalsIgnoreCase(layerName)) {
				return true;
			}
		}
		return false;
	}

	public String getTexureByleve(int level) {
		return "maps/map_" + level + ".tmx";
	}
}
