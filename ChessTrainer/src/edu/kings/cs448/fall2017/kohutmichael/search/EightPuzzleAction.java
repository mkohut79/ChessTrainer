package edu.kings.cs448.fall2017.kohutmichael.search;

/**
 * An action in the eight puzzle problem.
 * 
 * @author michael kohut
 *
 */
public enum EightPuzzleAction {

	/**
	 * Switch the empty tile with the number to the left of it.
	 */
	LEFT("left"),
	/**
	 * Switch the empty tile with the number to the right of it.
	 */
	RIGHT("right"),
	/**
	 * Switch the empty space with the tile above it.
	 */
	UP("up"),
	/**
	 * Switch the empty space with the tile below it.
	 */
	DOWN("down");

	/**
	 * The name of the action.
	 */
	private String name;

	/**
	 * Constructor for the eight puzzle action initializing the name of the
	 * action.
	 * 
	 * @param theAction
	 *            The name that is describing the action.
	 */
	private EightPuzzleAction(String theAction) {
		name = theAction;
	}

	@Override
	public String toString() {
		String result = "Name: " + name;
		return result;
	}

}
