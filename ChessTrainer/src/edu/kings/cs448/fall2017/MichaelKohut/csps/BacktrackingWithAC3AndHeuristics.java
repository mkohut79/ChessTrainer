package edu.kings.cs448.fall2017.MichaelKohut.csps;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.kings.cs448.fall2017.base.csps.ConstraintSatisfactionProblem;

/**
 * Class that represent the backtracking with Ac3 and heuristics class which
 * contains the override metho to process the unassigned value of a variable
 * first on the minimum domain and then breaks the ties with the variable that
 * has the highest amount of conflicts with the other unsassigned values.
 * 
 * @author Michael Kohut
 *
 */
public class BacktrackingWithAC3AndHeuristics extends BacktrackingWithAC3 {

	/**
	 * Chooses a next variable to work on, arbitrarily.
	 * 
	 * @param problem
	 *            The CSP that is being solved.
	 * @param assignment
	 *            The partial solution so far.
	 * @return A variable from the CSP that does not appear in the assignment.
	 */
	@Override
	public String chooseUnassignedVariable(ConstraintSatisfactionProblem problem, Map<String, Object> assignment) {
		Set<String> domainKeyset = problem.getDomains().keySet();
		Set<String> assignmentKeys = assignment.keySet();
		Iterator<String> iter = domainKeyset.iterator();

		String result = "";
		Set<String> lowestDomain = new HashSet<String>();
		int lowestDomainSize = Integer.MAX_VALUE;
		// find the set of variable that have the lowest domain
		while (iter.hasNext()) {
			String current = iter.next();
			if (!assignmentKeys.contains(current)) {
				Set<Object> currentDomain = problem.getDomains().get(current);
				if (currentDomain.size() < lowestDomainSize) {
					result = current;
					lowestDomain.clear();
					lowestDomain.add(current);
					lowestDomainSize = currentDomain.size();
				} else if (currentDomain.size() == lowestDomainSize) {
					result = current;
					lowestDomain.add(current);
					lowestDomainSize = currentDomain.size();
				}

			}
		}

		// check if there were ties and handling with the highest degree (most
		// variables that it has conflicts with)
		if (lowestDomain.size() != 1) {
			int highestConflicts = Integer.MIN_VALUE;
			for (String currentVar : lowestDomain) {
				int currentConflicts = 0;
				for (String other : lowestDomain) {

					if (!currentVar.equals(other)) {
						if (problem.areVariablesRelated(currentVar, other)) {
							currentConflicts++;
						}
					}
				}

				if (currentConflicts > highestConflicts) {
					highestConflicts = currentConflicts;
					result = currentVar;
				}
			}

		}
		return result;
	}

}
