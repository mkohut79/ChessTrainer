package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.Comparator;

import edu.kings.cs448.fall2017.base.search.SearchNode;

/**
 * Comparator used to select the nodes based on the lowest path cost.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class UniformCostPriority<S, A> implements Comparator<SearchNode<S, A>> {

	@Override
	public int compare(SearchNode<S, A> o1, SearchNode<S, A> o2) {
		int path1 = o1.getPathCost();
		int path2 = o2.getPathCost();
		int result = 0;
		if (path1 < path2) {
			result = -1;
		} else if (path1 > path2) {
			result = 1;
		}
		return result;
	}

}
