package poo.trabalho.labcrisis.scene;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.entity.text.*;

import poo.trabalho.labcrisis.entity.Parede;
import poo.trabalho.labcrisis.factory.ParedeFactory;

public class Fase_01Scene extends AbstractScene {
	
	private Parede parede;
	private Text scoreText;
	
	
	public Fase_01Scene() {
		ParedeFactory.getInstance().create(vbom);
		}
	
	@Override
	public void populate() {
	createBackground();
	//createParede();
	
	//escrevendo textos
	scoreText = new Text(16, 784, res.font, "SCORE XXX", new
			TextOptions(HorizontalAlign.LEFT), vbom);
			scoreText.setAnchorCenter(0, 1);
			attachChild(scoreText);
	}
	
	
	
	/*private void createParede() {
		parede = ParedeFactory.getInstance().createParede(240, 400);
		attachChild(parede);
		}*/

	
	private void createBackground() {
	Entity background = new Entity();
	Sprite comidaSprite = new Sprite(200, 300, res.comidaTextureRegion,
	vbom);
	Sprite globuloSprite = new Sprite(300, 600, res.globuloTextureRegion,
	vbom);
	background.attachChild(comidaSprite);
	background.attachChild(globuloSprite);
	setBackground(new EntityBackground(0.82f, 0.96f, 0.97f,
	background));
	}
	@Override

	public void onPause() {
	}
	@Override
	public void onResume() {
	}
	}