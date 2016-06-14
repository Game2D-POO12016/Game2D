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
import poo.trabalho.labcrisis.GameManager;
import poo.trabalho.labcrisis.MusicPlayer;
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
	private Text inicioText;
	private Text saidaText;
	private Player player;
	private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_MOON), true);
	final ArrayList<Parede> lista_paredes = new ArrayList<Parede>();
	ArrayList<Comida> lista_comidas = new ArrayList<Comida>();
	private float last_x = 0, last_y = 0;
	private float cresce1 = (float)0.45;
	private int index_comida;
	
	/* matriz que representa a fase 1, ela pode ser editada, copiada e colada(para criacao de novas fases) , etc
	*cada numero representa um elemento na fase:
	*
	* 0 - representa elemento vazio
	* 1 - representa parede tile 1
	* 2 - representa parede tile 2
	* 3 - representa parede tile 3
	* 4 - representa parede tile 4
	* 5 - representa parede tile 5
	* 6 - representa parede tile 6
	* 7 - representa bacteria 1
	* 8 - representa bacteria 2
	* 9 - representa bacteria 3
	*10 - representa virus 1
	*11 - representa virus 2
	*
	*
	* mudando o numero nesse mapa, voce muda o elemento que aparece na tela.
	* 
	* importante : !!!!! ainda nao temos todos os sprites implementados (bacterias e virus)
	**/
	int[][] Fase01 = {
			{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 7, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1}
			};
	
	public Fase_01Scene() {
		ParedeFactory.getInstance().create(physicsWorld, vbom);
		ComidaFactory.getInstance().create(physicsWorld, vbom);
		PlayerFactory.getInstance().create(physicsWorld, vbom);
	}
	
	@Override
	public void populate() {
		createBackground();
		createMAPA(Fase01);
//		createComida(100,100,lista_comidas);
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
				
				player.die();
				if(player.isDead())
				{
					if(GameManager.getInstance().getCurrentScore() > activity.getHiScore())
					{
						activity.setHiScore(GameManager.getInstance().getCurrentScore());
					}
					SceneManager.getInstance().showGameOverScene();
					camera.setHUD(null);
				}
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
					createHUD();
					//ResourceManager.getInstance().soundComer.play();
					//ResourceManager.getInstance().soundGameover.play();
					return false;
				}	
			};
			GameManager.getInstance().incrementScore(1);	
			CollisionHandler myCollisionHandler2 = new CollisionHandler(myCollisionCallback2, player, lista_comidas);
			registerUpdateHandler(myCollisionHandler2);
			
				//textos de guia de onde ficam entrada e saida do labirinto
			inicioText = new Text(100, 3300, res.font, "INICIO", new TextOptions(HorizontalAlign.LEFT), vbom);
			inicioText.setAnchorCenter(0, 1);					
			attachChild(inicioText);
			
			saidaText = new Text(2600, 300, res.font, "SAIDA", new TextOptions(HorizontalAlign.LEFT), vbom);
			saidaText.setAnchorCenter(0, 1);					
			attachChild(saidaText);
			
			
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
	
	
	/*
	 * Parser do mapa
	 * 
	 * recebe uma matriz de mapa e designa os respectivos
	 * elementos para cada posicao da matriz
	 * 
	 * */
	
	private void createMAPA(int[][] Mapa){
		int pos_x,pos_y;
		
			for(int x_matriz = 0; x_matriz < 30 ; x_matriz++){       //linha da matriz
		   	    for(int y_matriz = 0; y_matriz < 30; y_matriz++){    // coluna da matriz			 	
	   	    		pos_x = (y_matriz * 100); // posicao x comeca em 0 ->  0 + (y_matiz+100), e  é incrementada com o incremento da coluna da matriz
		  		    pos_y = 3200 -(x_matriz * 100); // posicao y começa na posicao maxima em pixels e decrementa com o decremento  das linhas da matriz
		   	    	
		  		    switch(Mapa[x_matriz][y_matriz]){
		   	    		
		   	    	//cases de 1 a 6 irao processar as posicoes onde as paredes do labirinto irao ser colocadas no mapa
		   	    	case 1: 	
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(1);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    		
						break;
						
		   	    	case 2:
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(2);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    	
						break;
						
		   	    	case 3:
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(3);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    	
						break;
						
		   	    	case 4:
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(4);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    	
						break;
						
		   	    	case 5:
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(5);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    	
						break;
		   	    	
		   	    	case 6:
						parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
						parede.setCurrentTileIndex(6);
						parede.setScale((float) 0.7);
						lista_paredes.add(parede);
						attachChild(parede);
		   	    	
						break;
						
		   	    	//cases de 7 a 9 irao processar a posicao onde as bacterias sao criadas mapa
		   	    	case 7:
		   	    		comida = ComidaFactory.getInstance().createComida(pos_x, pos_y);
		   	    		comida.setScale((float) 0.5);
		   	    		lista_comidas.add(comida);
		   	    		attachChild(comida);
		   	    		
		   	    		break;
		   	    		
		   	    	case 8:
		   	    		
		   	    		break;
		   	    	
		   	    	case 9:
		   	    		
		   	    		break;
		   	    	//cases de 10 a 11 irao processar a posicao onde os virus sao criados no mapa
		   	    	case 10:
		   	    		
		   	    		break;
		   	    		
		   	    	case 11:
		   	    		
		   	    		break;
		   	    		
		   	    	}
		   	    }
			}
	}
	

	private void createBackground() {
		Entity background = new Entity();
		setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
	}
	
	private void createPlayer() {
		player = PlayerFactory.getInstance().createPlayer(100, 3200);
		player.setScale((float) 0.2);
		attachChild(player);
	}
	
	private void createHUD() {
		HUD hud = new HUD();
		//escrevendo textos
		scoreText = new Text(16, 470, res.font, "SCORE " + GameManager.getInstance().getCurrentScore(), new TextOptions(HorizontalAlign.LEFT), vbom);
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
	
	/*@Override
	protected void onManagedUpdate(float pSecondsElapsed)
	{
		super.onManagedUpdate(pSecondsElapsed);
		
	}*/
	
}
