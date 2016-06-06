package poo.trabalho.labcrisis.scene;

import org.andengine.util.debug.Debug;
import android.os.AsyncTask;
import poo.trabalho.labcrisis.ResourceManager;

/**
 * Classe que gerencia as cenas do jogo bem como as migracoes de uma cena para outra.
 * @author ALEXANDRE CORREIA
 *
 */

public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	public static final long SPLASH_DURATION = 2000;
	private ResourceManager res = ResourceManager.getInstance();
	private AbstractScene currentScene;
	private LoadingScene loadingScene = null;
	
	/**
	 * Construtor vazio
	 */
	
	private SceneManager() {
	}
	
	/**
	 * Captura a instancia da cena
	 * @return instancia da cena
	 */
	
	public static SceneManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Pega a cena setada como atual
	 * @return cena atual
	 */
	
	public AbstractScene getCurrentScene() {
		return currentScene;
	}
	
	/**
	 * Seta uma cena
	 * @param currentScene: cena
	 */
	
	public void setCurrentScene(AbstractScene currentScene) {
		this.currentScene = currentScene;
		res.engine.setScene(currentScene);
		Debug.i("Current scene: " + currentScene.getClass().getName());
	}
	
	/**
	 * Primeira cena do jogo, a cena de splash, eh mostrada logo quando
	 * abre o aplicativo. Alem disso, instancia um objeto de loading e um 
	 * objeto que organiza a cena de menu.
	 * @return cena de splash
	 */
	
	public AbstractScene showSplashAndMenuScene() {
		final SplashScene splashScene = new SplashScene();
		splashScene.populate();
		setCurrentScene(splashScene);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				long timestamp = System.currentTimeMillis();
				res.loadFont();
				res.loadGameAudio();
				res.loadGameGraphics();
				loadingScene = new LoadingScene();
				loadingScene.populate();
				AbstractScene nextScene = new MenuSceneWrapper();
				
				if (System.currentTimeMillis() - timestamp < SPLASH_DURATION) {
					try {
						Thread.sleep(SPLASH_DURATION - (System.currentTimeMillis() - timestamp) );
					} catch (InterruptedException e) {
						Debug.e("Interrupted", e);
					}
				}
				
				nextScene.populate();
				setCurrentScene(nextScene);
				splashScene.destroy();
				res.unloadSplashGraphics();
				return null;
			}
		}.execute();
		return splashScene;
	}
	
	/**
	 * Migra para a cena da fase do jogo.
	 * Ha a presenca da loadingScene.
	 */
	
	public void showGameScene() {
		/**
		 * Seta a ultima cena.
		 */
		final AbstractScene previousScene = getCurrentScene();
		/**
		 * Seta a cena atual.
		 */
		setCurrentScene(loadingScene);
		/**
		 * Tarefa de migracao de uma cena para outra.
		 * Caso retorne null, a migracao foi bem-sucedida.
		 */
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				/**
				 * A cena de loading fica ativa por um tempo antes de migrar para a fase.
				 * Caso for interrompida nesse tempo, eh tratada a excecao de interrupcao.
				 */
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Debug.e("Interrupted", e);
				}
				/**
				 * Instancia a cena da fase e a popula na camera do Android.
				 * Depois, a cena anterior eh destruida e a atual eh setada
				 * como CurrentScene.
				 */
				Fase_01Scene gameScene = new Fase_01Scene();
				gameScene.populate();
				setCurrentScene(gameScene);
				previousScene.destroy();
				return null;
			}
		}.execute();
	}
	
	/**
	 * Migra para o menu do jogo.
	 */
	
	public void showMenuScene() {
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					MenuSceneWrapper menuSceneWrapper = new MenuSceneWrapper();
					menuSceneWrapper.populate();
					setCurrentScene(menuSceneWrapper);
					previousScene.destroy();
					return null;
				}
			}.execute();
	}
	
	/**
	 * Migra para a cena de tutorial do jogo.
	 */
	
	public void showHowToPlayScene() {
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Debug.e("Interrupted", e);
					}
					HowToPlayScene howtoplayScene = new HowToPlayScene();
					howtoplayScene.populate();
					previousScene.destroy();
					setCurrentScene(howtoplayScene);
					return null;
				}
			}.execute();
	}
	
	/**
	 * Migra para a cena de game over.
	 */
	
	public void showGameOverScene() {
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Debug.e("Interrupted", e);
					}
					GameOverScene gameoverScene = new GameOverScene();
					gameoverScene.populate();
					previousScene.destroy();
					setCurrentScene(gameoverScene);
					return null;
				}
			}.execute();
	}
	
}