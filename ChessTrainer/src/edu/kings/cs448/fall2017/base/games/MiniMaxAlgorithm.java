package edu.kings.cs448.fall2017.base.games;

import java.util.Set;

/**
 * A class that plays games by choosing their actions through the Minimax strategy.
 * 
 * @author CS448
 * @version 2017
 * @param <S> The type of states in the game.
 * @param <A> The type of actions in the game.
 */
public class MiniMaxAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/** The number of states expanded the last time we chose an action. */
	private int count;

	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		count = 0;
		A result = null;
		int max = 0;
		Set<A> actions = game.getActions(state);
		for (A action : actions) {
			if (result == null) {
				result = action;
				if (game.getPlayer(state) == GamePlayer.MAX) {
					max = minValue(game, game.getResultingState(result, state));
				} else {
					max = maxValue(game, game.getResultingState(result, state));
				}
			} else {
				if (game.getPlayer(state) == GamePlayer.MAX) {
					int current = minValue(game,
							game.getResultingState(action, state));
					if (current > max) {
						result = action;
						max = current;
					}
				}
				else{
					int current = maxValue(game,
							game.getResultingState(action, state));
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
	 * Computes the Minimax value of a state, assuming that it is Player.MIN's turn.
	 * 
	 * @param game The game being played.
	 * @param state The state in question.
	 * @return The Minimax value of that state.
	 */
	private int minValue(StrategyGame<S, A> game, S state) {
		int result = 0;
		count++;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else {
			result = Integer.MAX_VALUE;
			Set<A> actions = game.getActions(state);
			for (A action : actions) {
				result = Math.min(result,
						maxValue(game, game.getResultingState(action, state)));
			}
		}

		return result;

	}

	/**
	 * Computes the Minimax value of a state, assuming that it is Player.MAX's turn.
	 * 
	 * @param game The game being played.
	 * @param state The state in question.
	 * @return The Minimax value of that state.
	 */
	private int maxValue(StrategyGame<S, A> game, S state) {
		count++;
		int result = 0;
		if (game.isTerminalState(state)) {
			result = game.getUtility(state);
		} else {
			result = Integer.MIN_VALUE;
			Set<A> actions = game.getActions(state);
			for (A action : actions) {
				result = Math.max(result,
						minValue(game, game.getResultingState(action, state)));
			}
		}
		return result;

	}

	@Override
	public int getStateCount() {
		return count;
	}

}
