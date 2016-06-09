package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import poo.trabalho.labcrisis.MusicPlayer;

public class EndScene extends AbstractScene {
	
	private Text mensageText;

	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		
		mensageText = new Text(400, 350, res.font, "YOU PASSED!", new TextOptions(HorizontalAlign.CENTER), vbom);
		mensageText.setScale(2.0f);
		
		menuScene.attachChild(mensageText);
		
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
