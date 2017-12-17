package edu.kings.cs448.fall2017.base.search;

import java.util.ArrayList;
import java.util.Set;

/**
 * A generic, abstract algorithm for solving search problems.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the search problem to be solved.
 * @param <A> The type of actions in the search problem to be solved.
 */
public abstract class SearchAlgorithm<S, A> implements SearchSolver<S, A> {
	
	/** A flag indicating that the search depth should not be limited. */
	public static final int UNLIMITED_DEPTH = -1;

	/** A count of how many nodes were expanded when solving a problem. */
	private int numExpandedNodes;
	
	/**
	 * Solves a search problem.
	 * 
	 * @param problem The problem to be solved.
	 * @return A sequence of actions that solves the problem, or null if none exists.
	 */
	public ArrayList<A> solve(SearchProblem<S, A> problem ) {
		return solve(problem, UNLIMITED_DEPTH);
	}

	/**
	 * Solves a search problem.
	 * 
	 * @param problem The problem to be solved.
	 * @param maxDepth The maximum depth of nodes to consider (UNLIMITED_DEPTH if none).
	 * @return A sequence of actions that solves the problem, or null if none exists.
	 */
	public ArrayList<A> solve(SearchProblem<S, A> problem, int maxDepth) {
		ArrayList<A> solution = null;

		initializeFrontier(problem);
		initializeExplored();
		numExpandedNodes = 0;

		boolean done = false;
		while (!isFrontierEmpty() && !done) {
			SearchNode<S, A> node = selectNode(problem);
			if (problem.meetsGoal(node.getState())) {
				solution = generateSolution(node);
				done = true;
			} else {
				addToExplored(node);
				if(maxDepth == UNLIMITED_DEPTH  || node.getDepth() < maxDepth) {
					expandNode(problem, node);
				}
			}
		}

		return solution;
	}
	
	/**
	 * Gets the sequence of actions that leads from the root node to a goal node.
	 * 
	 * @param goalNode A node that satisfies the goal.
	 * @return The sequence of actions from the root node to the goal node.
	 */
	public ArrayList<A> generateSolution(SearchNode<S, A> goalNode) {
		ArrayList<A> solution = new ArrayList<>();
		SearchNode<S, A> currentNode = goalNode;
		while (currentNode.getParent() != null) {
			solution.add(0, currentNode.getAction());
			currentNode = currentNode.getParent();
		}
		return solution;
	}
	
	/**
	 * Expands a node, adding its children to the frontier (if appropriate).
	 * 
	 * @param problem The problem that is being solved.
	 * @param node The node to expand.
	 */
	public void expandNode(SearchProblem<S, A> problem, SearchNode<S, A> node) {
		numExpandedNodes++;
		S currentState = node.getState();
		Set<A> actions = problem.getActions(currentState);
		for (A action : actions) {
			S nextState = problem.getResultingState(action, currentState);
			SearchNode<S, A> child = new SearchNode<>(node,
					node.getPathCost() + problem.getStepCost(action, currentState),
					nextState, action);
			processChild(child);
		}		
	}
	
	/**
	 * Gets the number of nodes that were expanded for the previous solution.
	 * 
	 * @return The number of nodes that were expanded for the previous solution.
	 */
	@Override
	public int getNumExpandedNodes() {
		return numExpandedNodes;
	}

	/**
	 * Initializes the frontier data structure to contain a single node based on the initial state. 
	 * 
	 * @param problem The problem being solved.
	 */
	public abstract void initializeFrontier(SearchProblem<S, A> problem);
	
	/**
	 * Initializes the explored data structure (if any) to be empty.
	 */
	public abstract void initializeExplored();

	/**
	 * Gets whether or not the frontier is empty.
	 * 
	 * @return Whether or not the frontier is empty.
	 */
	public abstract boolean isFrontierEmpty();

	/**
	 * Removes a node from the frontier and returns it.
	 * 
	 * @param problem The problem that is being solved.
	 * @return A node that used to be in the frontier.
	 */
	public abstract SearchNode<S, A> selectNode(SearchProblem<S, A> problem);
	
	/**
	 * Processes a child node, either adding it to the frontier or discarding it.
	 * 
	 * @param node The node to process.
	 */
	public abstract void processChild(SearchNode<S, A> node);

	/**
	 * Adds a node to the frontier.
	 * 
	 * @param node The node to add.
	 */
	public abstract void addToFrontier(SearchNode<S, A> node);
	
	/**
	 * Adds a node to the explored set.
	 * 
	 * @param node The node to add.
	 */
	public abstract void addToExplored(SearchNode<S, A> node);
	
}
