package edu.kings.cs448.fall2017.base.games;

/**
 * A player in a two-player, pure strategy game.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public enum GamePlayer {
	
	/** The player who prefers high utilities and goes first. */
	MAX("MAX", 10000, 'X'),
	/** The player who prefers low utilities and goes second. */
	MIN("MIN", -10000, 'O');
	
	/** The name of this GamePlayer. */
	private String name;
	/** The utility of a winning state for this GamePlayer. */
	private int winUtility;
	/** The symbol used to represent this GamePlayer on a game board. */
	private char symbol;
	
	/**
	 * Constructs a new GamePlayer.
	 * 
	 * @param name The name of the new GamePlayer.
	 * @param winUtility The utility of a winning state for the new GamePlayer.
	 * @param symbol The symbol used to represent the new GamePlayer.
	 */
	private GamePlayer(String name, int winUtility, char symbol) {
		this.name = name;
		this.winUtility = winUtility;
		this.symbol = symbol;
	}

	/**
	 * Gets the name of this GamePlayer.
	 * 
	 * @return The name of this GamePlayer.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the utility of winning states for this GamePlayer.
	 * 
	 * @return The utility of winning states for this GamePlayer.
	 */
	public int getWinUtility() {
		return winUtility;
	}
	
	/**
	 * Gets the symbol used to represent this GamePlayer on game boards.
	 * 
	 * @return The symbol used to represent this GamePlayer on game boards.
	 */
	public char getSymbol() {
		return symbol;
	}
}
