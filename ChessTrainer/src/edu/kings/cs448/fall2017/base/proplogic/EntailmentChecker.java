package edu.kings.cs448.fall2017.base.proplogic;

/**
 * Anything with a method for checking whether or not one propositional Sentence entails another.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public interface EntailmentChecker {

	/**
	 * Tests whether or not one propositional Sentence entails another.
	 * 
	 * @param kb The Sentence that is believed to be true.
	 * @param alpha The sentence that might or might not be entailed.
	 * @return Whether or not alpha must be true whenever kb is true.
	 */
	public boolean entails(Sentence kb, Sentence alpha);
}
