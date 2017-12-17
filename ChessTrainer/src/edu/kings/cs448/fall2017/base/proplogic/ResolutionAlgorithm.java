package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A way to check entailment using the generalized resolution rule.
 * 
 * @author Chad Hogg
 *
 */
public class ResolutionAlgorithm implements EntailmentChecker {

	@Override
	public boolean entails(Sentence kb, Sentence alpha) {
		Conjunction kbCNF = (Conjunction)kb.convertToCNF();
		kbCNF.addSentence(new Negation(alpha));
		kbCNF = (Conjunction)kbCNF.convertToCNF();
		
		boolean foundContradiction = false;
		Set<Sentence> clausesToCheck = kbCNF.getOperands();
		while(!clausesToCheck.isEmpty() && !foundContradiction) {
			Set<Sentence> newClauses = new HashSet<Sentence>();
			
			Set<Sentence> clauses = kbCNF.getOperands();
			
			Iterator<Sentence> iter1 = clauses.iterator();
			while(iter1.hasNext() && !foundContradiction) {
				Disjunction clause1 = (Disjunction)iter1.next();

				Iterator<Sentence> iter2 = clausesToCheck.iterator();
				while(iter2.hasNext() && !foundContradiction) {
					Disjunction clause2 = (Disjunction)iter2.next();

					if(!clause1.equals(clause2)) {
						Set<Proposition> complements = getComplementaryLiterals(clause1, clause2);
						if(complements.size() == 1) {
							Disjunction newClause = getResolvent(clause1, clause2, complements);
							if(newClause.getOperands().isEmpty()) {
								foundContradiction = true;
							}
							else if(!somethingSubsumes(kbCNF.getOperands(), newClauses, newClause)){
								newClauses.add(newClause);
							}
						}
					}
				}
			}

			for(Sentence element : newClauses) {
				kbCNF.addSentence(element);
			}
			clausesToCheck = newClauses;
		}
		return foundContradiction;
	}
	
	/**
	 * Gets the set of complementary literals that exist in a pair of clauses.
	 * 
	 * @param clause1 The first clause.
	 * @param clause2 The second clause.
	 * @return The set of complementary literals that exist in those clauses.
	 */
	private Set<Proposition> getComplementaryLiterals(Disjunction clause1, Disjunction clause2) {
		Set<Proposition> result = new HashSet<Proposition>();
		
		Iterator<Sentence> iter1 = clause1.getOperands().iterator();
		while(iter1.hasNext()) {
			Sentence literal1 = iter1.next();
			boolean foundOne = false;
			Iterator<Sentence> iter2 = clause2.getOperands().iterator();
			while(iter2.hasNext() && !foundOne) {
				Sentence literal2 = iter2.next();
				if(literal1 instanceof Negation && literal2 instanceof Proposition && ((Negation)literal1).getSentence().equals(literal2)) {
					foundOne = true;
					result.add((Proposition)literal2);
				}
				else if(literal1 instanceof Proposition && literal2 instanceof Negation && ((Negation)literal2).getSentence().equals(literal1)) {
					foundOne = true;
					result.add((Proposition)literal1);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the resolvent of two clauses that have one set of complementary literals.
	 * 
	 * @param clause1 The first clause.
	 * @param clause2 The second clause.
	 * @param complements A set containing the one literal that is complementary between the two.
	 * @return The resolvent of the two clauses.
	 */
	private Disjunction getResolvent(Disjunction clause1, Disjunction clause2, Set<Proposition> complements) {
		Disjunction result = new Disjunction();
		
		Iterator<Sentence> iter = clause1.getOperands().iterator();
		while(iter.hasNext()) {
			Sentence literal = iter.next();
			if(literal instanceof Proposition && !complements.contains((Proposition)literal)) {
				result.addSentence(literal);
			}
			else if(literal instanceof Negation && !complements.contains((Proposition)((Negation)literal).getSentence())) {
				result.addSentence(literal);
			}
		}
		iter = clause2.getOperands().iterator();
		while(iter.hasNext()) {
			Sentence literal = iter.next();
			if(literal instanceof Proposition && !complements.contains((Proposition)literal)) {
				result.addSentence(literal);
			}
			else if(literal instanceof Negation && !complements.contains((Proposition)((Negation)literal).getSentence())) {
				result.addSentence(literal);
			}
		}
		return result;
	}

	/**
	 * Gets whether or not one clause subsumes another.
	 * A clause subsumes another if every literal in the first is also in the second.
	 * 
	 * @param clause1 The first clause (the possible subsumer).
	 * @param clause2 The second clause (the possible subsumee).
	 * @return Whether or not clause1 subsumes clause2.
	 */
	private boolean subsumes(Disjunction clause1, Disjunction clause2) {
		boolean result = true;
		Iterator<Sentence> iter1 = clause1.getOperands().iterator();
		while(iter1.hasNext() && result) {
			Sentence s1 = iter1.next();
			if(!clause2.getOperands().contains(s1)) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Tests whether or not one of the clauses already in one of two sets subsumes a new clause.
	 * 
	 * @param firstSet A set of extant clauses.
	 * @param secondSet Another set of extant clauses.
	 * @param sentence A new clause.
	 * @return Whether or not the new clause is subsumed by an existing one.
	 */
	private boolean somethingSubsumes(Set<Sentence> firstSet, Set<Sentence> secondSet, Disjunction sentence) {
		boolean result = false;
		Iterator<Sentence> iter = firstSet.iterator();
		while(iter.hasNext() && !result) {
			Sentence s = iter.next();
			if(subsumes((Disjunction)s, (Disjunction)sentence)) {
				result = true;
			}
		}
		iter = secondSet.iterator();
		while(iter.hasNext() && !result) {
			Sentence s = iter.next();
			if(subsumes((Disjunction)s, (Disjunction)sentence)) {
				result = true;
			}
		}
		return result;
	}
}
