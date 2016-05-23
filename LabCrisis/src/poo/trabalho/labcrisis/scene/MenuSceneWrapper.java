package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.decorator.*;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

public class MenuSceneWrapper extends AbstractScene implements IOnMenuItemClickListener {
	private IMenuItem playMenuItem;
	private IMenuItem loadMenuItem;
	private MyTextMenuItemDecorator soundMenuItem;
	private IMenuItem gameoverMenuItem;
	
	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0,res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(playMenuItem);
		loadMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1,res.font, "LOAD", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(loadMenuItem);
		soundMenuItem = new MyTextMenuItemDecorator(new TextMenuItem(2, res.font, getSoundLabel(), vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(soundMenuItem);
		gameoverMenuItem = new ColorMenuItemDecorator(new TextMenuItem(3,res.font, "GAME OVER", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(gameoverMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);
		Sprite player = new Sprite(150, 350, res.globuloTextureRegion,vbom);
		menuScene.attachChild(player);
		setChildScene(menuScene);
	}
	
	@Override
	public void onPause() {
	}
	@Override
	public void onResume() {
	}
	
	private CharSequence getSoundLabel()
	{
		return activity.isSound() ? "SOUND ON" : "SOUND OFF";
	}
	
	@Override
	public boolean onMenuItemClicked (MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case 0 :
				SceneManager.getInstance().showGameScene();
				return true;
			case 2 :
				boolean soundState = activity.isSound();
				soundState = !soundState;
				activity.setSound(soundState);
				soundMenuItem.setText(getSoundLabel());
				return true;
			case 3 :
				SceneManager.getInstance().showGameOverScene();
				return true;
			default :
				return false;
		}
	}
	
	@Override
	public void onBackKeyPressed() {
		activity.finish();
	}
	
}
