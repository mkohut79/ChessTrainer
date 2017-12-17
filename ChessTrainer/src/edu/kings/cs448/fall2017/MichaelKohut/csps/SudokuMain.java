package edu.kings.cs448.fall2017.MichaelKohut.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.kings.cs448.fall2017.base.csps.BinaryDifferentConstraint;
import edu.kings.cs448.fall2017.base.csps.CSPSolver;
import edu.kings.cs448.fall2017.base.csps.Constraint;
import edu.kings.cs448.fall2017.base.csps.ConstraintSatisfactionProblem;
import edu.kings.cs448.fall2017.base.csps.SimpleBacktrackingSearch;

/**
 * The main class of the sudoku problem that construct its CSP.
 * 
 * @author Michael Kohut
 *
 */
public class SudokuMain {

	/**
	 * The main argument to be processed.
	 * 
	 * @param args
	 *            NA.
	 */
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		// String board = SudokuPuzzles.EASY_PUZZLE;
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
		System.out.println(2 + "\t " + "SimpleBacktrackingSearch (Probably don't want to!)");

		int solverChoice = input.nextInt();

		if (solverChoice == 0 || solverChoice == 1 || solverChoice == 2) {
			search = solverMap.get(solverChoice);
		} else {
			System.out.println("Please pick only from the choices listed");
		}
		System.out.println("Please indicate the sudoku puzzle that you would like to solve as a string of numbers.");

		// Sample puzzles to use to test.

		// Easy One
		// 600030000403805000028700000800004360000020000034600001000003650000508702000010009 (works)
		// Medium One
		// 050000020001002700076400800700060300003805900002040005004007590008100600090000010 (works)
		// Hard One
		// 530400000690830000018000000169080007020070060700050189000000950000048031000006078 (works)
		// Very Hard
		// 120400300300010050006000100700090000040603000003002000500080700007000005000000098 (works)

		String board = input.next();

		ConstraintSatisfactionProblem csp = createProblem(board);

		long timeBefore = System.currentTimeMillis();
		Map<String, Object> assignment = search.solve(csp);
		System.out.println("That required " + (System.currentTimeMillis() - timeBefore) + " milliseconds.");

		if (assignment != null) {
			int newLine = 8;
			int separator = 0;
			int line = 26;
			System.out.println();
			System.out.println("-----------------");
			for (int i = 0; i < board.length(); i++) {
				System.out.print(assignment.get("C" + i));
				if (separator == 2) {
					System.out.print(" | ");
					separator = 0;
				} else {
					separator++;
				}
				if (i == newLine) {
					System.out.println();
					newLine += 9;
				}
				if (i == line) {
					System.out.println("-----------------");
					line += 27;
				}
			}
		} else {
			System.out.println("The problem has no solution.");

		}

	}

	/**
	 * Method that will construct the sudoku problem loading in all of the
	 * domains and constraints.
	 * 
	 * @param board
	 *            The string that represents the numbers at each global index.
	 * @return The sudoku satisfaction constraint problem.
	 */
	private static ConstraintSatisfactionProblem createProblem(String board) {

		Set<Object> domain = new HashSet<Object>();
		for (int i = 1; i < 10; i++) {
			domain.add(i);
		}

		Map<String, Set<Object>> variables = new HashMap<>();

		for (int i = 0; i < board.length(); i++) {

			int currentNum = board.charAt(i) - '0';
			if (currentNum != 0) {
				Set<Object> newDomain = new HashSet<Object>();
				newDomain.add(currentNum);
				variables.put("C" + i, newDomain);
			} else {
				variables.put("C" + i, new HashSet<Object>(domain));
			}
		}

		Set<Constraint> constraints = new HashSet<>();
		// Global size = board.length
		// row = index / colWidth
		// col = index % colWidth
		// int newIndex = xCord * width + yCord;

		int mainColWidth = 9;
		int checkInnerGrid = 0;
		int updateGrid = 0;
		for (int i = 0; i < 81; i++) {

			int row = i / mainColWidth;
			int col = i % mainColWidth;

			// Check columns
			for (int j = col + 1; j < mainColWidth; j++) {
				int index = row * mainColWidth + j;
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + index));
			}

			// Check rows
			for (int k = row + 1; k < mainColWidth; k++) {
				int index = k * mainColWidth + col;
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + index));
			}

			// Check inner grid
			if (i == checkInnerGrid) {
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + (i + 10)));
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + (i + 11)));
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + (i + 19)));
				constraints.add(new BinaryDifferentConstraint("C" + i, "C" + (i + 20)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 1), "C" + (i + 9)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 1), "C" + (i + 11)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 1), "C" + (i + 18)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 1), "C" + (i + 20)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 2), "C" + (i + 9)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 2), "C" + (i + 10)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 2), "C" + (i + 18)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 2), "C" + (i + 19)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 9), "C" + (i + 19)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 9), "C" + (i + 20)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 10), "C" + (i + 18)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 10), "C" + (i + 20)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 11), "C" + (i + 18)));
				constraints.add(new BinaryDifferentConstraint("C" + (i + 11), "C" + (i + 19)));
				updateGrid++;
				checkInnerGrid += 3;
				if (updateGrid == 3) {
					checkInnerGrid += 18;
					updateGrid = 0;
				}

			}

		}

		return new ConstraintSatisfactionProblem(variables, constraints);
	}

}
