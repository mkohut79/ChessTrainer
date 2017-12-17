package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A logical operand that can any number of operands.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public abstract class MultiOperator extends Sentence {

	/** The operands of this operator. */
	private Set<Sentence> sentences;

	/**
	 * Constructs a new MultiOperator with no operands.
	 */
	public MultiOperator() {
		sentences = new HashSet<Sentence>();
	}

	/**
	 * Adds a new operand, if there is not an equivalent operand.
	 * 
	 * @param newSentence The Sentence to add.
	 */
	public void addSentence(Sentence newSentence) {
		boolean found = false;
		Iterator<Sentence> iter = sentences.iterator();
		while(iter.hasNext() && !found) {
			Sentence current = iter.next();
			if(current.equivalent(newSentence)) {
				found = true;
			}
		}
		if(!found) {
			sentences.add(newSentence);
		}
	}
	
	/**
	 * Gets a view of the operands set that is not modifiable.
	 * 
	 * @return A view of the operands set that is not modifiable.
	 */
	public Set<Sentence> getOperands() {
		return Collections.unmodifiableSet(sentences);
	}
	
	/**
	 * Gets the symbol used for this MultiOperator.
	 * 
	 * @return The symbol used for this MultiOperator.
	 */
	public abstract String getSymbol();

	
	@Override
	public String toString() {

		StringBuffer result = new StringBuffer();

		result.append("( ");
		int counter = 0;
		for (Sentence current : sentences) {

			result.append(current.toString());

			if (counter < sentences.size() - 1) {
				result.append(" ");
				result.append(getSymbol());
				result.append(" ");
			}
			counter++;
		}

		result.append(" )");

		return result.toString();
	}

	@Override
	public boolean isInNNF() {

		boolean result = true;

		Iterator<Sentence> iter = sentences.iterator();

		while (result && iter.hasNext()) {
			Sentence current = iter.next();

			if (!current.isInNNF()) {
				result = false;
			}

		}

		return result;

	}

	@Override
	public Set<Proposition> getVariables() {

		Set<Proposition> variables = new HashSet<Proposition>();

		for (Sentence current : sentences) {
			variables.addAll(current.getVariables());
		}

		return variables;
	}

	@Override
	public boolean isLiteral() {
		return false;
	}


}
