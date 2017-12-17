package edu.kings.cs448.fall2017.base.search;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * A class that allows you to make the decisions to solve the problem.
 * 
 * @author Chad Hogg
 * @version 2017
 * @param <S> The type of states in the problem.
 * @param <A> The type of actions in the problem.
 */
public class ManualSearchSolver<S, A> implements SearchSolver<S, A> {

	@Override
	public ArrayList<A> solve(SearchProblem<S, A> problem) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		SearchNode<S, A> currentNode = new SearchNode<S, A>(null, 0, problem.getInitialState(), null);
		while(!problem.meetsGoal(currentNode.getState())) {
			System.out.println("Your current state:");
			System.out.println(currentNode.getState());
			Set<A> actions = problem.getActions(currentNode.getState());
			ArrayList<A> orderedActions = new ArrayList<A>(actions);
			int choice = -1;
			while(choice < 0 || choice >= orderedActions.size()) {
				System.out.println("Available actions:");
				for(int i = 0; i < orderedActions.size(); i++) {
					System.out.println(i + ":\t" + orderedActions.get(i));
				}
				System.out.println("Your choice: ");
				choice = input.nextInt();
			}
			currentNode = new SearchNode<S, A>(currentNode, currentNode.getPathCost() + problem.getStepCost(orderedActions.get(choice), currentNode.getState()), problem.getResultingState(orderedActions.get(choice), currentNode.getState()), orderedActions.get(choice));
		}
		ArrayList<A> solution = new ArrayList<A>();
		while(currentNode.getAction() != null) {
			solution.add(0, currentNode.getAction());
			currentNode = currentNode.getParent();
		}
		return solution;
	}

	@Override
	public int getNumExpandedNodes() {
		return 0;
	}

}
