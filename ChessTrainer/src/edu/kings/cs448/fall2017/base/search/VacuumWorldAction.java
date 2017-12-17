package edu.kings.cs448.fall2017.base.search;

/**
 * An action in the Vacuum World.
 * 
 * @author CS448
 * @version 2017
 */
public enum VacuumWorldAction {

	/** Move to the left. */
	LEFT("left"),
	/** Move to the right. */
	RIGHT("right"),
	/** Clean the current room. */
	SUCK("suck");
	
	/** The name of this VacuumWorldAction. */
	private String name;
	
	/**
	 * Constructs a new VacuumWorldAction.
	 * 
	 * @param theName The name of the action.
	 */
	private VacuumWorldAction(String theName) {
		name = theName;
	}
	
	@Override
	public String toString() {
		String result = "Name: " + name;
		return result;
	}
	
	
}
