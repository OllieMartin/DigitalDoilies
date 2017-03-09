import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GalleryPanel extends JPanel {

	private int width;
	private int adjustedWidth;
	private List<JToggleButton> images;
	//private ButtonGroup buttons;

	public GalleryPanel(int width) {
		this.width = width;
		adjustedWidth = width - 20;
		this.images = new ArrayList<JToggleButton>();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//this.buttons = new ButtonGroup();
	}

	public int getWidth() {
		return this.width;
	}

	public void addImage(BufferedImage image) {
		if (images.size() < 12) {
			
			Image scaledImage;
			
			JToggleButton newSave = new JToggleButton();
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(1, 0, 1, 0);
			Border compound = new CompoundBorder(line, margin);
			newSave.setBorder(compound);
			
			float iwidth = image.getWidth();
			float iheight = image.getHeight();
			
			scaledImage = image.getScaledInstance(adjustedWidth,(int) (adjustedWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);
			
			newSave.setIcon(new ImageIcon(scaledImage));
			newSave.setHorizontalAlignment(SwingConstants.CENTER);
			newSave.setVerticalAlignment(SwingConstants.CENTER);
			
			BufferedImage selectedImage = new BufferedImage(adjustedWidth,(int) (adjustedWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) selectedImage.getGraphics();
			g2.drawImage(scaledImage,0,0, this);
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(5,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.drawLine(0, 0, adjustedWidth, (int) (adjustedWidth*(iheight/iwidth)));
			g2.drawLine(adjustedWidth, 0, 0, (int) (adjustedWidth*(iheight/iwidth)));
			
			newSave.setSelectedIcon(new ImageIcon(selectedImage));
			
			this.add(newSave);
			images.add(newSave);

			this.updateUI();
		}
	}

	public void deleteSelected() {
		List<JToggleButton> toRemove = new ArrayList<JToggleButton>();
		for (JToggleButton image : images) {
			if (image.isSelected()) {
				this.remove(image);
				toRemove.add(image);
			}
		}
		images.removeAll(toRemove);
		this.updateUI();
	}

}