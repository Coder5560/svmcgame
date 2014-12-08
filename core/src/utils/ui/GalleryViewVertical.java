package utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GalleryViewVertical {

	public float					width, height, cell_defaultHeight,
			pageHeight, index;
	public int						cellInHeight;

	public Table					content;
	public PageScrollPaneVertical	pages;
	boolean							circle	= false;

	public GalleryViewVertical(Table content, int cellInHeight) {
		super();
		this.content = content;
		this.cellInHeight = cellInHeight;
		setUp();
	}

	public GalleryViewVertical(Table content, int cellInHeight, boolean circle) {
		super();
		this.content = content;
		this.cellInHeight = cellInHeight;
		this.circle = circle;
		setUp();
	}

	public GalleryViewVertical(Table content, int cellInHeight,
			Drawable backGround) {
		super();
		this.content = content;
		this.cellInHeight = cellInHeight;
		setUp();
	}

	public void setUp() {
		width = content.getWidth();
		height = content.getHeight();
		cell_defaultHeight = height / cellInHeight;
		buildPagesContainer();
		// buildComponent();
	}

	public void buildPagesContainer() {
		pages = new PageScrollPaneVertical(cellInHeight, cell_defaultHeight,
				circle);
		pages.setScrollingDisabled(true, false);
		pages.setFlingTime(0.4f);
		pages.setSmoothScrolling(true);
		pages.setPageSpacing(5);
		content.add(pages).expand().fill();

	}

	public Table newPage(Drawable background) {
		Table page = new Table();
		page.setName("" + pages.getContent().getChildren().size);
		page.setBackground(background);
		page.setHeight(cell_defaultHeight);
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