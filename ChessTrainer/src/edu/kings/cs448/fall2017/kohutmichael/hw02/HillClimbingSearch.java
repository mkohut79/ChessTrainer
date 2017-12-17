package edu.kings.cs448.fall2017.kohutmichael.hw02;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import edu.kings.cs448.fall2017.base.local.HillClimbingProblem;

/**
 * Class for the HillClimbinSearch algorithm.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 */
public class HillClimbingSearch<S> {

	/**
	 * Max moves taken to solve the problem.
	 */
	private int getMovesTaken = 0;

	/**
	 * Constructor to initialize the movesTaken.
	 */
	public HillClimbingSearch() {
		getMovesTaken = 0;
	}

	/**
	 * Attempts to solve a problem returning the best state it finds.
	 * 
	 * @param problem
	 *            The specific problem that is to be solved.
	 * @param maxSidewaysMoves
	 *            The maximum number of moves that can be taken to choose a
	 *            state with the same fitness level as the current one.
	 * @return The best possible state that can be found on the problem.
	 */
	public S solve(HillClimbingProblem<S> problem, int maxSidewaysMoves) {
		S current = problem.getRandomState();
		int consecutiveSideMoves = 0;
		boolean keepGoing = true;
		while (keepGoing) {
			if (problem.getFitness(current) == problem.getMaximumFitness()) {
				keepGoing = false;
			} else {
				Set<S> neighbors = problem.getNeighbors(current);
				int bestFitness = -1;
				ArrayList<S> best = new ArrayList<S>();
				for (S e : neighbors) {
					int nextNeighborFit = problem.getFitness(e);
					if (nextNeighborFit > bestFitness) {
						best.clear();
						bestFitness = nextNeighborFit;
						best.add(e);
					} else if (nextNeighborFit == bestFitness) {
						best.add(e);
					}
				}
				if (bestFitness > problem.getFitness(current)) {
					Random randomChoose = new Random();
					int choice = randomChoose.nextInt(best.size());
					current = best.get(choice);
					consecutiveSideMoves = 0;
					getMovesTaken++;
				} else if (bestFitness == problem.getFitness(current) && consecutiveSideMoves < maxSidewaysMoves) {
					Random randomChoose = new Random();
					int choice = randomChoose.nextInt(best.size());
					current = best.get(choice);
					consecutiveSideMoves++;
					getMovesTaken++;

				} else {
					keepGoing = false;
				}
			}

		}
		return current;
	}

	/**
	 * Returns the number of moves taken to solve a given problem.
	 * 
	 * @return The number of moves taken in the solve method which is either a
	 *         solution or a failure.
	 */
	public int getMovesTaken() {
		return getMovesTaken;
	}

}
