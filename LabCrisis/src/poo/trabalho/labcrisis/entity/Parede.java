package poo.trabalho.labcrisis.entity;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

public class Parede extends TiledSprite implements CollidableEntity{
	private Body body;
	public static final String TYPE = "Platform";
	
	public Parede(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,pVertexBufferObjectManager);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	@Override
	public void setBody(Body body) {
		this.body = body;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public String getType() {
		return TYPE;
	}	
		
}
