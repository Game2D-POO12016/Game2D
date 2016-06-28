package poo.trabalho.labcrisis.entity;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

public class Player extends TiledSprite implements CollidableEntity{
	boolean dead = false;
	private int size = 1;
	private Body body;
	public static final String TYPE = "Player";
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
	
	public Player(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,pVertexBufferObjectManager);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void turnLeft() {
		setFlippedHorizontal(true);
	}
	
	public void turnRight() {
		setFlippedHorizontal(false);
	}
	
	public void fly() {
		setCurrentTileIndex(0);
	}
	
	public void fall() {
		setCurrentTileIndex(1);
	}
	
	public void die() {
		setDead(true);
		setCurrentTileIndex(2);
	}
	
}
