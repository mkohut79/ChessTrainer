package edu.kings.cs448.fall2017.kohutmichael.hw02;

import java.util.Set;

import edu.kings.cs448.fall2017.base.local.GeneticProblem;
import edu.kings.cs448.fall2017.base.local.HillClimbingProblem;

/**
 * Class to represent the problem of the EightQueensBoard.
 * 
 * @author Michael Kohut
 *
 */
public class EightQueensProblem implements HillClimbingProblem<EightQueensState>, GeneticProblem<EightQueensState> {

	@Override
	public EightQueensState getRandomState() {
		return EightQueensState.getRandomState();

	}

	@Override
	public int getFitness(EightQueensState theState) {
		return theState.calculateFitness();
	}

	@Override
	public int getMaximumFitness() {
		return 28;
	}

	@Override
	public Set<EightQueensState> getNeighbors(EightQueensState theState) {
		return theState.getNeighbors();
	}

	@Override
	public EightQueensState crossover(EightQueensState firstParent, EightQueensState secondParent) {
		return EightQueensState.crossover(firstParent, secondParent);
	}

	@Override
	public void mutate(EightQueensState theState) {
		theState.mutate(theState);
	}

}
