package edu.kings.cs448.fall2017.base.csps;

/**
 * A collection of inputs for creating pre-determined Sudoku puzzles.
 * 
 * @author Chad Hogg
 * @version 2017
 */
public class SudokuPuzzles {
	/** The input for a Sudoku puzzle that should be easy. */
	public static final String EASY_PUZZLE = 
			"600030000" +
			"403805000" +
			"028700000" +
			"800004360" +
			"000020000" +
			"034600001" +
			"000003650" +
			"000508702" +
			"000010009";
	
	/** The input for a Sudoku puzzle that should be neither easy nor difficult. */
	public static final String MEDIUM_PUZZLE =
			"050000020" +
			"001002700" +
			"076400800" +
			"700060300" +
			"003805900" +
			"002040005" +
			"004007590" +
			"008100600" +
			"090000010";
	
	/** The input for a Sudoku puzzle that should be difficult. */
	public static final String HARD_PUZZLE =
			"530400000" +
			"690830000" +
			"018000000" +
			"169080007" +
			"020070060" +
			"700050189" +
			"000000950" +
			"000048031" +
			"000006078";
	
	/** The input for a Sudoku puzzle that should be very difficult. */
	public static final String VERY_HARD_PUZZLE =
			"120400300" +
			"300010050" +
			"006000100" +
			"700090000" +
			"040603000" +
			"003002000" +
			"500080700" +
			"007000005" +
			"000000098";
}
