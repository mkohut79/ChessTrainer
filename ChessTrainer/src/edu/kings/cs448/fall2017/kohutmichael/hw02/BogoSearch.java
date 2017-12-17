package edu.kings.cs448.fall2017.kohutmichael.hw02;

import edu.kings.cs448.fall2017.base.local.LocalSearchProblem;

/**
 * Class to represent the BOGO algorithm.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 */
public class BogoSearch<S> {

	/**
	 * Field storing states taken.
	 */
	private int statesNeeded;
	
	/**
	 * Constructor initializing the statesNeeded to zero.
	 */
	public BogoSearch() {
		statesNeeded = 0;
	}

	/**
	 * Randomly solve the problem by continuously picking random states.
	 * 
	 * @param problem
	 *            The problem to be solved.
	 * @return The solution.
	 */
	public S solve(LocalSearchProblem<S> problem) {
		S result = null;
		boolean done = false;
		while (!done) {
			S current = problem.getRandomState();
			if (problem.getFitness(current) == problem.getMaximumFitness()) {
				done = true;
				result = current;
			} else {
				current = problem.getRandomState();
				statesNeeded++;
			}
		}

		return result;
	}

	/**
	 * Getter for the states taken.
	 * @return The amount of states taken.
	 */
	public int getStatesNeeded() {
		return statesNeeded;
	}
}
