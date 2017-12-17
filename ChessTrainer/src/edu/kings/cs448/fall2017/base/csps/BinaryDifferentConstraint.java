package edu.kings.cs448.fall2017.base.csps;

import java.util.Map;

/**
 * A constraint that two variables may not have the same value.
 * 
 * @author CS448
 * @version 2017
 */
public class BinaryDifferentConstraint extends BinaryConstraint {

	/**
	 * Constructs a new BinaryDifferentConstraint.
	 * 
	 * @param first The first variable.
	 * @param second The second variable.
	 */
	public BinaryDifferentConstraint(String first, String second) {
		super(first, second);
	}

	@Override
	public boolean canBeSatisfied(Map<String, Object> assignment) {
		boolean result = false;
		Object one = assignment.get(getFirstVariable());
		Object two = assignment.get(getSecondVariable());
		if(one == null || two == null || !one.equals(two)) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		String result = getFirstVariable() + "!=" + getSecondVariable();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result;
		if(o == null) {
			result = false;
		}
		else if(o == this) {
			result = true;
		}
		else if(!(o instanceof BinaryDifferentConstraint)) {
			result = false;
		}
		else {
			BinaryDifferentConstraint other = (BinaryDifferentConstraint)o;
			result = (getFirstVariable().equals(other.getFirstVariable()) || getFirstVariable().equals(other.getSecondVariable())) &&
					(getSecondVariable().equals(other.getFirstVariable()) || getSecondVariable().equals(other.getSecondVariable()));
			}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		final int DIFFERENT_UNIQUE_CODE = 1;
		int result;
		if(getFirstVariable().compareTo(getSecondVariable()) < 0) {
			result = getFirstVariable().hashCode() * 374 + getSecondVariable().hashCode() * 73 + DIFFERENT_UNIQUE_CODE;
		}
		else {
			result = getSecondVariable().hashCode() * 374 + getFirstVariable().hashCode() * 73 + DIFFERENT_UNIQUE_CODE;
		}
		return result;
	}
}
