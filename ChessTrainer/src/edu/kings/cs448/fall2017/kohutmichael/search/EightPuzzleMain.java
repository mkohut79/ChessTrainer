package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.ArrayList;
import java.util.Scanner;

import edu.kings.cs448.fall2017.base.search.SearchSolver;
import edu.kings.cs448.fall2017.base.search.SearchUtilities;

/**
 * Main class to test the if it can solve an eight puzzle problem.
 * 
 * @author michael kohut
 *
 */
public class EightPuzzleMain {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		final double NANOSECONDS_PER_SECOND = 1000000000.0;
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Time to solve an EightPuzzle problem!");
		System.out.print("Enter the coordinate that the space tile(0) starts off at, as a string." + "\n");
		System.out.println("Ex. 00, 01, 11");
		int spaceStart = input.nextInt();
		String space = String.valueOf(spaceStart);
		System.out.print("What is the current State you are starting in?" + "\n");
		System.out.print("Enter the numbers one at a time creating the puzzle starting " + "\n");
		System.out.print("at the upper left and filling in the rows one by one." + "\n");
		System.out.println("In this case it will be just 9 numbers.");
		int first = input.nextInt();
		int second = input.nextInt();
		int third = input.nextInt();
		int fourth = input.nextInt();
		int fifth = input.nextInt();
		int sixth = input.nextInt();
		int seventh = input.nextInt();
		int eighth = input.nextInt();
		int ninth = input.nextInt();

		if (!(space.equals("00")) && !(space.equals("01")) && !(space.equals("02")) && !(space.equals("10"))
				&& !(space.equals("11")) && !(space.equals("12")) && !(space.equals("20")) && !(space.equals("21"))
				&& !(space.equals("22")))
		{
			System.out.println("Invalid input for initial state.");
			System.out.print("Space cooridnate needs to fit in the space between 00 and 22" + "\n");
			System.out.print("The first number represent the number of the row in the 2d matrix and the second" + "\n");
			System.out.println(" is the number of the column in the matrix");

		} else {
			EightPuzzleProblem problem = new EightPuzzleProblem(space, first, second, third, fourth, fifth, sixth,
					seventh, eighth, ninth);
			SearchSolver<EightPuzzleState, EightPuzzleAction> solver = SearchUtilities
					.chooseAlgorithm(SearchUtilities.getAlgorithmMap());
			long startTime = System.nanoTime();
			ArrayList<EightPuzzleAction> solution = solver.solve(problem);
			long endTime = System.nanoTime();
			SearchUtilities.printSolution(solution);
			System.out.println("Solving this problem required expanding " + solver.getNumExpandedNodes()
					+ " nodes and ran for " + (endTime - startTime) / NANOSECONDS_PER_SECOND + " seconds.");
		}

	}

//	0	Name: right
//	1	Name: up
//	2	Name: left
//	3	Name: left
//	4	Name: down
//	5	Name: right
//	6	Name: down
//	7	Name: left
//	8	Name: up
//	9	Name: right
//	10	Name: up
//	11	Name: right
//	12	Name: down
//	13	Name: left
//	14	Name: up
//	15	Name: up
//	16	Name: left
//	17	Name: down
//	18	Name: right
//	19	Name: up
//	20	Name: right
//	21	Name: left
//	22	Name: left
//	23	Name: up
	
}
