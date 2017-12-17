package edu.kings.cs448.fall2017.base.search;

/**
 * A node in a search tree / graph.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S>
 *            The type of actions in the problem domain being used.
 * @param <A>
 *            The type of states in the problem domain being used.
 */
public class SearchNode<S, A> implements Comparable<SearchNode<S, A>> {

	/** The node from which this one was reached. */
	private SearchNode<S, A> parent;

	/** The total cost to get to this node from the root. */
	private int pathCost;

	/** The state associated with this node. */
	private S state;

	/** The action that led from the parent to this node. */
	private A action;

	/**
	 * Constructs a new SearchNode.
	 * 
	 * @param parent
	 *            The parent of the new node.
	 * @param pathCost
	 *            The total cost to get to the new node.
	 * @param state
	 *            The state associated with the new node.
	 * @param action
	 *            The action that led to the new node.
	 */
	public SearchNode(SearchNode<S, A> parent, int pathCost, S state, A action) {
		this.parent = parent;
		this.pathCost = pathCost;
		this.state = state;
		this.action = action;
	}

	/**
	 * Gets the parent of this node.
	 * 
	 * @return The parent of this node.
	 */
	public SearchNode<S, A> getParent() {
		return parent;
	}

	/**
	 * Sets the parent of this node.
	 * 
	 * @param parent
	 *            The new parent of this node.
	 */
	public void setParent(SearchNode<S, A> parent) {
		this.parent = parent;
	}

	/**
	 * Gets the total cost to reach this node.
	 * 
	 * @return The total cost.
	 */
	public int getPathCost() {
		return pathCost;
	}

	/**
	 * Sets the total cost to reach this node.
	 * 
	 * @param pathCost
	 *            The new cost to get to this node.
	 */
	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	/**
	 * Gets the state associated with this node.
	 * 
	 * @return The state.
	 */
	public S getState() {
		return state;
	}

	/**
	 * Gets the action that led to this node.
	 * 
	 * @return The action.
	 */
	public A getAction() {
		return action;
	}

	/**
	 * Sets the action that led to this node.
	 * 
	 * @param action
	 *            The action.
	 */
	public void setAction(A action) {
		this.action = action;
	}

	/**
	 * Returns a string representation of this SearchNode.
	 * 
	 * @return A string representation of this SearchNode.
	 */
	@Override
	public String toString() {
		return "State: " + state + " Action: " + action + " Path Cost: " + pathCost + "Parent:\n" + parent;
	}

	/**
	 * Tests whether or not this SearchNode is equivalent to another object.
	 * 
	 * @param o
	 *            The other object.
	 * @return Whether or not this is equivalent to the other object.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null) {
			return false;
		} else if (o instanceof SearchNode<?, ?>) {
			SearchNode<?, ?> temp = (SearchNode<?, ?>) o;
			return (parent.equals(temp.parent) && pathCost == temp.pathCost && state.equals(temp.state)
					&& action.equals(temp.action));
		} else {
			return false;
		}
	}

	/**
	 * Computes a hash code for this SearchNode.
	 * 
	 * @return A hash code for this SearchNode.
	 */
	@Override
	public int hashCode() {
		// Only this because other fields could be modified after you hash this.
		return state.hashCode();
	}

	/**
	 * Gets the length of the path from the root node to this node.
	 * 
	 * @return The length of the path from the root node to this node.
	 */
	public int getDepth() {
		int depth = 0;
		SearchNode<S, A> ancestor = this;
		while (ancestor.parent != null) {
			depth++;
			ancestor = ancestor.parent;
		}
		return depth;
	}

	@Override
	public int compareTo(SearchNode<S, A> o) {
		int otherDepth = o.getDepth();
		int currentDepth = this.getDepth();
		int result = 0;
		if (otherDepth < currentDepth) {
			result = 1;
		} else if (otherDepth > currentDepth) {
			result = -1;
		}
		return result;
	}

}
