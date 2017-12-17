package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * A disjunction of multiple Sentences.
 * 
 * @author CS448
 * @version 2017
 */
public class Disjunction extends MultiOperator {

	
	@Override
	public boolean evaluate(Map<Proposition, Boolean> model)
			throws IllegalArgumentException {

		boolean result = false;

		Iterator<Sentence> iter = getOperands().iterator();

		while (!result && iter.hasNext()) {
			Sentence current = iter.next();

			if (current.evaluate(model)) {
				result = true;
			}

		}

		return result;
	}

	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {

		TernaryLogic result = TernaryLogic.FALSE;

		Iterator<Sentence> iter = getOperands().iterator();

		while (result != TernaryLogic.TRUE && iter.hasNext()) {
			Sentence current = iter.next();

			if (current.evaluatePartial(model) == TernaryLogic.TRUE) {

				result = TernaryLogic.TRUE;

			} else if (current.evaluatePartial(model) == TernaryLogic.UNKNOWN) {

				result = TernaryLogic.UNKNOWN;

			}

		}

		return result;
	}

	@Override
	public boolean isClause() {

		boolean result = true;

		Iterator<Sentence> iter = getOperands().iterator();

		while (result && iter.hasNext()) {
			Sentence current = iter.next();

			if (!current.isLiteral()) {
				result = false;
			}

		}

		return result;
	}

	@Override
	public boolean isInCNF() {
		return false;
	}

	@Override
	public String getSymbol() {
		return "||";
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
		else if(!(other instanceof Disjunction)) {
			result = false;
		}
		else {
			result = getOperands().equals(((Disjunction)other).getOperands());
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 0;
		for(Sentence s : getOperands()) {
			result += s.hashCode();
		}
		return result * 57;
	}
	
	@Override
	protected Sentence afterBiconditionalElimination() {
		Disjunction after = new Disjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterBiconditionalElimination());
		}
		return after;
	}

	@Override
	protected Sentence afterImplicationElimination() {
		Disjunction after = new Disjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterImplicationElimination());
		}
		return after;
	}
	
	@Override
	protected Sentence afterConversionToNNF() {
		Disjunction after = new Disjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterConversionToNNF());
		}
		return after;
	}
	
	@Override
	protected Sentence afterFlattening() {
		Disjunction after = new Disjunction();
		
		HashSet<Sentence> toAdd = new HashSet<>(getOperands());
		while(!toAdd.isEmpty()) {
			Iterator<Sentence> iter = toAdd.iterator();
			Sentence s = iter.next();
			toAdd.remove(s);
			if(s instanceof Disjunction) {
				toAdd.addAll(((Disjunction)s).getOperands());
			}
			else {
				after.addSentence(s.afterFlattening());
			}
		}
		return after;
	}
	
	@Override
	protected Sentence afterDistributingOrOverAnd() {
		Conjunction innerC = null;
		Iterator<Sentence> iter = getOperands().iterator();
		while(iter.hasNext() && innerC == null) {
			Sentence s = iter.next();
			if(s instanceof Conjunction) {
				innerC = (Conjunction)s;
			}
		}
		Sentence result;
		if(innerC != null) {
			Conjunction after = new Conjunction();
			for(Sentence innerCPart : innerC.getOperands()) {
				Disjunction newPart = new Disjunction();
				newPart.addSentence(innerCPart);
				for(Sentence outerPart : getOperands()) {
					if(!outerPart.equals(innerC)) {
						newPart.addSentence(outerPart);
					}
				}
				after.addSentence(newPart);
			}
			result = after;
		}
		else {
			Disjunction after = new Disjunction();
			for(Sentence s : getOperands()) {
				after.addSentence(s.afterDistributingOrOverAnd());
			}
			result = after;
		}
		
		return result;
	}

	@Override
	protected Sentence afterMakingClauses() {
		Disjunction after = new Disjunction();
		for(Sentence s : getOperands()) {
			after.addSentence(s.afterMakingClauses());
		}
		return after;
	}

}
