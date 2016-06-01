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
	private IMenuItem voltarMenuItem;
	
	private CharSequence text = "TESTANDO\n"
								+ "123";
	
	/**
	 * Construtor do tutorial. 
	 */
	@Override
	public void populate() 
	{
		MenuScene menuScene = new MenuScene(camera);
		menuScene.getBackground().setColor(0.82f, 0.96f, 0.97f);
		
		textText = new Text(16, 470, res.font, text, new TextOptions(HorizontalAlign.CENTER), vbom);
		textText.setAnchorCenter(0, 1);
		menuScene.attachChild(textText);
		voltarMenuItem = new ColorMenuItemDecorator(new TextMenuItem(0,res.font, "BACK", vbom), Color.CYAN, Color.WHITE);
		menuScene.addMenuItem(voltarMenuItem);
		
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
	public void onBackKeyPressed() {
		activity.finish();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {
		
		if(pMenuItem.getID() == 0)
		{
			SceneManager.getInstance().showMenuScene();
			return true;
		}
		else
			return false;
	}

}
