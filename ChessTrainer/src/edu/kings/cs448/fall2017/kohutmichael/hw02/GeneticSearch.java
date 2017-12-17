package edu.kings.cs448.fall2017.kohutmichael.hw02;

import java.util.ArrayList;
import java.util.Random;

import edu.kings.cs448.fall2017.base.local.GeneticProblem;

/**
 * Class that represents that genetic search algorithm.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 */
public class GeneticSearch<S> {

	/**
	 * Number within the generation.
	 */
	private int generationCount;

	/**
	 * Constructor initializing the generations needed.
	 */
	public GeneticSearch() {
		generationCount = 0;
	}

	/**
	 * Method to solve a genetic problem taking into account the likelyhood of a
	 * mutation and the generation size.
	 * 
	 * @param problem
	 *            The specific genetic problem that attempting to be solved.
	 * @param generationSize
	 *            The size of the generation being worked with.
	 * @param mutationRate
	 *            The likelyhood that a mutation will occur.
	 * @return The state that is an optimal solution to the problem.
	 */
	public S solve(GeneticProblem<S> problem, int generationSize, int mutationRate) {
		int counter = 0;
		// Generate random set of states
		ArrayList<S> currentGeneration = new ArrayList<S>();
		generationCount = 0;
		S optimalState = null;
		boolean keepGoing = true;
		while (counter < generationSize) {
			S toAdd = problem.getRandomState();
			currentGeneration.add(toAdd);
			counter++;
		}
		while (keepGoing) {

			S solution = null;
			for (S e : currentGeneration) {
				if (problem.getFitness(e) == problem.getMaximumFitness()) {
					solution = e;
					keepGoing = false;
				}
			}

			if (solution != null) {
				optimalState = solution;
				keepGoing = false;
			} else {

				// Continually pick two parents and created their child until
				// the nextGeneration is the same size as
				// the currentGeneration.
				int nextGen = 0;
				ArrayList<S> nextGeneration = new ArrayList<S>();
				while (nextGen < currentGeneration.size()) {
					S firstParent = fitnessProportionSelection(currentGeneration, problem);
					S secondParent = fitnessProportionSelection(currentGeneration, problem);
					S lovedChild = problem.crossover(firstParent, secondParent);
					Random mutateRate = new Random();
					int shouldMutate = mutateRate.nextInt(100);
					if (shouldMutate < mutationRate) {
						problem.mutate(lovedChild);
					}

					nextGeneration.add(lovedChild);
					nextGen++;
				}

				currentGeneration = nextGeneration;
				generationCount++;

			}
		}
		return optimalState;
	}

	/**
	 * Helper method to proportionally choose two parents based on their fitness
	 * values.
	 * 
	 * @param currentGeneration
	 *            The generation that needs a parent to be selected from.
	 * @param problem
	 *            The genetic problem used to calculate the fitness of the
	 *            members.
	 * @return The proportional parent.
	 */
	private S fitnessProportionSelection(ArrayList<S> currentGeneration, GeneticProblem<S> problem) {
		boolean found = false;
		S result = null;
		int accumulatedFitness = 0;
		Random rand = new Random();
		int totalFitness = 0;
		for (S e : currentGeneration) {
			totalFitness += problem.getFitness(e);
		}
		int neededFitness = rand.nextInt(totalFitness);
		int counter = 0;
		while (!found) {
			S currentState = currentGeneration.get(counter);
			accumulatedFitness += problem.getFitness(currentState);
			if (accumulatedFitness >= neededFitness) {
				found = true;
				result = currentState;
			} else {
				counter++;
			}
		}

		return result;
	}

	/**
	 * Returns the number of generations needed the last time a problem was
	 * solved.
	 * 
	 * @return The number of generations.
	 */
	public int getGenerationCount() {
		return generationCount;
	}
}
