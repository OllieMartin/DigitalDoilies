import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DigitalDoiliesWindow extends JFrame {

	public static void main(String args[]) {
		new DigitalDoiliesWindow();
	}
	
	private DigitalDoiliesPanel mainPane;
	
	public DigitalDoiliesWindow() {
		super("Digital Doilies");
		mainPane = new DigitalDoiliesPanel();
		this.add(mainPane);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setMinimumSize(new Dimension(300,300));
		this.setVisible(true);
	}
	
}