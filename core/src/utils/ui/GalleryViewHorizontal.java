package utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GalleryViewHorizontal {

	public float					width, height, cell_defaultWidth, pageWidth, index;
	public int						cellInWidth;

	public Table					content;
	public PageScrollPaneHorizontal	pages;

	public GalleryViewHorizontal(Table content, int cellInWidth) {
		super();
		this.content = content;
		this.cellInWidth = cellInWidth;
		setUp();
	}

	public GalleryViewHorizontal(Table content, int cellInWidth, Drawable backGround) {
		super();
		this.content = content;
		this.cellInWidth = cellInWidth;
		setUp();
	}

	public void setUp() {
		width = content.getWidth();
		height = content.getHeight();
		cell_defaultWidth = width / cellInWidth;
		System.out.println("" + width + " _ " + height);
		buildPagesContainer();
		// buildComponent();
	}

	public void buildPagesContainer() {
		pages = new PageScrollPaneHorizontal(cellInWidth, cell_defaultWidth);
		pages.setScrollingDisabled(false, true);
		pages.setFlingTime(0.1f);
		pages.setSmoothScrolling(true);
		pages.setPageSpacing(5);
		content.add(pages).expand().fill();
	}

	public Table newPage(Drawable background) {
		final int index = pages.getRoot().getChildren().size;
		Table page = new Table();
		page.setName("" + index);
		page.setBackground(background);
		page.setWidth(cell_defaultWidth);
		pages.addPage(page);
		return page;
	}

	public Table newPage() {
		final int index = pages.getRoot().getChildren().size;
		Table page = new Table();
		page.setName("" + index);
		page.setWidth(cell_defaultWidth);
		pages.addPage(page);
		return page;
	}

	public Table getPage(int index) {
		return ((Table) pages.getPage(index));
	}
	public void setScrollOver(boolean x, boolean y) {
		pages.setOverscroll(x, y);
	}

}