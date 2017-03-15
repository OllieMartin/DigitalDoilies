import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GalleryPanel extends JPanel {

	private JScrollPane galleryScroll; // A scroll panel to contain the gallery panel
	private JPanel imagePanel;
	private int displayedImageWidth;  // The width for images added to the gallery taking into account the scroll-bar
	private List<JToggleButton> galleryImages; // The list of images added to the gallery
	private JButton deleteButton; // Button to delete selected images in the gallery

	private static final int APPROX_SROLL_BAR_WIDTH = 20; // Approximate width of the vertical scroll bar used to properly place the gallery images
	private static final int MAXIMUM_GALLERY_IMAGES = 12; // The maximum number of images allowed in the gallery
	private static final int GALLERY_VERTICAL_PADDING = 1; // The number of pixels above and below each image in the gallery
	private static final int GALLERY_HORIZONTAL_PADDING = 0; // The number of pixels left and right of each image in the gallery
	private static final int SCROLL_FACTOR = 10; // The number of lines scrolled on the gallery scroll bar
	private static final int IMAGE_SELECT_STROKE = 5; // The width of the cross displayed when an image is selected
	
	/**
	 * Create a new gallery panel with the specified width
	 * 
	 * @param panelWidth
	 */
	public GalleryPanel(int panelWidth) {

		this.setLayout(new BorderLayout());
		imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
		galleryScroll = new JScrollPane(imagePanel);
		galleryScroll.setMinimumSize(new Dimension(panelWidth,panelWidth));
		galleryScroll.setPreferredSize(new Dimension(panelWidth,panelWidth));
		galleryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		galleryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		galleryScroll.getVerticalScrollBar().setUnitIncrement(SCROLL_FACTOR);
		this.add(galleryScroll, BorderLayout.CENTER);

		this.galleryImages = new ArrayList<JToggleButton>();
		this.displayedImageWidth = panelWidth - APPROX_SROLL_BAR_WIDTH;
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteSelected();	
			}

		});
		
		this.add(deleteButton, BorderLayout.SOUTH);

	}

	public void addImage(BufferedImage image) {
		if (galleryImages.size() < MAXIMUM_GALLERY_IMAGES) {

			Image scaledImage;

			JToggleButton newSave = new JToggleButton();
			Border line = new LineBorder(Color.BLACK);
			Border margin = new EmptyBorder(GALLERY_VERTICAL_PADDING, GALLERY_HORIZONTAL_PADDING, GALLERY_VERTICAL_PADDING, GALLERY_HORIZONTAL_PADDING);
			Border compound = new CompoundBorder(line, margin);
			newSave.setBorder(compound);

			float iwidth = image.getWidth();
			float iheight = image.getHeight();

			scaledImage = image.getScaledInstance(displayedImageWidth,(int) (displayedImageWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);

			newSave.setIcon(new ImageIcon(scaledImage));
			newSave.setHorizontalAlignment(SwingConstants.CENTER);
			newSave.setVerticalAlignment(SwingConstants.CENTER);

			BufferedImage selectedImage = new BufferedImage(displayedImageWidth,(int) (displayedImageWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) selectedImage.getGraphics();
			g2.drawImage(scaledImage,0,0, this);
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(IMAGE_SELECT_STROKE ,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.drawLine(0, 0, displayedImageWidth, (int) (displayedImageWidth*(iheight/iwidth)));
			g2.drawLine(displayedImageWidth, 0, 0, (int) (displayedImageWidth*(iheight/iwidth)));

			newSave.setSelectedIcon(new ImageIcon(selectedImage));

			imagePanel.add(newSave);
			galleryImages.add(newSave);

			this.updateUI();
		}
	}

	public void deleteSelected() {
		List<JToggleButton> toRemove = new ArrayList<JToggleButton>();
		for (JToggleButton image : galleryImages) {
			if (image.isSelected()) {
				imagePanel.remove(image);
				toRemove.add(image);
			}
		}
		galleryImages.removeAll(toRemove);
		this.updateUI();
	}

}