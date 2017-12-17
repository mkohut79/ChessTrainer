package edu.kings.cs448.fall2017.base.search;

import java.util.HashSet;
import java.util.Set;

/**
 * A problem in the Vacuum World.
 * 
 * @author CS448
 * @version 2017
 */
public class VacuumWorldProblem implements SearchProblem<VacuumWorldState, VacuumWorldAction> {

	/** The initial state of the problem. */
	private VacuumWorldState initialState;
	
	/**
	 * Constructs a new VacuumWorldProblem.
	 * 
	 * @param room The room of the initial state for the problem.
	 * @param aClean Whether or not the left room is clean in the initial state.
	 * @param bClean Whether or not the right room is clean in the initial state.
	 */
	public VacuumWorldProblem(char room, boolean aClean, boolean bClean){
		 initialState = new VacuumWorldState(room, aClean, bClean);
	}
	
	@Override
	public VacuumWorldState getInitialState() {
		return initialState;
	}

	@Override
	public Set<VacuumWorldAction> getActions(VacuumWorldState theState) {
		Set<VacuumWorldAction> actions = new HashSet<VacuumWorldAction>();
		actions.add(VacuumWorldAction.LEFT);
		actions.add(VacuumWorldAction.RIGHT);
		actions.add(VacuumWorldAction.SUCK);
		return actions;
	}

	@Override
	public VacuumWorldState getResultingState(VacuumWorldAction theAction,
			VacuumWorldState theState) {
		VacuumWorldState result = null;	
		
		if(theAction.equals(VacuumWorldAction.LEFT)){
			if(theState.getCurrentRoom() == VacuumWorldState.A){
				result = theState;
			}else{
				result = new VacuumWorldState(VacuumWorldState.A, theState.isaClean(), theState.isbClean());
			}
		}else if(theAction.equals(VacuumWorldAction.RIGHT)){
			if(theState.getCurrentRoom() == VacuumWorldState.B){
				result = theState;
			}else{
				result = new VacuumWorldState(VacuumWorldState.B, theState.isaClean(), theState.isbClean());
			}
		}else{
			if(theState.getCurrentRoom() == VacuumWorldState.A){
				result =  new VacuumWorldState(theState.getCurrentRoom(), true, theState.isbClean());
			}else{
				result = new VacuumWorldState(theState.getCurrentRoom(), theState.isaClean(), true);
			}	
		}
		return result;
	}

	@Override
	public boolean meetsGoal(VacuumWorldState theState) {
		boolean result = false;
		
		if(theState.isaClean() == true && theState.isbClean() == true){
			result = true;
		}

		return result;
	}

	@Override
	public int getStepCost(VacuumWorldAction theAction,
			VacuumWorldState theState) {

		return 1;
	}

	@Override
	public int heuristicForState(VacuumWorldState theState) {
		int dirtyRooms = 0;
		
		if(theState.isaClean() == false){
			dirtyRooms ++;
		}
		if(theState.isbClean() == false){
			dirtyRooms++;
		}
		
		return dirtyRooms;
	}

}
