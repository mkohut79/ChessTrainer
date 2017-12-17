/**
 * 
 */
package edu.kings.cs448.fall2017.base.games;

import java.util.Set;

/**
 * Any pure strategy game.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in this game.
 * @param <A> The type of actions in this game.
 */
public interface StrategyGame<S, A> {
		
	/**
	 * Gets the initial state of the game.
	 * 
	 * @return The initial state.
	 */
	public abstract S getInitialState();
	
	/**
	 * Gets the set of actions that can be taken from a state.
	 * 
	 * @param theState A state of the problem.
	 * @return The set of actions that can be taken from that state.
	 */
	public abstract Set<A> getActions(S theState);
	
	/**
	 * Gets the result of applying an action to a state.
	 * 
	 * @param theAction The chosen action.
	 * @param theState the old state.
	 * @return The resulting state.
	 */
	public abstract S getResultingState(A theAction, S theState);
	
	/**
	 * Gets whether or not a state is terminal.
	 * 
	 * @param theState The state in question.
	 * @return Whether or not it is a terminal state.
	 */
	public abstract boolean isTerminalState(S theState);
	
	/**
	 * Gets the value of a given state.
	 * 
	 * @param theState The given state.
	 * @return What the utility is for the state.
	 */
	public abstract int getUtility(S theState);
	
	/**
	 * Gets the player who is making the next move from a state.
	 * @param theState The current state being represented.
	 * @return The current player.
	 */
	public abstract GamePlayer getPlayer(S theState);
	
	/**
     * Gets an estimate of the utility that would eventually be reached from a state.
     * If the state happens to be terminal, this should return the same thing as getUtility().
     * 
     * @param state The state to evaluate.
     * @return An estimate of the utility of the terminal state most likely to be reached from the state.
     */
    public abstract int evaluateState(S state);
}
