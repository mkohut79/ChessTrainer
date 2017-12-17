package edu.kings.cs448.fall2017.base.search;

import java.util.ArrayList;

/**
 * An interface for any way to solve a search problem.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem.
 * @param <A> The type of actions in the problem.
 */
public interface SearchSolver<S, A> {
	/**
	 * Solves a search problem.
	 * 
	 * @param problem The problem to be solved.
	 * @return A sequence of actions that solves the problem, or null if none exists.
	 */
	public abstract ArrayList<A> solve(SearchProblem<S, A> problem);
	
	/**
	 * Gets the number of nodes that were expanded for the previous solution.
	 * 
	 * @return The number of nodes that were expanded for the previous solution.
	 */
	public abstract int getNumExpandedNodes();
}
