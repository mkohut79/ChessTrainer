package edu.kings.cs448.fall2017.kohutmichael.hw02;

/**
 * Main method to test the algorithm and the EightQueensState,
 * EightQueensProblem, and HillClimbingSearch class.
 * 
 * @author Michael Kohut
 *
 */
public class EightQueensMain {

	/**
	 * MAin arguments to be processed.
	 * 
	 * @param args
	 *            To be processed.
	 */
	public static void main(String[] args) {

		// Run 1000 times with not sideways moves allowed.
		System.out.println("Standard Hill-Climbing algorithm results ");
		int counter = 0;
		int successes = 0;
		int movesToSolve = 0;
		int movesInFailure = 0;
		while (counter < 1000) {
			HillClimbingSearch<EightQueensState> solver = new HillClimbingSearch<EightQueensState>();
			EightQueensProblem newProblem = new EightQueensProblem();
			EightQueensState resultState = solver.solve(newProblem, 0);
			int movesTaken = solver.getMovesTaken();
			if (newProblem.getFitness(resultState) == newProblem.getMaximumFitness()) {
				successes++;
				movesToSolve += movesTaken;
			} else {
				movesInFailure += movesTaken;
			}
			counter++;
		}

		System.out.println("Successful times : " + successes);
		float solvePercent = (float) ((successes * 100) / counter);
		System.out.println("Success Rate : " + solvePercent + "%");
		float movesSolve = (float) movesToSolve;
		float success = (float) successes;
		float failingMoves = (float) movesInFailure;
		float totalFailing = (float) counter - successes;
		if (successes != 0) {
			System.out.println("Average moves per solution : " + movesSolve / success);
		}
		if (counter - successes != 0) {
			System.out.println("Average moves per failure : " + failingMoves / totalFailing);
		}

		System.out.println();
		System.out.println();
		// Sideways stuff
		System.out.println("Sideways Moves Hill-Climbing algorithm results ");
		int sideWaysCounter = 0;
		int sideWaysSuccesses = 0;
		int sideWaysSuccessMoves = 0;
		int movesInFailureS = 0;
		while (sideWaysCounter < 1000) {
			HillClimbingSearch<EightQueensState> solverS = new HillClimbingSearch<EightQueensState>();
			EightQueensProblem newProblem = new EightQueensProblem();
			EightQueensState resultState = solverS.solve(newProblem, 100);
			int movesTaken = solverS.getMovesTaken();
			if (newProblem.getFitness(resultState) == newProblem.getMaximumFitness()) {
				sideWaysSuccesses++;
				sideWaysSuccessMoves += movesTaken;
			} else {
				movesInFailureS += movesTaken;
			}
			sideWaysCounter++;
		}

		System.out.println("Successful times : " + sideWaysSuccesses);
		float solvePercentS = (float) ((sideWaysSuccesses * 100) / sideWaysCounter);
		System.out.println("Success Rate : " + solvePercentS + "%");
		float movesInSolved = (float) sideWaysSuccessMoves;
		float sideWaysSuccess = (float) sideWaysSuccesses;
		if (sideWaysSuccesses != 0) {
			System.out.println("Average moves per solution : " + movesInSolved / sideWaysSuccess);
		}

		float movesInFailures = (float) movesInFailureS;
		float totalFailures = (float) 1000 - sideWaysSuccesses;

		if (sideWaysCounter - sideWaysSuccesses != 0) {
			System.out.println("Average moves per failure : " + movesInFailures / totalFailures);
		}

		System.out.println();
		System.out.println();
		System.out.println("Genetic Algorithm : Generation Size : 100  Mutation Rate : 5% ");
		int fitCounter = 0;
		int totalGenerationsUsed = 0;
		while (fitCounter < 10) {
			GeneticSearch<EightQueensState> fitSolver = new GeneticSearch<EightQueensState>();
			EightQueensProblem newProblem = new EightQueensProblem();
			fitSolver.solve(newProblem, 100, 5);
			totalGenerationsUsed += fitSolver.getGenerationCount();
			fitCounter++;
		}
		System.out.println("Average generations needed to solve : " + totalGenerationsUsed / 10);
		System.out.println();
		System.out.println();
		System.out.println("Genetic Algorithm : Generation Size : 100  Mutation Rate : 20% ");
		int twentyMutations = 0;
		totalGenerationsUsed = 0;
		while (twentyMutations < 10) {
			GeneticSearch<EightQueensState> twentyMuteSolver = new GeneticSearch<EightQueensState>();
			EightQueensProblem twentyProblem = new EightQueensProblem();
			twentyMuteSolver.solve(twentyProblem, 100, 20);
			twentyMutations++;
			totalGenerationsUsed += twentyMuteSolver.getGenerationCount();
		}
		System.out.println("Average generations needed to solve : " + totalGenerationsUsed / 10);
		System.out.println();
		System.out.println("Genetic Algorithm : Generation Size : 200  Mutation Rate : 20%");
		int thirdGeneticCounter = 0;
		totalGenerationsUsed = 0;
		while (thirdGeneticCounter < 10) {
			GeneticSearch<EightQueensState> thirdGenSolver = new GeneticSearch<EightQueensState>();
			EightQueensProblem thirdGenProblem = new EightQueensProblem();
			thirdGenSolver.solve(thirdGenProblem, 200, 20);
			thirdGeneticCounter++;
			totalGenerationsUsed += thirdGenSolver.getGenerationCount();

		}

		System.out.println("Average generations needed to solve : " + totalGenerationsUsed / 10);
		System.out.println();
		System.out.println();
		System.out.println("BOGO search algorithm results : ");

		int bogoCounter = 0;
		int totalBogoMoves = 0;
		while (bogoCounter < 10) {
			BogoSearch<EightQueensState> bogoSolver = new BogoSearch<EightQueensState>();
			EightQueensProblem bogoProblem = new EightQueensProblem();
			bogoSolver.solve(bogoProblem);
			totalBogoMoves += bogoSolver.getStatesNeeded();
			bogoCounter++;
		}

		System.out.println("Average states needed to solve : " + totalBogoMoves / 10);

	}

}
