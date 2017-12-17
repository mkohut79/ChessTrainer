package edu.kings.cs448.fall2017.base.search;

/**
 * A state in the Vacuum World.
 * 
 * @author CS448
 * @version 2017
 */
public class VacuumWorldState {
	
	/** The room on the left. */
	public static final char A = 'A';
	/** The room on the right. */
	public static final char B = 'B';
	
	/** The room the vacuum is in. */
	private char currentRoom;
	
	/** Whether or not the left room is clean. */
	private boolean aClean;
	
	/** Whether or not the right room is clean. */
	private boolean bClean;
	
	/**
	 * Constructs a new VacuumWorldState.
	 * 
	 * @param room The location of the robot.
	 * @param aCondition Whether or not the left room is clean.
	 * @param bCondition Whether or not the right room is clean.
	 */
	public VacuumWorldState(char room, boolean aCondition, boolean bCondition){
		currentRoom = room;
		aClean = aCondition;
		bClean = bCondition;
	}

	/**
	 * Gets the room the vacuum is in.
	 * 
	 * @return The room the vacuum is in.
	 */
	public char getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Gets whether or not the left room is clean.
	 * 
	 * @return Whether or not the left room is clean.
	 */
	public boolean isaClean() {
		return aClean;
	}

	/**
	 * Gets whether or not the right room is clean.
	 * 
	 * @return Whether or not the right room is clean.
	 */
	public boolean isbClean() {
		return bClean;
	}
	
	@Override
	public String toString(){
		String result = "Current Room: " + currentRoom  + "\n A Status: " + aClean + 
				"\n B Status: " + bClean;
		
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result;
		if(this == o) {
			result = true;
		}
		else {
			if(o == null) {
				result = false;
			}
			else {
				if(o instanceof VacuumWorldState) {
					VacuumWorldState otherState = (VacuumWorldState)o;
					if(currentRoom == otherState.currentRoom &&
							aClean == otherState.aClean &&
							bClean == otherState.bClean)
					{
						result = true;
					}
					else {
						result = false;
					}
						
				}
				else {
					result = false;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		
		if(currentRoom == A) {
			result += 100;
		}
		if(aClean) {
			result += 10;
		}
		if(bClean) {
			result += 1;
		}
		
		return result;
	}
	
	
}
