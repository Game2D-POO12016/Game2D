package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.decorator.*;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import poo.trabalho.labcrisis.GameManager;
import poo.trabalho.labcrisis.MusicPlayer;

/**
 * Classe que organiza a cena de menu do jogo.
 * @author ALEXANDRE CORREIA
 *
 */

public class MenuSceneWrapper extends AbstractScene implements IOnMenuItemClickListener {
	
	/**
	 * Opcoes de escolha no MenuScene.
	 */
	
	private IMenuItem playMenuItem;
	private IMenuItem loadMenuItem;
	private MyTextMenuItemDecorator soundMenuItem;
	private IMenuItem endMenuItem;
	private IMenuItem howtoplayMenuItem;
	
	private Text gamenameText;
	
	
	/**
	 * Construtor do MenuScene.
	 */
	
	@Override
	public void populate() {
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		
		playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0, res.font, "PLAY", vbom), Color.CYAN, Color.WHITE);	
		howtoplayMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1, res.font, "HOW TO PLAY", vbom), Color.CYAN, Color.WHITE);
		soundMenuItem = new MyTextMenuItemDecorator(new TextMenuItem(2, res.font, getSoundLabel(), vbom), Color.CYAN, Color.WHITE);
		endMenuItem = new ColorMenuItemDecorator(new TextMenuItem(3,res.font, "END SCENE", vbom), Color.CYAN, Color.WHITE);
		Sprite player = new Sprite(150, 350, res.globuloTextureRegion,vbom);
		gamenameText = new Text(500, 350, res.font, "LAB\nCRISIS", new TextOptions(HorizontalAlign.CENTER), vbom);
		gamenameText.setScale(2.0f);
		
		menuScene.attachChild(player);
		menuScene.attachChild(gamenameText);
		menuScene.addMenuItem(playMenuItem);
		menuScene.addMenuItem(howtoplayMenuItem);
		menuScene.addMenuItem(soundMenuItem);
		menuScene.addMenuItem(endMenuItem);
		
		menuScene.buildAnimations();
		playMenuItem.setPosition(200, 150);
		howtoplayMenuItem.setPosition(200, 50);
		soundMenuItem.setPosition(650, 150);
		endMenuItem.setPosition(650, 50);
		menuScene.setBackgroundEnabled(true);
		menuScene.setOnMenuItemClickListener(this);
		setChildScene(menuScene);
		MusicPlayer.getInstance().playMenu();
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
	 * Verifica se o som do jogo deve ser ativado ou nao.
	 * @return SOUND ON / SOUND OFF
	 */
	
	private CharSequence getSoundLabel()
	{
		return activity.isSound() ? "SOUND ON" : "SOUND OFF";
	}
	
	/**
	 * Metodo que reage quando ha um clique na opcao.
	 * Cada opcao eh identificada pelo ID, numero inteiro definido no metodo populate().
	 * Com isso, realiza-se uma atitude de acordo com a opcao selecionada.
	 */
	
	@Override
	public boolean onMenuItemClicked (MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
			/**
			 * Vai para a fase do jogo.
			 */
			case 0 :
				MusicPlayer.getInstance().stopMenu();
				SceneManager.getInstance().showGameScene();
				return true;	
			/**
			 * Vai para a cena de tutorial do jogo.
			 */
			case 1 :
				SceneManager.getInstance().showHowToPlayScene();
				return true;
			/**
			 * Ativa ou desativa o som do jogo.
			 */
			case 2 :
				boolean soundState = activity.isSound();
				soundState = !soundState;
				activity.setSound(soundState);
				soundMenuItem.setText(getSoundLabel());
				return true;
			/**
			 * Vai para a cena de fim de jogo.
			 * TODO Retirar essa opcao do MenuScene e ativa-la quando
			 * o personagem morre. Esta aqui apenas para testes.
			 */
			case 3 :
				SceneManager.getInstance().showEndScene();
				return true;
				
			default :
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
