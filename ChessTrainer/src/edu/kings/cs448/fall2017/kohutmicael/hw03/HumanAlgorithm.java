package edu.kings.cs448.fall2017.kohutmicael.hw03;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import edu.kings.cs448.fall2017.base.games.StrategyAlgorithm;
import edu.kings.cs448.fall2017.base.games.StrategyGame;

/**
 * Class that represents the human aproach at playing tic tac toe.
 * 
 * @author Michael Kohut
 *
 * @param <S>
 * @param <A>
 */
public class HumanAlgorithm<S, A> implements StrategyAlgorithm<S, A> {

	/**
	 * Scanner to keep track of the input entered by the user.
	 */
	private Scanner in;

	/**
	 * Mapping of the number of the action to the action itself.
	 */
	private HashMap<Integer, A> actionMap;

	/**
	 * Constructor which initializes the available actions.
	 */
	public HumanAlgorithm() {
		in = new Scanner(System.in);
		actionMap = new HashMap<Integer, A>();
	}

	@Override
	public A nextAction(StrategyGame<S, A> game, S state) {
		System.out.print("Indicate which action you would like to take by entering just the number next to" + "\n");
		System.out.println("the action you would like to choose.");
		Set<A> actions = game.getActions(state);
		int counter = 0;
		for (A action : actions) {
			System.out.println(counter + ". " + action.toString());
			actionMap.put(counter, action);
			counter++;
		}
		int selection = in.nextInt();
		A result = actionMap.get(selection);
		actionMap.clear();

		return result;
	}

	@Override
	public int getStateCount() {
		return 0;
	}

}
