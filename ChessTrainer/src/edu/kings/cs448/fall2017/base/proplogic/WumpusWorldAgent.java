package edu.kings.cs448.fall2017.base.proplogic;

import java.util.Scanner;

/**
 * A class that allows us to work with a knowledge base representing the Wumpus
 * World.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class WumpusWorldAgent {

	/** The width (and height) of the world. */
	public static final int GRID_SIZE = 4;

	/**
	 * The main method for playing the Wumpus World game.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Conjunction knowledgeBase = getInitialKnowledgeBase();
		EntailmentChecker algorithm = new DPLLAlgorithm();
		Sentence playerDeadSentence = new Proposition(buildPlayerDeadString());

		System.out.println("Welcome to the Wumpus World!");

		boolean isPlayerDead = false;
		boolean didPlayerQuit = false;
		while (!isPlayerDead && !didPlayerQuit) {

			for (int i = 0; i < GRID_SIZE; i++) {
				for (int j = 0; j < GRID_SIZE; j++) {
					printKnowledgeAboutSquare(algorithm, knowledgeBase, i, j);
				}
			}
			String typed;
			System.out.print("Did you quit the game? (y/n): ");
			typed = input.next();
			if (typed.equals("y")) {
				didPlayerQuit = true;
			} else {
				System.out.print("Which column are you standing in?: ");
				int column = input.nextInt();
				while (column < 0 || column > GRID_SIZE - 1) {
					System.out.println("Please try again.  Column must be in the range (" + 0 + ", " + (GRID_SIZE - 1)
							+ ") inclusive: ");
					column = input.nextInt();
				}
				System.out.print("Which row are you standing in?: ");
				int row = input.nextInt();
				while (row < 0 || row > GRID_SIZE - 1) {
					System.out.println("Please try again.  Row must be in the range (" + 0 + ", " + (GRID_SIZE - 1)
							+ ") inclusive: ");
					row = input.nextInt();
				}
				knowledgeBase.addSentence(new Proposition(buildVisitedString(column, row)));
				System.out.print("Have you fallen into a pit? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildPitAtString(column, row)));
				} else {
					knowledgeBase.addSentence(new Negation(new Proposition(buildPitAtString(column, row))));
				}
				System.out.print("Is there a terrible wumpus in the room with you? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildWumpusAtString(column, row)));
					if (!algorithm.entails(knowledgeBase, new Proposition(buildWumpusDeadString()))) {
						knowledgeBase.addSentence(new Proposition(buildPlayerDeadString()));
					}
				} else {
					knowledgeBase.addSentence(new Negation(new Proposition(buildWumpusAtString(column, row))));
				}
				System.out.print("Do you feel a cool breeze? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildBreezyAtString(column, row)));
				} else {
					knowledgeBase.addSentence(new Negation(new Proposition(buildBreezyAtString(column, row))));
				}
				System.out.print("Do you smell an awful stench? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildSmellyAtString(column, row)));
				} else {
					knowledgeBase.addSentence(new Negation(new Proposition(buildSmellyAtString(column, row))));
				}
				System.out.print("Do you see a beautiful glitter? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildPlayerHasGoldString()));
					System.out.println("Awesome!  We will assume you are smart enough to pick up the gold.");
				}
				System.out.print("Do you hear a piercing scream? (y/n): ");
				if (input.next().equals("y")) {
					knowledgeBase.addSentence(new Proposition(buildWumpusDeadString()));
					System.out.println("That means you have killed the wumpus!");
				}
			}

			isPlayerDead = algorithm.entails(knowledgeBase, playerDeadSentence);
		}

		if (isPlayerDead) {
			System.out.println("You appear to have died.  Better luck next time.");
		} else if (algorithm.entails(knowledgeBase, new Proposition(buildPlayerHasGoldString()))) {
			System.out.println("You escaped with the gold!  Congratulations!");
		} else {
			System.out.println("You didn't find the gold, but at least you got out with your life.");
		}
	}

	/**
	 * Prints the important information that we know about a square.
	 * 
	 * @param algorithm
	 *            An algorithm to use for checking entailment.
	 * @param knowledgeBase
	 *            That which we know for sure.
	 * @param column
	 *            The column.
	 * @param row
	 *            The row.
	 */
	public static void printKnowledgeAboutSquare(EntailmentChecker algorithm, Sentence knowledgeBase, int column,
			int row) {
		if (algorithm.entails(knowledgeBase, new Proposition(buildVisitedString(column, row)))) {
			System.out.println("You visited (" + column + ", " + row + ").");
		} else if (algorithm.entails(knowledgeBase, new Proposition(buildSafeToEnterString(column, row)))) {
			System.out.println("It is safe to enter (" + column + ", " + row + ").");
		} else {
			if (algorithm.entails(knowledgeBase, new Proposition(buildPitAtString(column, row)))) {
				System.out.println("There is a pit in (" + column + ", " + row + ")!");
			}
			if (algorithm.entails(knowledgeBase, new Proposition(buildWumpusAtString(column, row)))) {
				System.out.println("There is a wumpus in (" + column + ", " + row + ")!");
			}
		}
	}

	/**
	 * Gets all of the knowledge rules that are true in every instance of Wumpus
	 * World, before the player has observed anything.
	 * 
	 * @return An initial knowledge base for the Wumpus World.
	 */
	public static Conjunction getInitialKnowledgeBase() {
		Conjunction knowledgeBase = new Conjunction();

		// There can only be one wumpus.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				for (int k = 0; k < GRID_SIZE; k++) {
					for (int l = 0; l < GRID_SIZE; l++) {
						if (i != k && j != l) {
							knowledgeBase.addSentence(Sentence.parse("( " + buildWumpusAtString(i, j) + " --> ! ( "
									+ buildWumpusAtString(k, l) + " ) )"));
						}
					}
				}
			}
		}

		// A wumpus creates stench in its own and all adjacent squares.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Conjunction smellyPlaces = new Conjunction();
				smellyPlaces.addSentence(new Proposition(buildSmellyAtString(i, j)));
				if (i > 0) {
					smellyPlaces.addSentence(new Proposition(buildSmellyAtString(i - 1, j)));
				}
				if (i < GRID_SIZE - 1) {
					smellyPlaces.addSentence(new Proposition(buildSmellyAtString(i + 1, j)));
				}
				if (j > 0) {
					smellyPlaces.addSentence(new Proposition(buildSmellyAtString(i, j - 1)));
				}
				if (j < GRID_SIZE - 1) {
					smellyPlaces.addSentence(new Proposition(buildSmellyAtString(i, j + 1)));
				}

				knowledgeBase.addSentence(new Biconditional(new Proposition(buildWumpusAtString(i, j)), smellyPlaces));
			}
		}

		// A pit creates breeze in its own and all adjacent squares.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Conjunction breezyPlaces = new Conjunction();
				breezyPlaces.addSentence(new Proposition(buildBreezyAtString(i, j)));
				if (i > 0) {
					breezyPlaces.addSentence(new Proposition(buildBreezyAtString(i - 1, j)));
				}
				if (i < GRID_SIZE - 1) {
					breezyPlaces.addSentence(new Proposition(buildBreezyAtString(i + 1, j)));
				}
				if (j > 0) {
					breezyPlaces.addSentence(new Proposition(buildBreezyAtString(i, j - 1)));
				}
				if (j < GRID_SIZE - 1) {
					breezyPlaces.addSentence(new Proposition(buildBreezyAtString(i, j + 1)));
				}
				knowledgeBase.addSentence(new Biconditional(new Proposition(buildPitAtString(i, j)), breezyPlaces));
			}
		}

		// If you have visited a non-smelly square, neither it nor any adjacent
		// squares have a wumpus.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Conjunction antecedent = new Conjunction();
				antecedent.addSentence(new Proposition(buildVisitedString(i, j)));
				antecedent.addSentence(new Negation(new Proposition(buildSmellyAtString(i, j))));
				Conjunction wumpusFreeSpots = new Conjunction();
				wumpusFreeSpots.addSentence(new Negation(new Proposition(buildWumpusAtString(i, j))));
				if (i > 0) {
					wumpusFreeSpots.addSentence(new Negation(new Proposition(buildWumpusAtString(i - 1, j))));
				}
				if (i < GRID_SIZE - 1) {
					wumpusFreeSpots.addSentence(new Negation(new Proposition(buildWumpusAtString(i + 1, j))));
				}
				if (j > 0) {
					wumpusFreeSpots.addSentence(new Negation(new Proposition(buildWumpusAtString(i, j - 1))));
				}
				if (j < GRID_SIZE - 1) {
					wumpusFreeSpots.addSentence(new Negation(new Proposition(buildWumpusAtString(i, j + 1))));
				}
				knowledgeBase.addSentence(new Implication(antecedent, wumpusFreeSpots));
			}
		}

		// If you have visited a non-breezy square, neither it nor any adjacent
		// squares have a pit.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Conjunction antecedent = new Conjunction();
				antecedent.addSentence(new Proposition(buildVisitedString(i, j)));
				antecedent.addSentence(new Negation(new Proposition(buildBreezyAtString(i, j))));
				Conjunction pitFreeSpots = new Conjunction();
				pitFreeSpots.addSentence(new Negation(new Proposition(buildPitAtString(i, j))));
				if (i > 0) {
					pitFreeSpots.addSentence(new Negation(new Proposition(buildPitAtString(i - 1, j))));
				}
				if (i < GRID_SIZE - 1) {
					pitFreeSpots.addSentence(new Negation(new Proposition(buildPitAtString(i + 1, j))));
				}
				if (j > 0) {
					pitFreeSpots.addSentence(new Negation(new Proposition(buildPitAtString(i, j - 1))));
				}
				if (j < GRID_SIZE - 1) {
					pitFreeSpots.addSentence(new Negation(new Proposition(buildPitAtString(i, j + 1))));
				}
				knowledgeBase.addSentence(new Implication(antecedent, pitFreeSpots));
			}
		}

		// If a square is breezy, then at least one adjacent square or it is a
		// pit.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Sentence antecedent = new Proposition(buildBreezyAtString(i, j));
				Disjunction possiblePits = new Disjunction();
				possiblePits.addSentence(new Proposition(buildPitAtString(i, j)));
				if (i > 0) {
					possiblePits.addSentence(new Proposition(buildPitAtString(i - 1, j)));
				}
				if (i < GRID_SIZE - 1) {
					possiblePits.addSentence(new Proposition(buildPitAtString(i + 1, j)));
				}
				if (j > 0) {
					possiblePits.addSentence(new Proposition(buildPitAtString(i, j - 1)));
				}
				if (j < GRID_SIZE - 1) {
					possiblePits.addSentence(new Proposition(buildPitAtString(i, j + 1)));
				}
				knowledgeBase.addSentence(new Implication(antecedent, possiblePits));
			}
		}

		// If a square is smelly, then at least one adjacent square or it
		// contains the wumpus.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Sentence antecedent = new Proposition(buildSmellyAtString(i, j));
				Disjunction possibleWumpuses = new Disjunction();
				possibleWumpuses.addSentence(new Proposition(buildWumpusAtString(i, j)));
				if (i > 0) {
					possibleWumpuses.addSentence(new Proposition(buildWumpusAtString(i - 1, j)));
				}
				if (i < GRID_SIZE - 1) {
					possibleWumpuses.addSentence(new Proposition(buildWumpusAtString(i + 1, j)));
				}
				if (j > 0) {
					possibleWumpuses.addSentence(new Proposition(buildWumpusAtString(i, j - 1)));
				}
				if (j < GRID_SIZE - 1) {
					possibleWumpuses.addSentence(new Proposition(buildWumpusAtString(i, j + 1)));
				}
				knowledgeBase.addSentence(new Implication(antecedent, possibleWumpuses));
			}
		}

		// It is safe to enter a square if it does not have a pit and either
		// does not have a wumpus or the wumpus is dead.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				knowledgeBase.addSentence(Sentence
						.parse("( " + buildSafeToEnterString(i, j) + " <-> ( ! ( " + buildPitAtString(i, j) + " ) && ( "
								+ buildWumpusDeadString() + " || ! ( " + buildWumpusAtString(i, j) + " ) ) ) )"));
			}
		}

		// If you have visited a location with a pit or a live wumpus, you die.
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				knowledgeBase.addSentence(Sentence.parse("( ( " + buildVisitedString(i, j) + " && "
						+ buildPitAtString(i, j) + " ) --> " + buildPlayerDeadString() + " )"));
				knowledgeBase.addSentence(
						Sentence.parse("( ( " + buildVisitedString(i, j) + " && " + buildWumpusAtString(i, j)
								+ " && ! ( " + buildWumpusDeadString() + " ) ) --> " + buildPlayerDeadString() + " )"));
			}
		}

		return knowledgeBase;
	}

	/**
	 * Gets the String for a Proposition saying that the wumpus is at a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildWumpusAtString(int column, int row) {
		return "WumpusAt(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that the wumpus is dead.
	 * 
	 * @return A String for that Proposition.
	 */
	private static String buildWumpusDeadString() {
		return "WumpusDead";
	}

	/**
	 * Gets the String for a Proposition saying that it is smelly at a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildSmellyAtString(int column, int row) {
		return "SmellyAt(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that there is a pit at a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildPitAtString(int column, int row) {
		return "PitAt(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that it is breezy at a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildBreezyAtString(int column, int row) {
		return "BreezyAt(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that you have visited a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildVisitedString(int column, int row) {
		return "Visited(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that it is safe to enter a given
	 * location.
	 * 
	 * @param column
	 *            Which column.
	 * @param row
	 *            Which row.
	 * @return A String for that Proposition.
	 */
	private static String buildSafeToEnterString(int column, int row) {
		return "SafeToEnter(" + column + "," + row + ")";
	}

	/**
	 * Gets the String for a Proposition saying that you have died.
	 * 
	 * @return A String for that Proposition.
	 */
	private static String buildPlayerDeadString() {
		return "PlayerDead";
	}

	/**
	 * Gets the String for a Proposition saying that the player has the gold in
	 * his possession.
	 * 
	 * @return A String for that Proposition.
	 */
	private static String buildPlayerHasGoldString() {
		return "HasGold";
	}

}
