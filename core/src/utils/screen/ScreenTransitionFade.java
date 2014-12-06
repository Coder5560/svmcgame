package utils.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class ScreenTransitionFade
  implements ScreenTransition
{
  private static final ScreenTransitionFade instance = new ScreenTransitionFade();
  private float duration;
  
  public static ScreenTransitionFade init(float duration)
  {
    instance.duration = duration;
    return instance;
  }
  
  public float getDuration()
  {
    return this.duration;
  }
  
  public void render(SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha)
  {
    float w = currScreen.getWidth();
    float h = currScreen.getHeight();
    alpha = Interpolation.fade.apply(alpha);
    
    Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    Gdx.gl.glClear(16384);
    batch.begin();
    batch.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    batch.draw(currScreen, 0.0F, 0.0F, 0.0F, 0.0F, w, h, 1.0F, 1.0F, 0.0F, 0, 0, currScreen.getWidth(), currScreen.getHeight(), false, true);
    batch.setColor(1.0F, 1.0F, 1.0F, alpha);
    batch.draw(nextScreen, 0.0F, 0.0F, 0.0F, 0.0F, w, h, 1.0F, 1.0F, 0.0F, 0, 0, nextScreen.getWidth(), nextScreen.getHeight(), false, true);
    batch.end();
  }
}
