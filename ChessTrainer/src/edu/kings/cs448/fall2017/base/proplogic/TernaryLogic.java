package edu.kings.cs448.fall2017.base.proplogic;

/**
 * A logic result value that includes an option for undetermined.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public enum TernaryLogic {
	/** The sentence is definitely true. */
	TRUE,
	/** The sentence is definitely false. */
	FALSE,
	/** We do not yet have enough information to determine whether the sentence is true or false. */
	UNKNOWN;
}
