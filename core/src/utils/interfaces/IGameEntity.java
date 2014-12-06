package utils.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/* 
 *  Calculate the virtual size from the real size. 
 *  
 *  TextureRegion will be given from assets.
 * */

public interface IGameEntity {

	public Image getRectangle(float width, float height);

	public Image getCircle(float radius);

	public Image getLine(float distance);

	/* get the line with specific degrees */
	public Image getLine(float distance, float degree);
	
}
