package edu.kings.cs448.fall2017.base.search;

import java.util.Set;

/**
 * A generic problem that can be solved using search techniques.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in this problem domain.
 * @param <A> The type of actions in this problem domain.
 */
public interface SearchProblem<S, A> {
	
	/**
	 * Gets the initial state of the problem.
	 * 
	 * @return The initial state.
	 */
	public abstract S getInitialState();
	
	/**
	 * Gets the set of actions that may be taken from a given state.
	 * 
	 * @param theState A state.
	 * @return The set of actions that can be taken from that state.
	 */
	public abstract Set<A> getActions(S theState);
	
	/**
	 * Gets the result of applying a given action to a given state.
	 * 
	 * @param theAction An action.
	 * @param theState A state.
	 * @return The resulting state.
	 */
	public abstract S getResultingState(A theAction, S theState);
	
	/**
	 * Determines whether or not a state satisfies the goal test.
	 * 
	 * @param theState A state.
	 * @return Whether or not that state satisfies the goal test.
	 */
	public abstract boolean meetsGoal(S theState);
	
	/**
	 * Gets the cost of taking a given action from a given state.
	 * 
	 * @param theAction An action.
	 * @param theState A state.
	 * @return The step cost of taking the action from the state.
	 */
	public abstract int getStepCost(A theAction, S theState);
	
	/**
	 * Gets an estimated cost to achieve the goal from a certain state.
	 * 
	 * @param theState A state.
	 * @return An estimated cost of achieving the goal from that state.
	 */
	public abstract int heuristicForState(S theState);
}
