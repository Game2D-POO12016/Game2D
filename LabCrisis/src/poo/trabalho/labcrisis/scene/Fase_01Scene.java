package poo.trabalho.labcrisis.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.collision.ICollisionCallback;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.entity.text.*;
import poo.trabalho.labcrisis.MusicPlayer;
import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Comida;
import poo.trabalho.labcrisis.entity.Parede;
import poo.trabalho.labcrisis.entity.Player;
import poo.trabalho.labcrisis.factory.ComidaFactory;
import poo.trabalho.labcrisis.factory.ParedeFactory;
import poo.trabalho.labcrisis.factory.PlayerFactory;

public class Fase_01Scene extends AbstractScene {
	private Parede parede;
	private Comida comida;
	private Text scoreText;
	private Player player;
	
	public Fase_01Scene() {
		ParedeFactory.getInstance().create(vbom);
		ComidaFactory.getInstance().create(vbom);
		PlayerFactory.getInstance().create(vbom);
	}
	
	@Override
	public void populate() {
		createBackground();
		createParede();
		createComida(150,300);
		createComida(200,300);
		createComida(250,300);
		createComida(300,300);
		createComida(350,300);
		createPlayer();
		createHUD();

	setOnSceneTouchListener(new IOnSceneTouchListener() {
		@Override
		public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
			if (pSceneTouchEvent.isActionDown()) {
				player.clearEntityModifiers();
				player.registerEntityModifier(new MoveModifier(1,player.getX(), player.getY(), pSceneTouchEvent.getX(),pSceneTouchEvent.getY()));
				ResourceManager.getInstance().soundComer.play();
				return true;
			}
			return false;
		}
	});
			
	registerTouchArea(player);
	
	ICollisionCallback myCollisionCallback = new ICollisionCallback() {
		@Override
		public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
			parede.setColor(Color.RED);
			ResourceManager.getInstance().soundGameover.play();
			return false;
		}
	};
	
	CollisionHandler myCollisionHandler = new CollisionHandler(myCollisionCallback, parede, player);
	registerUpdateHandler(myCollisionHandler);
					
	MusicPlayer.getInstance().play();		
	camera.setChaseEntity(player);
	}
	
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().showMenuScene();
		MusicPlayer.getInstance().stop();
	}
	
	private void createParede() {
		
		//paredes superiores
		for(int i = 100 ; i<= 440 ; i = i+ 20){
			parede = ParedeFactory.getInstance().createParede(i, 400);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}
		
		
		//paredes inferiores
		for(int i = 10 ; i<= 340 ; i = i+ 20){
			parede = ParedeFactory.getInstance().createParede(i, 200);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}
		
		
		//lateral esquerda
		for(int i = 800 ; i>= 200 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(10, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}
		
		//lateral direita
		for(int i = 800 ; i>= 400 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(100, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}
		
		//segunda lateral direita
		for(int i = 400 ; i>= 20 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(440, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}	
				
		//segunda lateral esquerda
		for(int i = 200 ; i>= 20 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(340, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			attachChild(parede);
		}
				
	}
	
	private void createComida(int x, int y) {
		comida = ComidaFactory.getInstance().createComida(x, y);
		comida.setScale((float) 0.5);
		attachChild(comida);
	}

	private void createBackground() {
		Entity background = new Entity();
		setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
	}
	
	private void createPlayer() {
		player = PlayerFactory.getInstance().createPlayer(50, 600);
		player.setScale((float) 0.2);
		attachChild(player);
	}
	
	private void createHUD() {
		HUD hud = new HUD();
		//escrevendo textos
		scoreText = new Text(16, 784, res.font, "SCORE X", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 1);						
		hud.attachChild(scoreText);
		camera.setHUD(hud);
	}
	
	@Override
	public void onPause() {
		MusicPlayer.getInstance().pause();
	}
	
	@Override
	public void onResume() {
		MusicPlayer.getInstance().play();
	}
	
}