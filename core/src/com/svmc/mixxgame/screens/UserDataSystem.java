package com.svmc.mixxgame.screens;

import utils.listener.OnDoneListener;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.svmc.mixxgame.entity.UserData;

public class UserDataSystem {

	Array<UserData>	userDatas	= new Array<UserData>();
	boolean			show		= true;

	public void update(float delta) {
		for (UserData userData : userDatas) {
			userData.update(delta);
		}
	}

	public void render(SpriteBatch batch, float delta) {
		for (UserData userData : userDatas) {
			userData.render(batch, delta);
		}
	}

	public void show(OnDoneListener listener) {
		show = true;
		for (int i = 0; i < userDatas.size; i++) {
			UserData data = userDatas.get(i);
			if (i == userDatas.size - 1) {
				data.show(listener);
			} else
				data.show(null);
		}
	}

	public void hide(OnDoneListener listener) {
		show = false;
		for (int i = 0; i < userDatas.size; i++) {
			UserData data = userDatas.get(i);
			if (i == userDatas.size - 1) {
				data.hide(listener);
			} else
				data.hide(null);
		}

	}

	public boolean isShow() {
		return show;
	}

}
