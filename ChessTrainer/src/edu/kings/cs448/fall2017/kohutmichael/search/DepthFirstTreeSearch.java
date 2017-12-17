package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.Stack;

import edu.kings.cs448.fall2017.base.search.SearchNode;
import edu.kings.cs448.fall2017.base.search.SearchProblem;
import edu.kings.cs448.fall2017.base.search.TreeSearch;

/**
 * An algorithm that solves problems by searching through a tree in a
 * depth-first manner.
 * 
 * @param <S>
 *            The generic type to represent the state of a searchNode.
 * @param <A>
 *            The generic type to represent the action(s) of a searchNode.
 * @author Michael Kohut
 *
 */
public class DepthFirstTreeSearch<S, A> extends TreeSearch<S, A> {

	/** The collection of nodes that have not yet been explored. */
	private Stack<SearchNode<S, A>> frontier;

	/**
	 * Constructs a new BreadthFirstTreeSearch.
	 */
	public DepthFirstTreeSearch() {
		frontier = new Stack<>();
	}

	@Override
	public void initializeFrontier(SearchProblem<S, A> problem) {
		frontier.add(new SearchNode<S, A>(null, 0, problem.getInitialState(), null));
	}

	@Override
	public boolean isFrontierEmpty() {
		return frontier.isEmpty();
	}

	@Override
	public SearchNode<S, A> selectNode(SearchProblem<S, A> problem) {
		return frontier.pop();
	}

	@Override
	public void addToFrontier(SearchNode<S, A> node) {
		frontier.add(node);
	}
	
}
