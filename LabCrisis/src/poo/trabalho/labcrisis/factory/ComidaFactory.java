package poo.trabalho.labcrisis.factory;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import poo.trabalho.labcrisis.ResourceManager;
import poo.trabalho.labcrisis.entity.Comida;

public class ComidaFactory {
	private static ComidaFactory INSTANCE = new ComidaFactory();
	private VertexBufferObjectManager vbom;

	private ComidaFactory() {
	}

	public static ComidaFactory getInstance() {
		return INSTANCE;
	}

	public void create(VertexBufferObjectManager vbom) {
		this.vbom = vbom;
	}

	public Comida createComida(float x, float y) {
		Comida comida = new Comida(x, y, ResourceManager.getInstance().comidaTextureRegion, vbom);
		comida.setZIndex(2);
		return comida;
	}
}