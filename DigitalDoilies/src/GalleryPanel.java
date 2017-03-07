import java.awt.Dimension;
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
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void addImage(BufferedImage image) {
		JToggleButton newSave = new JToggleButton("test");
		newSave.setIcon(new ImageIcon(image));
		this.add(newSave);
		System.out.println("added");
	}
	
}