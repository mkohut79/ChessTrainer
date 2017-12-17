package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.HashMap;
import java.util.PriorityQueue;

import edu.kings.cs448.fall2017.base.search.SearchNode;
import edu.kings.cs448.fall2017.base.search.SearchProblem;

/**
 * An algorithm that represents choosing the next best node which is based on
 * the heuristic value.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class GreedyBestFirstGraphSearch<S, A> extends GraphSearch<S, A> {

	/**
	 * The collection of nodes that have not yet been explored.
	 */
	private PriorityQueue<SearchNode<S, A>> shallowest;

	/**
	 * The mapping of states the nodes that contain the specific states.
	 */
	private HashMap<S, SearchNode<S, A>> frontier;

	/**
	 * Comparator used to order in terms of heuristic values.
	 */
	private HeuristicComparator<S, A> hComp;

	/**
	 * Constructor to initialize the fields representing the frontier.
	 */
	public GreedyBestFirstGraphSearch() {
		hComp = new HeuristicComparator<S, A>();
		shallowest = new PriorityQueue<SearchNode<S, A>>(hComp);
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
		frontier.remove(problem.getInitialState());
		return shallowest.poll();
	}

	@Override
	public void addToFrontier(SearchNode<S, A> node) {
		shallowest.add(node);
		frontier.put(node.getState(), node);
	}

}
