package utils.ui;

import static com.badlogic.gdx.math.Interpolation.fade;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.svmc.mixxgame.Assets;
import com.svmc.mixxgame.attribute.Constants;
import com.svmc.mixxgame.attribute.Density;

public class Toast {
	public static int	LENGTH_LONG		= 4;
	public static int	LENGTH_SHORT	= 2;
	public static Toast	instance		= null;
	private Label		toastLabel		= null;
	private Table		toast			= null;
	private Stage		stage;
	private Image		back;

	public Toast(Stage stage) {
		super();
		this.stage = stage;
		// init();

	}

	private void init() {
		if (toast == null) {
			LabelStyle labelStyle = new LabelStyle();
			NinePatch patch = new NinePatch(Assets.instance.ui.reg_ninepatch2,
					4, 4, 4, 4);
			patch.setColor(Color.BLACK);
			back = new Image(patch);
			back.getColor().a = 0.9f;
			labelStyle.font = Assets.instance.fontFactory.getLight15();

			labelStyle.fontColor = Color.WHITE;
			toastLabel = new Label("", labelStyle);
			toastLabel.setAlignment(0);
			toastLabel.setPosition(5, 5);
			toast = new Table();
			toast.setTransform(true);
			toast.addActor(back);
			toast.addActor(toastLabel);
		}
		toast.clearActions();
		toast.setScale(0);

	}

	public void builToast() {
		init();
		stage.addActor(toast);
	}

	public void toast(String message) {
		toast.toFront();
		toastLabel.setText(message);
		toastLabel.pack();
		toastLabel.setWrap(true);

		back.setSize(toastLabel.getWidth() + 10, toastLabel.getHeight() + 10);
		positingForActor(
				new float[][] {
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								110 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								140 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								170 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								200 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								200 } }, toast);
		toast.setOrigin(toastLabel.getWidth() / 2, toastLabel.getHeight() / 2);
		toast.clearActions();
		toast.addAction(sequence(alpha(0), alpha(1, 0.2f, fade),
				scaleTo(1, 1, 0.2f, swingOut), delay(3), alpha(1),
				alpha(0, 0.3f, fade), scaleBy(-1, -1)));

	}

	public void toast(String message, float duration) {
		toast.toFront();
		toastLabel.setText(message);
		toastLabel.setWrap(true);
		toastLabel.setWidth(Constants.WIDTH_SCREEN - 50);
		toastLabel.setHeight(toastLabel.getHeight());
		// toastLabel.pack();
		back.setY(-toastLabel.getTextBounds().height / 2 - 5);
		back.setX(toastLabel.getWidth() / 2 - toastLabel.getTextBounds().width
				/ 2 - 5);
		back.setSize(toastLabel.getTextBounds().width + 20,
				toastLabel.getTextBounds().height + 25);
		positingForActor(
				new float[][] {
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								110 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								140 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								170 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								200 },
						{ (Constants.WIDTH_SCREEN - toastLabel.getWidth()) / 2,
								200 } }, toast);
		toast.setOrigin(toastLabel.getWidth() / 2, toastLabel.getHeight() / 2);
		toast.clearActions();
		toast.addAction(sequence(alpha(0), alpha(1, 0.2f, fade),
				scaleTo(1, 1, 0.2f, swingOut), delay(duration), alpha(1),
				alpha(0, 0.3f, fade), scaleBy(-1, -1)));
	}

	public static void positingForActor(float[][] position, Actor actor) {
		if (Constants.density == Density.mdpi) {
			actor.setPosition(position[0][0], position[0][1]);
		} else if (Constants.density == Density.hdpi) {
			actor.setPosition(position[1][0], position[1][1]);
		} else if (Constants.density == Density.xdpi) {
			actor.setPosition(position[2][0], position[2][1]);
		} else if (Constants.density == Density.xxdpi) {
			actor.setPosition(position[3][0], position[3][1]);
		} else if (Constants.density == Density.tablet) {
			actor.setPosition(position[4][0], position[4][1]);
		} else {
			actor.setPosition(position[0][0], position[0][1]);
		}
	}

	public static void makeText(Stage context, String text, float duration) {
		if (instance == null) {
			instance = new Toast(context);
			instance.builToast();
		}
		instance.stage = context;
		instance.toast(text, duration);
	}

}
