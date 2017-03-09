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
 * When created is provided with an instance of the drawing panel and gallery panel to control
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private JToggleButton reflectButton; // Button to toggle reflection of drawings
	private JButton clearButton; // Button to clear current drawing
	private JSlider sectorSlider; // Button to adjust number of sectors used
	private JSlider brushSlider; // Button to change brush size
	private JButton undoButton; // Button to undo last stroke of brush
	private JButton colourButton; // Button to show JColorChooser to set brush colour
	private JToggleButton sectorButton; // Button to toggle showing the sector lines
	private JButton saveButton; // Button to save current image to the gallery
	private JButton deleteButton; // Button to delete selected images in the gallery
	private JLabel sectorLabel; // Label to identify the sector slider
	private JLabel brushSizeLabel; // Label to identify the brush size slider

	/**
	 * Create a new control panel to act on the specified drawing panel and gallery panel
	 * 
	 * @param drawingPanel
	 * @param galleryPanel
	 */
	public ControlPanel(DrawingPanel drawingPanel, GalleryPanel galleryPanel) {

		this.setLayout(new GridLayout(11,1));

		reflectButton = new JToggleButton("Reflect");
		reflectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.toggleReflection();	
			}

		});

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.clearPoints();	
			}

		});

		sectorSlider = new JSlider(1,Math.max(30,drawingPanel.getSectors()),drawingPanel.getSectors());
		sectorLabel = new JLabel("Number of sectors (" + sectorSlider.getValue() + ")");
		sectorLabel.setHorizontalAlignment(JLabel.CENTER);
		sectorLabel.setVerticalAlignment(JLabel.CENTER);
		sectorSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				drawingPanel.changeSectors(sectorSlider.getValue());
				sectorLabel.setText("Number of sectors (" + sectorSlider.getValue() + ")");
			}

		});

		brushSlider = new JSlider(2,Math.max(20,drawingPanel.getBrushSize()),drawingPanel.getBrushSize());
		brushSizeLabel = new JLabel("Brush Size (" + brushSlider.getValue() + ")");
		brushSizeLabel.setHorizontalAlignment(JLabel.CENTER);
		brushSizeLabel.setVerticalAlignment(JLabel.CENTER);
		brushSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				drawingPanel.setBrushSize(brushSlider.getValue());
				brushSizeLabel.setText("Brush size (" + brushSlider.getValue() + ")");
			}

		});

		undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.undo();	
			}

		});

		colourButton = new JButton("Change Brush Colour");
		colourButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.setBrushColour(JColorChooser.showDialog(null, "Pick brush colour", drawingPanel.getBrushColour()));
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

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				galleryPanel.addImage((BufferedImage) drawingPanel.getImage());	
			}

		});
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				galleryPanel.deleteSelected();	
			}

		});

		this.add(undoButton);
		this.add(clearButton);
		this.add(sectorLabel);
		this.add(sectorSlider);
		this.add(brushSizeLabel);
		this.add(brushSlider);
		this.add(reflectButton);
		this.add(sectorButton);
		this.add(colourButton);
		this.add(saveButton);
		this.add(deleteButton);
	}

}