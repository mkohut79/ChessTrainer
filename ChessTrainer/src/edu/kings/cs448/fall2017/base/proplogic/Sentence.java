package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * A generic sentence in propositional logic.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public abstract class Sentence {

	/**
	 * Evaluates this Sentence given a model.
	 * 
	 * @param model The map of information that we have.
	 * @return Whether or not this Sentence is true in the given model.
	 * @throws IllegalArgumentException If the model is not complete.
	 */
	public abstract boolean evaluate(Map<Proposition, Boolean> model) throws IllegalArgumentException;
	
	/**
	 * Evaluates this Sentence given a partial model (so that it might not be provably true or false).
	 * 
	 * @param model A partial model.
	 * @return Either TernaryLogic.TRUE, TernaryLogic.FALSE, or TernaryLogic.UNKNOWN.
	 */
	public abstract TernaryLogic evaluatePartial(Map<Proposition, Boolean> model);
	
	/**
	 * Gets the set of all propositional variables that appear somewhere in this sentence.
	 * 
	 * @return A set of propositional symbols that appear somewhere in this sentence.
	 */
	public abstract Set<Proposition> getVariables();
	
	/**
	 * Gets whether or not this Sentence is a literal (Proposition or Negation of Proposition).
	 * 
	 * @return Whether or not this Sentence is a literal.
	 */
	public abstract boolean isLiteral();
	
	/**
	 * Gets whether or not this Sentence is a clause (Disjunction of literals).
	 * 
	 * @return Whether or not this Sentence is a clause.
	 */
	public abstract boolean isClause();
	
	/**
	 * Gets whether or not this Sentence is in Conjunctive Normal Form (a conjunction of clauses).
	 * 
	 * @return Whether or not this Sentence is in CNF.
	 */
	public abstract boolean isInCNF();
	
	/**
	 * Gets whether or not this Sentence is in Negation Normal Form (all Negations are of Propositions and there are no Implications or Biconditionals).
	 * 
	 * @return Whether or not this Sentence is in NNF.
	 */
	public abstract boolean isInNNF();

	/**
	 * Gets whether or not this Sentence is logically equivalent to another.
	 * 
	 * @param other The other Sentence.
	 * @return Whether or not they are logically equivalent.
	 */
	public final boolean equivalent(Sentence other) {
		return truthTableEquivalenceCheck(this, other);
	}
	
	/**
	 * Determines equivalence of two Sentences by checking whether or not each row of the truth table matches.
	 * 
	 * @param first The first Sentence.
	 * @param second The second Sentence.
	 * @return Whether or not the two Sentences are equivalent.
	 */
	private static boolean truthTableEquivalenceCheck(Sentence first, Sentence second) {
		Set<Proposition> allVars = new HashSet<Proposition>();
		allVars.addAll(first.getVariables());
		allVars.addAll(second.getVariables());
		return recursiveTruthTableCheck(first, second, allVars, new HashMap<Proposition, Boolean>());
	}
	
	/**
	 * Builds a truth table recursively, stopping if two Sentences do not match on any row.
	 * 
	 * @param first The first Sentence.
	 * @param second The second Sentence.
	 * @param allVars The variables used in the Sentences that are not yet in the model.
	 * @param model A (partial) model of the variables used in the Sentences.
	 * @return Whether or not the two Sentences are equal in the truth table rows that are extensions of the model.
	 */
	private static boolean recursiveTruthTableCheck(Sentence first, Sentence second, Set<Proposition> allVars, Map<Proposition, Boolean> model) {
		boolean result;
		if(allVars.isEmpty()) {
			result = first.evaluate(model) == second.evaluate(model);
		}
		else {
			Proposition aVar = allVars.iterator().next();
			allVars.remove(aVar);
			model.put(aVar, true);
			boolean trueResult = recursiveTruthTableCheck(first, second, allVars, model);
			if(trueResult) {
				model.put(aVar, false);
				result = recursiveTruthTableCheck(first, second, allVars, model);
			}
			else {
				result = false;
			}
			model.remove(aVar);
			allVars.add(aVar);
		}
		return result;
	}
	
	/**
	 * Converts a String to a Sentence.
	 * The String must follow these rules, including whitespace:
	 * <ul>
	 *   <li>A Proposition must be a single word containing only letters and digits.</li>
	 *   <li>A Negation must be in this format: "! ( <sentence> )".</li>
	 *   <li>A Conjunction must be in this format: "( <sentence> && <sentence> && ... )".</li>
	 *   <li>A Disjunction must be in this format: "( <sentence> || <sentence> || ... )".</li>
	 *   <li>An Implication must be in this format: "( <sentence> -> <sentence> )".</li>
	 *   <li>A Biconditional must be in this format: "( <sentence> <-> <sentence> )".</li>
	 * </ul>
	 * 
	 * @param str A properly formatted String containing a textual description of a propositional Sentence.
	 * @return The equivalent Sentence object.
	 */
	public static Sentence parse(String str) {
		String [] tokens = str.split("\\s");
		Deque<Object> stack = new LinkedList<>();
		for(String token : tokens) {
			if(token.equals("(") || token.equals("!") || token.equals("&&") || token.equals("||") || token.equals("-->") || token.equals("<->")) {
				stack.push(token);
			}
			else if(token.equals(")")) {
				Object topObject = stack.pop();
				if(!(topObject instanceof Sentence)) {
					throw new IllegalStateException("Parse error: expected sentence before ')'.");
				}
				else {
					Sentence topSentence = (Sentence)topObject;
					Object secondObject = stack.pop();
					if(secondObject.equals("(")) {
						if(stack.isEmpty()) {
							stack.push(topObject);
						}
						else {
							Object thirdObject = stack.pop();
							if(thirdObject.equals("!")) {
								stack.push(new Negation(topSentence));
							}
							else {
								stack.push(thirdObject);
								stack.push(topSentence);
//								throw new IllegalStateException("Parse error: expected '!' before (<sentence>).");
							}
						}
					}
					else if(secondObject.equals("&&")){
						Conjunction conjunction = new Conjunction();
						conjunction.addSentence(topSentence);
						Object nextObject = stack.pop();
						while(!(nextObject.equals("("))) {
							if(nextObject instanceof Sentence) {
								conjunction.addSentence((Sentence)nextObject);
								nextObject = stack.pop();
								if(!nextObject.equals("&&") && !nextObject.equals("(")) {
									throw new IllegalArgumentException("Parse error: expected '&&' or '(' 2x before '&&'.");
								}
								if(nextObject.equals("&&")) {
									nextObject = stack.pop();
								}
							}
							else {
								throw new IllegalStateException("Parse error: expected sentence before '&&'");
							}
						}
						stack.push(conjunction);
					}
					else if(secondObject.equals("||")){
						Disjunction disjunction = new Disjunction();
						disjunction.addSentence(topSentence);
						Object nextObject = stack.pop();
						while(!(nextObject.equals("("))) {
							if(nextObject instanceof Sentence) {
								disjunction.addSentence((Sentence)nextObject);
								nextObject = stack.pop();
								if(!nextObject.equals("||") && !nextObject.equals("(")) {
									throw new IllegalStateException("Parse error: expected '||' or '(' 2x before '||'.");
								}
								if(nextObject.equals("||")) {
									nextObject = stack.pop();
								}
							}
							else {
								throw new IllegalStateException("Parse error: expected sentence before '||'");
							}
						}
						stack.push(disjunction);
					}
					else if(secondObject.equals("-->")) {
						Object thirdObject = stack.pop();
						if(thirdObject instanceof Sentence) {
							Object fourthObject = stack.pop();
							if(fourthObject.equals("(")) {
								stack.push(new Implication((Sentence)thirdObject, topSentence));
							}
							else {
								throw new IllegalStateException("Parse error: expected '(' 2x before '->'.");
							}
						}
						else {
							throw new IllegalStateException("Parse error: expected sentence before '->'.");
						}
					}
					else if(secondObject.equals("<->")) {
						Object thirdObject = stack.pop();
						if(thirdObject instanceof Sentence) {
							Object fourthObject = stack.pop();
							if(fourthObject.equals("(")) {
								stack.push(new Biconditional((Sentence)thirdObject, topSentence));
							}
							else {
								throw new IllegalStateException("Parse error: expected '(' 2x before '<->'.");
							}
						}
						else {
							throw new IllegalStateException("Parse error: expected sentence before '<->'.");
						}						
					}
					else {
						throw new IllegalStateException("Parse error: expected operator before sentence.");
					}
				}
			}
			else {
				stack.push(new Proposition(token));
			}
			
		}
		Object top = stack.pop();
		if(!(top instanceof Sentence)) {
			throw new IllegalStateException("Parse error: expected only a sentence on stack.");
		}
		if(!stack.isEmpty()) {
			throw new IllegalStateException("Parse error: expected empty stack.");
		}
		return (Sentence)top;
	}

	/**
	 * Returns a version of this Sentence in which all Biconditionals have been replaced with Implications.
	 * 
	 * @return A version of this Sentence in which all Biconditions have been replaced with Implications.
	 */
	protected abstract Sentence afterBiconditionalElimination();
	
	/**
	 * Returns a version of this Sentence in which all Implications have been replaced with Disjunctions.
	 * You should not call this on a Sentence that contains Biconditionals; do afterBiconditionalElimination first.
	 * 
	 * @return A version of this Sentence in which all Implications have been replaced with Disjunctions.
	 */
	protected abstract Sentence afterImplicationElimination();
	
	/**
	 * Returns a version of this Sentence that is in Negation Normal Form.
	 * You should not call this on a Sentence that contains Implications; do afterImplicationElimination first.
	 * 
	 * @return A version of this Sentence that is in Negation Normal Form.
	 */
	protected abstract Sentence afterConversionToNNF();
	
	/**
	 * Returns a version of this Sentence that is in flattened NNF (no Disjunctions of Disjunctions or Conjunctions of Conjunctions).
	 * You should not call this on a Sentence that is not in NNF; do afterConversionToNNF first.
	 * 
	 * @return A version of this Sentence that has been flattened.
	 */
	protected abstract Sentence afterFlattening();
	
	/**
	 * Returns a version of this Sentence in which (one level of) Disjunction has been distributed over a deeper level of Conjunction.
	 * You should only call this on a Sentence that is in NNF.
	 * 
	 * @return A version of this Sentence in which (one level of) Disjunction has been distributed over a deeper level of Conjunction.
	 */
	protected abstract Sentence afterDistributingOrOverAnd();
	
	/**
	 * Returns a version of this Sentence in which any bare literals in a Conjunction have been moved into a clause.
	 * 
	 * @return A version of this Sentence in which any bare literals in a Conjunction have been moved into a clause.
	 */
	protected abstract Sentence afterMakingClauses();
	
	/**
	 * Returns a version of this Sentence that is in Conjunctive Normal Form.
	 * 
	 * @return A version of this Sentence that is in Conjunctive Normal Form.
	 */
	public Sentence convertToCNF() {
		Sentence sentence = this.afterBiconditionalElimination();
		sentence = sentence.afterImplicationElimination();
		sentence = sentence.afterConversionToNNF();
		sentence = sentence.afterFlattening();
		sentence = sentence.afterMakingClauses();
		
		while(!sentence.isLiteral() && !sentence.isClause() && !sentence.isInCNF()) {
			sentence = sentence.afterDistributingOrOverAnd();
			sentence = sentence.afterFlattening();
			sentence = sentence.afterMakingClauses();
		}
		Sentence result;
		if(sentence.isInCNF()) {
			result = sentence;
		}
		else if(sentence.isClause()) {
			Conjunction c = new Conjunction();
			c.addSentence(sentence);
			result = c;
		}
		else {
			Disjunction d = new Disjunction();
			d.addSentence(sentence);
			Conjunction c = new Conjunction();
			c.addSentence(d);
			result = c;
		}
		
		return result;
	}

}
