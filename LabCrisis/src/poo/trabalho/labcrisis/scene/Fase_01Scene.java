package poo.trabalho.labcrisis.scene;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.collision.ICollisionCallback;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.entity.text.*;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import com.badlogic.gdx.math.Vector2;
import android.hardware.SensorManager;
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
	private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_MOON), true);
	final ArrayList<Parede> lista_paredes = new ArrayList<Parede>();
	ArrayList<Comida> lista_comidas = new ArrayList<Comida>();
	private float last_x = 0, last_y = 0;
	private float cresce1 = (float)0.75;
	private int index_comida;
	
	public Fase_01Scene() {
		ParedeFactory.getInstance().create(physicsWorld, vbom);
		ComidaFactory.getInstance().create(physicsWorld, vbom);
		PlayerFactory.getInstance().create(physicsWorld, vbom);
	}
	
	@Override
	public void populate() {
		createBackground();
		createParede();
		//createComida(300,300,lista_comidas);
		createComida(150,300,lista_comidas);
		//createComida(200,300,lista_comidas);
		//createComida(250,300,lista_comidas);

		//createComida(350,300,lista_comidas);
		createPlayer();
		createHUD();

		//criar fisica que na qual o player deve ser submetido devido a acoes no joystick	
		final PhysicsHandler physicsHandler = new PhysicsHandler(player);
		player.registerUpdateHandler(physicsHandler);
	
		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(140, 140 , camera , res.mOnScreenControlBaseTextureRegion, res.mOnScreenControlKnobTextureRegion, 0.1f, 200, vbom, new IAnalogOnScreenControlListener() {	
			//onConrolChange controla a mudanca de direcao do joystick enquanto onControlClick controla clicks no joystick
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {		
				physicsHandler.setVelocity(pValueX * 300, pValueY * 300);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				//essa funcao pode ser usada para clicks no joystick
			}
		});
		
		//customizacao da sprite do joystick
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();
		
		//mostra o joystick na tela
		setChildScene(analogOnScreenControl);	
		
		//COLLISION HANDLER DE PAREDE
		ICollisionCallback myCollisionCallback = new ICollisionCallback() {		
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				last_x = -physicsHandler.getVelocityX();
				last_y = -(physicsHandler.getVelocityY());
				physicsHandler.setVelocity(last_x,last_y);
				//ResourceManager.getInstance().soundComer.play();
				//ResourceManager.getInstance().soundGameover.play();
				return false;
			}
		};
		
		CollisionHandler myCollisionHandler = new CollisionHandler(myCollisionCallback, player, lista_paredes);
		registerUpdateHandler(myCollisionHandler);
		
		
		//COLLISION HANDLER DE COMIDA
			ICollisionCallback myCollisionCallback2 = new ICollisionCallback() {		
				@Override
				public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
					//ArrayList<Comida> lista_temp = new ArrayList<Comida>();
					//lista_temp = lista_comidas;
										
					player.setScale(player.getScaleCenterX()*cresce1);
					index_comida = lista_comidas.indexOf(comida);
					detachChild(lista_comidas.get(index_comida));
					//lista_temp.remove(index_comida);
					//lista_comidas.removeAll(lista_comidas);
					//lista_comidas.addAll(lista_temp);

					//ResourceManager.getInstance().soundComer.play();
					//ResourceManager.getInstance().soundGameover.play();
					return false;
				}	
			};
				
			CollisionHandler myCollisionHandler2 = new CollisionHandler(myCollisionCallback2, player, lista_comidas);
			registerUpdateHandler(myCollisionHandler2);
				
		//Toca musica de background da fase.
		MusicPlayer.getInstance().play();
		camera.setChaseEntity(player);
	
	}
	
	@Override
	public void onBackKeyPressed() {
		MusicPlayer.getInstance().stop();
		SceneManager.getInstance().showMenuScene();
		camera.setHUD(null);
	}
	
	private void createParede() {
		
		//paredes superiores
		for(int i = 150 ; i<= 440 ; i = i+ 20){
			parede = ParedeFactory.getInstance().createParede(i, 400);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede);
			attachChild(parede);
		}
		
		
		//paredes inferiores
		for(int i = 10 ; i<= 340 ; i = i+ 20){
			parede = ParedeFactory.getInstance().createParede(i, 200);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede);
			attachChild(parede);
		}
		
		
		//lateral esquerda
		for(int i = 800 ; i>= 200 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(10, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede); 
			attachChild(parede);
		}
		
		//lateral direita
		for(int i = 800 ; i>= 400 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(150, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede);
			attachChild(parede);
		}
		
		//segunda lateral direita
		for(int i = 400 ; i>= 20 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(440, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede);
			attachChild(parede);
		}	
				
		//segunda lateral esquerda
		for(int i = 200 ; i>= 20 ; i = i- 20){
			parede = ParedeFactory.getInstance().createParede(340, i);
			parede.setCurrentTileIndex(4);
			parede.setScale((float) 0.2);
			lista_paredes.add(parede); //ajustar o tamnho desta parede também
			attachChild(parede);
		}
				
	}
	
	private void createComida(int x, int y, final ArrayList<Comida> lista_comida) {
		comida = ComidaFactory.getInstance().createComida(x, y);
		comida.setScale((float) 0.5);
		lista_comidas.add(comida);
		attachChild(comida);
	}

	private void createBackground() {
		Entity background = new Entity();
		setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
	}
	
	private void createPlayer() {
		player = PlayerFactory.getInstance().createPlayer(70, 600);
		player.setScale((float) 0.2);
		attachChild(player);
	}
	
	private void createHUD() {
		HUD hud = new HUD();
		//escrevendo textos
		scoreText = new Text(16, 470, res.font, "SCORE 000", new TextOptions(HorizontalAlign.LEFT), vbom);
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
