package edu.kings.cs448.fall2017.base.csps;

import java.util.Map;

/**
 * A rule that must be satisfied about the values of one or more variables.
 * 
 * @author CS448
 * @version 2017
 */
public interface Constraint {

	/**
	 * Gets whether or not it is still possible to satisfy this Constraint given an assignment.
	 * 
	 * @param assignment The assignment.
	 * @return True if this Constraint is definitely satisfied, or some part of its scope does not appear in the assignment.
	 */
	public boolean canBeSatisfied(Map<String, Object> assignment);
	
	/**
	 * Gets whether or not this Constraint discusses a particular variable.
	 * 
	 * @param variable The variable.
	 * @return Whether or not this Constraint uses that variable.
	 */
	public boolean doesScopeContain(String variable);
}
