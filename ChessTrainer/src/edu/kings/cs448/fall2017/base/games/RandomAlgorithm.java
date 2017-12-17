package edu.kings.cs448.fall2017.base.games;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * An algorithm that plays a game by always making a randomly-chosen legal move.
 * 
 * @author CS448
 * @version 2017
 * @param <S> The type of states in the game.
 * @param <A> The type of actions in the game.
 */
public class RandomAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/** A random-number generator. */
	private Random rand;
	
	/**
	 * Constructs a new RandomAlgorithm.
	 */
	public RandomAlgorithm() {
		rand = new Random();
	}
	
	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		Set<A> actions = game.getActions(state);
		
		int size = actions.size();		
		int choice = rand.nextInt(size);
		
		ArrayList<A> actionsList = new ArrayList<A>(actions);
		A action = actionsList.get(choice);
		
		return action;
	}

	@Override
	public int getStateCount() {
		return 0;
	}

}
