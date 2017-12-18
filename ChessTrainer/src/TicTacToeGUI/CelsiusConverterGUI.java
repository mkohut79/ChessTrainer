package TicTacToeGUI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;

public class CelsiusConverterGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CelsiusConverterGUI() {

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));

		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

		pack();

	}

	/**
	 * Main string of arguments to be processed.
	 * 
	 * @param args
	 *            The commands to be processed.
	 */
	public static void main(String[] args) {

		CelsiusConverterGUI celConverter = new CelsiusConverterGUI();
		celConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		celConverter.setTitle("Photo Pro");
		celConverter.setSize(1000, 600);
		celConverter.setVisible(true);

	}

}
