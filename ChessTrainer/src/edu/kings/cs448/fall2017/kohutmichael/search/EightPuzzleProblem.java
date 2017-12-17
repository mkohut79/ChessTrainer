package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.HashSet;
import java.util.Set;

import edu.kings.cs448.fall2017.base.search.SearchProblem;

/**
 * Class used to represent the eight puzzle problem that is going to be solved.
 * 
 * @author michael kohut
 *
 */
public class EightPuzzleProblem implements SearchProblem<EightPuzzleState, EightPuzzleAction> {

	/** The initial state of the problem. */
	private EightPuzzleState initialState;

	/**
	 * Constructs the initial state to be solved.
	 * 
	 * @param spaceCoord
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
	public EightPuzzleProblem(String spaceCoord, int first, int second, int third, int fourth, int fifth, int sixth,
			int seventh, int eighth, int ninth) {
		initialState = new EightPuzzleState(spaceCoord, first, second, third, fourth, fifth, sixth, seventh, eighth,
				ninth);
	}

	@Override
	public EightPuzzleState getInitialState() {
		return initialState;
	}

	@Override
	public Set<EightPuzzleAction> getActions(EightPuzzleState theState) {
		Set<EightPuzzleAction> actions = new HashSet<EightPuzzleAction>();
		String coordinate = theState.getSpaceCoordinate();
		if (coordinate.equals("00")) {
			actions.add(EightPuzzleAction.RIGHT);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("01")) {
			actions.add(EightPuzzleAction.LEFT);
			actions.add(EightPuzzleAction.RIGHT);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("02")) {
			actions.add(EightPuzzleAction.LEFT);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("10")) {
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.RIGHT);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("11")) {
			actions.add(EightPuzzleAction.LEFT);
			actions.add(EightPuzzleAction.RIGHT);
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("12")) {
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.LEFT);
			actions.add(EightPuzzleAction.DOWN);
		}
		if (coordinate.equals("20")) {
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.RIGHT);
		}
		if (coordinate.equals("21")) {
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.LEFT);
			actions.add(EightPuzzleAction.RIGHT);
		}
		if (coordinate.equals("22")) {
			actions.add(EightPuzzleAction.UP);
			actions.add(EightPuzzleAction.LEFT);
		}

		return actions;
	}

	@Override
	public EightPuzzleState getResultingState(EightPuzzleAction theAction, EightPuzzleState theState) {
		String spaceCoord = theState.getSpaceCoordinate();
		EightPuzzleState result = null;
		if (spaceCoord.equals("00")) {
			if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "10", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "01", theState);
			}
		} else if (spaceCoord.equals("01")) {
			if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "11", theState);
			} else if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "00", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "12", theState);
			}
		} else if (spaceCoord.equals("02")) {
			if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "01", theState);
			} else if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "12", theState);
			}
		} else if (spaceCoord.equals("10")) {
			if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "00", theState);
			} else if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "20", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "11", theState);
			}
		} else if (spaceCoord.equals("11")) {
			if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "10", theState);
			} else if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "01", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "12", theState);
			} else if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "21", theState);
			}
		} else if (spaceCoord.equals("12")) {
			if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "11", theState);
			} else if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "02", theState);
			} else if (theAction.equals(EightPuzzleAction.DOWN)) {
				result = switchSpace(spaceCoord, "22", theState);
			}
		} else if (spaceCoord.equals("20")) {
			if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "10", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "21", theState);
			}
		} else if (spaceCoord.equals("21")) {
			if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "20", theState);
			} else if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "11", theState);
			} else if (theAction.equals(EightPuzzleAction.RIGHT)) {
				result = switchSpace(spaceCoord, "22", theState);
			}
		} else if (spaceCoord.equals("22")) {
			if (theAction.equals(EightPuzzleAction.LEFT)) {
				result = switchSpace(spaceCoord, "21", theState);
			} else if (theAction.equals(EightPuzzleAction.UP)) {
				result = switchSpace(spaceCoord, "12", theState);
			}
		}

		return result;
	}

	@Override
	public boolean meetsGoal(EightPuzzleState theState) {
		boolean result = false;
		int[][] theMatrix = theState.getTheMatrix();
		String spaceCood = theState.getSpaceCoordinate();
		if ((theMatrix[0][0] == 0) && (theMatrix[0][1] == 1) && (theMatrix[0][2] == 2) && (theMatrix[1][0] == 3)
				&& (theMatrix[1][1] == 4) && (theMatrix[1][2] == 5) && (theMatrix[2][0] == 6) && (theMatrix[2][1] == 7)
				&& (theMatrix[2][2] == 8) && (spaceCood.equals("00")))
		{
			result = true;
		}

		return result;
	}

	@Override
	public int getStepCost(EightPuzzleAction theAction, EightPuzzleState theState) {
		return 1;
	}

	/**
	 * h1 implementation for the heuristic.
	 */
	@Override
	public int heuristicForState(EightPuzzleState theState) {
		int misplacedTiles = 0;
		int counter = 0;
		int[][] currentMatrix = theState.getTheMatrix();
		for (int i = 0; i < currentMatrix.length; i++) {
			for (int j = 0; j < currentMatrix.length; j++) {
				if (currentMatrix[i][j] != counter) {
					misplacedTiles++;
				}
				counter++;
			}
		}
		return misplacedTiles;
	}

	/**
	 * Helper method to create and return a new state that switch the space with
	 * the location of a number entered.
	 * 
	 * @param oldSpace
	 *            The location of the space(0) in the problem.
	 * @param newSpace
	 *            The location that the space is meant to move towards.
	 * @param theState
	 *            The current state.
	 * @return The new state that contains the updated matrix.
	 */
	public static EightPuzzleState switchSpace(String oldSpace, String newSpace, EightPuzzleState theState) {
		EightPuzzleState result = new EightPuzzleState();
		String rowString = oldSpace.substring(0, 1);
		String colString = oldSpace.substring(1, oldSpace.length());
		int oldRow = Integer.parseInt(rowString);
		int oldCol = Integer.parseInt(colString);

		String newRowString = newSpace.substring(0, 1);
		String newColString = newSpace.substring(1, newSpace.length());
		int newRow = Integer.parseInt(newRowString);
		int newCol = Integer.parseInt(newColString);

		int[][] matrix = theState.getTheMatrix();
		int newVal = matrix[newRow][newCol];
		int oldVal = matrix[oldRow][oldCol];

		int[][] newMatrix = new int[3][3];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if ((i == newRow) && (j == newCol)) {
					newMatrix[i][j] = oldVal;
				} else if (i == oldRow && j == oldCol) {
					newMatrix[i][j] = newVal;
				} else {
					newMatrix[i][j] = matrix[i][j];
				}
			}
		}

		result.setTheMatrix(newMatrix);
		result.setSpaceCoordinate(newSpace);

		return result;

	}

}
