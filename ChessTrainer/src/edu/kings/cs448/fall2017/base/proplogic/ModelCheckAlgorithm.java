package edu.kings.cs448.fall2017.base.proplogic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * An algorithm to check entailment of propositional sentences through model checking.
 * 
 * @author CS448
 * @version 2017
 */
public class ModelCheckAlgorithm implements EntailmentChecker {
	
	/**
	 * Tests whether or not one propositional Sentence entails another.
	 * 
	 * @param kb The Sentence that is believed to be true.
	 * @param alpha The sentence that might or might not be entailed.
	 * @return Whether or not alpha must be true whenever kb is true.
	 */
	public boolean entails(Sentence kb, Sentence alpha) {
		Set<Proposition> symbols = kb.getVariables();
		symbols.addAll(alpha.getVariables());
		Map<Proposition, Boolean> model = new HashMap<>();
		return modelCheck(kb, alpha, symbols, model);
	}	
	
	/**
	 * Recursively tests entailment.
	 * 
	 * @param kb The knowledge base.
	 * @param alpha The sentence that might be true.
	 * @param symbols A set of symbols that have not yet been assigned.
	 * @param model A partial model.
	 * @return Whether or not the sentence is entailed by the knowledge base.
	 */
	private boolean modelCheck(Sentence kb, Sentence alpha, Set<Proposition> symbols, Map<Proposition, Boolean> model){
		boolean result = false;
		if(symbols.isEmpty()){
			if(kb.evaluate(model)){
				result = alpha.evaluate(model);
			}else{
				result = true;
			}
		}else{
			Iterator<Proposition> iter = symbols.iterator();
			Proposition current = iter.next();
			symbols.remove(current);
			model.put(current, true);
			if(modelCheck(kb, alpha, symbols, model)){
				model.put(current, false);
				result = modelCheck(kb, alpha, symbols, model);
			}else{
				result = false;
			}
			model.remove(current);
			symbols.add(current);
		}
		
		return result;
	}
	
}
