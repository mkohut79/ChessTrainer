package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.HashMap;

import edu.kings.cs448.fall2017.base.search.SearchAlgorithm;
import edu.kings.cs448.fall2017.base.search.SearchNode;

/**
 * Class to represent the graphSearch algorithm.
 * 
 * @author michael kohut
 *
 * @param <S>
 * @param <A>
 */
public abstract class GraphSearch<S, A> extends SearchAlgorithm<S, A> {

	/**
	 * Field to store the specific states to the nodes that have that state.
	 */
	private HashMap<S, SearchNode<S, A>> exploredSet;

	/**
	 * Method to search the frontier of nodes with a specific state.
	 * 
	 * @param specificState
	 *            The specific state the is being sought-after.
	 * @return The node with that has the state if it exists.
	 */
	public abstract SearchNode<S, A> searchFrontier(S specificState);

	/**
	 * Method to search the explored Nodes looking for one that contains a
	 * specific state.
	 * 
	 * @param specificState
	 *            The specific state that is sought-after.
	 * @return The node that contains the specific state if it exists.
	 */
	public SearchNode<S, A> searchExplored(S specificState) {
		return exploredSet.get(specificState);
	}

	/**
	 * Method to clear the explored set.
	 */
	public void wipeExplored() {
		exploredSet.clear();
	}

	@Override
	public void initializeExplored() {
		// Tree search does not have an explored set, so do nothing at all.
		// Initialize a new hashmap to store the states to their nodes.
		exploredSet = new HashMap<S, SearchNode<S, A>>();
	}

	@Override
	public void processChild(SearchNode<S, A> node) {
		// In tree search we always add the child to the frontier, no matter
		// what. In graph search you have to see if a node with a certain state
		// has a better path cost and then needs to be updated.
		S state = node.getState();
		SearchNode<S, A> exploredNode = searchExplored(state);
		SearchNode<S, A> frontierNode = searchFrontier(state);
		if (exploredNode != null || frontierNode != null) {

			SearchNode<S, A> existingNode;
			if (exploredNode != null) {
				existingNode = exploredNode;
			} else {
				existingNode = frontierNode;
			}
			int currentPath = existingNode.getPathCost();
			int comparePath = node.getPathCost();
			if (comparePath < currentPath) {
				existingNode.setAction(node.getAction());
				existingNode.setParent(node.getParent());
				existingNode.setPathCost(node.getPathCost());
			}
		} else {
			addToFrontier(node);
		}

	}

	@Override
	public void addToExplored(SearchNode<S, A> node) {
		exploredSet.put(node.getState(), node);
	}

}
