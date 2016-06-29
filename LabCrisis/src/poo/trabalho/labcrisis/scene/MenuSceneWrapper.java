package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.decorator.*;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import poo.trabalho.labcrisis.GameActivity;
import poo.trabalho.labcrisis.GameManager;
import poo.trabalho.labcrisis.MusicPlayer;
import poo.trabalho.labcrisis.ResourceManager;

/**
 * Classe que organiza a cena de menu do jogo.
 * 
 * @author ALEXANDRE CORREIA
 *
 */

public class MenuSceneWrapper extends AbstractScene implements IOnMenuItemClickListener {

	/**
	 * Opcoes de escolha no MenuScene.
	 */

	private IMenuItem playMenuItem;
	private IMenuItem soundMenuItem;
	private IMenuItem howtoplayMenuItem;

	/**
	 * Construtor do MenuScene.
	 */

	@Override
	public void populate() {

		MusicPlayer.getInstance().playMenu();
		
		MenuScene menuScene = new MenuScene(camera);

		Sprite menuSprite = new Sprite(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2,
				res.menuTextureRegion, vbom);

		menuScene.setBackground(new SpriteBackground(menuSprite));
		playMenuItem = new ColorMenuItemDecorator(new SpriteMenuItem(0, res.playButtonRegion, vbom), Color.CYAN,
				Color.WHITE);
		howtoplayMenuItem = new ColorMenuItemDecorator(new SpriteMenuItem(1, res.howToPlayButtonRegion, vbom),
				Color.CYAN, Color.WHITE);
		soundMenuItem = new ColorMenuItemDecorator(new SpriteMenuItem(2, res.soundButtonRegion, vbom), Color.CYAN,
				Color.WHITE);

		menuScene.addMenuItem(playMenuItem);
		menuScene.addMenuItem(howtoplayMenuItem);
		menuScene.addMenuItem(soundMenuItem);

		//menuScene.buildAnimations();

		playMenuItem.setPosition(270, 130);
		playMenuItem.setScale(1.3f);

		howtoplayMenuItem.setPosition(530, 130);
		howtoplayMenuItem.setScale(1.3f);

		soundMenuItem.setPosition(730, 430);
		soundMenuItem.setScale(0.5f);

		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);

		setChildScene(menuScene);

		
		GameManager.getInstance().reset();
	}

	@Override
	public void onPause() {
		MusicPlayer.getInstance().pauseMenu();
	}

	@Override
	public void onResume() {
		MusicPlayer.getInstance().playMenu();
	}

	/**
	 * Metodo que reage quando ha um clique na opcao. Cada opcao eh identificada
	 * pelo ID, numero inteiro definido no metodo populate(). Com isso,
	 * realiza-se uma atitude de acordo com a opcao selecionada.
	 */

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		/**
		 * Vai para a fase do jogo.
		 */
		case 0:
			MusicPlayer.getInstance().stopMenu();
			SceneManager.getInstance().showGameScene();
			return true;
		/**
		 * Vai para a cena de tutorial do jogo.
		 */
		case 1:
			SceneManager.getInstance().showHowToPlayScene();
			return true;
		/**
		 * Ativa ou desativa o som do jogo.
		 */
		case 2:
			boolean soundState = activity.isSound();
			soundState = !soundState;
			activity.setSound(soundState);
			if (soundState == true) {
				MusicPlayer.getInstance().playMenu();
			} else {
				MusicPlayer.getInstance().stopMenu();
			}
			return true;

		default:
			return false;
		}
	}

	/**
	 * Sai do jogo caso o botao de voltar do Android for pressionado.
	 */

	@Override
	public void onBackKeyPressed() {
		activity.finish();
	}

}
