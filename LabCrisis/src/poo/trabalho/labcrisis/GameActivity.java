package poo.trabalho.labcrisis;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.*;
import org.andengine.engine.options.resolutionpolicy.*;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.content.SharedPreferences;
import android.view.KeyEvent;
import poo.trabalho.labcrisis.scene.SceneManager;


public class GameActivity extends BaseGameActivity {

	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	
	
	private final String KEY_SOUND = "Sound";
	private final String KEY_HISCORE = "HiScore";
	SharedPreferences settings;
	
	public void setSound(boolean sound) {
	SharedPreferences.Editor settingsEditor = settings.edit();
	settingsEditor.putBoolean(KEY_SOUND, sound);
	settingsEditor.commit();
	}
	public boolean isSound() {
	return settings.getBoolean(KEY_SOUND, true);
	}
	public void setHiScore(int score) {
	SharedPreferences.Editor settingsEditor = settings.edit();
	settingsEditor.putInt(KEY_HISCORE, score);
	settingsEditor.commit();
	}
	public int getHiScore() {
	return settings.getInt(KEY_HISCORE, 0);
	}
	
	
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {

	Camera camera = new Camera(0, 0, CAMERA_WIDTH,CAMERA_HEIGHT);
	IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
	EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED, resolutionPolicy, camera);
	engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
	engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	engineOptions.getRenderOptions().setDithering(true);
	engineOptions.getRenderOptions().setDithering(true);
	Debug.i("Engine configured");
	
	settings = getSharedPreferences("andengine_game_prefs",
			MODE_PRIVATE);
	
	return engineOptions;
	}

	@Override
	public void onCreateResources(
	OnCreateResourcesCallback pOnCreateResourcesCallback)
	throws IOException {
	ResourceManager.getInstance().create(this, getEngine(),
	getEngine().getCamera(), getVertexBufferObjectManager());
	ResourceManager.getInstance().loadSplashGraphics();
	pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	
	@Override
	public void onCreateScene(OnCreateSceneCallback
	pOnCreateSceneCallback)
	throws IOException {
	// we just have to pass something to the callback
	pOnCreateSceneCallback.onCreateSceneFinished(null);
	}
	
	
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback
	pOnPopulateSceneCallback)
	throws IOException {
	SceneManager.getInstance().showSplashAndMenuScene();
	pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	SceneManager.getInstance().getCurrentScene().
	onBackKeyPressed();
	return true;
	}
	return super.onKeyDown(keyCode, event);
	}
	

	
	@Override
	public synchronized void onResumeGame() {
	super.onResumeGame();
	SceneManager.getInstance().getCurrentScene().onResume();
	}
	@Override
	public synchronized void onPauseGame() {
	super.onPauseGame();
	SceneManager.getInstance().getCurrentScene().onPause();
	}
	
	
	
	
	
}

