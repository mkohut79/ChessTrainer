package edu.kings.cs448.fall2017.kohutmicael.hw03;

import java.util.HashSet;
import java.util.Set;

import edu.kings.cs448.fall2017.base.games.StrategyGame;
import edu.kings.cs448.fall2017.base.games.GamePlayer;

/**
 * Game class to represent a game of connect four.
 * 
 * @author Michael Kohut
 *
 */
public class ConnectFourGame implements StrategyGame<ConnectFourState, ConnectFourAction> {

	/**
	 * The initial state (an empty board).
	 */
	private ConnectFourState initialState;

	/**
	 * Constructs a new TicTacToeGame.
	 */
	public ConnectFourGame() {
		initialState = new ConnectFourState();
	}

	@Override
	public ConnectFourState getInitialState() {
		return initialState;
	}

	@Override
	public Set<ConnectFourAction> getActions(ConnectFourState theState) {
		char playerSymbol = getPlayer(theState).getSymbol();

		Set<ConnectFourAction> actions = new HashSet<ConnectFourAction>();

		char[][] board = theState.getGameBoardCopy();
		for (int i = 0; i < 7; i++) {
			boolean foundSpace = false;
			for (int j = 5; j >= 0 && !foundSpace; j--) {
				char current = board[j][i];
				if (current == ' ') {
					actions.add(new ConnectFourAction(playerSymbol, j, i));
					foundSpace = true;
				}
			}
		}

		return actions;
	}

	@Override
	public ConnectFourState getResultingState(ConnectFourAction theAction, ConnectFourState theState) {
		ConnectFourState result = null;
		char[][] board = theState.getGameBoardCopy();
		board[theAction.getRow()][theAction.getCol()] = theAction.getSymbol();
		result = new ConnectFourState(board);
		result.updateWinner(theAction.getSymbol(), result, theAction.getRow(), theAction.getCol());
		return result;
	}

	@Override
	public boolean isTerminalState(ConnectFourState theState) {
		boolean result = false;
		if (theState.isFull() || theState.didPlayerWin(GamePlayer.MAX) || theState.didPlayerWin(GamePlayer.MIN)) {
			result = true;
		}

		return result;
	}

	@Override
	public int getUtility(ConnectFourState theState) {
		int result = 0;
		if (theState.didPlayerWin(GamePlayer.MAX)) {
			result = GamePlayer.MAX.getWinUtility();
		} else if (theState.didPlayerWin(GamePlayer.MIN)) {
			result = GamePlayer.MIN.getWinUtility();
		}
		return result;
	}

	@Override
	public GamePlayer getPlayer(ConnectFourState theState) {
		return theState.getCurrentPlayer();
	}

	@Override
	public int evaluateState(ConnectFourState state) {
		return state.evaluate();
	}

}
