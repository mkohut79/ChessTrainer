package edu.kings.cs448.fall2017.base.proplogic;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Some tests for the logical entailment algorithms.
 * 
 * @author CS448
 * @version 2017
 */
public class EntailmentTests {
	
	/** A model-checking algorithm. */
	private ModelCheckAlgorithm modelCheck;
	
	/** A resolution algorithm. */
	private ResolutionAlgorithm resolution;
	
	/** A DPLL algorithm. */
	private DPLLAlgorithm dpll;
	
	/**
	 * Constructs a new EntailmentTests.
	 */
	public EntailmentTests(){
		modelCheck = new ModelCheckAlgorithm();
		resolution = new ResolutionAlgorithm();
		dpll = new DPLLAlgorithm();
	}
	
	/**
	 * Tests that the model-checking algorithm works correctly.
	 */
	@Test
	public void testModelCheck() {
		String knowledge = "( Q && ( P --> Q ) && ( R --> ( P || Q ) ) && ( ( P && Q ) --> ! ( R ) ) )";
		Sentence testKnowledgeBase = Sentence.parse(knowledge);
		String alpha = "( P --> ! ( R ) )";
		Sentence alphaSentence = Sentence.parse(alpha);
		String badApple = "( ! ( R ) --> P )";
		Sentence badAppleSentence = Sentence.parse(badApple);
		assertTrue(modelCheck.entails(testKnowledgeBase, alphaSentence));
		assertFalse(modelCheck.entails(testKnowledgeBase, badAppleSentence));
	}

	/**
	 * Tests that the resolution algorithm works correctly.
	 */
	@Test
	public void testResolution() {
		String knowledge = "( Q && ( P --> Q ) && ( R --> ( P || Q ) ) && ( ( P && Q ) --> ! ( R ) ) )";
		Sentence testKnowledgeBase = Sentence.parse(knowledge);
		String alpha = "( P --> ! ( R ) )";
		Sentence alphaSentence = Sentence.parse(alpha);
		String badApple = "( ! ( R ) --> P )";
		Sentence badAppleSentence = Sentence.parse(badApple);
		assertTrue(resolution.entails(testKnowledgeBase, alphaSentence));
		assertFalse(resolution.entails(testKnowledgeBase, badAppleSentence));
	}

	/**
	 * Tests that the DPLL algorithm works correctly.
	 */
	@Test
	public void testDPLL() {
		String knowledge = "( Q && ( P --> Q ) && ( R --> ( P || Q ) ) && ( ( P && Q ) --> ! ( R ) ) )";
		Sentence testKnowledgeBase = Sentence.parse(knowledge);
		String alpha = "( P --> ! ( R ) )";
		Sentence alphaSentence = Sentence.parse(alpha);
		String badApple = "( ! ( R ) --> P )";
		Sentence badAppleSentence = Sentence.parse(badApple);
		assertTrue(dpll.entails(testKnowledgeBase, alphaSentence));
		assertFalse(dpll.entails(testKnowledgeBase, badAppleSentence));
	}

}
