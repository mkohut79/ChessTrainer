package edu.kings.cs448.fall2017.base.games;

/**
 * An action in the game of Tic-Tac-Toe.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class TicTacToeAction {

	/** The symbol to be placed on the board. */
	private char symbol;
	/** The row to put it in. */
	private int row;
	/** The column to put it in. */
	private int col;
	
	/**
	 * Constructs a new TicTacToeAction.
	 * 
	 * @param theSymbol The symbol.
	 * @param theRow The row.
	 * @param theCol The column.
	 */
	public TicTacToeAction(char theSymbol, int theRow, int theCol) {
		symbol = theSymbol;
		row = theRow;
		col = theCol;
	}

	/**
	 * Gets the symbol.
	 * 
	 * @return The symbol.
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Gets the row.
	 * 
	 * @return The row.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column.
	 * 
	 * @return The column.
	 */
	public int getCol() {
		return col;
	}
	
	@Override
	public String toString() {
		return symbol + " in row " + row + ", column " + col;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result;
		if(o == null) {
			result = false;
		}
		else if(this == o) {
			result = true;
		}
		else if(!(o instanceof TicTacToeAction)) {
			result = false;
		}
		else {
			TicTacToeAction other = (TicTacToeAction)o;
			result = (symbol == other.symbol && row == other.row && col == other.col);
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return (symbol * 100) + (row * 10) + col;
	}
}
