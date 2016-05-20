package poo.trabalho.labcrisis.factory;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Parede;

public class ParedeFactory {

	private static ParedeFactory INSTANCE = new ParedeFactory();
	private VertexBufferObjectManager vbom;

	private ParedeFactory() {
	}

	public static ParedeFactory getInstance() {
		return INSTANCE;
	}

	public void create(VertexBufferObjectManager vbom) {
		this.vbom = vbom;
	}

	public Parede createParede(float x, float y) {
		Parede parede = new Parede(x, y, ResourceManager.
				getInstance().paredeTextureRegion, vbom);
		parede.setZIndex(2);
		return parede;
	}
}