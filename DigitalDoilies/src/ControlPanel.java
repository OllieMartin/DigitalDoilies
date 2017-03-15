import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The control panel for the application
 * 
 * When created is provided with a DrawingPanel and GalleryPanel to control
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private JButton undoButton; // Button to undo last stroke of brush
	private JButton clearButton; // Button to clear current drawing

	private JLabel sectorLabel; // Label to identify the sector slider
	private JSlider sectorSlider; // Button to adjust number of sectors used

	private JLabel brushSizeLabel; // Label to identify the brush size slider
	private JSlider brushSizeSlider; // Button to change brush size

	private JToggleButton reflectButton; // Button to toggle reflection of drawings
	private JToggleButton sectorButton; // Button to toggle showing the sector lines

	private JButton colourButton; // Button to show JColorChooser to set brush colour

	private JButton saveButton; // Button to save current image to the gallery

	private static final int SECTOR_MAX = 30; // Default maximum number of sectors for the slider
	private static final int SECTOR_MIN = 1; // Default minimum number of sectors for the slider

	private static final int BRUSH_MAX = 30; // Default maximum brush size for the slider
	private static final int BRUSH_MIN = 2; // Default minimum brush size for slider

	private static final int PANEL_ROWS = 10; // Number of rows for the control panel grid layout
	private static final int PANEL_COLS = 1; // Number of columns for the control panel grid layout

	/**
	 * Create a new control panel to act on the specified drawing panel and gallery panel
	 * 
	 * @param drawingPanel
	 * @param galleryPanel
	 */
	public ControlPanel(DrawingPanel drawingPanel, GalleryPanel galleryPanel) {

		this.setLayout(new GridLayout(PANEL_ROWS,PANEL_COLS)); // Set up layout of the control panel

		// Create the button to undo the last brush stroke, and the button to clear all strokes

		undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.undo();	
			}

		});

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.clearPoints();	
			}

		});

		// Create the sliders for number of sectors, and the brush size

		sectorSlider = new JSlider(SECTOR_MIN,Math.max(SECTOR_MAX,drawingPanel.getSectors()),drawingPanel.getSectors());
		sectorLabel = new JLabel("Number of sectors (" + sectorSlider.getValue() + ")");
		sectorLabel.setHorizontalAlignment(JLabel.CENTER);
		sectorLabel.setVerticalAlignment(JLabel.CENTER);
		sectorSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				sectorLabel.setText("Number of sectors (" + sectorSlider.getValue() + ")");
				if (!sectorSlider.getValueIsAdjusting()) {
					drawingPanel.changeSectors(sectorSlider.getValue());
				}
			}

		});

		brushSizeSlider = new JSlider(BRUSH_MIN,Math.max(BRUSH_MAX,drawingPanel.getBrushSize()),drawingPanel.getBrushSize());
		brushSizeLabel = new JLabel("Brush size (" + brushSizeSlider.getValue() + ")");
		brushSizeLabel.setHorizontalAlignment(JLabel.CENTER);
		brushSizeLabel.setVerticalAlignment(JLabel.CENTER);
		brushSizeSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				drawingPanel.setBrushSize(brushSizeSlider.getValue());
				brushSizeLabel.setText("Brush size (" + brushSizeSlider.getValue() + ")");
			}

		});

		// Create the toggle buttons for reflecting points and for showing sector lines

		reflectButton = new JToggleButton("Reflect");
		reflectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.toggleReflection();	
			}

		});

		sectorButton = new JToggleButton("Show sectors");
		sectorButton.setSelected(true);
		sectorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.toggleSectors();	
			}

		});

		// Create the colour button to allow the user to change brush colour

		colourButton = new JButton("Change Brush Colour");
		colourButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.setBrushColour(JColorChooser.showDialog(null, "Pick brush colour", drawingPanel.getBrushColour()));
			}

		});

		// Create the save button to save the current drawing to the gallery

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				galleryPanel.addImage((BufferedImage) drawingPanel.getImage());	
			}

		});

		// Add all of the components to the ControlPanel

		this.add(undoButton);
		this.add(clearButton);

		this.add(sectorLabel);
		this.add(sectorSlider);
		this.add(brushSizeLabel);
		this.add(brushSizeSlider);

		this.add(reflectButton);
		this.add(sectorButton);

		this.add(colourButton);
		this.add(saveButton);
	}

}