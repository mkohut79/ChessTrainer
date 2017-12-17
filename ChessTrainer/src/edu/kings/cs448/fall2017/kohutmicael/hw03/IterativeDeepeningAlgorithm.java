package edu.kings.cs448.fall2017.kohutmicael.hw03;

import edu.kings.cs448.fall2017.base.games.StrategyAlgorithm;
import edu.kings.cs448.fall2017.base.games.StrategyGame;

/**
 * Class to represent the iterative deepeneing algorithm.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 * @param <A>
 *            The generic action.
 */
public class IterativeDeepeningAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/**
	 * Field to represent the maximum amount of seconds to run the algorithm.
	 */
	private double maxSeconds;

	/**
	 * Field to represent how many nodes were evaluated in the calculateion.
	 */
	private int count = 0;

	/**
	 * Instance of the alphabeta algorithm to keep track of calculating the next
	 * step.
	 */
	private AlphaBetaAlgorithm<S, A> abAlgorithm;

	/**
	 * Field keepingtrack of how deep to check with the alphabetaAlgorithm.
	 */
	private int depth;

	/**
	 * Constructor initializing the maximum depth and seconds to spend selected
	 * by the user.
	 * 
	 * @param seconds
	 *            The maximum number of second spent on solcing the problem.
	 */
	public IterativeDeepeningAlgorithm(double seconds) {
		maxSeconds = seconds;
		depth = 1;
		abAlgorithm = new AlphaBetaAlgorithm<S, A>(depth);

	}

	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		count = 0;
		A result = null;
		long currentTime = System.currentTimeMillis();
		boolean done = false;
		long timeToUse = (long) maxSeconds / 2;
		long timeToStop = currentTime + timeToUse;
		while (currentTime < timeToStop && !done) {
			result = abAlgorithm.nextAction(game, state);
			count += abAlgorithm.getStateCount();
			done = !abAlgorithm.prevCalCuttOff();
			depth++;
			abAlgorithm.setMaximumDepth(depth);
			currentTime = System.currentTimeMillis();
		}

		return result;

	}

	@Override
	public int getStateCount() {
		return count;
	}

}
