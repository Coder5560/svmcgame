package utils.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class ScreenTransitionSlide
  implements ScreenTransition
{
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int UP = 3;
  public static final int DOWN = 4;
  private static final ScreenTransitionSlide instance = new ScreenTransitionSlide();
  private float duration;
  private int direction;
  private boolean slideOut;
  private Interpolation easing;
  
  public static ScreenTransitionSlide init(float duration, int direction, boolean slideOut, Interpolation easing)
  {
    instance.duration = duration;
    instance.direction = direction;
    instance.slideOut = slideOut;
    instance.easing = easing;
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
    float x = 0.0F;
    float y = 0.0F;
    if (this.easing != null) {
      alpha = this.easing.apply(alpha);
    }
    switch (this.direction)
    {
    case 1: 
      x = -w * alpha;
      if (!this.slideOut) {
        x += w;
      }
      break;
    case 2: 
      x = w * alpha;
      if (!this.slideOut) {
        x -= w;
      }
      break;
    case 3: 
      y = h * alpha;
      if (!this.slideOut) {
        y -= h;
      }
      break;
    case 4: 
      y = -h * alpha;
      if (!this.slideOut) {
        y += h;
      }
      break;
    }
    Texture texBottom = this.slideOut ? nextScreen : currScreen;
    Texture texTop = this.slideOut ? currScreen : nextScreen;
    

    Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    Gdx.gl.glClear(16384);
    batch.begin();
    batch.draw(texBottom, 0.0F, 0.0F, 0.0F, 0.0F, w, h, 1.0F, 1.0F, 0.0F, 0, 0, currScreen.getWidth(), currScreen.getHeight(), false, true);
    batch.draw(texTop, x, y, 0.0F, 0.0F, w, h, 1.0F, 1.0F, 0.0F, 0, 0, nextScreen.getWidth(), nextScreen.getHeight(), false, true);
    batch.end();
  }
}
