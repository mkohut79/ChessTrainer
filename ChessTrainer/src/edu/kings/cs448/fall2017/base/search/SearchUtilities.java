package edu.kings.cs448.fall2017.base.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import edu.kings.cs448.fall2017.kohutmichael.search.AStarGraphSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.BreadthFirstGraphSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.DepthFirstGraphSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.DepthFirstTreeSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.GreedyBestFirstGraphSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.IterativeDeepeningGraphSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.IterativeDeepeningTreeSearch;
import edu.kings.cs448.fall2017.kohutmichael.search.UniformCostGraphSearch;

/**
 * A class containing static methods useful for writing search problem programs.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class SearchUtilities {

	/**
	 * Allows the user to choose one of the available search algorithms by name.
	 * 
	 * @param <S>
	 *            The type of states in the problem.
	 * @param <A>
	 *            The type of actions in the problem.
	 * @param choices
	 *            A map of algorithm names to algorithm objects.
	 * @return The chosen algorithm.
	 */
	public static <S, A> SearchSolver<S, A> chooseAlgorithm(HashMap<String, SearchSolver<S, A>> choices) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		SearchSolver<S, A> algorithm = null;

		while (algorithm == null) {
			System.out.println("Possible search algorithms:");
			for (String name : choices.keySet()) {
				System.out.println("\t" + name);
			}
			System.out.print("Your choice: ");
			String choice = input.next();
			algorithm = choices.get(choice);
			if (algorithm == null) {
				System.out.println("That is not a valid algorithm name.");
			}
		}

		return algorithm;
	}

	/**
	 * Gets a map of algorithms and their names for solving a specific problem
	 * type.
	 * 
	 * @param <S>
	 *            The type of states in the problem.
	 * @param <A>
	 *            The type of actions in the problem.
	 * @return A map of the available algorithms and their names.
	 */
	public static <S, A> HashMap<String, SearchSolver<S, A>> getAlgorithmMap() {
		HashMap<String, SearchSolver<S, A>> map = new HashMap<>();
		map.put("Manual", new ManualSearchSolver<S, A>());
		map.put("BreadthFirstTreeSearch", new BreadthFirstTreeSearch<S, A>());
		map.put("BreadthFirstGraphSearch", new BreadthFirstGraphSearch<S, A>());
		map.put("DepthFirstTreeSearch", new DepthFirstTreeSearch<S, A>());
		map.put("DepthFirstGraphSearch", new DepthFirstGraphSearch<S, A>());
		map.put("IterativeDeepeningTreeSearch", new IterativeDeepeningTreeSearch<S, A>());
		map.put("IterativeDeepeningGraphSearch", new IterativeDeepeningGraphSearch<S, A>());
		map.put("GreedyBestFirstGraphSearch", new GreedyBestFirstGraphSearch<S, A>());
		map.put("AStarGraphSearch", new AStarGraphSearch<S, A>());
		map.put("UniformCostGraphSearch", new UniformCostGraphSearch<S, A>());
		return map;
	}

	/**
	 * Prints a solution to a search problem.
	 * 
	 * @param <A>
	 *            The type of actions in the problem.
	 * @param solution
	 *            A sequence of actions that solves the problem, or null if
	 *            there is no solution.
	 */
	public static <A> void printSolution(ArrayList<A> solution) {
		if (solution == null) {
			System.out.println("The problem has no solution.");
		} else if (solution.isEmpty()) {
			System.out.println("The initial state already satisfies the goal!");
		} else {
			System.out.println("Solution:");
			for (int i = 0; i < solution.size(); i++) {
				System.out.println("\t" + i + "\t" + solution.get(i));
			}
		}
	}
}
