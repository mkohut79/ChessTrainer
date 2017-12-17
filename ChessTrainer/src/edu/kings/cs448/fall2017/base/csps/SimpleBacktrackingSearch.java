package edu.kings.cs448.fall2017.base.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A basic backtracking search approach to solving CSPs.
 * 
 * @author CS448
 * @version 2017
 */
public class SimpleBacktrackingSearch implements CSPSolver {

	@Override
	public Map<String, Object> solve(ConstraintSatisfactionProblem problem) {
		Map<String, Object> assignment = new HashMap<>();
		Map<String, Object> result = solve(problem, assignment);
		return result;
	}

	/**
	 * Chooses a next variable to work on, arbitrarily.
	 * 
	 * @param problem
	 *            The CSP that is being solved.
	 * @param assignment
	 *            The partial solution so far.
	 * @return A variable from the CSP that does not appear in the assignment.
	 */
	public String chooseUnassignedVariable(ConstraintSatisfactionProblem problem, Map<String, Object> assignment) {
		String result = "";
		Set<String> domainKeyset = problem.getDomains().keySet();
		Set<String> assignmentKeys = assignment.keySet();
		Iterator<String> iter = domainKeyset.iterator();

		while (iter.hasNext() && result.equals("")) {
			String current = iter.next();

			if (!assignmentKeys.contains(current)) {
				result = current;
			}
		}
		return result;
	}

	/**
	 * Recursively solves the problem.
	 * 
	 * @param problem
	 *            A CSP to solve.
	 * @param assignment
	 *            A consistent assignment for that problem.
	 * @return A solution that is a descendant of the assignment, or null if
	 *         none exists.
	 */
	private Map<String, Object> solve(ConstraintSatisfactionProblem problem, Map<String, Object> assignment) {
		Map<String, Object> result = null;
		if (problem.isComplete(assignment)) {
			result = assignment;
		} else {
			String current = chooseUnassignedVariable(problem, assignment);
			Map<String, Set<Object>> domains = problem.getDomains();

			// We need to iterate over a copy, because otherwise there will be
			// ConcurrentModificationExceptions.
			Set<Object> currentDoman = new HashSet<>(domains.get(current));
			Iterator<Object> iter = currentDoman.iterator();
			while (result == null && iter.hasNext()) {
				Object domainValue = iter.next();

				assignment.put(current, domainValue);

				if (problem.isConsistent(assignment)) {
					// If we change the domains, we will be able to get it back.
					problem.saveDomains();
					if (extraCheck(problem, assignment, current)) {
						Map<String, Object> aPrime = solve(problem, assignment);
						if (aPrime != null) {
							result = aPrime;
						}
					}
					// Go back to the original domains.
					problem.restoreLastDomains();
				}

				if (result == null) {
					assignment.remove(current);
				}
			}

		}

		return result;
	}

	/**
	 * Does any kind of extra checking of whether or not to proceed with adding
	 * to a partial solution. This method exists for subclasses to override if
	 * they want special processing.
	 * 
	 * @param problem
	 *            The CSP being solved.
	 * @param assignment
	 *            The current assignment.
	 * @param mostRecentVar
	 *            The variable that was most recently added to the assignment.
	 * @return Whether or not it is worth making a recursive call with this
	 *         assignment.
	 */
	public boolean extraCheck(ConstraintSatisfactionProblem problem, Map<String, Object> assignment,
			String mostRecentVar) {
		return true;

	}
}
