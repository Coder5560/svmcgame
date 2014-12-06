package utils.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.svmc.mixxgame.Assets;

public abstract class GameCore implements ApplicationListener {

	private boolean				init;
	private AbstractGameScreen	currScreen;
	private AbstractGameScreen	nextScreen;
	private FrameBuffer			currFbo;
	private FrameBuffer			nextFbo;
	private SpriteBatch			batch;
	private float				t;
	private ScreenTransition	screenTransition;
	public InputMultiplexer		inputMultiplexer;

	@Override
	public void create() {
	}

	public void setScreen(AbstractGameScreen screen) {
		setScreen(screen, null);
	}

	public void setScreen(AbstractGameScreen screen, ScreenTransition screenTransition) {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if (!this.init) {
			this.currFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
			this.nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
			this.batch = new SpriteBatch();
			this.init = true;
		}
		this.nextScreen = screen;
		this.nextScreen.show();
		this.nextScreen.resize(w, h);
		this.nextScreen.render(0.0F);
		if (this.currScreen != null) {
			this.currScreen.pause();
		}
		this.nextScreen.pause();
		Gdx.input.setInputProcessor(null);
		this.screenTransition = screenTransition;
		this.t = 0.0F;
	}

	public void render() {
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 0.01666667F);
		if (this.nextScreen == null) {
			if (this.currScreen != null) {
				this.currScreen.render(deltaTime);
			}
		} else {
			float duration = 0.0F;
			if (this.screenTransition != null) {
				duration = this.screenTransition.getDuration();
			}
			this.t = Math.min(this.t + deltaTime, duration);
			if ((this.screenTransition == null) || (this.t >= duration)) {
				if (this.currScreen != null) {
					this.currScreen.hide();
				}
				this.nextScreen.resume();

				Gdx.input.setInputProcessor(this.nextScreen.getInputProcessor());

				this.currScreen = this.nextScreen;
				this.nextScreen = null;
				this.screenTransition = null;
			} else {
				this.currFbo.begin();
				if (this.currScreen != null) {
					this.currScreen.render(deltaTime);
				}
				this.currFbo.end();
				this.nextFbo.begin();
				this.nextScreen.render(deltaTime);
				this.nextFbo.end();

				float alpha = this.t / duration;
				this.screenTransition.render(this.batch, this.currFbo.getColorBufferTexture(), this.nextFbo.getColorBufferTexture(), alpha);
			}
		}
	}

	public void resize(int width, int height) {
		if (this.currScreen != null) {
			this.currScreen.resize(width, height);
		}
		if (this.nextScreen != null) {
			this.nextScreen.resize(width, height);
		}
	}

	public void pause() {
		if (this.currScreen != null) {
			this.currScreen.pause();
		}
	}

	public void resume() {
		if (this.currScreen != null) {
			this.currScreen.resume();
		}
	}

	public void dispose() {
		if (this.currScreen != null) {
			this.currScreen.hide();
		}
		if (this.nextScreen != null) {
			this.nextScreen.hide();
		}
		if (this.init) {
			this.currFbo.dispose();
			this.currScreen = null;
			this.nextFbo.dispose();
			this.nextScreen = null;
			this.batch.dispose();
			this.init = false;
		}
		Assets.instance.assetManager.dispose();

	}
}
