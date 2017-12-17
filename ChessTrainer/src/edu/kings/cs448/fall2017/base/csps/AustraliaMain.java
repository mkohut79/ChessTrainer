/**
 * 
 */
package edu.kings.cs448.fall2017.base.csps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Solves map coloring in Australia as a CSP.
 * 
 * @author CS448
 * @version 2017
 */
public class AustraliaMain {

	/**
	 * The main method.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		String wa = "WA";
		String nt = "NT";
		String sa = "SA";
		String ql = "QL";
		String ns = "NS";
		String vt = "VT";
		String tm = "TM";
		
		String red = "red";
		String green = "green";
		String blue = "blue";
		String[] variableArray = {wa, nt, sa, ql, ns, vt, tm};
		Set<String> variables = new HashSet<String>();
		for(String current : variableArray){
			variables.add(current);
		}
		
		Set<String> domain = new HashSet<>();
		domain.add(red);
		domain.add(green);
		domain.add(blue);
		Map<String, Set<Object>> domains = new HashMap<>();
		for(String current : variables){
			domains.put(current, new HashSet<Object>(domain));
		}
		Set<Constraint> constraints = new HashSet<>();
		
		constraints.add(new BinaryDifferentConstraint(wa, nt));
		constraints.add(new BinaryDifferentConstraint(wa, sa));
		constraints.add(new BinaryDifferentConstraint(nt, sa));
		constraints.add(new BinaryDifferentConstraint(nt, ql));
		constraints.add(new BinaryDifferentConstraint(sa, ql));
		constraints.add(new BinaryDifferentConstraint(sa, ns));
		constraints.add(new BinaryDifferentConstraint(sa, vt));
		constraints.add(new BinaryDifferentConstraint(ns, ql));
		constraints.add(new BinaryDifferentConstraint(ns, vt));
		
		ConstraintSatisfactionProblem problem = new ConstraintSatisfactionProblem(domains, constraints);

		SimpleBacktrackingSearch solver = new SimpleBacktrackingSearch();

		long timeBefore = System.currentTimeMillis();
		Map<String, Object> assignment = solver.solve(problem);
		System.out.println("That required " + (System.currentTimeMillis() - timeBefore) + " milliseconds.");

		for(String variable : assignment.keySet()) {
			System.out.println(variable + " = " + assignment.get(variable));
		}
		
	}

}
