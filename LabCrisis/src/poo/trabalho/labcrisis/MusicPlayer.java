package poo.trabalho.labcrisis;

	/*	TODO: Flexibilizar a implementação dos métodos de play e pause, 
	 * 	pois estão completamente dependentes de ResourceManager.
	 * */
	public class MusicPlayer {
		private static MusicPlayer INSTANCE = new MusicPlayer();
		private ResourceManager res;

		private MusicPlayer() {
			res = ResourceManager.getInstance();
			res.music.setLooping(true);
			res.menuMusic.setLooping(true);
			res.introMusic.setLooping(false);
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
				res.menuMusic.setVolume(0.5f, 0.5f);
			}
		}
		
		public void playIntro() {
			if (res.activity.isSound() && !res.introMusic.isPlaying()) {
				res.introMusic.play();
				res.introMusic.setVolume(0.5f, 0.5f);
			}
		}
		
		public void pause() {
			if (res.music.isPlaying()) {
				res.music.pause();
			}
		}
		
		public void pauseMenu() {
			if (res.menuMusic.isPlaying()) {
				res.menuMusic.pause();
			}
		}
		
		/*	TODO: seekTo não funciona corretamente!
		 * 	Procurar workaround ou outra maneira de usá-lo.
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