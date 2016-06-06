package poo.trabalho.labcrisis;

	/*	TODO: Flexibilizar a implementa��o dos m�todos de play e pause, 
	 * 	pois est�o completamente dependentes de ResourceManager.
	 * */
	public class MusicPlayer {
		private static MusicPlayer INSTANCE = new MusicPlayer();
		private ResourceManager res;

		private MusicPlayer() {
			res = ResourceManager.getInstance();
			res.music.setLooping(true);
			res.menuMusic.setLooping(true);
		}
		
		public static MusicPlayer getInstance() {
			return INSTANCE;
		}

		public void play() {
			if (res.activity.isSound() && !res.music.isPlaying()) {
				res.music.play();
			}
		}
		
		public void playMenu() {
			if (res.activity.isSound() && !res.menuMusic.isPlaying()) {
				res.menuMusic.play();
			}
		}
		
		public void pause() {
			if (res.music.isPlaying()) {
				res.music.pause();
			}
		}
		
		public void pauseMenu() {
			if (res.activity.isSound() && !res.menuMusic.isPlaying()) {
				res.menuMusic.pause();
				res.menuMusic.setVolume(0.5f, 0.5f);
			}
		}
		
		public void restartMenu() {
			res.menuMusic.seekTo(0);
		}
		
		/*	TODO: seekTo n�o funciona corretamente!
		 * 	Procurar workaround ou outra maneira de us�-lo.
		 * */
		
		public void stopMenu() {
			if (res.menuMusic.isPlaying()) {
				res.menuMusic.pause();
				res.menuMusic.seekTo(0);
			}
		}
		
		public void stop() {
			if (res.music.isPlaying()) {
				res.music.pause();
				res.music.seekTo(0);
			}
		}

	}