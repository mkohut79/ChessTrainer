package edu.kings.cs448.fall2017.base.proplogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Some tests for the methods that convert a string to CNF.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class ConversionTests {
	
	/**
	 * Tests that biconditional elimination works correctly.
	 */
	@Test
	public void testBiconditionalElimination() {
		Sentence sentence1 = Sentence.parse("! ( ( P && ! ( ( Q <-> R ) ) ) )");
		Sentence sentence2 = sentence1.afterBiconditionalElimination();
		assertTrue("Biconditional elimination should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("There should be no more biconditionals.", !sentence2.toString().contains("<->"));
	}

	/**
	 * Tests that implication elimination works correctly.
	 */
	@Test
	public void testImplicationElimination() {
		Sentence sentence1 = Sentence.parse("! ( ( P && ! ( ( Q --> R ) ) ) )");
		Sentence sentence2 = sentence1.afterImplicationElimination();
		assertTrue("Implication elimination should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("There should be no more implications.", !sentence2.toString().contains("-->"));
	}

	/**
	 * Tests that conversion to negation normal form works correctly.
	 */
	@Test
	public void testConversionToNNF() {
		Sentence sentence1 = Sentence.parse("! ( ( P && Q && ! ( ( P || ! ( R ) ) ) ) )");
		Sentence sentence2 = sentence1.afterConversionToNNF();
		assertTrue("Conversion to NNF should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("The sentence should now be in NNF.", sentence2.isInNNF());
	}
	
	/**
	 * Tests that flattening works correctly.
	 */
	@Test
	public void testFlattening() {
		Sentence sentence1 = Sentence.parse("( A && B && ( C && D ) && ( ( E || ( F || G ) ) ) )");
		Sentence sentence2 = sentence1.afterFlattening();
		assertTrue("Flattening should not change meaning.", sentence1.equivalent(sentence2));
		assertEquals("Flattened sentence should have been this.", Sentence.parse("( A && B && C && D && ( E || F || G ) )"), sentence2);
	}
	
	/**
	 * Tests that distributing or over and works correctly.
	 */
	@Test
	public void testDistributingOrOverAnd() {
		Sentence sentence1 = Sentence.parse("( A || ( B && C ) || ( D && ! ( E ) ) )");
		Sentence sentence2 = sentence1.afterDistributingOrOverAnd();
		assertTrue("Distributing or over and should not change meaning.", sentence1.equivalent(sentence2));
		Sentence option1 = Sentence.parse("( ( A || B || ( D && ! ( E ) ) ) && ( A || C || ( D && ! ( E ) ) ) )");
		Sentence option2 = Sentence.parse("( ( A || ( B && C ) || D ) && ( A || ( B && C ) || ! ( E ) ) )");
		assertTrue("Distriuted sentence should have been " + option1 + " or " + option2 + " but was " + sentence2 + ".", sentence2.equals(option1) || sentence2.equals(option2));
	}
	
	/**
	 * Tests that making clauses works correctly.
	 */
	@Test
	public void testMakingClauses() {
		Sentence sentence1 = Sentence.parse("( P && ( Q || R ) )");
		Sentence sentence2 = sentence1.afterMakingClauses();
		assertTrue("Making clauses should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("Result should be in CNF.", sentence2.isInCNF());
	}
	
	/**
	 * Tests that conversion to CNF works correctly.
	 */
	@Test
	public void testConvertToCNF() {
		Sentence sentence1 = Sentence.parse("! ( ( P && ! ( ( Q <-> R ) ) ) )");
		Sentence sentence2 = sentence1.convertToCNF();
		assertTrue("Converting to CNF should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("Result should be in CNF.", sentence2.isInCNF());

		sentence1 = Sentence.parse("P");
		sentence2 = sentence1.convertToCNF();
		assertTrue("Converting to CNF should not change meaning.", sentence1.equivalent(sentence2));
		assertTrue("Result should be in CNF.", sentence2.isInCNF());

	}
}
