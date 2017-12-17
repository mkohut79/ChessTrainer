package edu.kings.cs448.fall2017.MichaelKohut.proplogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import edu.kings.cs448.fall2017.base.proplogic.Conjunction;
import edu.kings.cs448.fall2017.base.proplogic.DPLLAlgorithm;
import edu.kings.cs448.fall2017.base.proplogic.Disjunction;
import edu.kings.cs448.fall2017.base.proplogic.EntailmentChecker;
import edu.kings.cs448.fall2017.base.proplogic.Negation;
import edu.kings.cs448.fall2017.base.proplogic.Proposition;
import edu.kings.cs448.fall2017.base.proplogic.Sentence;

/**
 * A class that allows us to interact with a game that represents a game of
 * clue.
 * 
 * @author Michael Kohut
 *
 */
public class ClueAgent {

	/**
	 * Array for all the cards in the game.
	 */
	private static String[] cards = { "Mustard", "Plum", "Green", "Peacock", "Scarlet", "White", "Knife", "Candlestick",
			"Revolver", "Rope", "Pipe", "Wrench", "Hall", "Lounge", "Dining", "Kitchen", "Ballroom", "Conservatory",
			"Billiards", "Library", "Study" };

	/**
	 * All of the locations in the game.
	 */
	private static String[] locations = { "CF", "P1", "P2", "P3", "P4", "P5", "P6" };

	/**
	 * All of the suspects in the game.
	 */
	private static String[] suspects = { "Mustard", "Plum", "Green", "Peacock", "Scarlet", "White" };

	/**
	 * All of the weapons in the game.
	 */
	private static String[] weapons = { "Knife", "Candlestick", "Revolver", "Rope", "Pipe", "Wrench" };

	/**
	 * All of the rooms in the game.
	 */
	private static String[] rooms = { "Hall", "Lounge", "Dining", "Kitchen", "Ballroom", "Consvervatory", "Billiards",
			"Library", "Study" };

	/**
	 * All of the players in the game.
	 */
	private static String[] players = { "P1", "P2", "P3", "P4", "P5", "P6" };

	/**
	 * The main string of arguments to be processed.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Conjunction knowledgeBase = getInitialKnowledgeBase();
		EntailmentChecker algorithm = new DPLLAlgorithm();
		// Sentence playerLostgame = new Proposition(buildPlayerLostString());

		System.out.println("I hear that you are playing a 6-person game of Clue and would like some help. \n"
				+ "I am going to assume that you are the first player (P1). First, I need to know what\n"
				+ "cards you were dealt. Please print 3 of the following, exactly.\n ");

		System.out.println("Mustard, Plum, Green, Peacock, Scarlet, White, Knife, Candlestick, Revolver, \n"
				+ "Rope, Pipe, Wrench, Hall, Lounge, Dining, Kitchen, Ballroom, Conservatory, Billiards, Library, Study");

		String card1 = input.next();
		knowledgeBase.addSentence(new Proposition(cardInLocationString(card1, "P1")));
		String card2 = input.next();
		knowledgeBase.addSentence(new Proposition(cardInLocationString(card2, "P1")));
		String card3 = input.next();
		knowledgeBase.addSentence(new Proposition(cardInLocationString(card3, "P1")));
		for (int i = 0; i < cards.length; i++) {
			if (!cards[i].equals(card1) && !cards[i].equals(card2) && !cards[i].equals(card3)) {
				knowledgeBase.addSentence(new Negation(new Proposition(cardInLocationString(cards[i], "P1"))));
			}
		}

		boolean playerLost = false;
		boolean didPlayerQuit = false;
		ArrayList<String> otherPlayersInIt = new ArrayList<String>();
		otherPlayersInIt.add("P2");
		otherPlayersInIt.add("P3");
		otherPlayersInIt.add("P4");
		otherPlayersInIt.add("P5");
		otherPlayersInIt.add("P6");
		ArrayList<String> losers = new ArrayList<String>();

		while (!playerLost && !didPlayerQuit) {

			for (int i = 0; i < losers.size(); i++) {
				otherPlayersInIt.remove(losers.get(i));
			}

			System.out.println("It's our turn!");
			System.out.println("We get to make a suggestion of a suspect, weapon, and a room.");
			// Information about the suspects
			System.out.println("Here is what we know about the suspect cards.");
			for (int i = 0; i < suspects.length; i++) {
				System.out.print(suspects[i] + " is in one of the following: ");
				for (int j = 0; j < locations.length; j++) {
					printKnowledgeAboutCards(algorithm, knowledgeBase, suspects[i], locations[j]);
				}
				System.out.println();
			}
			System.out.println("Who do you wish to suggest?");
			String suspectSuggestion = input.next();

			// Information about the weapons
			System.out.println("Here is what we know about the weapon cards.");
			for (int i = 0; i < weapons.length; i++) {
				System.out.print(weapons[i] + " is in one of the following: ");
				for (int j = 0; j < locations.length; j++) {
					printKnowledgeAboutCards(algorithm, knowledgeBase, weapons[i], locations[j]);
				}
				System.out.println();
			}
			System.out.println("Which weapon would you like to suggest?");
			String weaponSuggestion = input.next();

			// Information about the rooms
			System.out.println("Here is what we know about the room cards.");
			for (int i = 0; i < rooms.length; i++) {
				System.out.print(rooms[i] + " is in one of the following: ");
				for (int j = 0; j < locations.length; j++) {
					printKnowledgeAboutCards(algorithm, knowledgeBase, rooms[i], locations[j]);
				}
				System.out.println();
			}
			System.out.println("Which room would you like to suggest?");
			String roomSuggestion = input.next();

			// Read in new information based on your guess to one person
			boolean shown = false;
			for (int i = 0; i < locations.length - 2 && !shown; i++) {
				int player = i + 2;
				String playerShown = "P" + player;
				System.out.println("Does player " + playerShown + " show you a card? (y/n): ");
				if (input.next().equals("y")) {
					shown = true;
					System.out.println("Which card do they show you?");
					String cardShown = input.next();
					knowledgeBase.addSentence(new Proposition(cardInLocationString(cardShown, playerShown)));
				} else {
					Conjunction doesntHave = new Conjunction();
					doesntHave.addSentence(
							new Negation(new Proposition(cardInLocationString(suspectSuggestion, playerShown))));
					doesntHave.addSentence(
							new Negation(new Proposition(cardInLocationString(weaponSuggestion, playerShown))));
					doesntHave.addSentence(
							new Negation(new Proposition(cardInLocationString(roomSuggestion, playerShown))));
					knowledgeBase.addSentence(doesntHave);

				}
			}

			System.out.println("Do you wish to make an accusation? (y/n)");
			if (input.next().equals("y")) {
				System.out.println("Welp hopefully you played well did you win? (y/n)");
				String answer = input.next();
				if (answer.equals("y")) {
					System.out.println("Congratulations you have won the game! Well done.");
				} else {
					System.out.println("You have lost the game");
					playerLost = true;
				}

			}

			// Update according to the other players turns
			int initSize = otherPlayersInIt.size();
			for (int i = 0; i < initSize && !playerLost; i++) {
				String currentPlayer = otherPlayersInIt.get(i);
				System.out.println("It is now " + currentPlayer + "'s turn.");
				System.out.println("What suspect, weapon, and room did " + currentPlayer + " suggest?");
				String suspectGuess = input.next();
				String weaponGuess = input.next();
				String roomGuess = input.next();
				System.out.println("Which of these players if any shows " + currentPlayer + " a card?");
				System.out.println("Enter the word 'None' if no player shows " + currentPlayer + " anything");
				System.out.println(Arrays.toString(players));
				String playerAsked = input.next();
				if (playerAsked.equals("None")) {
					System.out.println("Sorry but " + currentPlayer
							+ " has won assuming they are smart enough to make the accusation.");
					playerLost = true;
				} else {
					// Player asked shows one card to the current player so the
					// player asked has at least one of the cards guessed
					int indexPlayerAnswering = Integer.parseInt(playerAsked.substring(1));
					int indexPlayerAsking = Integer.parseInt(currentPlayer.substring(1));
					updatePreviousDenials(indexPlayerAsking, indexPlayerAnswering, knowledgeBase, suspectGuess,
							weaponGuess, roomGuess);
					Disjunction doesntHave = new Disjunction();
					doesntHave.addSentence(
							new Negation(new Proposition(cardInLocationString(suspectGuess, playerAsked))));
					doesntHave
							.addSentence(new Negation(new Proposition(cardInLocationString(weaponGuess, playerAsked))));
					doesntHave.addSentence(new Negation(new Proposition(cardInLocationString(roomGuess, playerAsked))));

					System.out.println("Does " + currentPlayer + " make an accusation? (y/n)");
					String response = input.next();
					if (response.equals("y")) {
						System.out.println("What is the suspect, weapon, and room of the accusation?");
						String suspectAccused = input.next();
						String weaponAccused = input.next();
						String roomAccused = input.next();
						System.out.println("Were they correct? (y/n)");
						String wonAnswer = input.next();
						if (wonAnswer.equals("y")) {
							playerLost = true;
							System.out.println("Sorry but " + currentPlayer + " has bested you and won the game");
						} else {
							losers.add(currentPlayer);
							// otherPlayersInIt.remove(currentPlayer);
							Disjunction possiblyNotCF = new Disjunction();
							possiblyNotCF.addSentence(
									new Negation(new Proposition(cardInLocationString(suspectAccused, locations[0]))));
							possiblyNotCF.addSentence(
									new Negation(new Proposition(cardInLocationString(weaponAccused, locations[0]))));
							possiblyNotCF.addSentence(
									new Negation(new Proposition(cardInLocationString(roomAccused, locations[0]))));
							knowledgeBase.addSentence(possiblyNotCF);
						}
					}
				}
			}

		}

		input.close();

	}

	/**
	 * Helper method to update the previous players that inherenelty refuted
	 * answering the current players suggestion.
	 * 
	 * @param indexPlayerAsking
	 *            The index of the player that is making the suggestion.
	 * @param indexPlayerAnswering
	 *            The index of the player that is not showing a card to the
	 *            asker.
	 * @param knowledgeBase
	 *            Our base of what we know is true.
	 * @param suspectGuess
	 *            The suspect of the suggestion.
	 * @param weaponGuess
	 *            The weapon of the suggestion.
	 * @param roomGuess
	 *            The room of the suggestion.
	 */
	private static void updatePreviousDenials(int indexPlayerAsking, int indexPlayerAnswering,
			Conjunction knowledgeBase, String suspectGuess, String weaponGuess, String roomGuess) {

		int startToUpdate = indexPlayerAsking;
		if (startToUpdate == 6) {
			startToUpdate = 1;
		} else {
			startToUpdate++;
		}
		while (startToUpdate != indexPlayerAnswering) {
			Conjunction notInPlayersHand = new Conjunction();
			notInPlayersHand.addSentence(
					new Negation(new Proposition(cardInLocationString(suspectGuess, players[startToUpdate - 1]))));
			notInPlayersHand.addSentence(
					new Negation(new Proposition(cardInLocationString(weaponGuess, players[startToUpdate - 1]))));
			notInPlayersHand.addSentence(
					new Negation(new Proposition(cardInLocationString(roomGuess, players[startToUpdate - 1]))));
			knowledgeBase.addSentence(notInPlayersHand);
			if (startToUpdate == 6) {
				startToUpdate = 1;
			} else {
				startToUpdate++;
			}
		}

	}

	/**
	 * Print knowledge about a specific card's location. *
	 * 
	 * @param algorithm
	 *            An algorithm to use for checking entailment.
	 * @param knowledgeBase
	 *            That which we know for sure.
	 * @param card
	 *            The card in question as to which location it is in.
	 * @param location
	 *            The location that is being tested if the negation of it can be
	 *            entailed.
	 */
	private static void printKnowledgeAboutCards(EntailmentChecker algorithm, Conjunction knowledgeBase, String card,
			String location) {
		if (!algorithm.entails(knowledgeBase, new Negation(new Proposition(cardInLocationString(card, location))))) {
			System.out.print(location + " ");
		}
	}

	/**
	 * Gets all of the knowledge rules that are true in every instance of Clue
	 * game before the player has observed anything.
	 * 
	 * @return An initial knowledge base for the Clue game.
	 */
	public static Conjunction getInitialKnowledgeBase() {
		Conjunction knowledgeBase = new Conjunction();

		// Each card is in at least one of the seven location (the case file or
		// one
		// of the player's hands and not multiple places. and no more than one.
		for (int i = 0; i < cards.length; i++) {
			Disjunction possibleLocations = new Disjunction();
			for (int j = 0; j < locations.length; j++) {
				possibleLocations.addSentence(new Proposition(cardInLocationString(cards[i], locations[j])));
				for (int k = 0; k < locations.length; k++) {
					if (j != k) {
						knowledgeBase.addSentence(Sentence.parse("( " + cardInLocationString(cards[i], locations[j])
								+ " --> ! ( " + cardInLocationString(cards[i], locations[k]) + " ) )"));
					}
				}

			}
			knowledgeBase.addSentence(possibleLocations);
		}

		// At least one suspect is in the case file
		// The case file contains at most one suspect.
		Disjunction possibleSuspects = new Disjunction();
		for (int i = 0; i < suspects.length; i++) {
			possibleSuspects.addSentence(new Proposition(cardInLocationString(suspects[i], locations[0])));
			for (int j = 0; j < suspects.length; j++) {
				if (i != j) {
					knowledgeBase.addSentence(Sentence.parse("( " + cardInLocationString(suspects[i], locations[0])
							+ " --> ! ( " + cardInLocationString(suspects[j], locations[0]) + " ) )"));
				}
			}

		}
		knowledgeBase.addSentence(possibleSuspects);

		// At least one room is in the case file
		// The case file contains at most one room.
		Disjunction possibleRooms = new Disjunction();
		for (int i = 0; i < rooms.length; i++) {
			possibleRooms.addSentence(new Proposition(cardInLocationString(rooms[i], locations[0])));
			for (int j = 0; j < rooms.length; j++) {
				if (i != j) {
					knowledgeBase.addSentence(Sentence.parse("( " + cardInLocationString(rooms[i], locations[0])
							+ " --> ! ( " + cardInLocationString(rooms[j], locations[0]) + " ) )"));
				}
			}
		}
		knowledgeBase.addSentence(possibleRooms);

		// At least one weapon is in the case file
		// The case file contains at most one weapon.
		Disjunction possibleWeapons = new Disjunction();
		for (int i = 0; i < weapons.length; i++) {
			possibleWeapons.addSentence(new Proposition(cardInLocationString(weapons[i], locations[0])));
			for (int j = 0; j < weapons.length; j++) {
				if (i != j) {
					knowledgeBase.addSentence(Sentence.parse("( " + cardInLocationString(weapons[i], locations[0])
							+ " --> ! ( " + cardInLocationString(weapons[j], locations[0]) + " ) )"));
				}
			}
		}
		knowledgeBase.addSentence(possibleWeapons);

		return knowledgeBase;
	}

	/**
	 * Helper method to create the proposition for a card being in a specific
	 * location.
	 * 
	 * @param card
	 *            The name of the card.
	 * @param location
	 *            The specific location that it is found in.
	 * @return The string of description.
	 */
	private static String cardInLocationString(String card, String location) {
		return card + "In" + location;
	}
}
