package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashSet;
import java.util.Set;

/**
 * A logical operator with two operands.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public abstract class BinaryOperator extends Sentence {

	/** The first operand. */
	private Sentence first;
	
	/** The second operand. */
	private Sentence second;
	
	/**
	 * Constructs a new BinaryOperator.
	 * 
	 * @param firstValue The first operand.
	 * @param secondValue The second operand.
	 */
	public BinaryOperator(Sentence firstValue, Sentence secondValue) {
		first = firstValue;
		second = secondValue;
	}
	
	/**
	 * Gets the first operand.
	 * 
	 * @return The first operand.
	 */
	public Sentence getFirst() {
		return first;
	}
	
	/**
	 * Gets the second operand.
	 * 
	 * @return The second operand.
	 */
	public Sentence getSecond() {
		return second;
	}
	
	@Override
	public Set<Proposition> getVariables() {
		Set<Proposition> result = new HashSet<>();
		result.addAll(first.getVariables());
		result.addAll(second.getVariables());
		return result;
	}

	@Override
	public boolean isLiteral() {
		return false;
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
		return false;
	}

	@Override
	public String toString() {
		String result = "( " + first + " " + getSymbol() + " " + second + " )";
		return result;
	}
	
	/**
	 * Gets the symbol used to represent this operator.
	 * 
	 * @return The symbol used to represent this operator.
	 */
	public abstract String getSymbol();

}
