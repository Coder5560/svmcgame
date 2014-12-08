package utils.ui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class PageScrollPaneHorizontal extends ScrollPane {

	boolean			wasPanDragFling	= false;
	private Table	root;
	public Actor	currentPage;
	public int		index			= 0;
	public float	pageSpacing		= 10;
	public float	viewWidth;
	public float	elementWidth;
	public int		elementInWidth;

	public PageScrollPaneHorizontal(int elementInWidth, float viewWidth) {
		super(null);
		this.viewWidth = viewWidth;
		this.elementInWidth = elementInWidth;
		this.elementWidth = viewWidth / elementInWidth;
		setup();
	}

	public PageScrollPaneHorizontal(Skin skin) {
		super(null, skin);
		setup();
	}

	public PageScrollPaneHorizontal(Skin skin, String styleName) {
		super(null, skin, styleName);
		setup();
	}

	public PageScrollPaneHorizontal(Actor widget, ScrollPaneStyle style) {
		super(null, style);
		setup();
	}

	private void setup() {
		root = new Table();
		root.defaults().width(viewWidth).space(10);
		setClamp(true);
		super.setWidget(root);
	}

	public void addPages(Actor... pages) {
		for (Actor page : pages) {
			root.add(page).expandY().fillY();
		}
	}

	public void addPage(Actor page) {
		if (root.getChildren().size == 0) {
			root.add(page).expandY().fillY().padLeft(38);
		} else
			root.add(page).expandY().fillY();
	}

	public Actor getPage(int index) {
		return root.getChildren().get(index);
	}

	public void focusOnPage(int index) {
		this.index = index;
		setScrollX(index * viewWidth);
		scrollToPage();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
			wasPanDragFling = false;
			scrollToPage();
			index = (int) (getScrollX() / viewWidth);
		} else {
			if (isPanning() || isDragging() || isFlinging()) {
				wasPanDragFling = true;
			}
		}

	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		if (root != null) {
			for (Cell<?> cell : root.getCells()) {
				cell.width(width);
			}
			root.invalidate();
		}
	}

	public void setPageSpacing(float pageSpacing) {
		if (root != null) {
			root.defaults().space(pageSpacing);
			for (Cell<?> cell : root.getCells()) {
				cell.space(pageSpacing);
			}
			this.pageSpacing = pageSpacing;
			root.invalidate();
		}
	}

	public void scrollToPage() {
		final float scrollX = getScrollX();
		final float maxX = getMaxX();
		if (scrollX >= maxX || scrollX <= 0)
			return;
		Array<Actor> pages = root.getChildren();
		float pageX = 0;
		float pageWidth = 0;
		if (pages.size > 0) {
			for (Actor a : pages) {
				pageX = a.getX();
				pageWidth = a.getWidth();
				if (scrollX < (pageX + pageWidth * 0.5)) {
					break;
				}
			}

			if (pageX <pageWidth) {
				pageX -= 38;
			}
			setScrollX(MathUtils.clamp(pageX, 0, maxX));
		}
	}

	public Table getRoot() {
		return root;
	}

	public int getCurrentPage() {
		return (int) (getScrollX() / elementWidth);
	}
}