package edu.kings.cs448.fall2017.MichaelKohut.csps;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import edu.kings.cs448.fall2017.base.csps.Constraint;
import edu.kings.cs448.fall2017.base.csps.ConstraintSatisfactionProblem;
import edu.kings.cs448.fall2017.base.csps.NQueensMain;


/**
 * Some tests for the AC3 algorithm.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class TestAC3 {
	
	/** The algorithm under test. */
	private BacktrackingWithAC3 algorithm;
	
	/**
	 * Constructs a new TestAC3.
	 */
	public TestAC3() {
		algorithm = new BacktrackingWithAC3();
	}

	/**
	 * Makes a deep copy of a map of variables and domains.
	 *
	 * @param old The original.
	 * @return A deep copy of it.
	 */
	public Map<String, Set<Object>> deepDomainsCopy(Map<String, Set<Object>> old) {
		Map<String, Set<Object>> copy = new HashMap<>();
		for(String key : old.keySet()) {
			copy.put(key, new HashSet<>(old.get(key)));
		}
		return copy;
	}
	
	/**
	 * Tests that revise does nothing when nothing can be done.
	 */
	@Test
	public void testReviseDoesNothing() {
		ConstraintSatisfactionProblem csp = NQueensMain.createProblem(4);
		csp.getDomains().put("C5", new HashSet<>(Arrays.asList(new Object[] {0, 1, 2, 3})));
		Map<String, Set<Object>> domainsCopy = deepDomainsCopy(csp.getDomains());
		Set<Constraint> constraintsCopy = new HashSet<>(csp.getConstraints());
		
		Set<Object> toRemove = algorithm.revise(csp, "C0", "C5");
		assertTrue("Nothing should have been removed, because there is no relationship between these variables.", toRemove.isEmpty());
		assertEquals("The domains should not have changed.", domainsCopy, csp.getDomains());
		assertEquals("The constraints should not have changed.", constraintsCopy, csp.getConstraints());

		toRemove = algorithm.revise(csp, "C0", "C3");
		assertTrue("Nothing should have been removed because every value of C0 is still valid", toRemove.isEmpty());
		assertEquals("The domains should not have changed.", domainsCopy, csp.getDomains());
		assertEquals("The constraints should not have changed.", constraintsCopy, csp.getConstraints());
	}
	
	/**
	 * Tests that revise removes some values when it should.
	 */
	@Test
	public void testReviseRemovesSome() {
		ConstraintSatisfactionProblem csp = NQueensMain.createProblem(4);
		csp.getDomains().put("C5", new HashSet<>(Arrays.asList(new Object[] {0, 1, 2, 3})));
		
		csp.getDomains().get("C0").remove(1);
		csp.getDomains().get("C0").remove(3);
		Map<String, Set<Object>> domainsCopy = deepDomainsCopy(csp.getDomains());
		Set<Constraint> constraintsCopy = new HashSet<>(csp.getConstraints());
		
		Set<Object> toRemove = algorithm.revise(csp, "C1", "C0");
		Set<Object> expected = new HashSet<>();
		expected.add(1);
		assertEquals("We should have removed (only) 1 from the domain.", expected, toRemove);
		assertEquals("The domains should not have changed.", domainsCopy, csp.getDomains());
		assertEquals("The constraints should not have changed.", constraintsCopy, csp.getConstraints());

		csp.getDomains().get("C0").remove(2);
		domainsCopy.get("C0").remove(2);
		toRemove = algorithm.revise(csp, "C1", "C0");
		expected.add(0);
		assertEquals("We should have removed (only) 0 and 1 from the domain.", expected, toRemove);
		assertEquals("The domains should not have changed.", domainsCopy, csp.getDomains());
		assertEquals("The constraints should not have changed.", constraintsCopy, csp.getConstraints());
	}

	/**
	 * Tests that revise removes all values when it should.
	 */
	@Test
	public void testReviseRemovesAll() {
		ConstraintSatisfactionProblem csp = NQueensMain.createProblem(4);
		csp.getDomains().put("C5", new HashSet<>(Arrays.asList(new Object[] {0, 1, 2, 3})));
		
		csp.getDomains().get("C0").remove(1);
		csp.getDomains().get("C0").remove(2);
		csp.getDomains().get("C0").remove(3);
		csp.getDomains().get("C1").remove(3);
		csp.getDomains().get("C1").remove(2);
		Map<String, Set<Object>> domainsCopy = deepDomainsCopy(csp.getDomains());
		Set<Constraint> constraintsCopy = new HashSet<>(csp.getConstraints());
		
		Set<Object> toRemove = algorithm.revise(csp, "C1", "C0");
		Set<Object> expected = new HashSet<>();
		expected.add(0);
		expected.add(1);
		assertEquals("We should have removed both 0 and 1 from the domain.", expected, toRemove);
		assertEquals("The domains should not have changed.", domainsCopy, csp.getDomains());
		assertEquals("The constraints should not have changed.", constraintsCopy, csp.getConstraints());
		
	}
}
