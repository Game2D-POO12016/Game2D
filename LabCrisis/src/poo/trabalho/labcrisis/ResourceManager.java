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
	public ITextureRegion splashTextureRegion, menuTextureRegion, gameBkgdTextureRegion, introFase1TextureRegion;
	private BitmapTextureAtlas splashTextureAtlas, menuTextureAtlas, gameBkgdTextureAtlas, introFase1TextureAtlas;

	public void loadIntroFase1Graphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		introFase1TextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		introFase1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(introFase1TextureAtlas,
				activity.getAssets(), "tela_fase1_titulo.png", 0, 0);
		introFase1TextureAtlas.load();
	}
	
	public void unloadIntroFase1Graphics() {
		introFase1TextureAtlas.unload();
	}
	
	public void loadGameBkgdGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		gameBkgdTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gameBkgdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBkgdTextureAtlas,
				activity.getAssets(), "background_labirinto.png", 0, 0);
		gameBkgdTextureAtlas.load();
	}

	public void unloadGameBkgdGraphics() {
		gameBkgdTextureAtlas.unload();
	}

	public void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		menuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480, BitmapTextureFormat.RGBA_8888,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas,
				activity.getAssets(), "tela_menu.png", 0, 0);
		menuTextureAtlas.load();
	}

	public void unloadMenuGraphics() {
		menuTextureAtlas.unload();
	}

	public void loadSplashGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
				activity.getAssets(), "tela_abertura.png", 0, 0);
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
	public TextureRegion howToPlayButtonRegion, playButtonRegion, soundButtonRegion;
	public TextureRegion fase1Intro;

	public void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		paredeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "Parede.png", 6, 1);

		globuloTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "gosminho_branco.png", 1, 1);

		comidaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,
				activity.getAssets(), "comida.png", 1, 1);
				
		inimigoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, 
				activity.getAssets(), "virus1.png", 1, 1);

		mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "joystick_base.png");

		mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "joystick_botao.png");

		howToPlayButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "botao_howto.png");

		playButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "botao_play.png");

		soundButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,
				activity.getAssets(), "botao_sound_onoff.png");

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
	public Music introMusic;

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
			introMusic = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "176005__quartzgate__qvarz-5.wav");
		} catch (Exception e) {
			throw new RuntimeException("Error while loading audio", e);
		}
	}

	// fonte
	public Font font;

	public void loadFont() {
		font = FontFactory.createStroke(activity.getFontManager(), activity.getTextureManager(), 256, 256,
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 50, true, Color.WHITE_ARGB_PACKED_INT, 2f,
				Color.BLACK_ABGR_PACKED_INT);
		font.prepareLetters("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?".toCharArray());
		font.load();
	}

}
