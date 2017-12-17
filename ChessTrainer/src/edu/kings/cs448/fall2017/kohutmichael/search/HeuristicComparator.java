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
public class HeuristicComparator<S, A> implements Comparator<SearchNode<S, A>> {

	/**
	 * Empty instance of an eightPuzzleProblem used to get the heuristic.
	 */
	private EightPuzzleProblem eightProblem;

	/**
	 * Constructor instantiating an empty EightPuzzleProblem.
	 */
	public HeuristicComparator() {
		eightProblem = new EightPuzzleProblem(null, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public int compare(SearchNode<S, A> o1, SearchNode<S, A> o2) {
		int h1 = eightProblem.heuristicForState((EightPuzzleState) o1.getState());
		int h2 = eightProblem.heuristicForState((EightPuzzleState) o2.getState());
		int result = 0;
		if (h1 < h2) {
			result = -1;
		}
		else if (h1 > h2) {
			result = 1;
		}
		return result;
	}

}
