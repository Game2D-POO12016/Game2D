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
	private IMenuItem optionMenuItem;
	
	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0,res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(playMenuItem);
		loadMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1,res.font, "LOAD", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(loadMenuItem);
		optionMenuItem = new ColorMenuItemDecorator(new TextMenuItem(2,res.font, "OPTION", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(optionMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);
		Sprite player = new Sprite(240, 640, res.globuloTextureRegion,vbom);
		menuScene.attachChild(player);
		setChildScene(menuScene);
	}
	
	@Override
	public void onPause() {
	}
	@Override
	public void onResume() {
	}
	
	@Override
	public boolean onMenuItemClicked (MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			case 0 :
				SceneManager.getInstance().showGameScene();
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
