import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class GalleryPanel extends JPanel {
	
	private int width;

	public GalleryPanel(int width) {
		this.width = width;
		this.setMinimumSize(new Dimension(width,width));
		this.setPreferredSize(new Dimension(width,width));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.setLayout(new FlowLayout());
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void addImage(Image image) {
		JToggleButton newSave = new JToggleButton();
		Image newImage = image;
		newSave.setIcon(new ImageIcon(newImage));
		newSave.setSelectedIcon(new ImageIcon(image));
		this.add(newSave);
		this.updateUI();
	}
	
}