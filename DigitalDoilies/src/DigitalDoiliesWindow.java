import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The main window of the application which holds the main container for its components
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
@SuppressWarnings("serial")
public class DigitalDoiliesWindow extends JFrame {

	// Main method creates a new digital doilies window
	public static void main(String args[]) {
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() {  
				new DigitalDoiliesWindow();
			}
		});
	}

	private static final int DEFAULT_WIDTH = 1000; // Default width for the main window pixels
	private static final int DEFAULT_HEIGHT = 500; // Default height for the main window pixels
	private static final int MINIMUM_WIDTH = 750; // Minimum width for the main window pixels
	private static final int MINIMUM_HEIGHT = 300; // Minimum height for the main window pixels

	/**
	 * Holds the components of the digital doilies application
	 */
	private DigitalDoiliesPanel mainPane;

	/**
	 * Creates a new digital doilies window
	 */
	public DigitalDoiliesWindow() {

		super("Digital Doilies");

		mainPane = new DigitalDoiliesPanel();
		this.add(mainPane);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

		this.setVisible(true);

	}

}