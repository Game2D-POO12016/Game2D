package poo.trabalho.labcrisis.scene;

import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.collision.ICollisionCallback;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.entity.text.*;

import poo.trabalho.labcrisis.entity.Parede;
import poo.trabalho.labcrisis.entity.Player;
import poo.trabalho.labcrisis.factory.ParedeFactory;
import poo.trabalho.labcrisis.factory.PlayerFactory;

public class Fase_01Scene extends AbstractScene {
	
	private Parede parede;
	private Text scoreText;
	private Player player;
	
	
	public Fase_01Scene() {
		ParedeFactory.getInstance().create(vbom);
		PlayerFactory.getInstance().create(vbom);
		}
	
	@Override
	public void populate() {
	createBackground();
	createParede();
	createPlayer();
	
	//escrevendo textos
	scoreText = new Text(16, 784, res.font, "SCORE X", new
			TextOptions(HorizontalAlign.LEFT), vbom);
			scoreText.setAnchorCenter(0, 1);
			attachChild(scoreText);
			
			
			setOnSceneTouchListener(new IOnSceneTouchListener() {
				@Override
				public boolean onSceneTouchEvent(Scene pScene, TouchEvent
				pSceneTouchEvent) {
				if (pSceneTouchEvent.isActionDown()) {
				player.clearEntityModifiers();
				player.registerEntityModifier(new MoveModifier(1,
						player.getX(), player.getY(), pSceneTouchEvent.getX(),
						pSceneTouchEvent.getY()));
						return true;
						}
						return false;
						}
						});
			
			registerTouchArea(player);
			
			
			ICollisionCallback myCollisionCallback = new ICollisionCallback
					() {
					@Override
					public boolean onCollision(IShape pCheckShape, IShape
					pTargetShape) {
					parede.setColor(Color.RED);
					return false;
					}
					};
					CollisionHandler myCollisionHandler = new
					CollisionHandler(myCollisionCallback, parede, player);
					registerUpdateHandler(myCollisionHandler);
			
			
	}
	
	@Override
	public void onBackKeyPressed() {
	SceneManager.getInstance().showMenuScene();
	}
	
	private void createParede() {
		parede = ParedeFactory.getInstance().createParede(240, 400);
		attachChild(parede);
		}

	
	private void createBackground() {
	Entity background = new Entity();
	//Sprite comidaSprite = new Sprite(200, 300, res.comidaTextureRegion,
	//vbom);
	//background.attachChild(comidaSprite);
	setBackground(new EntityBackground(0.82f, 0.96f, 0.97f,
	background));
	}
	
	
	private void createPlayer() {
		player = PlayerFactory.getInstance().createPlayer(240, 600);
		player.setScale((float) 0.2);
		attachChild(player);
		}
	
	
	
	@Override
	public void onPause() {
	}
	
	@Override
	public void onResume() {
	}
	}