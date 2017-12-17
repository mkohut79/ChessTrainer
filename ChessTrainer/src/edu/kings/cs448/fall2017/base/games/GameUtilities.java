package edu.kings.cs448.fall2017.base.games;

import java.util.Scanner;

import edu.kings.cs448.fall2017.kohutmicael.hw03.AlphaBetaAlgorithm;
import edu.kings.cs448.fall2017.kohutmicael.hw03.HumanAlgorithm;
import edu.kings.cs448.fall2017.kohutmicael.hw03.IterativeDeepeningAlgorithm;
import edu.kings.cs448.fall2017.kohutmicael.hw03.MiniMaxWithCutoffAlgorithm;

/**
 * A collection of static methods that will make it easier to write code for
 * playing strategy games.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class GameUtilities {

	/**
	 * Allows the user to choose one of the available game playing algorithms by
	 * name.
	 * 
	 * @param <S>
	 *            The type of states in the game.
	 * @param <A>
	 *            The type of actions in the game.
	 * @param playerNumber
	 *            The number of the player whose algorithm should be chosen.
	 * @return The chosen algorithm.
	 */
	public static <S, A> StrategyAlgorithm<S, A> chooseAlgorithm(int playerNumber) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		StrategyAlgorithm<S, A> algorithm = null;

		while (algorithm == null) {
			System.out.println("Possible search algorithms:");

			// TODO: Add additional strings here.
			System.out.println("\tRandom");
			System.out.println("\tMiniMax");
			System.out.println("\tHuman");
			System.out.println("\tMiniMaxWithCutoff");
			System.out.println("\tAlphaBeta");
			System.out.println("\tIterativeDeepening");
			System.out.print("Choose an algorithm for player " + playerNumber + ": ");
			String choice = input.next();
			if (choice.equalsIgnoreCase("Random")) {
				algorithm = new RandomAlgorithm<S, A>();
			} else if (choice.equalsIgnoreCase("MiniMax")) {
				algorithm = new MiniMaxAlgorithm<S, A>();
			} else if (choice.equalsIgnoreCase("Human")) {
				algorithm = new HumanAlgorithm<S, A>();
			} else if (choice.equalsIgnoreCase("MiniMaxWithCutoff")) {
				System.out.println("What depth would you like to set as the max?");
				int depth = input.nextInt();
				algorithm = new MiniMaxWithCutoffAlgorithm<S, A>(depth);
			} else if (choice.equalsIgnoreCase("AlphaBeta")) {
				System.out.println("What depth would you like to set as the max?");
				int depth = input.nextInt();
				algorithm = new AlphaBetaAlgorithm<S, A>(depth);
			}
			else if (choice.equalsIgnoreCase("IterativeDeepening")) {
				System.out.println("What would you like to set as the maximum amount of seconds?");
				int seconds = input.nextInt();
				algorithm = new IterativeDeepeningAlgorithm<S, A>(seconds);
			}

			
			// TODO: Add additional cases here.

			if (algorithm == null) {
				System.out.println("That is not a valid algorithm name.");
			}
		}

		return algorithm;
	}

	/**
	 * Plays a pure strategy game.
	 * 
	 * @param <S>
	 *            The type of states in the game.
	 * @param <A>
	 *            The type of actions in the game.
	 * @param game
	 *            The game to play.
	 * @param algorithm1
	 *            The algorithm to use for the first player.
	 * @param algorithm2
	 *            The algorithm to use for the second player.
	 * @return The final state of the game.
	 */
	public static <S, A> S playGame(StrategyGame<S, A> game, StrategyAlgorithm<S, A> algorithm1,
			StrategyAlgorithm<S, A> algorithm2) {
		S theState = game.getInitialState();
		A chosenAction = null;
		int stateCount = 0;
		System.out.println("\n" + theState);
		while (!game.isTerminalState(theState)) {
			if (game.getPlayer(theState) == GamePlayer.MAX) {
				chosenAction = algorithm1.nextAction(game, theState);
				stateCount = algorithm1.getStateCount();
			} else {
				chosenAction = algorithm2.nextAction(game, theState);
				stateCount = algorithm2.getStateCount();
			}
			System.out.println("Player " + game.getPlayer(theState).getName() + " chooses action " + chosenAction
					+ " after exploring " + stateCount + " states.");
			theState = game.getResultingState(chosenAction, theState);
			System.out.println("\n" + theState);
		}
		return theState;

	}

}
