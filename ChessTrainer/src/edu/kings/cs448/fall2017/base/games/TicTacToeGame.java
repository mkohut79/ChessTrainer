package edu.kings.cs448.fall2017.base.games;

import java.util.HashSet;
import java.util.Set;

/**
 * A game of Tic-Tac-Toe.
 * 
 * @author CS448
 * @version 2017
 */
public class TicTacToeGame implements
		StrategyGame<TicTacToeState, TicTacToeAction> {

	/**
	 * The initial state (an empty board).
	 */
	private TicTacToeState initialState;

	/**
	 * Constructs a new TicTacToeGame.
	 */
	public TicTacToeGame() {
		initialState = new TicTacToeState();
	}

	@Override
	public TicTacToeState getInitialState() {
		return initialState;
	}

	@Override
	public Set<TicTacToeAction> getActions(TicTacToeState theState) {
		char playerSymbol = getPlayer(theState).getSymbol();

		Set<TicTacToeAction> actions = new HashSet<TicTacToeAction>();

		char[][] board = theState.getGameBoardCopy();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				char current = board[i][j];
				if (current == ' ') {
					actions.add(new TicTacToeAction(playerSymbol, i, j));
				}
			}
		}

		return actions;
	}

	@Override
	public TicTacToeState getResultingState(TicTacToeAction theAction,
			TicTacToeState theState) {
		TicTacToeState result = null;
		char[][] board = theState.getGameBoardCopy();
		board[theAction.getRow()][theAction.getCol()] = theAction.getSymbol();
		result = new TicTacToeState(board);
		return result;
	}

	@Override
	public boolean isTerminalState(TicTacToeState theState) {
		boolean result = false;
		if(theState.isFull() || theState.didPlayerWin(GamePlayer.MAX) || theState.didPlayerWin(GamePlayer.MIN)){
			result = true;
		}
		
		return result;
	}

	@Override
	public int getUtility(TicTacToeState theState) {
		int result = 0;
		if(theState.didPlayerWin(GamePlayer.MAX)){
			result = GamePlayer.MAX.getWinUtility();
		}
		else if(theState.didPlayerWin(GamePlayer.MIN)){
			result = GamePlayer.MIN.getWinUtility();			
		}
		return result;
	}

	@Override
	public GamePlayer getPlayer(TicTacToeState theState) {
		return theState.getCurrentPlayer();
	}

	@Override
	public int evaluateState(TicTacToeState state) {
		return state.evaluate();
	}

}
