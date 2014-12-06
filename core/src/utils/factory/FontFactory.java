package utils.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.svmc.mixxgame.attribute.Constants;

public class FontFactory {

	private BitmapFont	ttf_roboto_light_15;
	private BitmapFont	ttf_roboto_light_20;
	private BitmapFont	ttf_roboto_light_30;

	public FontFactory(AssetManager assetManager) {
		
		
	}

	public BitmapFont loadFont(String filePath, int size) {
		float SCALE = 1.0f * Gdx.graphics.getWidth() / Constants.WIDTH_SCREEN;
		if (SCALE < 1)
			SCALE = 1;

		boolean flip = false;
		FileHandle fontFile = Gdx.files.internal(filePath);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int) (size * SCALE);
		parameter.characters = "Ffaáàảãạăắẳằẵặâấẩầẫậbcdđeéẻèẽẹêếểềễệghiíỉìĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvxyýỳỹỷỵwz AÁÀẢÃẠĂẰẮẲẴẶÂẦẤẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊJKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴWZ1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*";
		parameter.flip = flip;
		parameter.genMipMaps = true;
		generator.generateData(parameter);
		BitmapFont font = generator.generateFont(parameter);
		font.setScale((float) (1.0 / SCALE));
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		return font;

	}

	public BitmapFont getLight15() {
		if (ttf_roboto_light_15 == null) {
			ttf_roboto_light_15 = loadFont("font/Roboto-Light.ttf", 15);
		}
		return ttf_roboto_light_15;
	}

	public BitmapFont getLight20() {
		if (ttf_roboto_light_20 == null) {
			ttf_roboto_light_20 = loadFont("font/Roboto-Light.ttf", 20);
		}
		return ttf_roboto_light_20;
	}

	public BitmapFont getLight30() {
		if (ttf_roboto_light_30 == null) {
			ttf_roboto_light_30 = loadFont("font/Roboto-Light.ttf", 30);
		}
		return ttf_roboto_light_30;
	}

}
