import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DigitalDoiliesPanel extends JPanel {

	private DrawingPanel drawingPanel;
	private ControlPanel controlPanel;
	private GalleryPanel galleryPanel;
	
	public DigitalDoiliesPanel() {
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(500,500));
		this.setMinimumSize(new Dimension(300,300));
		
		drawingPanel = new DrawingPanel(12);
		controlPanel = new ControlPanel(drawingPanel);
		galleryPanel = new GalleryPanel();
		this.add(controlPanel);
		this.add(drawingPanel);
		this.add(galleryPanel);
	}
	
}