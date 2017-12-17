package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Map;
import java.util.Set;

/**
 * A negation of another Sentence.
 * 
 * @author CS448
 * @version 2017
 */
public class Negation extends Sentence {

	/** The Sentence of which this is a Negation. */
	private Sentence sentence;

	/**
	 * Constructs a new Negation.
	 * 
	 * @param input The Sentence that the new Negation negates.
	 */
	public Negation(Sentence input) {
		sentence = input;
	}
	
	/**
	 * Gets the Sentence this is a Negation of.
	 * 
	 * @return The Sentence this is a Negation of.
	 */
	public Sentence getSentence() {
		return sentence;
	}
	

	@Override
	public boolean evaluate(Map<Proposition, Boolean> model)
			throws IllegalArgumentException {
		boolean result = false;

		result = sentence.evaluate(model);
		result = !result;

		return result;
	}

	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {
		TernaryLogic result = TernaryLogic.UNKNOWN;

		result = sentence.evaluatePartial(model);

		if (result == TernaryLogic.TRUE) {
			result = TernaryLogic.FALSE;
		} else if (result == TernaryLogic.FALSE) {
			result = TernaryLogic.TRUE;
		}

		return result;
	}

	@Override
	public Set<Proposition> getVariables() {
		return sentence.getVariables();
	}

	@Override
	public boolean isLiteral() {
		boolean result = false;
		if (sentence instanceof Proposition) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean isClause() {
		return false;
	}

	@Override
	public boolean isInCNF() {
		return false;
	}

	@Override
	public boolean isInNNF() {
		boolean result = false;
		if (sentence instanceof Proposition) {
			result = true;
		}
		return result;
	}

	@Override
	public String toString() {
		return "! ( " + sentence + " )";
	}

	@Override
	public boolean equals(Object other) {
		boolean result;
		if(other == null) {
			result = false;
		}
		else if(this == other) {
			result = true;
		}
		else if(!(other instanceof Negation)) {
			result = false;
		}
		else {
			result = (sentence.equals(((Negation)other).sentence));
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return sentence.hashCode() * 3;
	}

	@Override
	protected Sentence afterBiconditionalElimination() {
		return new Negation(sentence.afterBiconditionalElimination());
	}
	
	@Override
	protected Sentence afterImplicationElimination() {
		return new Negation(sentence.afterImplicationElimination());
	}
	
	@Override
	protected Sentence afterConversionToNNF() {
		Sentence result;
		if(sentence instanceof Negation) {
			// remove double negations
			result = ((Negation)sentence).sentence.afterConversionToNNF();
		}
		else if(sentence instanceof Conjunction) {
			// use DeMorgan's Law to push negations down.
			Conjunction before = (Conjunction)sentence;
			Disjunction after = new Disjunction();
			for(Sentence s : before.getOperands()) {
				Negation n = new Negation(s);
				after.addSentence(n.afterConversionToNNF());
			}
			result = after;
		}
		else if(sentence instanceof Disjunction) {
			// use DeMorgan's Law to push negations down.
			Disjunction before = (Disjunction)sentence;
			Conjunction after = new Conjunction();
			for(Sentence s : before.getOperands()) {
				Negation n = new Negation(s);
				after.addSentence(n.afterConversionToNNF());
			}
			result = after;
		}
		else if(sentence instanceof Proposition){
			// no change required
			result = this;
		}
		else {
			throw new IllegalStateException("You may not convert to NNF while Implications or Biconditionals remain.");
		}
		return result;
	}
	
	@Override
	protected Sentence afterFlattening() {
		return new Negation(sentence.afterFlattening());
	}

	@Override
	protected Sentence afterDistributingOrOverAnd() {
		return new Negation(sentence.afterDistributingOrOverAnd());
	}
	
	@Override
	protected Sentence afterMakingClauses() {
		return new Negation(sentence.afterMakingClauses());
	}
}
