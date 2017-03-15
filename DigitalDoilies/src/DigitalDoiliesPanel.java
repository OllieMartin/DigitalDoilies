import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * <p>The main panel which holds the components of the application:</p>
 * 
 * <ul>
 * <li>The drawing panel</li>
 * <li>The control panel</li>
 * <li>The gallery panel</li>
 * </ul>
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
	private static final int DEFAULT_BRUSH_SIZE = 5; // The default brush size to draw with
	private static final int DEFAULT_GALLERY_WIDTH = 200; // The default width of the gallery panel in pixels

	/**
	 * Create a new panel with a specified width for the gallery images to be displayed
	 * 
	 * @param galleryWidth The width of the gallery panel in pixels
	 */
	public DigitalDoiliesPanel() {
		
		this(DEFAULT_GALLERY_WIDTH, DEFAULT_SECTORS, DEFAULT_BRUSH_SIZE);

	}
	
	public DigitalDoiliesPanel(int galleryWidth, int initialSectors, int initialBrushSize) {

		this.setLayout(new BorderLayout());

		drawingPanel = new DrawingPanel(initialSectors, initialBrushSize);
		galleryPanel = new GalleryPanel(galleryWidth);
		controlPanel = new ControlPanel(drawingPanel, galleryPanel);

		this.add(controlPanel, BorderLayout.WEST);
		this.add(drawingPanel, BorderLayout.CENTER);
		this.add(galleryPanel, BorderLayout.EAST);

	}

}