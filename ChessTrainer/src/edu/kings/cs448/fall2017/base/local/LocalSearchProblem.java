package edu.kings.cs448.fall2017.base.local;

/**
 * A problem that can be solved by at least one local search technique.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem.
 */
public interface LocalSearchProblem<S> {

	/**
	 * Gets a new, randomly chosen state in the problem domain.
	 * 
	 * @return A new, randomly chosen state.
	 */
	public abstract S getRandomState();

	/**
	 * Gets the fitness of a particular state.
	 * Higher numbers are better.
	 * 
	 * @param theState The state whose fitness is desired.
	 * @return The fitness of that state.
	 */
	public abstract int getFitness(S theState);
	
	/**
	 * Gets the maximum possible fitness that any state in this problem domain can have.
	 * 
	 * @return The maximum fitness a state can have.
	 */
	public abstract int getMaximumFitness();

}
