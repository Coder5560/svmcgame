package com.svmc.mixxgame.screens;

import utils.listener.FocusListener;
import utils.listener.OnClickListener;
import utils.listener.OnDoneListener;
import utils.ui.GalleryViewHorizontal;
import utils.ui.Img;
import utils.ui.ImgLevel;
import utils.ui.PointView;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.attribute.Direct;

public class SelectLevel {
	Table					container;
	GalleryViewHorizontal	galleryViewHorizontal;
	private Stage			stage;
	boolean					show		= false;
	Array<ImgLevel>			listCurrent	= new Array<ImgLevel>();
	OnClickListener			selectLevelListener;
	PointView				pointView;

	public SelectLevel(Stage stage, OnClickListener listener) {
		super();
		this.selectLevelListener = listener;
		this.stage = stage;
		create();
		buildList();
		container.setVisible(false);
		show = false;

		pointView = new PointView(container, 4, Direct.BOTTOM,
				new FocusListener() {
					@Override
					public void touchIndex(int index) {
						galleryViewHorizontal.pages.focusOnPage(index);
					}
				});
		pointView.build();
		pointView.setHeight(20);
		pointView.hide(null);
	}

	public void create() {
		container = new Table();
		container.setSize(2 * Constants.WIDTH_SCREEN / 3,
				2 * Constants.HEIGHT_SCREEN / 3);
		container.setPosition(Constants.WIDTH_SCREEN / 2,
				Constants.HEIGHT_SCREEN / 2, Align.center);
		galleryViewHorizontal = new GalleryViewHorizontal(container, 1);
		stage.addActor(container);
	}

	public void buildList() {
		for (int i = 0; i < 4; i++) {
			Table page = galleryViewHorizontal.newPage();
			buildPage(page, 1 + i);
		}
	}

	private void buildPage(final Table page, int index) {
		for (int i = 0; i < 8; i++) {
			Img bg = new Img(Assets.instance.game.getHexTile());
			bg.setSize(80, 80);
			final int id = i + 1 + (index - 1) * 8;
			Img level = new Img(Assets.instance.strings.get("" + id));
			final ImgLevel imgLevel = new ImgLevel(bg, level);
			if (id > 9)
				imgLevel.scaleText.set(0.7f, 0.7f);
			if (id == 9)
				imgLevel.scaleText.set(0.45f, 0.45f);
			imgLevel.setIndex(id);
			imgLevel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(int count) {
					hide(new OnDoneListener() {
						@Override
						public void done() {
							selectLevelListener.onClick(id);
						}
					});
				}
			});
			if (index == 1) {
				page.add(imgLevel).width(80).height(80).pad(10);
			} else {
				page.add(imgLevel).width(80).height(80).pad(10);
			}
			if (i == 3)
				page.row();
		}
	}

	public void update(float delta) {
		if (container.isVisible()) {
			pointView.update();
			pointView.setIndex(galleryViewHorizontal.pages.index);
		}
	}

	public void show(final OnDoneListener listener) {
		buildCurrent();
		for (ImgLevel level : listCurrent) {
			level.setVisible(false);
		}
		show = true;
		container.setVisible(true);
		for (int i = 0; i < listCurrent.size; i++) {
			final ImgLevel level = listCurrent.get(i);
			final int index = i;
			level.addAction(Actions.sequence(Actions.moveBy(
					Constants.WIDTH_SCREEN, 0, .4f), Actions
					.run(new Runnable() {
						@Override
						public void run() {
							level.setVisible(true);
						}
					}), Actions.delay(0.1f * i % 3), Actions.moveBy(
					-Constants.WIDTH_SCREEN, 0, .8f, Interpolation.exp10Out),
					Actions.run(new Runnable() {

						@Override
						public void run() {
							if (index == listCurrent.size - 1) {
								if (listener != null)
									listener.done();
								pointView.show(null);
							}
						}
					})));
		}
	}

	public void hide(final OnDoneListener listener) {
		pointView.hide(null);
		buildCurrent();
		show = false;
		for (int i = 0; i < listCurrent.size; i++) {
			final int index = i;
			final ImgLevel level = listCurrent.get(i);
			level.addAction(Actions.sequence(Actions.delay(0.1f * i % 3),
					Actions.moveBy(-Constants.WIDTH_SCREEN, 0, .4f,
							Interpolation.exp10In), Actions.run(new Runnable() {
						@Override
						public void run() {
							if (index == listCurrent.size - 1) {
								container.setVisible(false);
								if (listener != null)
									listener.done();

								for (ImgLevel img : listCurrent) {
									img.addAction(Actions.moveBy(
											Constants.WIDTH_SCREEN, 0));
								}
							}
						}
					})));
		}
	}

	public boolean isShowing() {
		return show;
	}

	public void buildCurrent() {
		listCurrent.clear();
		Table page = galleryViewHorizontal.getPage(galleryViewHorizontal.pages
				.getCurrentPage());
		for (Actor actor : page.getChildren()) {
			if (actor instanceof ImgLevel) {
				listCurrent.add((ImgLevel) actor);
			}
		}
	}

}
