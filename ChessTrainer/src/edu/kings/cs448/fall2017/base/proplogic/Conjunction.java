package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * A conjunction of multiple Sentences.
 * 
 * @author CS448
 * @version 2017
 */
public class Conjunction extends MultiOperator {

	@Override
	public boolean evaluate(Map<Proposition, Boolean> model)
			throws IllegalArgumentException {

		boolean result = true;

		Iterator<Sentence> iter = getOperands().iterator();

		while (result && iter.hasNext()) {
			Sentence current = iter.next();

			if (!current.evaluate(model)) {
				result = false;
			}

		}

		return result;
	}

	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {

		TernaryLogic result = TernaryLogic.TRUE;

		Iterator<Sentence> iter = getOperands().iterator();

		while (result != TernaryLogic.FALSE && iter.hasNext()) {
			Sentence current = iter.next();

			if (current.evaluatePartial(model) == TernaryLogic.FALSE) {

				result = TernaryLogic.FALSE;

			} else if (current.evaluatePartial(model) == TernaryLogic.UNKNOWN) {

				result = TernaryLogic.UNKNOWN;

			}

		}

		return result;
	}

	@Override
	public boolean isClause() {
		return false;
	}

	@Override
	public boolean isInCNF() {

		boolean result = true;

		Iterator<Sentence> iter = getOperands().iterator();

		while (result && iter.hasNext()) {
			Sentence current = iter.next();

			if (!current.isClause()) {
				result = false;
			}

		}

		return result;
	}

	@Override
	public String getSymbol() {
		return "&&";
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
		else if(!(other instanceof Conjunction)) {
			result = false;
		}
		else {
			result = getOperands().equals(((Conjunction)other).getOperands());
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for(Sentence s : getOperands()) {
			result += s.hashCode();
		}
		return result * 39;
	}

	@Override
	protected Sentence afterBiconditionalElimination() {
		Conjunction after = new Conjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterBiconditionalElimination());
		}
		return after;
	}
	
	@Override
	protected Sentence afterImplicationElimination() {
		Conjunction after = new Conjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterImplicationElimination());
		}
		return after;
	}
	
	@Override
	protected Sentence afterConversionToNNF() {
		Conjunction after = new Conjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterConversionToNNF());
		}
		return after;
	}
	
	@Override
	protected Sentence afterFlattening() {
		Conjunction after = new Conjunction();
		
		HashSet<Sentence> toAdd = new HashSet<>(getOperands());
		while(!toAdd.isEmpty()) {
			Iterator<Sentence> iter = toAdd.iterator();
			Sentence s = iter.next();
			toAdd.remove(s);
			if(s instanceof Conjunction) {
				toAdd.addAll(((Conjunction)s).getOperands());
			}
			else {
				after.addSentence(s.afterFlattening());
			}
		}
		return after;
	}
	
	@Override
	protected Sentence afterDistributingOrOverAnd() {
		Conjunction after = new Conjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterDistributingOrOverAnd());
		}
		return after;
	}

	@Override
	protected Sentence afterMakingClauses() {
		Conjunction after = new Conjunction();
		for(Sentence s : getOperands()) {
			if(s.isLiteral()) {
				Disjunction d = new Disjunction();
				d.addSentence(s);
				after.addSentence(d);
			}
			else {
				after.addSentence(s);
			}
		}
		return after;
	}

}
