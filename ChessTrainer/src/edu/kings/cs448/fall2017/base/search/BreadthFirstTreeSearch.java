package edu.kings.cs448.fall2017.base.search;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An algorithm that solves problems by searching through a tree in a breadth-first manner.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem.
 * @param <A> The type of actions in the problem.
 */
public class BreadthFirstTreeSearch<S, A> extends TreeSearch<S, A> {
	
	/** The collection of nodes that have not yet been explored. */
	private Queue<SearchNode<S, A>> frontier;
	
	/**
	 * Constructs a new BreadthFirstTreeSearch.
	 */
	public BreadthFirstTreeSearch() {
		frontier = new LinkedList<>();
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
		return frontier.poll();
	}

	@Override
	public void addToFrontier(SearchNode<S, A> node) {
		frontier.add(node);
	}

}
