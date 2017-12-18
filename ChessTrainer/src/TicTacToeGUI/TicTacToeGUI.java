package TicTacToeGUI;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class TicTacToeGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicTacToeGUI() {
		
		this.setLayout(new GridLayout(1,2));
		
		

	}

	/**
	 * Main string of arguments to be processed.
	 * 
	 * @param args
	 *            The commands to be processed.
	 */
	public static void main(String[] args) {

		TicTacToeGUI tttGUI = new TicTacToeGUI();
		tttGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tttGUI.setTitle("Photo Pro");
		tttGUI.setSize(1000, 600);
		tttGUI.setVisible(true);

	}

}
