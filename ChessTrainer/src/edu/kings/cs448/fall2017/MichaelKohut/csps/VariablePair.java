package edu.kings.cs448.fall2017.MichaelKohut.csps;

/**
 * Class used just to make the pairs needed for the variable constraints.
 * 
 * @author Michael Kohut
 *
 */
public class VariablePair {

	/**
	 * The first variable.
	 */
	private String variableOne;

	/**
	 * The second variable.
	 */
	private String variableTwo;

	/**
	 * The constructor for the variable pairs keeping track of those with
	 * conflicts.
	 * 
	 * @param varOne
	 *            The first variable.
	 * @param varTwo
	 *            The second variable.
	 */
	public VariablePair(String varOne, String varTwo) {
		variableOne = varOne;
		variableTwo = varTwo;
	}

	/**
	 * Getter for the first variable.
	 * 
	 * @return The first variable.
	 */
	public String getVariableOne() {
		return variableOne;
	}

	/**
	 * Getter for the second variable.
	 * 
	 * @return The second variable of the pair.
	 */
	public String getVariableTwo() {
		return variableTwo;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		VariablePair other = (VariablePair) o;
		if (other.getVariableOne().equals(this.getVariableOne())
				&& other.getVariableTwo().equals(this.getVariableTwo())) 
		{
			result = true;
		}

		return result;

	}

}
