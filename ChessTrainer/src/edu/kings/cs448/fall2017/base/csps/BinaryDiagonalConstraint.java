package edu.kings.cs448.fall2017.base.csps;

import java.util.Map;

/**
 * A constraint saying that two Integer variables may not differ by a specified amount.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class BinaryDiagonalConstraint extends BinaryConstraint {

	/** The amount by which the two variables may not differ. */
	private int difference;

	/**
	 * Constructs a new BinaryDiagonalConstraint.
	 * 
	 * @param variableA The first variable.
	 * @param variableB The second variable.
	 * @param difference What, when added to the first variable, may not result in the second variable.
	 */
	public BinaryDiagonalConstraint(String variableA, String variableB, int difference) {
		super(variableA, variableB);
		this.difference = difference;
	}

	@Override
	public boolean canBeSatisfied(Map<String, Object> assignments) {
		Object value1 = assignments.get(getFirstVariable());
		Object value2 = assignments.get(getSecondVariable());
		boolean result;
		if(value1 == null || value2 == null) {
			result = true;
		} else if(!(value1 instanceof Integer) || !(value2 instanceof Integer)){
			throw new IllegalArgumentException("The variables in a BinaryDiagonalConstraint must have domains that are Integers.");
		} else {
			if(Math.abs((Integer)value1 - (Integer)value2) != difference) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

}
