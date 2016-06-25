package poo.trabalho.labcrisis.factory;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.Constants;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Parede;

public class ParedeFactory {
	public static final FixtureDef PAREDE_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0.0f, 1f);
	private static ParedeFactory INSTANCE = new ParedeFactory();
	private PhysicsWorld physicsWorld;
	private VertexBufferObjectManager vbom;

	private ParedeFactory() {
	}

	public static ParedeFactory getInstance() {
		return INSTANCE;
	}

	public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
		this.physicsWorld = physicsWorld;
		this.vbom = vbom;
	}

	public Parede createParede(float x, float y) {
		Parede parede = new Parede(x, y, ResourceManager.getInstance().paredeTextureRegion, vbom);
		parede.setZIndex(2);
		parede.setAnchorCenterY(1);

		final float[] sceneCenterCoordinates = parede.getSceneCenterCoordinates();
		final float centerX = sceneCenterCoordinates[Constants.VERTEX_INDEX_X];
		final float centerY = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y];
		Body paredeBody = PhysicsFactory.createBoxBody(physicsWorld, centerX, centerY, parede.getWidth(),
				parede.getHeight(), BodyType.StaticBody, PAREDE_FIXTURE);
		parede.setUserData(parede);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(parede, paredeBody));
		parede.setBody(paredeBody);
		return parede;
	}
}
