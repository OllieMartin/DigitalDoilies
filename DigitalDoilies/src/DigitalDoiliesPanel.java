import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The main panel which holds the components of the application:
 * The drawing panel,
 * The control panel,
 * and The gallery panel
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
@SuppressWarnings("serial")
public class DigitalDoiliesPanel extends JPanel {

	private DrawingPanel drawingPanel; // The panel in which the user draws
	private ControlPanel controlPanel; // The panel containing the control elements for the application
	private GalleryPanel galleryPanel; // The panel containing the gallery for saved images

	private JScrollPane galleryScroll; // A scroll panel to contain the gallery panel

	private static final int DEFAULT_SECTORS = 12; // The default number of sectors for the drawing panel
	private static final int SCROLL_FACTOR = 10; // The number of lines scrolled on the gallery scroll bar

	/**
	 * Create a new panel with a specified width for the gallery images to be displayed
	 * 
	 * @param galleryWidth The width of the gallery panel in pixels
	 */
	public DigitalDoiliesPanel(int galleryWidth) {

		this.setLayout(new BorderLayout());

		drawingPanel = new DrawingPanel(DEFAULT_SECTORS);
		galleryPanel = new GalleryPanel(galleryWidth);
		controlPanel = new ControlPanel(drawingPanel, galleryPanel);

		galleryScroll = new JScrollPane(galleryPanel);
		galleryScroll.setMinimumSize(new Dimension(galleryWidth,galleryWidth));
		galleryScroll.setPreferredSize(new Dimension(galleryWidth,galleryWidth));
		galleryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		galleryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		galleryScroll.getVerticalScrollBar().setUnitIncrement(SCROLL_FACTOR);

		this.add(controlPanel, BorderLayout.WEST);
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(galleryScroll, BorderLayout.EAST);

	}

}