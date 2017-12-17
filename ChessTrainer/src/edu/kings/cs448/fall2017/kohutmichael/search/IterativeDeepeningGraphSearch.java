package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.ArrayList;

import edu.kings.cs448.fall2017.base.search.SearchProblem;
import edu.kings.cs448.fall2017.base.search.SearchSolver;

/**
 * An algorithm that solves a problems by searching through a graph in an
 * iterative deepening approach.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class IterativeDeepeningGraphSearch<S, A> implements SearchSolver<S, A> {

	/**
	 * Field to represent the DepthFirstGraphSearch instance.
	 */
	private DepthFirstGraphSearch<S, A> dfgs;

	/**
	 * Constructor to initialize the depthFirstGraphSearch instance.
	 */
	public IterativeDeepeningGraphSearch() {
		dfgs = new DepthFirstGraphSearch<S, A>();
	}

	@Override
	public ArrayList<A> solve(SearchProblem<S, A> problem) {
		ArrayList<A> solution = null;
		int maxDepth = 0;

		while (solution == null) {
			solution = dfgs.solve(problem, maxDepth);
			maxDepth++;
			dfgs.wipeExplored();
		}

		return solution;
	}

	@Override
	public int getNumExpandedNodes() {
		return dfgs.getNumExpandedNodes();
	}

}
