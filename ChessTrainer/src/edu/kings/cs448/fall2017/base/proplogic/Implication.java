package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Map;

/**
 * A logical implication of two Sentences.
 * 
 * @author CS448
 * @version 2017
 */
public class Implication extends BinaryOperator {

	/**
	 * Constructs a new Implication.
	 * 
	 * @param antecedent The antecedent.
	 * @param consequent The consequent.
	 */
	public Implication(Sentence antecedent, Sentence consequent) {
		super(antecedent, consequent);
	}

	@Override
	public boolean evaluate(Map<Proposition, Boolean> model)
			throws IllegalArgumentException {
		boolean result = true;
		Sentence first = getFirst();
		Sentence second = getSecond();
		if (first.evaluate(model) == true && second.evaluate(model) == false) {
			result = false;
		}
		return result;
	}

	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {
		Sentence first = getFirst();
		Sentence second = getSecond();
		TernaryLogic result = TernaryLogic.TRUE;
		if (first.evaluatePartial(model) == TernaryLogic.TRUE
				&& second.evaluatePartial(model) == TernaryLogic.FALSE)
		{
			result = TernaryLogic.FALSE;
		} else if (second.evaluatePartial(model) == TernaryLogic.UNKNOWN
				&& first.evaluatePartial(model) != TernaryLogic.FALSE)
		{
			result = TernaryLogic.UNKNOWN;
		} else if (second.evaluatePartial(model) == TernaryLogic.FALSE
				&& first.evaluatePartial(model) == TernaryLogic.UNKNOWN)
		{
			result = TernaryLogic.UNKNOWN;
		}
		return result;
	}

	@Override
	public String getSymbol() {
		return "-->";
	}

	@Override
	public boolean equals(Object other) {
		boolean result;
		if(other == null) {
			result = false;
		}
		else if(other == this) {
			result = true;
		}
		else if(!(other instanceof Implication)) {
			result = false;
		}
		else {
			result = getFirst().equals(((Implication)other).getFirst()) && getSecond().equals(((Implication)other).getSecond());
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return getFirst().hashCode() * 73 + getSecond().hashCode() * 91;
	}

	@Override
	protected Sentence afterBiconditionalElimination() {
		return new Implication(getFirst().afterBiconditionalElimination(), getSecond().afterBiconditionalElimination());
	}
	
	@Override
	protected Sentence afterImplicationElimination() {
		Disjunction after = new Disjunction();
		after.addSentence(new Negation(getFirst().afterImplicationElimination()));
		after.addSentence(getSecond().afterImplicationElimination());
		return after;
	}
	
	@Override
	protected Sentence afterConversionToNNF() {
		throw new IllegalStateException("You may not convert to NNF if any Implications still exist.");
	}
	
	@Override
	protected Sentence afterFlattening() {
		throw new IllegalStateException("You may not flatten if any Implications still exist.");
	}
	
	@Override
	protected Sentence afterDistributingOrOverAnd() {
		throw new IllegalStateException("You may not distribute if any Implications still exist.");
	}

	@Override
	protected Sentence afterMakingClauses() {
		throw new IllegalStateException("You may not make clauses if any Implications still exist.");
	}

}
