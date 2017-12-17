package edu.kings.cs448.fall2017.base.search;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A program that solves problems in the Vacuum World.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class VacuumWorldMain {

	/**
	 * The main method.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		final double NANOSECONDS_PER_SECOND = 1000000000.0;
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Time to solve a Vacuum World problem!");
		System.out.print("What room do you start in? (A/B): ");
		String room = input.next();
		System.out.print("Is room A clean? (y/n): ");
		String aClean = input.next();
		System.out.print("Is room B clean? (y/n): ");
		String bClean = input.next();
		if((!room.equals("A") && !room.equals("B") || (!aClean.equals("y") && !aClean.equals("n")) || (!bClean.equals("y") && !bClean.equals("n")))) {
			System.out.println("Invalid input for initial state.");
		}
		else {
			VacuumWorldProblem problem = new VacuumWorldProblem(room.charAt(0), aClean.equals("y"), bClean.equals("y"));
			SearchSolver<VacuumWorldState, VacuumWorldAction> solver = SearchUtilities.chooseAlgorithm(SearchUtilities.getAlgorithmMap());
			long startTime = System.nanoTime();
			ArrayList<VacuumWorldAction> solution = solver.solve(problem);
			long endTime = System.nanoTime();
			SearchUtilities.printSolution(solution);
			System.out.println("Solving this problem required expanding " + solver.getNumExpandedNodes() + " nodes and ran for " + (endTime - startTime) / NANOSECONDS_PER_SECOND + " seconds.");
		}
	}
}
