import java.awt.BorderLayout;

import javax.swing.JPanel;

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

	private static final int DEFAULT_SECTORS = 12; // The default number of sectors for the drawing panel

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

		this.add(controlPanel, BorderLayout.WEST);
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(galleryPanel, BorderLayout.EAST);

	}

}