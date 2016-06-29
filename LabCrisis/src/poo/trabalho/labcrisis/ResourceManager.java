package poo.trabalho.labcrisis;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import android.graphics.Typeface;

public class ResourceManager {
	public GameActivity activity;
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public ITextureRegion splashTextureRegion;
	private BitmapTextureAtlas splashTextureAtlas;

	public void loadSplashGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
				activity.getAssets(), "badge.png", 0, 0);
		splashTextureAtlas.load();
	}

	public void unloadSplashGraphics() {
		splashTextureAtlas.unload();
	}

	private static final ResourceManager INSTANCE = new ResourceManager();

	private ResourceManager() {
	}

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	// cria o resource manager
	public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObjectManager vbom) {
		this.activity = activity;
		this.engine = engine;
		this.camera = camera;
		this.vbom = vbom;
	}

	// game textures
	public ITiledTextureRegion paredeTextureRegion;
	public ITiledTextureRegion comidaTextureRegion;
	public ITiledTextureRegion globuloTextureRegion;
	public ITiledTextureRegion inimigoTextureRegion;
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	// recursos para o joystick
	public TextureRegion mOnScreenControlBaseTextureRegion;
	public TextureRegion mOnScreenControlKnobTextureRegion;

	public void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		paredeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "Parede.png", 6, 1);

		globuloTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "gosminho_azul.png", 1, 1);

		comidaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "comida.png", 1, 1);
				
		inimigoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, 
				activity.getAssets(), "virus1.png", 1, 1);

		mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "onscreen_control_base.png");

		mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "onscreen_control_knob.png");

		try {
			gameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
			gameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			throw new RuntimeException("Error while loading game textures", e);
		}

	}

	// sounds
	public Sound soundGameover;
	public Sound soundComer;

	// music
	public Music music;
	public Music menuMusic;

	public void loadGameAudio() {
		try {
			SoundFactory.setAssetBasePath("sfx/");
			soundComer = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "comer.wav");
			soundGameover = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "gameOver.wav");
			MusicFactory.setAssetBasePath("mfx/");
			music = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity,
					"345838__shadydave__abstract-ambient-loop.mp3");
			menuMusic = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity,
					"324252__rhodesmas__rings-of-saturn-music-loop.wav");
		} catch (Exception e) {
			throw new RuntimeException("Error while loading audio", e);
		}
	}

	// fonte
	public Font font;

	public void loadFont() {
		font = FontFactory.createStroke(activity.getFontManager(), activity.getTextureManager(), 256, 256,
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 50, true, Color.WHITE_ABGR_PACKED_INT, 2,
				Color.BLACK_ABGR_PACKED_INT);
		font.prepareLetters("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?".toCharArray());
		font.load();
	}

}
