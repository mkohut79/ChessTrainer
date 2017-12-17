package edu.kings.cs448.fall2017.base.games;


/**
 * A program for playing Tic-Tac-Toe.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class TicTacToeMain {

	/**
	 * The main method.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		System.out.println("Let's play a game of Tic-Tac-Toe!");
		
		StrategyAlgorithm<TicTacToeState, TicTacToeAction> firstPlayer = GameUtilities.chooseAlgorithm(1);
		StrategyAlgorithm<TicTacToeState, TicTacToeAction> secondPlayer = GameUtilities.chooseAlgorithm(2);
		TicTacToeGame game = new TicTacToeGame();
		TicTacToeState finalState = GameUtilities.playGame(game, firstPlayer, secondPlayer);

		if(finalState.didPlayerWin(GamePlayer.MAX)) {
			System.out.println(GamePlayer.MAX.getName() + " wins!");
		}
		else if(finalState.didPlayerWin(GamePlayer.MIN)) {
			System.out.println(GamePlayer.MIN.getName() + " wins!");
		}
		else {
			System.out.println("The game is a draw!");
		}

	}

}
