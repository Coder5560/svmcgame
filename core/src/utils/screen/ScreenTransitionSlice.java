package utils.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

public class ScreenTransitionSlice
  implements ScreenTransition
{
  public static final int UP = 1;
  public static final int DOWN = 2;
  public static final int UP_DOWN = 3;
  private static final ScreenTransitionSlice instance = new ScreenTransitionSlice();
  private float duration;
  private int direction;
  private Interpolation easing;
  private Array<Integer> sliceIndex = new Array<Integer>();
  
  public ScreenTransitionSlice init(float duration, int direction, int numSlices, Interpolation easing)
  {
    instance.duration = duration;
    instance.direction = direction;
    instance.easing = easing;
    
    instance.sliceIndex.clear();
    for (int i = 0; i < numSlices; i++) {
      instance.sliceIndex.add(Integer.valueOf(i));
    }
    instance.sliceIndex.shuffle();
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
    int sliceWidth = (int)(w / this.sliceIndex.size);
    
    Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
    Gdx.gl.glClear(16384);
    batch.begin();
    batch.draw(currScreen, 0.0F, 0.0F, 0.0F, 0.0F, w, h, 1.0F, 1.0F, 0.0F, 0, 0, currScreen.getWidth(), currScreen.getHeight(), false, true);
    if (this.easing != null) {
      alpha = this.easing.apply(alpha);
    }
    for (int i = 0; i < this.sliceIndex.size; i++)
    {
      x = i * sliceWidth;
      
      float offsetY = h * (1.0F + ((Integer)this.sliceIndex.get(i)).intValue() / this.sliceIndex.size);
      switch (this.direction)
      {
      case 1: 
        y = -offsetY + offsetY * alpha;
        break;
      case 2: 
        y = offsetY - offsetY * alpha;
        break;
      case 3: 
        if (i % 2 == 0) {
          y = -offsetY + offsetY * alpha;
        } else {
          y = offsetY - offsetY * alpha;
        }
        break;
      }
      batch.draw(nextScreen, x, y, 0.0F, 0.0F, sliceWidth, h, 1.0F, 1.0F, 0.0F, i * sliceWidth, 0, sliceWidth, nextScreen.getHeight(), false, 
        true);
    }
    batch.end();
  }
}
