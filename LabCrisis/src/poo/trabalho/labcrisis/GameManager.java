package poo.trabalho.labcrisis;

/**	Game Manager eh a classe responsavel pelo score, vidas(se houver) e outras variaveis do dominio
 *	essa classe foi construida seguindo o padrao sigleton
 *	@author Matheus Crestani 
 **/
public class GameManager {
	private int mCurrentScore = 0;
	private static GameManager INSTANCE;
	GameManager(){}
	/** getInstance: metodo para obter instancia*/
	public static GameManager getInstance(){
		if (INSTANCE == null) INSTANCE = new GameManager();
		return INSTANCE;
	}
	/** getCurrentScore: getter para obter Score*/
	public int getCurrentScore(){
		return this.mCurrentScore;
	}
	/** incrementScore: setter para score, recebe como parametros um inteiro 
	 que representa o incremento desejado*/
	public void incrementScore(int pIncrementBy){
		mCurrentScore += pIncrementBy;
	}
	
	public void reset(){
		mCurrentScore=0;
	}
}
