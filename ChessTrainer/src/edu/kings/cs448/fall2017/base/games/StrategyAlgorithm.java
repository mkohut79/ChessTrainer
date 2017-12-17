package edu.kings.cs448.fall2017.base.games;

/**
 * Any agent that plays a game.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the game.
 * @param <A> The type of actions in the game.
 */
public interface StrategyAlgorithm<S, A> {

	/**
	 * Gets the next action to take in a given state.
	 * 
	 * @param game The game being played.
	 * @param state The current state under consideration.
	 * @return The move that the agent would like to make.
	 */
	public abstract A nextAction(StrategyGame<S, A> game, S state);
	
	/**
	 * Gets the number of states that were explored the last time this algorithm made a move.
	 * 
	 * @return The number of states that were explored the last time this algorithm made a move.
	 */
	public abstract int getStateCount();
	
}
