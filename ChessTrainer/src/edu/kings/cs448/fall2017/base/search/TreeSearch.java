package edu.kings.cs448.fall2017.base.search;

/**
 * An algorithm that solves problems by searching through a tree.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem to solve.
 * @param <A> The type of actions in the problem to solve.
 */
public abstract class TreeSearch<S, A> extends SearchAlgorithm<S, A> {

	@Override
	public void initializeExplored() {
		// Tree search does not have an explored set, so do nothing at all.
	}

	@Override
	public void processChild(SearchNode<S, A> node) {
		// In tree search we always add the child to the frontier, no matter what.
		addToFrontier(node);
	}

	@Override
	public void addToExplored(SearchNode<S, A> node) {
		// Tree search does not have an explored set, so do nothing at all.
	}


}
