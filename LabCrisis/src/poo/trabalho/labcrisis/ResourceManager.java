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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import android.graphics.Typeface;

public class ResourceManager {
	
	//common objects
	public GameActivity activity;
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	
	// single instance is created only
	private static final ResourceManager INSTANCE = new
	ResourceManager();
	
	// constructor is private to ensure nobody can call it from outside
	private ResourceManager() { }
	public static ResourceManager getInstance() {
	return INSTANCE;
	}
	
	//create resource manager
	public void create(GameActivity activity, Engine engine, Camera
			camera, VertexBufferObjectManager vbom) {
			this.activity = activity;
			this.engine = engine;
			this.camera = camera;
			this.vbom = vbom;
			}
	
	//game textures
	public ITiledTextureRegion paredeTextureRegion;
	public ITextureRegion comidaTextureRegion;
	public ITextureRegion globuloTextureRegion;
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	
	public void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(),1024, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		paredeTextureRegion = BitmapTextureAtlasTextureRegionFactory.
		createTiledFromAsset(
		gameTextureAtlas, activity.getAssets(), "Parede.png", 4, 1);
		
		globuloTextureRegion = BitmapTextureAtlasTextureRegionFactory.
		createFromAsset(
		gameTextureAtlas, activity.getAssets(), "Globulo.png");
		
		comidaTextureRegion = BitmapTextureAtlasTextureRegionFactory.
		createFromAsset(
		gameTextureAtlas, activity.getAssets(), "comida.png");
		
		
				try {
				gameTextureAtlas.build(new
				BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
				BitmapTextureAtlas>(2, 0, 2));
				gameTextureAtlas.load();
				} catch (final TextureAtlasBuilderException e) {
				throw new RuntimeException("Error while loading game textures", e);
				}
	}
	//sounds
	public Sound soundGameover;
	public Sound soundComer;
	//music
	public Music music;
	
	public void loadGameAudio() {
		try {
		SoundFactory.setAssetBasePath("sfx/");
		soundComer = SoundFactory.createSoundFromAsset
		(activity.getSoundManager(), activity, "comer.wav");
		soundGameover = SoundFactory.createSoundFromAsset
		(activity.getSoundManager(), activity, "gameOver.wav");
		MusicFactory.setAssetBasePath("mfx/");
		music = MusicFactory.createMusicFromAsset
		(activity.getMusicManager(), activity, "music.wav");
		} catch (Exception e) {
		throw new RuntimeException("Error while loading audio", e);
		}
		}
	
	//font
	public Font font;
	public void loadFont() {
	font = FontFactory.createStroke(activity.getFontManager(),
	activity.getTextureManager(), 256, 256,
	Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 50,
	true, Color.WHITE_ABGR_PACKED_INT, 2,
	Color.BLACK_ABGR_PACKED_INT);
	font.prepareLetters("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?".toCharArray());
	font.load();
	}
	
	

}