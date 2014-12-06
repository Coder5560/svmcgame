package utils.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract interface ScreenTransition
{
  public abstract float getDuration();
  
  public abstract void render(SpriteBatch paramSpriteBatch, Texture paramTexture1, Texture paramTexture2, float paramFloat);
}
