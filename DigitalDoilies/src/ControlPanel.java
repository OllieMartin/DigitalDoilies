import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private JButton testButton;
	
	public ControlPanel() {
		testButton = new JButton("Test");
		this.add(testButton);
	}
	
}