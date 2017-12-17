package edu.kings.cs448.fall2017.kohutmicael.hw03;

import edu.kings.cs448.fall2017.base.games.GamePlayer;
import edu.kings.cs448.fall2017.base.games.GameUtilities;
import edu.kings.cs448.fall2017.base.games.StrategyAlgorithm;

/**
 * Program for playing a game of connect four.
 * 
 * @author Michael Kohut
 *
 */
public class ConnectFourMain {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		
		System.out.println("Let's play a game of ConnectFour!");
		StrategyAlgorithm<ConnectFourState, ConnectFourAction> firstPlayer = GameUtilities.chooseAlgorithm(1);
		StrategyAlgorithm<ConnectFourState, ConnectFourAction> secondPlayer = GameUtilities.chooseAlgorithm(2);
		ConnectFourGame game = new ConnectFourGame();
		ConnectFourState finalState = GameUtilities.playGame(game, firstPlayer, secondPlayer);

		if (finalState.didPlayerWin(GamePlayer.MAX)) {
			System.out.println(GamePlayer.MAX.getName() + " wins!");
		} else if (finalState.didPlayerWin(GamePlayer.MIN)) {
			System.out.println(GamePlayer.MIN.getName() + " wins!");
		} else {
			System.out.println("The game is a draw!");
		}

	}

}
