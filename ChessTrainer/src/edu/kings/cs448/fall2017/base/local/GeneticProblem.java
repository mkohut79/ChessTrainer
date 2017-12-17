package edu.kings.cs448.fall2017.base.local;

/**
 * A problem that can be solved using genetic algorithms.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem domain.
 */
public interface GeneticProblem<S> extends LocalSearchProblem<S> {

	/**
	 * Creates a new state from two parent states.
	 * 
	 * @param firstParent The first parent state.
	 * @param secondParent The second parent state.
	 * @return A new state created by combining the parents' "DNA".
	 */
	public abstract S crossover(S firstParent, S secondParent);
	
	/**
	 * Modifies a specified state in some randomly-chosen way.
	 * 
	 * @param theState The state to be mutated.
	 */
	public abstract void mutate(S theState);
}
