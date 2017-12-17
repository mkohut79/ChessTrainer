package edu.kings.cs448.fall2017.base.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Deque;


/**
 * A problem that can be expressed using variables with constraints between them.
 * 
 * @author CS448
 * @version 2017
 */
public class ConstraintSatisfactionProblem {

	/** A map of each variable to its domain. */
	private Map<String, Set<Object>> domains;
	
	/** The set of constraints. */
	private Set<Constraint> constraints;
	
	/** A stack of what the domains had been in the past. */
	private Deque<Map<String, Set<Object>>> oldDomains;
	
	/**
	 * Constructs a new ConstraintSatisfactionProblem.
	 * 
	 * @param domains The domains of all of the variables.
	 * @param constraints The set of all constraints.
	 */
	public ConstraintSatisfactionProblem(Map<String, Set<Object>> domains, Set<Constraint> constraints) {
		this.domains = domains;
		this.constraints = constraints;
		this.oldDomains = new LinkedList<>();
	}
	
	/**
	 * Gets a map of each variable to its domain.
	 * 
	 * @return A map of each variable to its domain.
	 */
	public Map<String, Set<Object>> getDomains() {
		return domains;
	}
	
	/**
	 * Saves the current domains onto the stack, so that we can restore them later if what we are trying does not work out.
	 */
	public void saveDomains() {
		Map<String, Set<Object>> clone = new HashMap<>();
		for(String var : domains.keySet()) {
			clone.put(var, new HashSet<>(domains.get(var)));
		}
		oldDomains.push(clone);
	}

	/**
	 * Restores the most recently saved copy of the domains, because what we tried did not work.
	 */
	public void restoreLastDomains() {
		domains = oldDomains.pop();
	}
	
	/**
	 * Gets the set of constraints.
	 * 
	 * @return The set of constraints.
	 */
	public Set<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * Gets whether or not an assignment is complete for this problem.
	 * 
	 * @param assignment An assignment.
	 * @return Whether or not the assignment contains every variable in this problem.
	 */
	public boolean isComplete(Map<String, Object> assignment) {
		boolean complete = true;
		Set<String> domainKey = domains.keySet();
		
		Set<String> assignmentKey = assignment.keySet();
	
		Iterator<String> iter = domainKey.iterator();
		
		while(iter.hasNext() && complete){
			String current = iter.next();
			if(!assignmentKey.contains(current)){
				complete = false;
			}
		}

		return complete;
	}
	
	/**
	 * Gets whether or not an assignment is consistent for this problem.
	 * 
	 * @param assignment An assignment.
	 * @return Whether or not no constraints are violated by the assignment.
	 */
	public boolean isConsistent(Map<String, Object> assignment) {
		boolean consistent = true;
		
		Set<String> assignmentKey = assignment.keySet();
		
		Iterator<String> iter = assignmentKey.iterator();
		
		while(iter.hasNext() && consistent){
			String current = iter.next();
			if(!domains.get(current).contains(assignment.get(current))){
				consistent = false;
			}
		}
		
		Iterator<Constraint> iterator = constraints.iterator();
		
		while(iterator.hasNext() && consistent){
			Constraint current = iterator.next();
			consistent = current.canBeSatisfied(assignment);
		}
		
		return consistent;
	}
	
	/**
	 * Gets whether or not the constraint graph contains a connection between two variables.
	 * 
	 * @param var1 The first variable.
	 * @param var2 The second variable.
	 * @return Whether or not there is a constraint that involves both variables.
	 */
	public boolean areVariablesRelated(String var1, String var2) {
		boolean result = false;
		Iterator<Constraint> iter = constraints.iterator();
		while(iter.hasNext() && !result) {
			Constraint constraint = iter.next();
			if(constraint.doesScopeContain(var1) && constraint.doesScopeContain(var2)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		result += "Variables:";
		for(String var : domains.keySet()) {
			result += "\t" + var + ": {";
			for(Object value : domains.get(var)) {
				result += value + " ";
			}
			result += "}";
		}
		result += "Constraints:";
		for(Constraint c : constraints) {
			result += "\t" + c;
		}
		return result;
	}
}
