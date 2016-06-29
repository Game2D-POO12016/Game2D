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
 * Informa as regras do jogo ao usuario e como jogar esse jogo.
 * @author ALEXANDRE CORREIA
 *
 */

public class HowToPlayScene extends AbstractScene implements IOnMenuItemClickListener{

	private Text textText;
	private Text rulesText;
	private IMenuItem nextMenuItem;
	private IMenuItem backMenuItem;
	
	private CharSequence text = "ENREDO DO JOGO\n"
								+ "\nO CORPO DO FULANO FOI ATACADO POR INVASORES.\n"
								+ "BOB, UM GLOBULO BRANCO, FOI CONVOCADO AO\n"
								+ "SISTEMA SANGUINEO PARA COMBATER CONTRA ESSES\n"
								+ "TERRIVEIS INVASORES. POREM, O CAMINHO ESTA\n"
								+ "DIFICIL E PERIGOSO.\n"
								+ "\nBOB! O FULANO PRECISA DE VOCE!";
	
	private CharSequence rules = "REGRAS DO JOGO\n"
								+ "\nUSE O BOTAO DIRECIONAL PARA MOVIMENTAR O BOB\n"
								+ "E PARA FAGOCITAR OS INVASORES. QUANDO BOB\n"
								+ "DEVORA AS BACTÉRIAS ELE FICA CADA VEZ MAIOR \n"
								+ "E GANHA PONTOS. POREM, HA PASSAGENS QUE SAO\n"
								+ "MUITO PEQUENAS PARA O BOB PASSAR E, POR ISSO,\n"
								+ "ELE PRECISA SE DIVIDIR. BOB MORRE QUANDO SE\n"
								+ "CHOCAR COM UM VÍRUS!";
	
	/**
	 * Construtor do tutorial. 
	 */
	@Override
	public void populate() 
	{
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		
		textText = new Text(16, 470, res.font, text, new TextOptions(HorizontalAlign.LEFT), vbom);
		textText.setAnchorCenter(0, 1);
		textText.setScale(0.6f);
		rulesText = new Text(16, 470, res.font, rules, new TextOptions(HorizontalAlign.LEFT), vbom);
		rulesText.setAnchorCenter(0, 1);
		rulesText.setScale(0.6f);
		nextMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0,res.font, "NEXT", vbom), Color.CYAN, Color.WHITE);
		backMenuItem = new ColorMenuItemDecorator(new TextMenuItem(1,res.font, "BACK", vbom), Color.CYAN, Color.WHITE);
		
		menuScene.attachChild(textText);
		menuScene.addMenuItem(nextMenuItem);
		
		menuScene.buildAnimations();
		nextMenuItem.setPosition(400, 100);
		backMenuItem.setPosition(400, 100);
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
	public void onBackKeyPressed() {
		SceneManager.getInstance().showMenuScene();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {
		
		switch(pMenuItem.getID()) 
		{
			case 0 :
				pMenuScene.detachChild(textText);
				pMenuScene.attachChild(rulesText);
				pMenuScene.clearMenuItems();
				pMenuScene.addMenuItem(backMenuItem);
				return true;
				
			case 1 :
				pMenuScene.detachChild(rulesText);
				pMenuScene.attachChild(textText);
				pMenuScene.clearMenuItems();
				pMenuScene.addMenuItem(nextMenuItem);
				return true;
			
			default :
				return false;
		
		}
	}

}
