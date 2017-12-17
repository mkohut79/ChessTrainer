package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.ArrayList;

import edu.kings.cs448.fall2017.base.search.SearchProblem;
import edu.kings.cs448.fall2017.base.search.SearchSolver;

/**
 * An algorithm that solves a problems by searching through a graph in a
 * iterative deepening search manner.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class IterativeDeepeningTreeSearch<S, A> implements SearchSolver<S, A> {

	/**
	 * Field to store the depthFirstTreeSearch instance.
	 */
	private DepthFirstTreeSearch<S, A> dfts;

	/**
	 * Constructor to initialize the DepthFirstTreeSearch instance.
	 */
	public IterativeDeepeningTreeSearch() {
		dfts = new DepthFirstTreeSearch<S, A>();
	}

	@Override
	public ArrayList<A> solve(SearchProblem<S, A> problem) {
		ArrayList<A> solution = null;
		int maxDepth = 0;
		
		while (solution == null) {
			solution = dfts.solve(problem, maxDepth);
			maxDepth++;
		}
		
		return solution;
	}

	@Override
	public int getNumExpandedNodes() {
		return dfts.getNumExpandedNodes();
	}

}
