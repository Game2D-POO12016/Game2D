package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import poo.trabalho.labcrisis.GameManager;
import poo.trabalho.labcrisis.MusicPlayer;

public class EndScene extends AbstractScene {
	
	private Text mensageText;
	private Text highscoreText;
	private Text scoreText; 

	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		
		mensageText = new Text(400, 350, res.font, "YOU PASSED!", new TextOptions(HorizontalAlign.CENTER), vbom);
		mensageText.setScale(2.0f);
		highscoreText = new Text(400, 200, res.font, "HIGH SCORE: " + activity.getHiScore(), new TextOptions(HorizontalAlign.CENTER), vbom);
		scoreText = new Text(400, 100, res.font, "SCORE: " + GameManager.getInstance().getCurrentScore(), new TextOptions(HorizontalAlign.CENTER), vbom);
		
		menuScene.attachChild(mensageText);
		menuScene.attachChild(highscoreText);
		menuScene.attachChild(scoreText);

		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(true);
		setChildScene(menuScene);
		
	}

	@Override
	public void onPause() 
	{}

	@Override
	public void onResume() 
	{}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().showMenuScene();
	}
	
}
