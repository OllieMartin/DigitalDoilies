import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private JToggleButton reflectButton;
	private JButton clearButton;
	private JSlider sectorSlider;
	private JSlider brushSlider;
	private JButton undoButton;
	private JButton colourButton;
	private JToggleButton sectorButton;
	private JButton saveButton;
	
	public ControlPanel(DrawingPanel drawingPanel, GalleryPanel galleryPanel) {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		sectorSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				drawingPanel.changeSectors(sectorSlider.getValue());
			}
			
		});
		
		brushSlider = new JSlider(2,Math.max(20,drawingPanel.getBrushSize()),drawingPanel.getBrushSize());
		brushSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				drawingPanel.setBrushSize(brushSlider.getValue());
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
				galleryPanel.addImage(drawingPanel.getBufferedImage(galleryPanel.getWidth()));	
			}
			
		});
		
		this.add(reflectButton);
		this.add(clearButton);
		this.add(sectorSlider);
		this.add(brushSlider);
		this.add(undoButton);
		this.add(colourButton);
		this.add(sectorButton);
		this.add(saveButton);
	}
	
}