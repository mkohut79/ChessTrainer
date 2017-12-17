package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Map;

/**
 * An if-and-only-if of two Sentences.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class Biconditional extends BinaryOperator {

	/**
	 * Constructs a new Biconditional.
	 * 
	 * @param one
	 *            The first Sentence.
	 * @param two
	 *            The second Sentence.
	 */
	public Biconditional(Sentence one, Sentence two) {
		super(one, two);
	}

	@Override
	public boolean evaluate(Map<Proposition, Boolean> model) throws IllegalArgumentException {
		return getFirst().evaluate(model) == getSecond().evaluate(model);
	}

	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {
		TernaryLogic result = TernaryLogic.UNKNOWN;
		TernaryLogic firstResult = getFirst().evaluatePartial(model);
		TernaryLogic secondResult = getSecond().evaluatePartial(model);
		if (firstResult == TernaryLogic.TRUE && secondResult == TernaryLogic.TRUE) {
			result = TernaryLogic.TRUE;
		} else if (firstResult == TernaryLogic.TRUE && secondResult == TernaryLogic.FALSE) {
			result = TernaryLogic.FALSE;
		} else if (firstResult == TernaryLogic.FALSE && secondResult == TernaryLogic.TRUE) {
			result = TernaryLogic.FALSE;
		} else if (firstResult == TernaryLogic.FALSE && secondResult == TernaryLogic.FALSE) {
			result = TernaryLogic.TRUE;
		}
		return result;
	}

	@Override
	public String getSymbol() {
		return "<->";
	}

	@Override
	public boolean equals(Object other) {
		boolean result;
		if (other == null) {
			result = false;
		} else if (other == this) {
			result = true;
		} else if (!(other instanceof Biconditional)) {
			result = false;
		} else {
			result = (getFirst().equals(((Biconditional) other).getFirst())
					&& getSecond().equals(((Biconditional) other).getSecond()))
					|| (getFirst().equals(((Biconditional) other).getSecond())
							&& getSecond().equals(((Biconditional) other).getFirst()));
		}
		return result;

	}

	@Override
	public int hashCode() {
		return getFirst().hashCode() + getSecond().hashCode();
	}

	@Override
	protected Sentence afterBiconditionalElimination() {
		Conjunction after = new Conjunction();
		after.addSentence(new Implication(getFirst().afterBiconditionalElimination(),
				getSecond().afterBiconditionalElimination()));
		after.addSentence(new Implication(getSecond().afterBiconditionalElimination(),
				getFirst().afterBiconditionalElimination()));
		return after;
	}

	@Override
	protected Sentence afterImplicationElimination() {
		throw new IllegalStateException("You may not eliminate Implications while any Biconditionals remain.");
	}

	@Override
	protected Sentence afterConversionToNNF() {
		throw new IllegalStateException("You may not convert to NNF while any Biconditionals remain.");
	}

	@Override
	protected Sentence afterFlattening() {
		throw new IllegalStateException("You may not flatten while any Biconditionals remain.");
	}

	@Override
	protected Sentence afterDistributingOrOverAnd() {
		throw new IllegalStateException("You may not distribute while any Biconditionals remain.");
	}

	@Override
	protected Sentence afterMakingClauses() {
		throw new IllegalStateException("You may not make clauses while any Biconditionals remain.");
	}

}
