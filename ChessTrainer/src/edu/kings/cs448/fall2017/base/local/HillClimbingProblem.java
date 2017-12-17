package edu.kings.cs448.fall2017.base.local;

import java.util.Set;

/**
 * A problem that can be solved using Hill-Climbing techniques.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem.
 */
public interface HillClimbingProblem<S> extends LocalSearchProblem<S> {

	/**
	 * Gets the states that can be reached from a state in a single step.
	 * 
	 * @param theState The state whose neighbors are desired.
	 * @return The neighbors of a state.
	 */
	public abstract Set<S> getNeighbors(S theState);
	
}
