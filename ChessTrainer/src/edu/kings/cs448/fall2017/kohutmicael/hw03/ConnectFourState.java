package edu.kings.cs448.fall2017.kohutmicael.hw03;

import java.util.Arrays;

import edu.kings.cs448.fall2017.base.games.GamePlayer;

/**
 * State Class to represent the state of a connect four board.
 * 
 * @author Michael Kohut
 *
 */
public class ConnectFourState {

	/**
	 * Representation of the connect four board.
	 */
	private char[][] fourBoard;

	/** The number of spots Player.MAX has filled. */
	private int maxCount;
	/** The number of spots Player.MIN has filled. */
	private int minCount;
	/**
	 * Boolean to keep track if the max player has won the game.
	 */
	private boolean maxWon;
	
	/**
	 * Field to represent the evaluation of the state.
	 */
	private int evalState;

	/**
	 * Boolean to keep track if the min player has won the game.
	 */
	private boolean minWon;

	/**
	 * Constructs a new ConnectFourState for the beginning of a game.
	 */
	public ConnectFourState() {
		fourBoard = new char[6][7];
		maxCount = 0;
		minCount = 0;
		for (int row = 0; row < fourBoard.length; row++) {
			for (int col = 0; col < fourBoard[row].length; col++) {
				fourBoard[row][col] = ' ';
			}
		}
	}

	/**
	 * Constructs a new ConnectFourState based on an existing array.
	 * 
	 * @param board
	 *            A 2-d array of characters.
	 */
	public ConnectFourState(char[][] board) {
		fourBoard = new char[6][7];
		maxCount = 0;
		minCount = 0;
		for (int row = 0; row < fourBoard.length; row++) {
			for (int col = 0; col < fourBoard[row].length; col++) {
				char player = board[row][col];
				fourBoard[row][col] = player;
				if (player == GamePlayer.MAX.getSymbol()) {
					maxCount++;
				} else if (player == GamePlayer.MIN.getSymbol()) {
					minCount++;
				}
			}
		}
	}

	/**
	 * Setter for the max player winning.
	 * 
	 * @param maxWon
	 *            The value if the max player won.
	 */
	public void setMaxWon(boolean maxWon) {
		this.maxWon = maxWon;
	}

	/**
	 * Setter for the min player winning the game.
	 * 
	 * @param minWon
	 *            The value if the min player won.
	 */
	public void setMinWon(boolean minWon) {
		this.minWon = minWon;
	}

	/**
	 * Gets a deep copy of the board for this state.
	 * 
	 * @return A deep copy of the board for this state.
	 */
	public char[][] getGameBoardCopy() {
		char[][] copy = new char[6][7];
		for (int row = 0; row < fourBoard.length; row++) {
			for (int col = 0; col < fourBoard[row].length; col++) {
				copy[row][col] = fourBoard[row][col];
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
		return fourBoard[row][col];
	}

	/**
	 * Gets the GamePlayer whose turn it is in this ConnectFourState.
	 * 
	 * @return The GamePlayer whose turn it is in this ConnectFourState.
	 */
	public GamePlayer getCurrentPlayer() {
		GamePlayer result = GamePlayer.MAX;

		if (maxCount > minCount) {
			result = GamePlayer.MIN;
		}

		return result;
	}

	/**
	 * Gets whether or not the board is completely full in this
	 * ConnectFourState.
	 * 
	 * @return Whether or not the board is completely full.
	 */
	public boolean isFull() {
		return (maxCount + minCount == 42);

	}

	/**
	 * Method that will determine if a given player has won the game.
	 * 
	 * @param player
	 *            The player who might have won the game.
	 * @return True if the player has indeed won and false otherwise.
	 */
	public boolean didPlayerWin(GamePlayer player) {
		boolean winner = false;
		String playerName = player.getName();
		if (playerName.equals("MAX")) {
			winner = maxWon;
		} else {
			winner = minWon;
		}

		return winner;
	}

	/**
	 * Method to determine if a winner has been made after an action has taken
	 * place.
	 * 
	 * @param playerSymbol
	 *            The character that the player has just placed on the board.
	 * @param state
	 *            The current state of the board after the move has been made.
	 * @param row
	 *            The row that the action was placed in.
	 * @param col
	 *            The column that the action was placed in.
	 */
	public void updateWinner(char playerSymbol, ConnectFourState state, int row, int col) {
		boolean foundWinner = false;
		char[][] board = state.getGameBoardCopy();
		int maxPositions = 13;
		int positionsChecked = 0;
		
		int oneRight = col + 1;
		int twoRight = col + 2;
		int threeRight = col + 3;
		int oneLeft = col - 1;
		int twoLeft = col - 2;
		int threeLeft = col - 3;
		int oneUp = row - 1;
		int twoUp = row - 2;
		int threeUp = row - 3;
		int oneDown = row + 1;
		int twoDown = row + 2;
		int threeDown = row + 3;


		while (positionsChecked != maxPositions) {

			//Check vertical
			if (inRBounds(oneDown) && inRBounds(twoDown) && inRBounds(threeDown)) {
				if (board[oneDown][col] == playerSymbol && board[twoDown][col] == playerSymbol
						&& board[threeDown][col] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneDown, col, twoDown, col, threeDown, col);
			}
			//1
			positionsChecked++;

			//Check horizantal 
			if (inCBounds(oneRight) && inCBounds(twoRight) && inCBounds(threeRight)) {
				if (board[row][oneRight] == playerSymbol && board[row][twoRight] == playerSymbol
						&& board[row][threeRight] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, row, oneRight, row, twoRight, row, threeRight);	
			}
			//2
			positionsChecked++;

			if (inCBounds(oneLeft) && inCBounds(oneRight) && inCBounds(twoRight)) {
				if (board[row][oneLeft] == playerSymbol && board[row][oneRight] == playerSymbol
						&& board[row][twoRight] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, row, oneLeft, row, oneRight, row, twoRight);

			}
			//3
			positionsChecked++;

			if (inCBounds(oneLeft) && inCBounds(twoLeft) && inCBounds(oneRight)) {
				if (board[row][oneLeft] == playerSymbol && board[row][twoLeft] == playerSymbol
						&& board[row][oneRight] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, row, oneLeft, row, twoLeft, row, oneRight);

			}
			//4
			positionsChecked++;

			if (inCBounds(oneLeft) && inCBounds(twoLeft) && inCBounds(threeLeft)) {
				if (board[row][oneLeft] == playerSymbol && board[row][twoLeft] == playerSymbol
						&& board[row][threeLeft] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, row, oneLeft, row, twoLeft, row, threeLeft);

			}
			//5
			positionsChecked++;

			//Check upper diagonal
			if (inCBounds(oneRight) && inRBounds(oneUp) && inCBounds(twoRight) && inRBounds(twoUp)
					&& inRBounds(threeRight) && inRBounds(threeUp)) 
			{
				if (board[oneUp][oneRight] == playerSymbol && board[twoUp][twoRight] == playerSymbol
						&& board[threeUp][threeRight] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneUp, oneRight, twoUp, twoRight, threeUp, threeRight);

			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneDown) && inCBounds(oneRight) && inRBounds(oneUp)
					&& inRBounds(twoRight) && inRBounds(twoUp))
			{
				if (board[oneDown][oneLeft] == playerSymbol && board[oneUp][oneRight] == playerSymbol
						&& board[twoUp][twoRight] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneDown, oneLeft, oneUp, oneRight, twoUp, twoRight);
				
			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneDown) && inCBounds(twoLeft) && inRBounds(twoDown)
					&& inRBounds(oneRight) && inRBounds(oneUp))
			{
				if (board[oneDown][oneLeft] == playerSymbol && board[twoDown][twoLeft] == playerSymbol
						&& board[oneUp][oneRight] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneDown, oneLeft, twoDown, twoLeft, oneUp, oneRight);

			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneDown) && inCBounds(twoLeft) && inRBounds(twoDown)
					&& inRBounds(threeLeft) && inRBounds(threeDown))
			{
				if (board[oneDown][oneLeft] == playerSymbol && board[twoDown][twoLeft] == playerSymbol
						&& board[threeDown][threeLeft] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneDown, oneLeft, twoDown, twoLeft, threeDown, threeLeft);

			}
			positionsChecked++;

			// Upper Left Diagonal
			if (inCBounds(oneRight) && inRBounds(oneDown) && inCBounds(twoRight) && inRBounds(twoDown)
					&& inRBounds(threeRight) && inRBounds(threeDown))
			{
				if (board[oneDown][oneRight] == playerSymbol && board[twoDown][twoRight] == playerSymbol
						&& board[threeDown][threeRight] == playerSymbol)
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneDown, oneRight, twoDown, twoRight, threeDown, threeRight);
			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneUp) && inCBounds(oneRight) && inRBounds(oneDown)
					&& inRBounds(twoRight) && inRBounds(twoDown))
			{
				if (board[oneUp][oneLeft] == playerSymbol && board[oneDown][oneRight] == playerSymbol
						&& board[twoDown][twoRight] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneUp, oneLeft, oneDown, oneRight, twoDown, twoRight);

			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneUp) && inCBounds(twoLeft) && inRBounds(twoUp) && inRBounds(oneRight)
					&& inRBounds(oneDown)) 
			{
				if (board[oneUp][oneLeft] == playerSymbol && board[twoUp][twoLeft] == playerSymbol
						&& board[oneDown][oneRight] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneUp, oneLeft, twoUp, twoLeft, oneDown, oneRight);

			}
			positionsChecked++;

			if (inCBounds(oneLeft) && inRBounds(oneUp) && inCBounds(twoLeft) && inRBounds(twoUp) && inRBounds(threeLeft)
					&& inRBounds(threeUp))
			{
				if (board[oneUp][oneLeft] == playerSymbol && board[twoUp][twoLeft] == playerSymbol
						&& board[threeUp][threeLeft] == playerSymbol) 
				{
					foundWinner = true;
				}
				updateEval(board, playerSymbol, oneUp, oneLeft, twoUp, twoLeft, threeUp, threeLeft);

			}
			positionsChecked++;

		}

		if (foundWinner) {
			if (playerSymbol == 'X') {
				state.setMaxWon(true);
			} else {
				state.setMinWon(true);
			}

		}
		
		state.setEvalState(evalState);
		

	}
	
	/**
	 * Setter for the running evaluation total.
	 * @param evalState The amount to be assigned to the state.
	 */
	public void setEvalState(int evalState) {
		this.evalState = evalState;
	}

	/**
	 * Helper method to detrmine if a win is still possible given the action that has been taken. 
	 * @param board The state of the board.
	 * @param symbol The symbol of the player that has moved.
	 * @param row1 The x Coordinate of the first surrounding square.
	 * @param col1 The y Coordinate of the first surrounding square.
	 * @param row2 The x Coordinate of the second surrounding square.
	 * @param col2 The x Coordinate of the second surrounding square.
	 * @param row3 The x Coordinate of the third surrounding square.
	 * @param col3 The x Coordinate of the third surrounding square.
	 */
	public void updateEval(char[][] board, char symbol, int row1, int col1, int row2, int col2, int row3, int col3) {
		
		if (board[row1][col1] != symbol && board[row2][col2] != symbol && board[row3][col3] != symbol) {
			if (symbol == 'O') {
				evalState++;
			}
			else {
				evalState--;
			}
		}
	}

	/**
	 * Helper method to determine if the value is in the bounds of the board.
	 * 
	 * @param value
	 *            The vaoue that may be in the bounds.
	 * @return True if the value is in the row bounds false otherwise.
	 */
	public boolean inRBounds(int value) {
		return (value >= 0 && value < 6);
	}

	/**
	 * Helper method to determine if the value is in the column bounds of the
	 * board.
	 * 
	 * @param value
	 *            The value checked if it fits in the board.
	 * @return True if the value is in the column constraints false otherwise.
	 */
	public boolean inCBounds(int value) {
		return (value >= 0 && value < 7);
	}

	/**
	 * Gets an estimate of the utility that would eventually be reached from
	 * this state.
	 * 
	 * @return An estimate of the utility that will be reached from this state
	 *         with both players playing optimally.
	 */
	public int evaluate() {
		
		return evalState;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int row = 0; row < fourBoard.length; row++) {
			for (int col = 0; col < fourBoard[row].length; col++) {
				buffer.append(fourBoard[row][col]);
				if (col <= 6) {
					buffer.append("|");
				}
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		int result = 0;
		for (int row = 0; row < fourBoard.length; row++) {
			for (int col = 0; col < fourBoard[row].length; col++) {
				result += fourBoard[row][col];
				result = result << 7;
			}
		}                
		return result;

	}

	@Override
	public boolean equals(Object o) {
		boolean result;
		if (o == null) {
			result = false;
		} else if (this == o) {
			result = true;
		} else if (!(o instanceof ConnectFourState)) {
			result = false;
		} else {
			ConnectFourState other = (ConnectFourState) o;
			result = Arrays.deepEquals(fourBoard, other.fourBoard);
		}
		return result;
	}

}
