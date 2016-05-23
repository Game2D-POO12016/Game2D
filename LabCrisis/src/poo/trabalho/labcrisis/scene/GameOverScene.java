package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.util.adt.color.Color;

public class GameOverScene extends AbstractScene implements IOnMenuItemClickListener{
	private IMenuItem continueMenuItem;
	private IMenuItem menuMenuItem;

	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		continueMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0,res.font, "CONTINUE", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(continueMenuItem);
		menuMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1,res.font, "MENU", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(menuMenuItem);
		menuScene.buildAnimations();
		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);
		setChildScene(menuScene);
	}

	@Override
	public void onPause() 
	{}

	@Override
	public void onResume() 
	{}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {

		switch (pMenuItem.getID()) {
			case 0 :
				SceneManager.getInstance().showGameScene();
				return true;
			case 1 :
				SceneManager.getInstance().showMenuScene();
				return true;
			default :
				return false;
		}
	}

}
