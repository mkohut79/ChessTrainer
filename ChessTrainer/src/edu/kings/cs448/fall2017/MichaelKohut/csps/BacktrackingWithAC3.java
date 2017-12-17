package edu.kings.cs448.fall2017.MichaelKohut.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import edu.kings.cs448.fall2017.base.csps.ConstraintSatisfactionProblem;
import edu.kings.cs448.fall2017.base.csps.SimpleBacktrackingSearch;

/**
 * Class that is similar to the BackTrackingWithAC3 algorithm.
 * 
 * @author Michael Kohut
 *
 */
public class BacktrackingWithAC3 extends SimpleBacktrackingSearch {

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
	@Override
	public boolean extraCheck(ConstraintSatisfactionProblem problem, Map<String, Object> assignment,
			String mostRecentVar) {

		boolean result = true;

		Map<String, Set<Object>> domains = problem.getDomains();
		Set<Object> newDomain = new HashSet<Object>();
		newDomain.add(assignment.get(mostRecentVar));
		domains.put(mostRecentVar, newDomain);

		Queue<VariablePair> pairs = getPairs(problem, domains, mostRecentVar);

		while (!pairs.isEmpty() && result) {
			VariablePair currentPair = pairs.poll();
			String xOne = currentPair.getVariableOne();
			String xTwo = currentPair.getVariableTwo();
			Set<Object> revisions = revise(problem, xOne, xTwo);
			if (revisions.size() != 0) {
				Set<Object> currentDomain = domains.get(xOne);
				for (Object revised : revisions) {
					currentDomain.remove(revised);
				}
				domains.put(xOne, currentDomain);
				if (currentDomain.size() == 0) {
					result = false;
				} else {

					addNeighbors(pairs, problem, domains, xOne);

				}

			}

		}

		return result;
	}

	/**
	 * Helper method to find all the neighbors of a given variable and add that
	 * new pair to the queue of pairs.
	 * 
	 * @param pairs
	 *            Listing of all the pairs that have constraints with each
	 *            other.
	 * @param csp
	 *            The constraint satisfaction problem to be solved used in this
	 *            case to determine if two variable have conflict with one
	 *            another.
	 * @param domains The domain of the problem used to get the key set to iterate
	 *         over.
	 * @param xOne
	 *            The variable who neighbors we are looking for.
	 */
	public void addNeighbors(Queue<VariablePair> pairs, ConstraintSatisfactionProblem csp,
			Map<String, Set<Object>> domains, String xOne) {

		for (String key : domains.keySet()) {
			if (!key.equals(xOne)) {
				VariablePair newOne = new VariablePair(key, xOne);
				if (!pairs.contains(newOne)) {
					if (csp.areVariablesRelated(key, xOne)) {
						pairs.add(newOne);
					}
				}
			}

		}
	}

	/**
	 * Helper method to reduce the domain of the first variable after the second
	 * variables domain has just been update.
	 * 
	 * @param problem
	 *            The constraint satisfaction problem containing all of the
	 *            domain mappings.
	 * @param firstElement
	 *            theVery first variable who domains we are looking at.
	 * @param secondElement
	 *            The second element whose domain we are looking at.
	 * @return The set of domain values that are to be removed from the first
	 *         variable.
	 */
	public Set<Object> revise(ConstraintSatisfactionProblem problem, String firstElement, String secondElement) {

		Set<Object> revised = new HashSet<Object>();
		Map<String, Set<Object>> domains = problem.getDomains();
		for (Object firstVarDomain : domains.get(firstElement)) {
			boolean ok = false;
			Map<String, Object> assignment = new HashMap<String, Object>();
			assignment.put(firstElement, firstVarDomain);
			Iterator<Object> iter = domains.get(secondElement).iterator();
			while (iter.hasNext() && !ok) {
				// for (Object secondVarDomain : domains.get(secondElement)) {
				Object secondVarDomain = iter.next();
				assignment.put(secondElement, secondVarDomain);
				if (problem.isConsistent(assignment)) {
					ok = true;
				}
				assignment.remove(secondElement);

			}
			if (!ok) {
				revised.add(firstVarDomain);
				ok = true;
			}

		}

		return revised;
	}

	/**
	 * Helper method to calculate all the pairs of variables that have conflicts
	 * with each other.
	 * 
	 * @param csp
	 *            A constraint satisfaction problem that has the mappings of the
	 *            domains for all of the variable as well as their constraints.
	 * @param domains
	 *            The domain of the string to each value it can have.
	 * @param mostRecentVar
	 *            The name of the variable who domain was just updated.
	 * @return A queue of pairs which is essentially just a class containing the
	 *         variables that have conflicts with each other.
	 */
	public Queue<VariablePair> getPairs(ConstraintSatisfactionProblem csp, Map<String, Set<Object>> domains,
			String mostRecentVar) {
		Queue<VariablePair> pairs = new LinkedList<VariablePair>();

		for (String key : domains.keySet()) {
			if (!key.equals(mostRecentVar)) {
				if (csp.areVariablesRelated(key, mostRecentVar)) {
					VariablePair toAdd = new VariablePair(key, mostRecentVar);
					if (!pairs.contains(toAdd)) {
						pairs.add(toAdd);
					}
				}

			}
		}

		return pairs;
	}
}
