package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.decorator.*;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.adt.color.Color;

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
	private IMenuItem gameoverMenuItem;
	
	
	/**
	 * Construtor do MenuScene.
	 */
	
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
				SceneManager.getInstance().showGameScene();
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
			 * Vai para a cena de Game over.
			 * TODO Retirar essa opcao do MenuScene e ativa-la quando
			 * o personagem morre. Esta aqui apenas para testes.
			 */
			case 3 :
				SceneManager.getInstance().showGameOverScene();
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
