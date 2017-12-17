package edu.kings.cs448.fall2017.kohutmichael.search;

import java.util.Comparator;

import edu.kings.cs448.fall2017.base.search.SearchNode;

/**
 * Comparator used to store the searchNodes in greatest to least depth order in
 * the priorityQueue.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class PathAndHeurComp<S, A> implements Comparator<SearchNode<S, A>> {

	/**
	 * Empty instance of an eightPuzzleProblem used to get the heuristic.
	 */
	private EightPuzzleProblem eightProblem;

	/**
	 * Constructor instantiating an empty EightPuzzleProblem.
	 */
	public PathAndHeurComp() {
		eightProblem = new EightPuzzleProblem(null, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public int compare(SearchNode<S, A> o1, SearchNode<S, A> o2) {
		int h1 = eightProblem.heuristicForState((EightPuzzleState) o1.getState());
		int h2 = eightProblem.heuristicForState((EightPuzzleState) o2.getState());
		int path1 = o1.getPathCost();
		int path2 = o2.getPathCost();
		int firstSum = h1 + path1;
		int secondSum = h2 + path2;
		int result = 0;
		if (firstSum < secondSum) {
			result = -1;
		} else if (firstSum > secondSum) {
			result = 1;
		}
		return result;
	}

}
