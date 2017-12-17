package edu.kings.cs448.fall2017.kohutmicael.hw03;

import java.util.Iterator;
import java.util.Set;

import edu.kings.cs448.fall2017.base.games.GamePlayer;
import edu.kings.cs448.fall2017.base.games.StrategyAlgorithm;
import edu.kings.cs448.fall2017.base.games.StrategyGame;

/**
 * Class to represent the alpha beta pruning algorithm which is a shortened
 * version of minimax.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 * @param <A>
 *            The generic action.
 */
public class AlphaBetaAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/**
	 * Field representing the maximumDepth explored.
	 */
	private int maximumDepth;

	/**
	 * Number of nodes expanded.
	 */
	private int count;

	/**
	 * Boolean used to determine whether the last call to nextAction was cut off
	 * due to reaching the maximum depth.
	 */
	private boolean prevCutOff;

	/**
	 * Constructor initializing what the maximumDepth should be.
	 * 
	 * @param maxDepth
	 *            The maxDepth value.
	 */
	public AlphaBetaAlgorithm(int maxDepth) {
		maximumDepth = maxDepth;
	}

	/**
	 * Setter for the maximum amount of depth used when searching for the
	 * children.
	 * 
	 * @param maximumDepth
	 *            The maximum depth used in restricting when serching.
	 */
	public void setMaximumDepth(int maximumDepth) {
		this.maximumDepth = maximumDepth;
	}

	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		count = 0;
		A result = null;
		int max = 0;
		int depth = 1;
		prevCutOff = false;
		Set<A> actions = game.getActions(state);
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		for (A action : actions) {
			if (result == null) {
				result = action;
				if (game.getPlayer(state) == GamePlayer.MAX) {
					max = minValue(game, game.getResultingState(result, state), depth, alpha, beta);
				} else {
					max = maxValue(game, game.getResultingState(result, state), depth, alpha, beta);
				}
			} else {
				if (game.getPlayer(state) == GamePlayer.MAX) {
					int current = minValue(game, game.getResultingState(action, state), depth, alpha, beta);
					if (current > max) {
						result = action;
						max = current;
					}
				} else {
					int current = maxValue(game, game.getResultingState(action, state), depth, alpha, beta);
					if (current < max) {
						result = action;
						max = current;
					}
				}
			}
		}

		return result;

	}

	/**
	 * Computes the Minimax value of a state, assuming that it is Player.MIN's
	 * turn.
	 * 
	 * @param game
	 *            The game being played.
	 * @param state
	 *            The state in question.
	 * @param depth
	 *            The currentDepth that represents how deep has been explored.
	 * @param alpha
	 *            The current alpha value.
	 * @param beta
	 *            The current beta value.
	 * @return The Minimax value of that state.
	 */
	private int minValue(StrategyGame<S, A> game, S state, int depth, int alpha, int beta) {
		count++;
		int result = 0;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else if (depth == maximumDepth) {
			result = game.evaluateState(state);
			prevCutOff = true;
		} else {
			result = Integer.MAX_VALUE;
			Set<A> actions = game.getActions(state);
			Iterator<A> actionIt = actions.iterator();
			boolean done = false;
			while (actionIt.hasNext() && !done) {
				A action = actionIt.next();
				result = Math.min(result,
						maxValue(game, game.getResultingState(action, state), depth + 1, alpha, beta));
				if (result <= alpha) {
					done = true;
				} else {
					beta = Math.min(beta, result);
				}

			}
		}

		return result;

	}

	/**
	 * Computes the Minimax value of a state, assuming that it is Player.MAX's
	 * turn.
	 * 
	 * @param game
	 *            The game being played.
	 * @param state
	 *            The state in question.
	 * @param depth
	 *            The currentDepth that represents how deep has been explored.
	 * @param alpha
	 *            The current alpha value.
	 * @param beta
	 *            The current beta value.
	 * @return The Minimax value of that state.
	 */
	private int maxValue(StrategyGame<S, A> game, S state, int depth, int alpha, int beta) {
		count++;
		int result = 0;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else if (depth == maximumDepth) {
			result = game.evaluateState(state);
			prevCutOff = true;
		} else {
			result = Integer.MIN_VALUE;
			Set<A> actions = game.getActions(state);
			Iterator<A> actionIt = actions.iterator();
			boolean done = false;
			while (actionIt.hasNext() && !done) {
				A action = actionIt.next();
				result = Math.max(result,
						minValue(game, game.getResultingState(action, state), depth + 1, alpha, beta));
				if (result >= beta) {
					done = true;
				} else {
					alpha = Math.max(alpha, result);
				}

			}
		}
		return result;

	}

	@Override
	public int getStateCount() {
		return count;
	}

	/**
	 * Method to determine whether the previous call to calculate the next
	 * action was cut off early due to hitting the maximum depth.
	 * 
	 * @return prevCutOff True if the previous call was cutt off for the depth
	 *         and false otherwise.
	 */
	public boolean prevCalCuttOff() {
		return prevCutOff;
	}

}
