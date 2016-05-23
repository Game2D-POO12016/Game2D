package poo.trabalho.labcrisis.factory;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.Constants;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Comida;

public class ComidaFactory {
	public static final FixtureDef COMIDA_FIXTURE = PhysicsFactory.createFixtureDef(0f, 0f, 1f, false);
	private static ComidaFactory INSTANCE = new ComidaFactory();
	private VertexBufferObjectManager vbom;
	private PhysicsWorld physicsWorld;

	private ComidaFactory() {
	}

	public static ComidaFactory getInstance() {
		return INSTANCE;
	}

	public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
		this.physicsWorld = physicsWorld;
		this.vbom = vbom;
	}

	public Comida createComida(float x, float y) {
		Comida comida = new Comida(x, y, ResourceManager.getInstance().comidaTextureRegion, vbom);
		comida.setZIndex(2);
		comida.setAnchorCenterY(1);
		
		final float[] sceneCenterCoordinates =	comida.getSceneCenterCoordinates();
		final float centerX =sceneCenterCoordinates[Constants.VERTEX_INDEX_X];
		final float centerY =sceneCenterCoordinates[Constants.VERTEX_INDEX_Y];
		Body comidaBody = PhysicsFactory.createBoxBody(physicsWorld,centerX, centerY,comida.getWidth(), comida.getHeight(),BodyType.KinematicBody, COMIDA_FIXTURE);
		comida.setUserData(comida);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(comida, comidaBody));
		comida.setBody(comidaBody);
		return comida;
	}
}
