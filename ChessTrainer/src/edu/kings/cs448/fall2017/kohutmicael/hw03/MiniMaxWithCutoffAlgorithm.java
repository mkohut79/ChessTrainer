package edu.kings.cs448.fall2017.kohutmicael.hw03;

import java.util.Set;

import edu.kings.cs448.fall2017.base.games.GamePlayer;
import edu.kings.cs448.fall2017.base.games.StrategyAlgorithm;
import edu.kings.cs448.fall2017.base.games.StrategyGame;

/**
 * Algorithm class similar to mini max although this one allows for a maximum
 * exploration depth.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 *            The generic state.
 * @param <A>
 *            The generic action.
 */
public class MiniMaxWithCutoffAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/**
	 * Field representing the maximumDepth explored.
	 */
	private int maximumDepth;

	/**
	 * Number of nodes expanded.
	 */
	private int count;

	/**
	 * Constructor initializing what the maximumDepth should be.
	 * 
	 * @param maxDepth
	 *            The maxDepth value.
	 */
	public MiniMaxWithCutoffAlgorithm(int maxDepth) {
		maximumDepth = maxDepth;

	}

	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		count = 0;
		A result = null;
		int max = 0;
		int depth = 1;
		Set<A> actions = game.getActions(state);
		for (A action : actions) {
			if (result == null) {
				result = action;
				if (game.getPlayer(state) == GamePlayer.MAX) {
					max = minValue(game, game.getResultingState(result, state), depth);
				} else {
					max = maxValue(game, game.getResultingState(result, state), depth);
				}
			} else {
				if (game.getPlayer(state) == GamePlayer.MAX) {
					int current = minValue(game, game.getResultingState(action, state), depth);
					if (current > max) {
						result = action;
						max = current;
					}
				} else {
					int current = maxValue(game, game.getResultingState(action, state), depth);
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
	 * @return The Minimax value of that state.
	 */
	private int minValue(StrategyGame<S, A> game, S state, int depth) {
		count++;
		int result = 0;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else if (depth == maximumDepth) {
			result = game.evaluateState(state);
		} else {
			result = Integer.MAX_VALUE;
			Set<A> actions = game.getActions(state);
			for (A action : actions) {
				result = Math.min(result, maxValue(game, game.getResultingState(action, state), depth + 1));
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
	 * @return The Minimax value of that state.
	 */
	private int maxValue(StrategyGame<S, A> game, S state, int depth) {
		count++;
		int result = 0;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else if (depth == maximumDepth) {
			result = game.evaluateState(state);
		} else {
			result = Integer.MIN_VALUE;
			Set<A> actions = game.getActions(state);
			for (A action : actions) {
				result = Math.max(result, minValue(game, game.getResultingState(action, state), depth + 1));
			}
		}
		return result;

	}

	@Override
	public int getStateCount() {
		return count;
	}

}
