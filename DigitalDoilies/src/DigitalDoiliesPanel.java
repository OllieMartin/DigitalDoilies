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
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(500,500));
		this.setMinimumSize(new Dimension(300,300));
		
		galleryScroll = new JScrollPane();
		galleryScroll.setMinimumSize(new Dimension(galleryWidth,galleryWidth));
		galleryScroll.setPreferredSize(new Dimension(galleryWidth,galleryWidth));
		galleryScroll.setSize(new Dimension(galleryWidth,galleryWidth));
		drawingPanel = new DrawingPanel(12);
		galleryPanel = new GalleryPanel(galleryWidth);
		controlPanel = new ControlPanel(drawingPanel, galleryPanel);
		this.add(controlPanel);
		this.add(drawingPanel);
		//galleryScroll.add(galleryPanel);
		//this.add(galleryScroll);
		this.add(galleryPanel);
	}
	
}