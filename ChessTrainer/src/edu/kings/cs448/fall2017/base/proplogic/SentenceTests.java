package edu.kings.cs448.fall2017.base.proplogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * A collection of basic tests to ensure that the Sentence class hierarchy works correctly.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class SentenceTests {

	/**
	 * Tests that the parse() and toString() methods work.
	 */
	@Test
	public void testParser() {
		String sentence1Str = "( ( ( P && ! ( Q ) && R ) || ! ( R ) ) --> ( ! ( ! ( P ) ) <-> P ) )";
		Sentence sentence1 = Sentence.parse(sentence1Str);
		assertTrue(sentence1 instanceof Implication);
		Sentence antecedent = ((Implication)sentence1).getFirst();
		Sentence consequent = ((Implication)sentence1).getSecond();
		assertTrue(antecedent instanceof Disjunction);
		assertTrue(consequent instanceof Biconditional);
		Set<Sentence> disjuncts = ((Disjunction)antecedent).getOperands();
		boolean foundConjunction = false;
		boolean foundNegation = false;
		assertEquals(disjuncts.size(), 2);
		for(Sentence s : disjuncts) {
			if(s instanceof Conjunction) {
				foundConjunction = true;
				Set<Sentence> conjuncts = ((Conjunction)s).getOperands();
				boolean foundP = false;
				boolean foundNotQ = false;
				boolean foundR = false;
				assertEquals(conjuncts.size(), 3);
				for(Sentence c : conjuncts) {
					if(c instanceof Proposition) {
						if(c.equals(new Proposition("P"))) {
							foundP = true;
						}
						else if(c.equals(new Proposition("R"))) {
							foundR = true;
						}
					}
					else if(c instanceof Negation) {
						assertEquals(c, new Negation(new Proposition("Q")));
						foundNotQ = true;
					}
				}
				assertTrue(foundP && foundNotQ && foundR);
			}
			else if(s instanceof Negation) {
				foundNegation = true;
				assertEquals(s, new Negation(new Proposition("R")));
			}
		}
		assertTrue(foundConjunction && foundNegation);
		
		Sentence first = ((Biconditional)consequent).getFirst();
		Sentence second = ((Biconditional)consequent).getSecond();
		assertTrue(first instanceof Negation);
		assertEquals(first, new Negation( new Negation( new Proposition("P"))));
		assertEquals(second, new Proposition("P"));

		Sentence copy = Sentence.parse(sentence1.toString());
		assertEquals(sentence1, copy);
	}

	/**
	 * Tests that the equalivalent method works correctly.
	 */
	@Test
	public void testEquivalent() {
		Sentence sentence1 = Sentence.parse("( P || ! ( Q ) )");
		Sentence sentence2 = Sentence.parse("( Q --> P )");
		Sentence sentence3 = Sentence.parse("P");
		Sentence sentence4 = Sentence.parse("! ( ! ( P ) )");
		assertTrue(sentence1.equivalent(sentence1));
		assertTrue(sentence1.equivalent(sentence2));
		assertFalse(sentence1.equivalent(sentence3));
		assertFalse(sentence1.equivalent(sentence4));
		assertTrue(sentence2.equivalent(sentence1));
		assertTrue(sentence2.equivalent(sentence2));
		assertFalse(sentence2.equivalent(sentence3));
		assertFalse(sentence2.equivalent(sentence4));
		assertFalse(sentence3.equivalent(sentence1));
		assertFalse(sentence3.equivalent(sentence2));
		assertTrue(sentence3.equivalent(sentence3));
		assertTrue(sentence3.equivalent(sentence4));
		assertFalse(sentence4.equivalent(sentence1));
		assertFalse(sentence4.equivalent(sentence2));
		assertTrue(sentence4.equivalent(sentence3));
		assertTrue(sentence4.equivalent(sentence4));
	}
	
	/**
	 * Tests that the evaluate method works correctly for propositions.
	 */
	@Test
	public void testEvaluateProposition() {
		Sentence sentence1 = Sentence.parse("P");
		Map<Proposition, Boolean> model1 = new HashMap<>();
		Map<Proposition, Boolean> model2 = new HashMap<>();
		model1.put(new Proposition("P"), true);
		model2.put(new Proposition("P"), false);
		assertTrue(sentence1.evaluate(model1));
		assertFalse(sentence1.evaluate(model2));
	}
	
	/**
	 * Tests that the evaluate method works correctly for negations.
	 */
	@Test
	public void testEvaluateNegation() {
		Sentence sentence1 = Sentence.parse("! ( P ) ");
		Map<Proposition, Boolean> model1 = new HashMap<>();
		Map<Proposition, Boolean> model2 = new HashMap<>();
		model1.put(new Proposition("P"), true);
		model2.put(new Proposition("P"), false);
		assertFalse(sentence1.evaluate(model1));
		assertTrue(sentence1.evaluate(model2));
	}
	
	/**
	 * Tests that the evaluate method works correctly for disjunctions.
	 */
	@Test
	public void testEvaluateDisjunction() {
		Sentence sentence1 = Sentence.parse("( P || Q || R )");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put(new Proposition("P"), false);
		model.put(new Proposition("Q"), false);
		model.put(new Proposition("R"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		model.put(new Proposition("R"), false);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("P"), true);
		model.put(new Proposition("Q"), false);
		model.put(new Proposition("R"), false);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		model.put(new Proposition("R"), false);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertTrue(sentence1.evaluate(model));
	}
	
	/**
	 * Tests that the evaluate method works correctly for conjunctions.
	 */
	@Test
	public void testEvaluateConjunction() {
		Sentence sentence1 = Sentence.parse("( P && Q && R )");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put(new Proposition("P"), false);
		model.put(new Proposition("Q"), false);
		model.put(new Proposition("R"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		model.put(new Proposition("R"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("P"), true);
		model.put(new Proposition("Q"), false);
		model.put(new Proposition("R"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		model.put(new Proposition("R"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("R"), true);
		assertTrue(sentence1.evaluate(model));
	}
	
	/**
	 * Tests that the evaluate method works correctly for implications.
	 */
	@Test
	public void testEvaluateImplication() {
		Sentence sentence1 = Sentence.parse("( P --> Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put(new Proposition("P"), false);
		model.put(new Proposition("Q"), false);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("P"), true);
		model.put(new Proposition("Q"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		assertTrue(sentence1.evaluate(model));		
	}

	/**
	 * Tests that the evaluate method works correctly for biconditionals.
	 */
	@Test
	public void testEvaluateBicondition() {
		Sentence sentence1 = Sentence.parse("( P <-> Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put(new Proposition("P"), false);
		model.put(new Proposition("Q"), false);
		assertTrue(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("P"), true);
		model.put(new Proposition("Q"), false);
		assertFalse(sentence1.evaluate(model));
		model.put(new Proposition("Q"), true);
		assertTrue(sentence1.evaluate(model));
	}
	
	/**
	 * Tests that the evaluatePartial method works correctly for propositions.
	 */
	@Test
	public void testEvaluatePartialProposition() {
		Sentence sentence1 = new Proposition("P");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put( new Proposition("Q"), true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(new Proposition("P"), false);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.FALSE);
		model.put(new Proposition("P"), true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.TRUE);
	}
	
	/**
	 * Tests that the evaluatePartial method works correctly for negations.
	 */
	@Test
	public void testEvaluatePartialNegation() {
		Sentence sentence1 = Sentence.parse("! ( P )");
		Map<Proposition, Boolean> model = new HashMap<>();
		model.put( new Proposition("Q"), true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(new Proposition("P"), false);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(new Proposition("P"), true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.FALSE);		
	}
	
	/**
	 * Tests that the evaluatePartial method works correctly for disjunctions.
	 */
	@Test
	public void testEvaluatePartialDisjunction() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Sentence sentence1 = Sentence.parse("( P || Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, false);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(q, false);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.FALSE);
		model.remove(p);
		model.put(q, true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.TRUE);
	}

	/**
	 * Tests that the evaluatePartial method works correctly for conjunctions.
	 */
	@Test
	public void testEvaluatePartialConjunction() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Sentence sentence1 = Sentence.parse("( P && Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(q, true);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.TRUE);
		model.remove(p);
		model.put(q, false);
		assertEquals(sentence1.evaluatePartial(model), TernaryLogic.FALSE);
	}
	
	/**
	 * Tests that the evaluatePartial method works correctly for implications.
	 */
	@Test
	public void testEvaluatePartialImplication() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Sentence s = Sentence.parse("( P --> Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.remove(p);
		model.put(q, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(q, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(p, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.FALSE);
		model.put(q, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
	}

	/**
	 * Tests that the evaluatePartial method works correctly for biconditionals.
	 */
	@Test
	public void testEvaluatePartialBiconditional() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Sentence s = Sentence.parse("( P <-> Q )");
		Map<Proposition, Boolean> model = new HashMap<>();
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.remove(p);
		model.put(q, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(q, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.UNKNOWN);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(p, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.FALSE);
		model.put(q, true);
		assertEquals(s.evaluatePartial(model), TernaryLogic.TRUE);
		model.put(p, false);
		assertEquals(s.evaluatePartial(model), TernaryLogic.FALSE);
	}
	
	/**
	 * Tests that getVariables method works correctly for propositions.
	 */
	@Test
	public void testGetVariablesProposition() {
		Sentence s = new Proposition("P");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 1);
		assertTrue(vars.contains(s));
	}
	
	/**
	 * Tests that getVariables method works correctly for negations.
	 */
	@Test
	public void testGetVariablesNegation() {
		Proposition p = new Proposition("P");
		Sentence s = Sentence.parse("! ( P ) ");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 1);
		assertTrue(vars.contains(p));
	}
	
	/**
	 * Tests that getVariables method works correctly for disjunctions.
	 */
	@Test
	public void testGetVariablesDisjunction() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Sentence s = Sentence.parse("( ! ( P ) || P || Q )");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 2);
		assertTrue(vars.contains(p));
		assertTrue(vars.contains(q));
	}
	
	/**
	 * Tests that getVariables method works correctly for conjunctions.
	 */
	@Test
	public void testGetVariablesConjunction() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Proposition r = new Proposition("R");
		Sentence s = Sentence.parse("( ! ( ( P || Q ) ) && R )");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 3);
		assertTrue(vars.contains(p));
		assertTrue(vars.contains(q));
		assertTrue(vars.contains(r));
	}
	
	/**
	 * Tests that getVariables method works correctly for implications.
	 */
	@Test
	public void testGetVariablesImlication() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Proposition r = new Proposition("R");
		Sentence s = Sentence.parse("( ( P || Q ) --> ( P && ! ( R ) ) )");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 3);
		assertTrue(vars.contains(p));
		assertTrue(vars.contains(q));
		assertTrue(vars.contains(r));
	}

	/**
	 * Tests that getVariables method works correctly for biconditionals.
	 */
	@Test
	public void testGetVariablesBiconditional() {
		Proposition p = new Proposition("P");
		Proposition q = new Proposition("Q");
		Proposition r = new Proposition("R");
		Sentence s = Sentence.parse("( ( P || Q ) <-> ( P && ! ( R ) ) )");
		Set<Proposition> vars = s.getVariables();
		assertEquals(vars.size(), 3);
		assertTrue(vars.contains(p));
		assertTrue(vars.contains(q));
		assertTrue(vars.contains(r));
	}

	/**
	 * Tests that isLiteral method works through the Sentence hierarchy.
	 */
	@Test
	public void testIsLiteral() {
		assertTrue(Sentence.parse("P").isLiteral());
		assertTrue(Sentence.parse("! ( P )").isLiteral());
		assertFalse(Sentence.parse("! ( ! ( P ) )").isLiteral());
		assertFalse(Sentence.parse("( P || Q )").isLiteral());
		assertFalse(Sentence.parse("( P && Q )").isLiteral());
		assertFalse(Sentence.parse("( P --> Q )").isLiteral());
		assertFalse(Sentence.parse("( P <-> Q )").isLiteral());
	}
	
	
	//TODO Test isClause
	
	//TODO Test isInCNF
	
	//TODO Test isInNNF
}
