package edu.kings.cs448.fall2017.base.games;

import java.util.Arrays;

/**
 * A state in the game of Tic-Tac-Toe.
 * 
 * @author CS448, Chad Hogg
 * @version 2017
 */
public class TicTacToeState {

	/** The board itself. */
	private char[][] gameBoard;

	/** The number of spots Player.MAX has filled. */
	private int maxCount;
	/** The number of spots Player.MIN has filled. */
	private int minCount;

	/**
	 * Constructs a new TicTacToeState for the beginning of a game.
	 */
	public TicTacToeState() {
		gameBoard = new char[3][3];
		maxCount = 0;
		minCount = 0;
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard[row].length; col++) {
				gameBoard[row][col] = ' ';
			}
		}
	}

	/**
	 * Constructs a new TicTacToeState based on an existing array.
	 * 
	 * @param board
	 *            A 2-d array of characters.
	 */
	public TicTacToeState(char[][] board) {
		gameBoard = new char[3][3];
		maxCount = 0;
		minCount = 0;
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard[row].length; col++) {
				char player = board[row][col];
				gameBoard[row][col] = player;
				if (player == GamePlayer.MAX.getSymbol()) {
					maxCount++;
				} else if (player == GamePlayer.MIN.getSymbol()) {
					minCount++;
				}
			}
		}
	}

	/**
	 * Gets a deep copy of the board for this state.
	 * 
	 * @return A deep copy of the board for this state.
	 */
	public char[][] getGameBoardCopy() {
		char[][] copy = new char[3][3];
		for (int row = 0; row < copy.length; row++) {
			for (int col = 0; col < copy.length; col++) {
				copy[row][col] = gameBoard[row][col];
			}
		}
		return copy;
	}

	/**
	 * Gets the character at a specified row and column.
	 * 
	 * @param row
	 *            The row number.
	 * @param col
	 *            The column number.
	 * @return The character at that location on the board.
	 */
	public char getSymbol(int row, int col) {
		return gameBoard[row][col];
	}

	/**
	 * Gets the GamePlayer whose turn it is in this TicTacToeState.
	 * 
	 * @return The GamePlayer whose turn it is in this TicTacToeState.
	 */
	public GamePlayer getCurrentPlayer() {
		GamePlayer result = GamePlayer.MAX;

		if (maxCount > minCount) {
			result = GamePlayer.MIN;
		}

		return result;
	}

	/**
	 * Gets whether or not the board is completely full in this TicTacToeState.
	 * 
	 * @return Whether or not the board is completely full.
	 */
	public boolean isFull() {
		boolean result = false;
		if (maxCount + minCount == 9) {
			result = true;
		}
		return result;
	}

	/**
	 * Gets whether or not a given GamePlayer has won in this state.
	 * 
	 * @param thePlayer
	 *            The GamePlayer who might have won.
	 * @return Whether or not they won.
	 */
	public boolean didPlayerWin(GamePlayer thePlayer) {
		boolean result = false;
		// check rows
		int i = 0;
		while (i < gameBoard.length && !result) {
			char firstChar = gameBoard[i][0];
			char secondChar = gameBoard[i][1];
			char thirdChar = gameBoard[i][2];
			if (firstChar == secondChar && secondChar == thirdChar) {
				if (thePlayer.getSymbol() == firstChar) {
					result = true;
				}
			}
			i++;
		}
		// columns
		i = 0;
		while (i < gameBoard.length && !result) {
			char firstChar = gameBoard[0][i];
			char secondChar = gameBoard[1][i];
			char thirdChar = gameBoard[2][i];
			if (firstChar == secondChar && secondChar == thirdChar) {
				if (thePlayer.getSymbol() == firstChar) {
					result = true;
				}
			}
			i++;
		}

		// diagonals
		if (!result) {
			char first = gameBoard[0][0];
			char second = gameBoard[1][1];
			char third = gameBoard[2][2];
			if (first == second && second == third) {
				if (thePlayer.getSymbol() == first) {
					result = true;
				}
			}
			if (!result) {
				first = gameBoard[0][2];
				third = gameBoard[2][0];
				if (first == second && second == third) {
					if (thePlayer.getSymbol() == first) {
						result = true;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets an estimate of the utility that would eventually be reached from
	 * this state. The heuristic I have chosen is the difference in the number
	 * of rows / columns / diagonals in which a win is still possible for each
	 * player.
	 * 
	 * @return An estimate of the utility that will be reached from this state
	 *         with both players playing optimally.
	 */
	public int evaluate() {
		int result = 0;
		if (didPlayerWin(GamePlayer.MAX)) {
			result = GamePlayer.MAX.getWinUtility();
		} else if (didPlayerWin(GamePlayer.MIN)) {
			result = GamePlayer.MIN.getWinUtility();
		} else if (isFull()) {
			result = 0;
		} else {
			char max = GamePlayer.MAX.getSymbol();
			char min = GamePlayer.MIN.getSymbol();
			// Check rows
			for (int i = 0; i < 3; i++) {
				if (gameBoard[i][0] != min && gameBoard[i][1] != min && gameBoard[i][2] != min) {
					result++;
				}
				if (gameBoard[i][0] != max && gameBoard[i][1] != max && gameBoard[i][2] != max) {
					result--;
				}
			}
			// Check columns
			for (int i = 0; i < 3; i++) {
				if (gameBoard[0][i] != min && gameBoard[1][i] != min && gameBoard[2][i] != min) {
					result++;
				}
				if (gameBoard[0][i] != max && gameBoard[1][i] != max && gameBoard[2][i] != max) {
					result--;
				}
			}
			// Check downward diagonal.
			if (gameBoard[0][0] != min && gameBoard[1][1] != min && gameBoard[2][2] != min) {
				result++;
			}
			if (gameBoard[0][0] != max && gameBoard[1][1] != max && gameBoard[2][2] != max) {
				result--;
			}
			// Check upward diagonal.
			if (gameBoard[0][2] != min && gameBoard[1][1] != min && gameBoard[2][0] != min) {
				result++;
			}
			if (gameBoard[0][2] != max && gameBoard[1][1] != max && gameBoard[2][0] != max) {
				result--;
			}
		}
		return result;

	}

	@Override
	public String toString() {
		StringBuffer seanBuffer = new StringBuffer();
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard.length; col++) {
				seanBuffer.append(gameBoard[row][col]);
				if (col <= 1) {
					seanBuffer.append("|");
				}
			}
			seanBuffer.append("\n");
			if (row <= 1) {
				seanBuffer.append("-----");
				seanBuffer.append("\n");
			}
		}
		return seanBuffer.toString();
	}

	@Override
	public boolean equals(Object o) {
		boolean result;
		if (o == null) {
			result = false;
		} else if (this == o) {
			result = true;
		} else if (!(o instanceof TicTacToeState)) {
			result = false;
		} else {
			TicTacToeState other = (TicTacToeState) o;
			result = Arrays.deepEquals(gameBoard, other.gameBoard);
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard.length; col++) {
				result += gameBoard[row][col];
				result = result << 3;
			}
		}
		return result;
	}
}
