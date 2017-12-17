package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.HashMap;
import java.util.Stack;

import edu.kings.cs448.fall2017.base.search.SearchNode;
import edu.kings.cs448.fall2017.base.search.SearchProblem;

/**
 * An algorithm that solves a problems by searching through a graph in a breadth
 * first manner.
 * 
 * @author Michael Kohut
 * @param <S>
 * @param <A>
 *
 */
public class DepthFirstGraphSearch<S, A> extends GraphSearch<S, A> {

	/**
	 * The collection of nodes that have not yet been explored.
	 */
	private Stack<SearchNode<S, A>> shallowest;

	/**
	 * The mapping of states the nodes that contain the specific states.
	 */
	private HashMap<S, SearchNode<S, A>> frontier;

	/**
	 * Constructor to initialize the fields representing the frontier.
	 */
	public DepthFirstGraphSearch() {
		shallowest = new Stack<SearchNode<S, A>>();
		frontier = new HashMap<S, SearchNode<S, A>>();
	}

	@Override
	public SearchNode<S, A> searchFrontier(S searchNode) {
		return frontier.get(searchNode);

	}

	@Override
	public void initializeFrontier(SearchProblem<S, A> problem) {
		shallowest.add(new SearchNode<S, A>(null, 0, problem.getInitialState(), null));
		frontier.put(problem.getInitialState(), new SearchNode<S, A>(null, 0, problem.getInitialState(), null));

	}

	@Override
	public void processChild(SearchNode<S, A> node) {
		super.processChild(node);
	}

	@Override
	public boolean isFrontierEmpty() {
		return frontier.isEmpty() && shallowest.isEmpty();
	}

	@Override
	public SearchNode<S, A> selectNode(SearchProblem<S, A> problem) {
		SearchNode<S, A> popped = shallowest.pop();
		frontier.remove(popped.getState());
		return popped;

	}

	@Override
	public void addToFrontier(SearchNode<S, A> node) {
		shallowest.add(node);
		frontier.put(node.getState(), node);
	}

}
