package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

/**
 * Classe identica a MenuSceneWrapper. Organiza a cena de game over.
 * Possui apenas duas opcoes: continue e menu.
 * A opcao continue retorna a fase que perdeu como nova tentativa de vence-la.
 * A opcao menu direciona o usuario para a cena do menu principal.
 * @author ALEXANDRE CORREIA
 *
 */

public class GameOverScene extends AbstractScene implements IOnMenuItemClickListener{
	
	private Text gameoverText;
	private IMenuItem continueMenuItem;
	private IMenuItem menuMenuItem;

	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		gameoverText = new Text(250, 370, res.font, "GAME OVER", new TextOptions(HorizontalAlign.CENTER), vbom);
		gameoverText.setAnchorCenter(0, 1);
		menuScene.attachChild(gameoverText);
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
			/**
			 * Retorna a fase que perdeu.
			 */
			case 0 :
				SceneManager.getInstance().showGameScene();
				return true;
			/**
			 * Retorna ao menu do jogo.
			 */
			case 1 :
				SceneManager.getInstance().showMenuScene();
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
