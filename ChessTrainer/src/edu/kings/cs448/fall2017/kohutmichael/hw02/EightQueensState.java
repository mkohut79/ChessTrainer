package edu.kings.cs448.fall2017.kohutmichael.hw02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Class to represent the state of the EightQueens board.
 * 
 * @author Michael Kohut
 *
 */
public class EightQueensState {

	/**
	 * 1D array representing the board where each index represents one of the
	 * eight columns and the number at that index represents where the queen is
	 * in that column.
	 */
	private int[] rowOnColumn;

	/**
	 * Constructor to initialize where the queens are on the board.
	 * 
	 * @param first
	 *            The row in column one.
	 * @param second
	 *            The row in column two.
	 * @param third
	 *            The row in column three.
	 * @param fourth
	 *            The row in column four.
	 * @param fifth
	 *            The row in column five.
	 * @param sixth
	 *            The row in column six.
	 * @param seventh
	 *            The row in column seven.
	 * @param eighth
	 *            The row in column eight.
	 */
	public EightQueensState(int first, int second, int third, int fourth, int fifth, int sixth, int seventh,
			int eighth) {
		rowOnColumn = new int[8];
		rowOnColumn[0] = first;
		rowOnColumn[1] = second;
		rowOnColumn[2] = third;
		rowOnColumn[3] = fourth;
		rowOnColumn[4] = fifth;
		rowOnColumn[5] = sixth;
		rowOnColumn[6] = seventh;
		rowOnColumn[7] = eighth;
	}

	/**
	 * Constructor involving a 1D array to represent the number that the queen
	 * is placed on in the column.
	 * 
	 * @param rows
	 *            The representation of the queens on the rows.
	 */
	public EightQueensState(int[] rows) {
		rowOnColumn = new int[8];
		for (int i = 0; i < rows.length; i++) {
			rowOnColumn[i] = rows[i];
		}
	}

	/**
	 * Constructor to initialize a state of the board.
	 */
	public EightQueensState() {
		rowOnColumn = new int[8];

	}

	/**
	 * Setter for the representation of what row the queen is on.
	 * 
	 * @param rowOnColumn
	 *            The array of number of rows.
	 */
	public void setRowOnColumn(int[] rowOnColumn) {
		this.rowOnColumn = rowOnColumn;
	}

	/**
	 * Create a random EightQueensState.
	 * 
	 * @return The randomized state.
	 */
	public static EightQueensState getRandomState() {
		int[] rowOnColumn = new int[8];
		Random choose = new Random();
		for (int i = 0; i < rowOnColumn.length; i++) {
			int pick = choose.nextInt(8);
			rowOnColumn[i] = pick;
		}
		EightQueensState result = new EightQueensState(rowOnColumn);
		return result;
	}

	/**
	 * Helper method to check is a current state is a solution by determine if
	 * the row representation contains two of the same integers since that would
	 * mean there is to queens in the same row.
	 * 
	 * @return Whether or not the state is a solution.
	 */
	public boolean isASolution() {
		boolean keepChecking = true;
		int[] rows = getRowOnColCopy();
		for (int i = 0; i < rows.length - 1 && keepChecking; i++) {
			for (int j = i + 1; j < rows.length && keepChecking; j++) {
				if (rows[i] == rows[j] || rows[i] == rows[j] + j || rows[i] == rows[j] - j) {
					keepChecking = false;
				}

			}

		}
		return keepChecking;

	}

	/**
	 * Returns a copy of the representation of what rows the queens are on in
	 * the columns.
	 * 
	 * @return The array that tells what rows the queens are on.
	 */
	public int[] getRowOnColCopy() {
		int[] copy = new int[8];
		for (int i = 0; i < rowOnColumn.length; i++) {
			copy[i] = rowOnColumn[i];
		}
		return copy;
	}

	/**
	 * Method to calculate the fitness level of a state.
	 * 
	 * @return nonAttacking The number of pairs of nonatacking queens.
	 */
	public int calculateFitness() {
		int[] rows = getRowOnColCopy();
		int nonAttacking = 0;
		for (int i = 0; i < rows.length - 1; i++) {
			int counter = 1;
			for (int j = i + 1; j < rows.length; j++) {
				if (rows[i] != rows[j] && rows[i] != rows[j] + counter && rows[i] != rows[j] - counter) {
					nonAttacking++;
				}
				counter++;

			}
		}
		return nonAttacking;
	}

	/**
	 * Helper method to find all the neighbors from a specific column.
	 * 
	 * @param set
	 *            The set to be added to.
	 * @param currentRow
	 *            The current row its in.
	 * @param currentCol
	 *            The current column its in.
	 */
	public void findAllNeighbors(HashSet<EightQueensState> set, int currentRow, int currentCol) {
		int[] rowCopy = getRowOnColCopy();
		for (int i = 0; i < rowCopy.length; i++) {
			if (i != currentRow) {
				int[] changed = new int[8];
				changed[currentCol] = i;
				for (int j = 0; j < rowCopy.length; j++) {
					if (j != currentCol) {
						changed[j] = rowCopy[j];
					}
				}
				EightQueensState neighbor = new EightQueensState(changed);
				set.add(neighbor);
			}
		}
	}

	/**
	 * Method that will return all of the neighbors of a state.
	 * 
	 * @return The set that contains all of the reachable state from the
	 *         starting state.
	 */
	public HashSet<EightQueensState> getNeighbors() {
		int[] rowOnCol = getRowOnColCopy();
		HashSet<EightQueensState> neighbors = new HashSet<EightQueensState>();
		for (int i = 0; i < rowOnCol.length; i++) {
			findAllNeighbors(neighbors, rowOnCol[i], i);
		}
		return neighbors;
	}

	/**
	 * Method to move a queen on a random col to a randomly different row.
	 * 
	 * @param state
	 *            The state that will produce a mutation.
	 */
	public void mutate(EightQueensState state) {
		Random change = new Random();
		int colChoice = change.nextInt(8);
		int newRow = change.nextInt(8);
		int[] rowCopy = getRowOnColCopy();
		int currentRow = rowCopy[colChoice];
		while (currentRow == newRow) {
			newRow = change.nextInt(8);
		}
		rowCopy[colChoice] = newRow;
		state.setRowOnColumn(rowCopy);

	}

	/**
	 * Method to calculate what the resulting EightQueensState will be when
	 * combining two 'Parent' states.
	 * 
	 * @param firstParent
	 *            The state whose columns will be used in the first section of
	 *            the child.
	 * @param secondParent
	 *            The state whose columns will be used later section of the
	 *            child.
	 * @return offspring The offspring of the two parents.
	 */
	public static EightQueensState crossover(EightQueensState firstParent, EightQueensState secondParent) {
		Random separator = new Random();
		int selection = separator.nextInt(8);
		if (selection == 0) {
			selection++;
		}
		if (selection == 7) {
			selection--;
		}
		int[] firstDNA = firstParent.getRowOnColCopy();
		int[] secondDNA = secondParent.getRowOnColCopy();
		int[] kidDNA = new int[8];
		for (int i = 0; i < selection; i++) {
			kidDNA[i] = firstDNA[i];
		}
		for (int j = selection; j < secondDNA.length; j++) {
			kidDNA[j] = secondDNA[j];
		}

		EightQueensState offSpring = new EightQueensState(kidDNA);
		return offSpring;
	}

	@Override
	public boolean equals(Object o) {
		boolean result;
		if (o == null) {
			result = false;
		} else if (this == o) {
			result = true;
		} else if (!(o instanceof EightQueensState)) {
			result = false;
		} else {
			EightQueensState other = (EightQueensState) o;
			result = Arrays.equals(rowOnColumn, other.rowOnColumn);
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = ((rowOnColumn[0] * 10000000) + (rowOnColumn[1] * 1000000) + (rowOnColumn[2] * 100000)
				+ (rowOnColumn[3] * 10000) + (rowOnColumn[4] * 1000) + (rowOnColumn[5] * 100) + (rowOnColumn[6] * 10)
				+ rowOnColumn[7]);

		return result;
	}

}
