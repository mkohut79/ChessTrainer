package edu.kings.cs448.fall2017.base.csps;

/**
 * A constraint between two variables.
 * 
 * @author CS448
 * @version 2017
 */
public abstract class BinaryConstraint implements Constraint {
	
	/** The first variable in the Constraint. */
	private String firstVariable;
	/** The second variable in the Constraint. */
	private String secondVariable;
	
	/**
	 * Constructs a new BinaryConstraint.
	 * 
	 * @param first The first variable.
	 * @param second The second variable.
	 */
	public BinaryConstraint(String first, String second) {
		firstVariable = first;
		secondVariable = second;
	}

	/**
	 * Gets the first variable.
	 * 
	 * @return The first variable.
	 */
	public String getFirstVariable() {
		return firstVariable;
	}

	/**
	 * Gets the second variable.
	 * 
	 * @return The second variable.
	 */
	public String getSecondVariable() {
		return secondVariable;
	}

	@Override
	public boolean doesScopeContain(String variable) {
		boolean result = false;
		if(firstVariable.equals(variable) || secondVariable.equals(variable)) {
			result = true;
		}
		return result;
	}

}
