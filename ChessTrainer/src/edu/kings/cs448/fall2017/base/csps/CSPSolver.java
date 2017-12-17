package edu.kings.cs448.fall2017.base.csps;

import java.util.Map;

/**
 * Any algorithm that attempts to solve Constraint Satisfaction Problems.
 * 
 * @author CS448
 * @version 2017
 */
public interface CSPSolver {

	/**
	 * Solves a Constraint Satisfaction Problem.
	 * 
	 * @param problem A CSP.
	 * @return A solution for that CSP, or null if none exists.
	 */
	public Map<String, Object> solve(ConstraintSatisfactionProblem problem);
}
