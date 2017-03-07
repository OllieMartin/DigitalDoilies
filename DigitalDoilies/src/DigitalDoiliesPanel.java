import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class DigitalDoiliesPanel extends JPanel {

	private DrawingPanel drawingPanel;
	private ControlPanel controlPanel;
	private GalleryPanel galleryPanel;
	private JScrollPane galleryScroll;
	
	public DigitalDoiliesPanel(int galleryWidth) {
		//this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500,500));
		this.setMinimumSize(new Dimension(300,300));
		
		drawingPanel = new DrawingPanel(12);
		galleryPanel = new GalleryPanel(galleryWidth);
		controlPanel = new ControlPanel(drawingPanel, galleryPanel);
		this.add(controlPanel, BorderLayout.WEST);
		this.add(drawingPanel, BorderLayout.CENTER);
		galleryScroll = new JScrollPane(galleryPanel);
		galleryScroll.setPreferredSize(new Dimension(galleryWidth,galleryWidth));
		this.add(galleryScroll, BorderLayout.EAST);
	}
	
}