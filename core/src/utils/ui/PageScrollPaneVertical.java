package utils.ui;

import utils.listener.ScrollListenner;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class PageScrollPaneVertical extends CustomScroll {

	boolean			wasPanDragFling	= false;
	private Table	content;
	public Actor	currentPage;
	public int		index			= 1;
	public float	pageSpacing		= 10;
	public float	viewHeight;
	public float	elementHeight;
	public int		elementInHeight;

	public PageScrollPaneVertical(int elementInHeight, float viewHeight) {
		super(null);
		this.viewHeight = viewHeight;
		this.elementInHeight = elementInHeight;
		this.elementHeight = viewHeight / elementInHeight;
		setup();
	}

	public PageScrollPaneVertical(int elementInHeight, float viewHeight,
			boolean circle) {
		super(null);
		this.viewHeight = viewHeight;
		this.elementInHeight = elementInHeight;
		this.elementHeight = viewHeight / elementInHeight;
		setup();
	}

	public PageScrollPaneVertical(Skin skin) {
		super(null, skin);
		setup();
	}

	public PageScrollPaneVertical(Skin skin, String styleName) {
		super(null, skin, styleName);
		setup();
	}

	public PageScrollPaneVertical(Actor widget, ScrollPaneStyle style) {
		super(null, style);
		setup();
	}

	public void elementValidate() {
	}

	private void setup() {
		content = new Table();
		content.defaults().height(viewHeight).space(10);
		setScrollListenner(new ScrollListenner() {
			@Override
			public void onOverUp(float amount) {
				focusOnPage(content.getChildren().size
						- (2 * elementInHeight - 1));
			}

			@Override
			public void onOverRight(float amount) {

			}

			@Override
			public void onOverLeft(float amount) {

			}

			@Override
			public void onOverDown(float amount) {
				focusOnPage(elementInHeight + 1);
			}
		});

		super.setWidget(content);
	}

	public void addPages(Actor... pages) {
		for (Actor page : pages) {
			content.add(page).expandX().fillX();
		}
	}

	public void addPage(Actor page) {
		content.add(page).expandX().fillX();
		content.row();
	}

	public Actor getPage(int index) {
		return content.getChildren().get(index);
	}

	public void focusOnPage(int index) {
		Array<Actor> pages = content.getChildren();
		Actor a = pages.get(index % pages.size);
		scrollToCenter(a.getX(), a.getY() + pageSpacing, a.getWidth(),
				a.getHeight());
		scrollToPage();
	}

	public void focusOnPageNoFiling(int index) {
		setSmoothScrolling(false);
		Array<Actor> pages = content.getChildren();
		Actor a = pages.get(index % pages.size);
		scrollToCenter(a.getX(), a.getY() + pageSpacing, a.getWidth(),
				a.getHeight());
		scrollToPage();
		act(0.016f);
		setSmoothScrolling(true);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
			wasPanDragFling = false;
			scrollToPage();
		} else {
			if (isPanning() || isDragging() || isFlinging()) {
				wasPanDragFling = true;
			}
		}
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		if (content != null) {
			for (Cell<?> cell : content.getCells()) {
				cell.height(height);
			}
			content.invalidate();
		}
	}

	public void setPageSpacing(float pageSpacing) {
		if (content != null) {
			content.defaults().space(pageSpacing);
			for (Cell<?> cell : content.getCells()) {
				cell.space(pageSpacing);
			}
			this.pageSpacing = pageSpacing;
			content.invalidate();
		}
	}

	int	current	= 0;

	public void scrollToPage() {
		final float scrollY = getScrollY();
		final float maxY = getMaxY();
		if (scrollY >= maxY || scrollY <= 0)
			return;

		Array<Actor> pages = content.getChildren();
		float pageY = 0;
		float pageHeight = 0;
		if (pages.size > 0) {
			for (int i = 0; i < pages.size; i++) {
				Actor a = pages.get(i);
				pageY = a.getY();
				pageHeight = a.getHeight();
				if (scrollY > (pageY + pageHeight * 0.3)) {
					pageY = a.getY() + a.getHeight() + pageSpacing;
					current = pages.size - i;
					break;
				}
			}
			setScrollY(MathUtils.clamp(pageY, 0, maxY));
		}
	}

	public Table getContent() {
		return content;
	}

	public int getCurrentPage() {
		return current + elementInHeight / 2;
	}
}