// FASE COM INIMIGOS MÃ“VEIS!!!!!!!

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
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.LineJointDef;

import android.hardware.SensorManager;
import poo.trabalho.labcrisis.GameActivity;
import poo.trabalho.labcrisis.GameManager;
import poo.trabalho.labcrisis.MusicPlayer;
import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Comida;
import poo.trabalho.labcrisis.entity.Enemy;
import poo.trabalho.labcrisis.entity.Parede;
import poo.trabalho.labcrisis.entity.Player;
import poo.trabalho.labcrisis.factory.ComidaFactory;
import poo.trabalho.labcrisis.factory.EnemyFactory;
import poo.trabalho.labcrisis.factory.ParedeFactory;
import poo.trabalho.labcrisis.factory.PlayerFactory;

public class Fase_00Scene extends AbstractScene {
	private Parede parede;
	private Comida comida;
	private Text scoreText;
	private Player player;
	private Player player2;
	private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_MOON), true);
	final ArrayList<Parede> lista_paredes_v = new ArrayList<Parede>();
	final ArrayList<Parede> lista_paredes_h = new ArrayList<Parede>();
	final ArrayList<Parede> lista_paredes_q = new ArrayList<Parede>();
	final ArrayList<Parede> lista_paredes_f = new ArrayList<Parede>();
	ArrayList<Comida> lista_comidas = new ArrayList<Comida>();
	private float cresce1 = (float) 0.60;
	private int index_comida;
	private float p1pos[];
	private float originalScale = ResourceManager.getInstance().globuloTextureRegion.getScale() * ((float) 0.2);
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	/*
	 * matriz que representa a fase 1, ela pode ser editada, copiada e
	 * colada(para criacao de novas fases) , etc cada numero representa um
	 * elemento na fase:
	 *
	 * 0 - representa elemento vazio 1 - representa parede tile 1 2 - representa
	 * parede tile 2 3 - representa parede tile 3 4 - representa parede tile 4 5
	 * - representa parede tile 5 6 - representa parede tile 6 7 - representa
	 * bacteria 1 8 - representa bacteria 2 9 - representa bacteria 3 10 -
	 * representa virus 1 11 - representa virus 2
	 *
	 *
	 * mudando o numero nesse mapa, voce muda o elemento que aparece na tela.
	 * 
	 * importante : !!!!! ainda nao temos todos os sprites implementados
	 * (bacterias e virus)
	 **/
	int[][] Fase00 = { { 1, 4, 2, 2, 2, 2, 2, 2, 2, 1 }, { 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1, 10, 0, 0, 0, 1 }, { 1, 0, 10, 0, 1, 0, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 1 }, { 1, 0, 10, 0, 1, 0, 10, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 }, { 1, 2, 2, 2, 2, 2, 2, 2, 4, 1 }, };

	public Fase_00Scene() {
		ParedeFactory.getInstance().create(physicsWorld, vbom);
		ComidaFactory.getInstance().create(physicsWorld, vbom);
		PlayerFactory.getInstance().create(physicsWorld, vbom);
		EnemyFactory.getInstance().create(physicsWorld, vbom);
	}

	@Override
	public void populate() {
		createBackground();
		createMAPA(Fase00);
		createPlayer();
		createHUD();

		// criar fisica que na qual o player deve ser submetido devido a acoes
		// no joystick
		final PhysicsHandler physicsHandler = new PhysicsHandler(player);
		player.registerUpdateHandler(physicsHandler);

		final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(120, 120, camera,
				res.mOnScreenControlBaseTextureRegion, res.mOnScreenControlKnobTextureRegion, 0.1f, 200, vbom,
				new IAnalogOnScreenControlListener() {
					// onConrolChange controla a mudanca de direcao do joystick
					// enquanto onControlClick controla clicks no joystick
					@Override
					public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX,
							final float pValueY) {
						// physicsHandler.setVelocity(pValueX * 300, pValueY *300);
						if(physicsHandler.getVelocityX() >= 500 && physicsHandler.getVelocityY() >= 500){
							physicsHandler.setAccelerationX(0);
							physicsHandler.setAccelerationY(0);
						}
						else if(physicsHandler.getVelocityY() >= 500){
							physicsHandler.setAccelerationX(pValueX*250);
							physicsHandler.setAccelerationY(0);
						}
						else if(physicsHandler.getVelocityX() >= 500){
							physicsHandler.setAccelerationX(0);
							physicsHandler.setAccelerationY(pValueY*250);
						}
						else{
							physicsHandler.setAccelerationX(pValueX*250);
							physicsHandler.setAccelerationY(pValueY*250);
						}
					}

					@Override
					public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
						// essa funcao pode ser usada para clicks no joystick

						if (player2 == null && player.getSize() > 1) {
							player.setSize(player.getSize() - 1);
							player.setScale(originalScale);
							createPlayer2();

							LineJointDef linejointdef = new LineJointDef();
							// PrismaticJointDef prismaticJointDef = new
							// PrismaticJointDef();
							linejointdef.bodyA = player.getBody();
							linejointdef.bodyB = player2.getBody();
							linejointdef.localAnchorA.set(new Vector2(0, 0));
							linejointdef.localAnchorB.set(new Vector2(0, 0));
							linejointdef.collideConnected = false;

							physicsWorld.createJoint(linejointdef);
						}

						/*
						 * player.die(); if(player.isDead()) {
						 * if(GameManager.getInstance().getCurrentScore() >
						 * activity.getHiScore()) {
						 * activity.setHiScore(GameManager.getInstance().
						 * getCurrentScore()); }
						 * SceneManager.getInstance().showGameOverScene();
						 * camera.setHUD(null); }
						 */
					}
				});

		// customizacao da sprite do joystick
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.9f);
		//analogOnScreenControl.getControlBase().setScale(1.5f);
		//analogOnScreenControl.getControlKnob().setScale(1.5f);
		analogOnScreenControl.refreshControlKnobPosition();

		// mostra o joystick na tela
		setChildScene(analogOnScreenControl);

		// COLLISION HANDLER DE PAREDE horizontal
		ICollisionCallback myCollisionCallback = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandler.setVelocityX(-physicsHandler.getVelocityX()*((float)0.7));
				return false;
			}
		};

		CollisionHandler myCollisionHandler = new CollisionHandler(myCollisionCallback, player, lista_paredes_h);
		registerUpdateHandler(myCollisionHandler);

		// COLLISION HANDLER DE PAREDE vertical
		ICollisionCallback myCollisionCallbackV = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandler.setVelocityY(-physicsHandler.getVelocityY()*((float)0.7));
				return false;
			}
		};

		CollisionHandler myCollisionHandlerV = new CollisionHandler(myCollisionCallbackV, player, lista_paredes_v);
		registerUpdateHandler(myCollisionHandlerV);

		// COLLISION HANDLER DE PAREDE quina
		ICollisionCallback myCollisionCallbackQ = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandler.setVelocityY(-physicsHandler.getVelocityY()*((float)0.7));
				physicsHandler.setVelocityX(-physicsHandler.getVelocityX()*((float)0.7));
				return false;
			}
		};

		CollisionHandler myCollisionHandlerQ = new CollisionHandler(myCollisionCallbackQ, player, lista_paredes_q);
		registerUpdateHandler(myCollisionHandlerQ);

		// COLLISION HANDLER DE PAREDE FINAL
		ICollisionCallback myCollisionCallbackF = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				SceneManager.getInstance().showGameScene01();
				return false;
			}
		};

		CollisionHandler myCollisionHandlerF = new CollisionHandler(myCollisionCallbackF, player, lista_paredes_f);
		registerUpdateHandler(myCollisionHandlerF);

		// COLLISION HANDLER DE COMIDA
		ICollisionCallback myCollisionCallback2 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				player.setSize(player.getSize() + 1);
				player.setScale(player.getScaleCenterX() * cresce1);
				index_comida = lista_comidas.indexOf(comida);
				detachChild(lista_comidas.get(index_comida));
				// incrementa score
				GameManager.getInstance().incrementScore(100);
				//scoreText.setText("SCORE " + GameManager.getInstance().getCurrentScore());
				createHUD();
				return false;
			}
		};

		CollisionHandler myCollisionHandler2 = new CollisionHandler(myCollisionCallback2, player, lista_comidas);
		registerUpdateHandler(myCollisionHandler2);

		// fisica para o inimigo 0
		final PhysicsHandler physicsHandlerI = new PhysicsHandler(enemies.get(0));
		enemies.get(0).registerUpdateHandler(physicsHandlerI);
		physicsHandlerI.setVelocityY(100);

		// COLLISION HANDLER DE VÃ­rus com jogador
		ICollisionCallback myCollisionCallback3 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				player.die();

				// Check Player Status
				if (player.isDead()) {
					if (GameManager.getInstance().getCurrentScore() > activity.getHiScore()) {
						activity.setHiScore(GameManager.getInstance().getCurrentScore());
					}
					MusicPlayer.getInstance().stop();
					SceneManager.getInstance().showGameOverScene();
					camera.setHUD(null);
				}
				return false;
			}
		};

		CollisionHandler myCollisionHandler3 = new CollisionHandler(myCollisionCallback3, player, enemies);
		registerUpdateHandler(myCollisionHandler3);

		// COLLISION HANDLER DE VÃ�RUS 0 COM PAREDE vertical
		ICollisionCallback myCollisionCallback4 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandlerI.setVelocityY(-physicsHandlerI.getVelocityY());
				return false;
			}
		};

		CollisionHandler myCollisionHandler4 = new CollisionHandler(myCollisionCallback4, enemies.get(0),
				lista_paredes_v);
		registerUpdateHandler(myCollisionHandler4);

		// fisica para o inimigo 1
		final PhysicsHandler physicsHandlerI1 = new PhysicsHandler(enemies.get(1));
		enemies.get(1).registerUpdateHandler(physicsHandlerI1);
		physicsHandlerI1.setVelocityX(100);

		// COLLISION HANDLER DE VÃ�RUS 1 COM PAREDE horizontal
		ICollisionCallback myCollisionCallbackI5 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandlerI1.setVelocityX(-physicsHandlerI1.getVelocityX());
				return false;
			}
		};
		CollisionHandler myCollisionHandlerI5 = new CollisionHandler(myCollisionCallbackI5, enemies.get(1),
				lista_paredes_h);
		registerUpdateHandler(myCollisionHandlerI5);

		// fisica para o inimigo 2
		final PhysicsHandler physicsHandlerI2 = new PhysicsHandler(enemies.get(2));
		enemies.get(2).registerUpdateHandler(physicsHandlerI2);
		physicsHandlerI2.setVelocityX(-100);

		// COLLISION HANDLER DE VÃ�RUS 2 COM PAREDE horizontal
		ICollisionCallback myCollisionCallbackI6 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandlerI2.setVelocityX(-physicsHandlerI2.getVelocityX());
				return false;
			}
		};
		CollisionHandler myCollisionHandlerI6 = new CollisionHandler(myCollisionCallbackI6, enemies.get(2),
				lista_paredes_h);
		registerUpdateHandler(myCollisionHandlerI6);

		// fisica para o inimigo 3
		final PhysicsHandler physicsHandlerI3 = new PhysicsHandler(enemies.get(3));
		enemies.get(3).registerUpdateHandler(physicsHandlerI3);
		physicsHandlerI3.setVelocityY(-100);

		// COLLISION HANDLER DE VÃ�RUS 3 COM PAREDE vertical
		ICollisionCallback myCollisionCallbackI7 = new ICollisionCallback() {
			@Override
			public boolean onCollision(IShape pCheckShape, IShape pTargetShape) {
				physicsHandlerI3.setVelocityY(-physicsHandlerI3.getVelocityY());
				return false;
			}
		};
		CollisionHandler myCollisionHandlerI7 = new CollisionHandler(myCollisionCallbackI7, enemies.get(3),
				lista_paredes_v);
		registerUpdateHandler(myCollisionHandlerI7);

		// toca mÃºsica no background da fase
		MusicPlayer.getInstance().play();
		// POSICIONA CÃ‚MERA
		camera.setChaseEntity(player);

	}

	private void createBackground() {
		RepeatingSpriteBackground bkgd = new RepeatingSpriteBackground(GameActivity.CAMERA_WIDTH,
				GameActivity.CAMERA_HEIGHT, res.gameBkgdTextureRegion, vbom);
		setBackground(bkgd);
	}

	private void createPlayer() {
		player = PlayerFactory.getInstance().createPlayer(200, 2900);
		player.setScale((float) 0.2);
		attachChild(player);
	}

	private void createMAPA(int[][] Mapa) {
		int pos_x, pos_y, i = 0;

		for (int x_matriz = 0; x_matriz < 10; x_matriz++) { // linha da matriz
			for (int y_matriz = 0; y_matriz < 10; y_matriz++) { // coluna da
																// matriz
				pos_x = (y_matriz * 100); // posicao x comeca em 0 -> 0 +
											// (y_matiz+100), e Ã© incrementada
											// com o incremento da coluna da
											// matriz
				pos_y = 3200 - (x_matriz * 100); // posicao y comeÃ§a na posicao
													// maxima em pixels e
													// decrementa com o
													// decremento das linhas da
													// matriz

				switch (Mapa[x_matriz][y_matriz]) {

				// cases de 1 a 6 irao processar as posicoes onde as paredes do
				// labirinto irao ser colocadas no mapa
				case 1:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(1);
					parede.setScale((float) 0.7);
					lista_paredes_h.add(parede);
					attachChild(parede);

					break;

				case 2:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(2);
					parede.setScale((float) 0.7);
					lista_paredes_v.add(parede);
					attachChild(parede);

					break;

				case 3:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(3);
					parede.setScale((float) 0.72);
					lista_paredes_q.add(parede);
					attachChild(parede);

					break;

				case 4:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(4);
					parede.setScale((float) 0.7);
					lista_paredes_f.add(parede);
					attachChild(parede);

					break;

				case 5:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(5);
					parede.setScale((float) 0.7);
					lista_paredes_v.add(parede);
					attachChild(parede);

					break;

				case 6:
					parede = ParedeFactory.getInstance().createParede(pos_x, pos_y);
					parede.setCurrentTileIndex(6);
					parede.setScale((float) 0.7);
					lista_paredes_v.add(parede);
					attachChild(parede);

					break;

				// cases de 7 a 9 irao processar a posicao onde as bacterias sao
				// criadas mapa
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
				// cases de 10 a 11 irao processar a posicao onde os virus sao
				// criados no mapa
				case 10:
					Enemy enemy = EnemyFactory.getInstance().createEnemy(pos_x, pos_y);
					enemy.setScale((float) 0.2);
					enemy.getBody().setLinearVelocity(100, 0);
					enemies.add(i, enemy);
					attachChild(enemy);
					i++;
					break;

				case 11:

					break;

				}
			}
		}
	}

	private void createHUD() {
		HUD hud = new HUD();
		// escrevendo textos
		scoreText = new Text(16, 470, res.font, "SCORE " + GameManager.getInstance().getCurrentScore(),
				new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 1);
		hud.attachChild(scoreText);
		camera.setHUD(hud);
	}

	@Override
	public void onBackKeyPressed() {
		MusicPlayer.getInstance().stop();
		SceneManager.getInstance().showMenuScene();
		camera.setHUD(null);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	private void createPlayer2() {
		p1pos = player.getSceneCenterCoordinates();
		player2 = PlayerFactory.getInstance().createPlayer(p1pos[0], p1pos[1] + 100);
		player2.setScale((float) 0.2);
		attachChild(player2);
	}
}
