package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a propositional variable.
 * 
 * @author CS448
 * @version 2017
 */
public final class Proposition extends Sentence {

	/** The name of the variable. */
	private String variable;
	
	/**
	 * Creates a new Proposition.
	 * 
	 * @param variable The name of the propositional variable.
	 */
	public Proposition(String variable){
		this.variable = variable;
	}

	@Override
	public boolean evaluate(Map<Proposition, Boolean> model) throws IllegalArgumentException {
		// TODO: Complete override
		boolean result = false;
		if(model.containsKey(this) == false){
			throw new IllegalArgumentException();
		}
		else{
			result = model.get(this);
		}
		
		return result;
	}
	
	@Override
	public TernaryLogic evaluatePartial(Map<Proposition, Boolean> model) {
		// TODO: Complete override
		TernaryLogic result = TernaryLogic.UNKNOWN;
		
		if(model.containsKey(this)){
			boolean value = model.get(this);
			
			if(value){
				result = TernaryLogic.TRUE;
			}
			else{
				result = TernaryLogic.FALSE;
			}
		}
		
		return result;
	}
	
	@Override
	public Set<Proposition> getVariables() {
		HashSet<Proposition> result = new HashSet<>();
		result.add(this);
		return result;
	}

	@Override
	public boolean isLiteral() {
		return true;
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
		return true;
	}
	
	@Override
	public String toString() {
		return variable;
	}

	@Override
	public boolean equals(Object other) {
		boolean result;
		if(other == null) {
			result = false;
		}
		else if(other instanceof Proposition) {
			Proposition p = (Proposition)other;
			result = variable.equals(p.variable);
		}
		else {
			result = false;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return variable.hashCode();
	}

	@Override
	protected Sentence afterBiconditionalElimination() {
		return this;
	}

	@Override
	protected Sentence afterImplicationElimination() {
		return this;
	}

	@Override
	protected Sentence afterConversionToNNF() {
		return this;
	}

	@Override
	protected Sentence afterFlattening() {
		return this;
	}

	@Override
	protected Sentence afterDistributingOrOverAnd() {
		return this;
	}

	@Override
	protected Sentence afterMakingClauses() {
		return this;
	}

}
