package edu.kings.cs448.fall2017.base.proplogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

/**
 * My implementation of the DPLL algorithm (Figure 7.17 in Russell & Norvig).
 * 
 * @author Chad Hogg
 *
 */
public class DPLLAlgorithm implements EntailmentChecker {

	@Override
	public boolean entails(Sentence kb, Sentence alpha) {
		Conjunction complete = (Conjunction)kb.convertToCNF();
		complete.addSentence(new Negation(alpha));
		complete = (Conjunction)complete.convertToCNF();
		Map<Proposition, Boolean> emptyModel = new HashMap<>();
		Set<Proposition> variables = complete.getVariables();
		return !dPLL(complete, variables, emptyModel);
	}
	
	/**
	 * Recursively tests whether or not a CNF sentence is satisfiable given a partial model.
	 * 
	 * @param clauses A sentence in CNF.
	 * @param unassignedVariables A set of all variables that appear in clauses but not in model.
	 * @param model A partial model of the world.
	 * @return Whether or not the clauses can be satisfied with an extension of the model.
	 */
	private boolean dPLL(Conjunction clauses, Set<Proposition> unassignedVariables, Map<Proposition, Boolean> model) {
		boolean result;
		TernaryLogic evaluation = clauses.evaluatePartial(model);
		if(evaluation == TernaryLogic.TRUE) {
			result = true;
		}
		else if(evaluation == TernaryLogic.FALSE) {
			result = false;
		}
		else {
			Set<Sentence> pureLiterals = getPureLiterals(clauses, model);
			if(!pureLiterals.isEmpty()) {
				addToModelRemoveFromSet(pureLiterals, unassignedVariables, model);
				result = dPLL(clauses, unassignedVariables, model);
				removeFromModelAddToSet(pureLiterals, unassignedVariables, model);
			}
			else {
				Set<Sentence> unitLiterals = getUnitClauseLiterals(clauses, model);
				if(!unitLiterals.isEmpty()) {
					addToModelRemoveFromSet(unitLiterals, unassignedVariables, model);
					result = dPLL(clauses, unassignedVariables, model);
					removeFromModelAddToSet(unitLiterals, unassignedVariables, model);
				}
				else {
					Proposition p = chooseUnassignedVariable(clauses, unassignedVariables, model);
					unassignedVariables.remove(p);
					model.put(p, true);
					result = dPLL(clauses, unassignedVariables, model);
					unassignedVariables.add(p);
					model.remove(p);
					if(result == false) {
						unassignedVariables.remove(p);
						model.put(p, false);
						result = dPLL(clauses, unassignedVariables, model);
						unassignedVariables.add(p);
						model.remove(p);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Gets a set of a all pure literals from a CNF formula.
	 * A pure literal is one whose complement does not appear in a clause that remains UNKNOWN.
	 * 
	 * @param clauses A CNF sentence.
	 * @param model A partial model of that formula.
	 * @return A set of pure literals that exist in the sentence but not in the model.
	 */
	private Set<Sentence> getPureLiterals(Conjunction clauses, Map<Proposition, Boolean> model) {
		Set<Sentence> allUnassignedLiterals = new HashSet<>();
		Iterator<Sentence> iter1 = clauses.getOperands().iterator();
		while(iter1.hasNext()) {
			Disjunction d = (Disjunction)iter1.next();
			if(d.evaluatePartial(model) != TernaryLogic.TRUE) {
				Iterator<Sentence> iter2 = d.getOperands().iterator();
				while(iter2.hasNext()) {
					Sentence l = iter2.next();
					if(l instanceof Proposition && !model.containsKey(l)) {
						allUnassignedLiterals.add(l);
					}
					else if(l instanceof Negation && !model.containsKey(((Negation)l).getSentence())) {
						allUnassignedLiterals.add(l);
					}
				}
			}
		}
		
		Set<Sentence> pureLiterals = new HashSet<>();
		iter1 = allUnassignedLiterals.iterator();
		while(iter1.hasNext()) {
			Sentence l1 = iter1.next();
			boolean foundComplement = false;
			Iterator<Sentence> iter2 = allUnassignedLiterals.iterator();
			while(iter2.hasNext() && !foundComplement) {
				Sentence l2 = iter2.next();
				if(l1 instanceof Proposition && l2 instanceof Negation && l1.equals(((Negation)l2).getSentence())) {
					foundComplement = true;
				}
				else if(l1 instanceof Negation && l2 instanceof Proposition && l2.equals(((Negation)l1).getSentence())) {
					foundComplement = true;
				}
			}
			if(!foundComplement) {
				pureLiterals.add(l1);
			}
		}
		
		return pureLiterals;
	}
	
	/**
	 * Add each of the literals in a set to a model while removing them from a set of unassigned variables.
	 * 
	 * @param literals The literals to add.
	 * @param unassigneds The set of unassigned variables.
	 * @param model The model.
	 */
	private void addToModelRemoveFromSet(Set<Sentence> literals, Set<Proposition> unassigneds, Map<Proposition, Boolean> model) {
		Iterator<Sentence> iter = literals.iterator();
		while(iter.hasNext()) {
			Sentence l = iter.next();
			if(l instanceof Proposition) {
				model.put((Proposition)l, true);
				unassigneds.remove((Proposition)l);
			}
			else {
				model.put((Proposition)((Negation)l).getSentence(), false);
				unassigneds.remove((Proposition)((Negation)l).getSentence());
			}
		}
	}
	
	/**
	 * Remove each of the literals in a set from a model while adding them to a set of unassigned variables.
	 * 
	 * @param literals The literals to remove.
	 * @param unassigneds The set of unassigned variables.
	 * @param model The model.
	 */
	private void removeFromModelAddToSet(Set<Sentence> literals, Set<Proposition> unassigneds, Map<Proposition, Boolean> model) {
		Iterator<Sentence> iter = literals.iterator();
		while(iter.hasNext()) {
			Sentence l = iter.next();
			if(l instanceof Proposition) {
				model.remove((Proposition)l);
				unassigneds.add((Proposition)l);
			}
			else {
				model.remove((Proposition)((Negation)l).getSentence());
				unassigneds.add((Proposition)((Negation)l).getSentence());
			}
		}		
	}
	
	/**
	 * Gets a set of the literals from unit clauses in a CNF sentence.
	 * A unit clause is one that contains only a single unassigned literal.
	 * 
	 * @param clauses A CNF sentence.
	 * @param model A partial model of the CNF sentence.
	 * @return A set of literals that appear in unit clauses in the CNF sentence.
	 */
	private Set<Sentence> getUnitClauseLiterals(Conjunction clauses, Map<Proposition, Boolean> model) {
		Set<Sentence> units = new HashSet<Sentence>();
		
		Iterator<Sentence> iter1 = clauses.getOperands().iterator();
		while(iter1.hasNext()) {
			Disjunction d = (Disjunction)iter1.next();
			
			if(d.evaluatePartial(model) == TernaryLogic.UNKNOWN) {
				ArrayList<Sentence> unassignedLiteralsInClause = new ArrayList<>();
				Iterator<Sentence> iter2 = d.getOperands().iterator();
				while(iter2.hasNext() && unassignedLiteralsInClause.size() < 2) {
					Sentence l = iter2.next();
					if(l.evaluatePartial(model) == TernaryLogic.UNKNOWN) {
						unassignedLiteralsInClause.add(l);
					}
				}
				if(unassignedLiteralsInClause.size() == 1) {
					units.add(unassignedLiteralsInClause.get(0));
				}
			}
		}
		
		return units;
	}
	
	/**
	 * Chooses a variable that has not been assigned a value yet.
	 * This uses the maximum degree heuristic.
	 * 
	 * @param clauses A CNF sentence.
	 * @param unassignedVariables A set of unassigned variables.
	 * @param model The model.
	 * @return One of the unassigned variables.
	 */
	private Proposition chooseUnassignedVariable(Conjunction clauses, Set<Proposition> unassignedVariables, Map<Proposition, Boolean> model) {
		Proposition bestSoFar = null;
		int highestDegreeSoFar = Integer.MIN_VALUE;
		for(Proposition p : unassignedVariables) {
			int degree = 0;
			for(Sentence s : clauses.getOperands()) {
				if(s.evaluatePartial(model) == TernaryLogic.UNKNOWN) {
					Disjunction d = (Disjunction)s;
					Iterator<Sentence> iter = d.getOperands().iterator();
					boolean foundIt = false;
					while(iter.hasNext() && !foundIt) {
						Sentence l = iter.next();
						if(l instanceof Proposition && l.equals(p)) {
							foundIt = true;
						}
						else if(l instanceof Negation && ((Negation)l).getSentence().equals(p)) {
							foundIt = true;
						}
					}
					if(foundIt) {
						degree++;
					}
				}
			}
			if(degree > highestDegreeSoFar) {
				bestSoFar = p;
				highestDegreeSoFar = degree;
			}
		}
		
		return bestSoFar;
	}

}
