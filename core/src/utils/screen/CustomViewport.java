package utils.screen;



import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class CustomViewport extends ScalingViewport {
	/** Creates a new viewport using a new {@link OrthographicCamera}. */
	public CustomViewport (float worldWidth, float worldHeight) {
		super(Scaling.stretch, worldWidth, worldHeight);
	}

	public CustomViewport (float worldWidth, float worldHeight, Camera camera) {
		super(Scaling.stretchY, worldWidth, worldHeight, camera);
	}
}
