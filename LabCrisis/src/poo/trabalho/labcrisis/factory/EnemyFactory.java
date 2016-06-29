package poo.trabalho.labcrisis.factory;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class EnemyFactory {
	public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f);
	private static EnemyFactory INSTANCE = new EnemyFactory();
	private PhysicsWorld physicsWorld;
	private VertexBufferObjectManager vbom;
	
	private EnemyFactory() {}
			
	public static EnemyFactory getInstance() {
		return INSTANCE;
	}
	
	public void create(PhysicsWorld physicsWorld,VertexBufferObjectManager vbom) {
		this.physicsWorld = physicsWorld;
		this.vbom = vbom;
	}
	
	public Enemy createEnemy(float x, float y) {
		Enemy enemy = new Enemy(x, y, ResourceManager.getInstance().inimigoTextureRegion, vbom);
		Body enemyBody = PhysicsFactory.createCircleBody(physicsWorld, enemy,BodyType.KinematicBody, ENEMY_FIXTURE);
		enemyBody.setUserData(enemy);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(enemy,enemyBody));
		enemy.setBody(enemyBody);
		enemy.setZIndex(1);
		enemyBody.setFixedRotation(true);
		return enemy;
	}
}
