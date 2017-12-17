package edu.kings.cs448.fall2017.base.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.kings.cs448.fall2017.MichaelKohut.csps.BacktrackingWithAC3;
import edu.kings.cs448.fall2017.MichaelKohut.csps.BacktrackingWithAC3AndHeuristics;

import java.util.Scanner;

/**
 * A program to solve N-queens problems as constraint-satisfaction problems.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class NQueensMain {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		HashMap<Integer, CSPSolver> solverMap = new HashMap<>();
		CSPSolver search = new SimpleBacktrackingSearch();
		solverMap.put(0, new BacktrackingWithAC3());
		solverMap.put(1, new BacktrackingWithAC3AndHeuristics());
		solverMap.put(2, new SimpleBacktrackingSearch());
		System.out.print(
				"Please indicate the CSPSolver you would like to use to solve a sudoku puzzle by entering " + "\n");
		System.out.println("the number that is next to the desired solver.");

		System.out.println(0 + "\t " + "BacktrackingWithAC3");
		System.out.println(1 + "\t " + "BacktrackingWithAC3AndHeuristics");
		System.out.println(2 + "\t " + "SimpleBacktrackingSearch");
		int solverChoice = input.nextInt();

		if (solverChoice == 0 || solverChoice == 1 || solverChoice == 2) {
			search = solverMap.get(solverChoice);
		} else {
			System.out.println("Please pick only from the choices listed");
		}

		System.out.print("How many queens: ");
		int n = input.nextInt();

		ConstraintSatisfactionProblem csp = createProblem(n);

		long timeBefore = System.currentTimeMillis();
		Map<String, Object> assignment = search.solve(csp);
		System.out.println("That required " + (System.currentTimeMillis() - timeBefore) + " milliseconds.");

		if (assignment != null) {
			for (String variable : assignment.keySet()) {
				System.out.println(variable + " = " + assignment.get(variable));
			}
			for (int i = 0; i < n + 2; i++) {
				System.out.print("*");
			}
			System.out.println();
			for (int row = 0; row < n; row++) {
				System.out.print("*");
				for (int col = 0; col < n; col++) {
					if ((Integer) assignment.get("C" + col) == row) {
						System.out.print("Q");
					} else {
						System.out.print(" ");
					}
				}
				System.out.println("*");
			}
			for (int i = 0; i < n + 2; i++) {
				System.out.print("*");
			}
			System.out.println();

		} else {
			System.out.println("The problem has no solution.");
		}

	}

	/**
	 * Creates an instance of an N-queens problem.
	 * 
	 * @param n
	 *            How many queens.
	 * @return A CSP representing an N-queens problem.
	 */
	public static ConstraintSatisfactionProblem createProblem(int n) {
		Set<Object> domain = new HashSet<Object>();
		for (int i = 0; i < n; i++) {
			// Possible values are row numbers.
			domain.add(i);
		}
		Map<String, Set<Object>> variables = new HashMap<>();
		for (int i = 0; i < n; i++) {
			// One variable for each column.
			variables.put("C" + i, new HashSet<Object>(domain));
		}
		Set<Constraint> constraints = new HashSet<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				// They can't be in the same row.
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + j));
				// They can't be in a diagonal line either.
				constraints.add(new BinaryDiagonalConstraint("C" + i, "C" + j, j - i));
			}
		}
		return new ConstraintSatisfactionProblem(variables, constraints);
	}

}
