package edu.kings.cs448.fall2017.kohutmichael.search;

/**
 * A state in the eight puzzle problem.
 * 
 * @author michael kohut
 *
 */
public class EightPuzzleState {

	/**
	 * The 2d representation of the puzzle.
	 */
	private int[][] theMatrix;

	/**
	 * The coordinate of where the 0 which is acting as the empty space is.
	 */
	private String spaceCoordinate;

	/**
	 * Constructor which will initializes the numbers on the matrix. Will read
	 * in the numbers starting at the top left corner, fill in the row and then
	 * do the next until the last row.
	 * 
	 * @param spaceCoordinate
	 *            The location that the space is in.
	 * @param first
	 *            The number in the 00 location.
	 * @param second
	 *            The number in the 01 location.
	 * @param third
	 *            The number in the 02 location.
	 * @param fourth
	 *            The number in the 10 location.
	 * @param fifth
	 *            The number in the 11 location.
	 * @param sixth
	 *            The number in the 12 location.
	 * @param seventh
	 *            The number in the 20 location.
	 * @param eighth
	 *            The number in the 21 location.
	 * @param ninth
	 *            The number in the 22 location.
	 */

	public EightPuzzleState(String spaceCoordinate, int first, int second, int third, int fourth, int fifth, int sixth,
			int seventh, int eighth, int ninth) {
		this.spaceCoordinate = spaceCoordinate;
		theMatrix = new int[3][3];
		theMatrix[0][0] = first;
		theMatrix[0][1] = second;
		theMatrix[0][2] = third;
		theMatrix[1][0] = fourth;
		theMatrix[1][1] = fifth;
		theMatrix[1][2] = sixth;
		theMatrix[2][0] = seventh;
		theMatrix[2][1] = eighth;
		theMatrix[2][2] = ninth;
	}

	/**
	 * Default constructor that is used in the helper method to switch the 0
	 * with a specific location.
	 */
	public EightPuzzleState() {
		theMatrix = new int[3][3];
	}

	/**
	 * Getter for the matrix representing the puzzle.
	 * 
	 * @return The matrix of the puzzle.
	 */
	public int[][] getTheMatrix() {
		return theMatrix;
	}

	/**
	 * Getter for the coordinate of the 0 number.
	 * 
	 * @return The name of the location that the 0 is in.
	 */
	public String getSpaceCoordinate() {
		return spaceCoordinate;
	}

	/**
	 * Setter for the 0.
	 * 
	 * @param spaceCoordinate
	 *            Setter for where the 0 is in the matrix.
	 */
	public void setSpaceCoordinate(String spaceCoordinate) {
		this.spaceCoordinate = spaceCoordinate;
	}

	/**
	 * Setter for the matrix.
	 * 
	 * @param theMatrix
	 *            The matrix that is representing the state of the puzzle.
	 */
	public void setTheMatrix(int[][] theMatrix) {
		this.theMatrix = theMatrix;
	}

	/**
	 * Method to print out all of the numbers in the matrix.
	 */
	public void print() {
		for (int i = 0; i < theMatrix.length; i++) {
			for (int j = 0; j < theMatrix.length; j++) {
				System.out.print(theMatrix[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		String result = "Current state : ";
		print();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean identical;
		if (this == o) {
			identical = true;
		} else {
			if (o == null) {
				identical = false;
			} else if (o instanceof EightPuzzleState) {
				EightPuzzleState other = (EightPuzzleState) o;
				int[][] otherMatrix = other.getTheMatrix();
				int[][] currentMatrix = this.getTheMatrix();
				String currentSpaceCood = this.getSpaceCoordinate();
				String otherSpaceCood = other.getSpaceCoordinate();
				if (otherMatrix[0][0] == currentMatrix[0][0] && otherMatrix[0][1] == currentMatrix[0][1]
						&& otherMatrix[0][2] == currentMatrix[0][2] && otherMatrix[1][0] == currentMatrix[1][0]
						&& otherMatrix[1][1] == currentMatrix[1][1] && otherMatrix[1][2] == currentMatrix[1][2]
						&& otherMatrix[2][0] == currentMatrix[2][0] && otherMatrix[2][1] == currentMatrix[2][1]
						&& otherMatrix[2][2] == currentMatrix[2][2] && currentSpaceCood.equals(otherSpaceCood))
				{
					identical = true;
				} else {
					identical = false;
				}

			} else {
				identical = false;
			}
		}

		return identical;

	}

	@Override
	public int hashCode() {
		int result = 0;
		result = ((theMatrix[0][0] * 100000000) + (theMatrix[0][1] * 10000000) + (theMatrix[0][2] * 1000000)
				+ (theMatrix[1][0] * 100000) + (theMatrix[1][1] * 10000) + (theMatrix[1][2] * 1000)
				+ (theMatrix[2][0] * 100) + (theMatrix[2][1] * 10) + (theMatrix[2][2]));

		return result;
	}

}
