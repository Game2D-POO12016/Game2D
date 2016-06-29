package poo.trabalho.labcrisis.scene;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

import poo.trabalho.labcrisis.GameActivity;
import poo.trabalho.labcrisis.MusicPlayer;

public class IntroScene extends AbstractScene {

	@Override
	public void populate() {
		Sprite bkgd = new Sprite(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2, res.introFase1TextureRegion, vbom);
		MusicPlayer.getInstance().playIntro();
		setBackground(new SpriteBackground(bkgd));
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

}
