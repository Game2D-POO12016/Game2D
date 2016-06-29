package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import poo.trabalho.labcrisis.GameActivity;
import poo.trabalho.labcrisis.MusicPlayer;

/**
 * Classe identica a MenuSceneWrapper. Organiza a cena de game over.
 * Possui apenas duas opcoes: continue e menu.
 * A opcao continue retorna a fase que perdeu como nova tentativa de vence-la.
 * A opcao menu direciona o usuario para a cena do menu principal.
 * @author ALEXANDRE CORREIA
 *
 */

public class GameOverScene extends AbstractScene implements IOnMenuItemClickListener{
	
	private IMenuItem continueMenuItem;

	@Override
	public void populate() {
		res.soundGameover.play();
		MenuScene menuScene = new MenuScene(camera);
		
		/*Background contido na sprite.*/
		Sprite bkgd = new Sprite(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2, res.gameOverTextureRegion, vbom);
		menuScene.setBackground(new SpriteBackground(bkgd));
	
		/*"Botão" de continue*/
		continueMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "CONTINUE", vbom), Color.WHITE, Color.WHITE);

		/*Adiciona à cena.*/
		menuScene.addMenuItem(continueMenuItem);
		
		/*Seta a posição, adiciona o Listener e coloca a cena na tela.*/
		continueMenuItem.setPosition(520, 80);
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
				
			default :
				return false;
		}
	}
	
	/**
	 * Retorna ao menu do jogo.
	 */
	
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().showMenuScene();
	}

}
