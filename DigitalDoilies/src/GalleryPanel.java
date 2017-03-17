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
import java.util.Iterator;
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

/**
 * The gallery panel to display saved drawings by the user
 * 
 * @author Oliver Martin (ojm1g16)
 *
 */
@SuppressWarnings("serial")
public class GalleryPanel extends JPanel {

	private JScrollPane galleryScroll; // A scroll panel to contain the image panel
	private JPanel imagePanel; // The image panel contains the saved images
	private List<JToggleButton> galleryImages; // The list of images added to the gallery
	private JButton deleteButton; // Button to delete selected images in the gallery
	
	private int displayedImageWidth;  // The width for images added to the gallery taking into account the scroll-bar

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
		
		// Initialise and set defaults
		
		this.galleryImages = new ArrayList<JToggleButton>();
		this.displayedImageWidth = panelWidth - APPROX_SROLL_BAR_WIDTH;
		
		this.setLayout(new BorderLayout());
		
		imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
		
		galleryScroll = new JScrollPane(imagePanel);
		galleryScroll.setMinimumSize(new Dimension(panelWidth,panelWidth));
		galleryScroll.setPreferredSize(new Dimension(panelWidth,panelWidth));
		galleryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		galleryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		galleryScroll.getVerticalScrollBar().setUnitIncrement(SCROLL_FACTOR);
		
		// Setup delete button with appropriate listener
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteSelected();	
			}

		});
		
		// Add the items to the pane
		this.add(galleryScroll, BorderLayout.CENTER);
		this.add(deleteButton, BorderLayout.SOUTH);

	}

	/**
	 * Adds an image to the gallery if the maximum number of images has not been reached
	 * 
	 * @param image
	 */
	public void addImage(BufferedImage image) {
		if (galleryImages.size() < MAXIMUM_GALLERY_IMAGES) {
			
			Image scaledImage; // The scaled image to be displayed in the gallery
			BufferedImage selectedImage; // The image to be displayed when the image is selected in the gallery
			
			JToggleButton newSave; // The toggle button which will be used to display and select the image
			
			Border line; // The outline around the image button
			Border margin; // The margin to be used around the image in the button;
			Border compound; // The compound border comprised of line and margin
			
			float iwidth; // The width of the original image
			float iheight; // The height of the original image
			
			Graphics2D g2; // Graphics object to be used when creating selected image
			
			newSave = new JToggleButton();
			
			line = new LineBorder(Color.BLACK);
			margin = new EmptyBorder(GALLERY_VERTICAL_PADDING, GALLERY_HORIZONTAL_PADDING, GALLERY_VERTICAL_PADDING, GALLERY_HORIZONTAL_PADDING);
			compound = new CompoundBorder(line, margin);
			newSave.setBorder(compound);

			iwidth = image.getWidth();
			iheight = image.getHeight();

			// Create the scaled image from the original image
			scaledImage = image.getScaledInstance(displayedImageWidth,(int) (displayedImageWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);

			// Setup the image in the toggle button
			newSave.setIcon(new ImageIcon(scaledImage));
			newSave.setHorizontalAlignment(SwingConstants.CENTER);
			newSave.setVerticalAlignment(SwingConstants.CENTER);

			// Create the selected image using the scaled image and drawing a cross over it from corner to corner
			selectedImage = new BufferedImage(displayedImageWidth,(int) (displayedImageWidth*(iheight/iwidth)),BufferedImage.TYPE_INT_RGB);
			g2 = (Graphics2D) selectedImage.getGraphics();
			g2.drawImage(scaledImage,0,0, null);
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(IMAGE_SELECT_STROKE ,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2.drawLine(0, 0, displayedImageWidth, (int) (displayedImageWidth*(iheight/iwidth)));
			g2.drawLine(displayedImageWidth, 0, 0, (int) (displayedImageWidth*(iheight/iwidth)));

			// Set the selected icon to selectedImage
			newSave.setSelectedIcon(new ImageIcon(selectedImage));

			// Add the new button to the image panel and the list of all images
			imagePanel.add(newSave);
			galleryImages.add(newSave);

			// Refresh this component
			this.updateUI();
		}
	}

	/**
	 * Deletes all selected images in the gallery
	 */
	public void deleteSelected() {
		
		JToggleButton currentImage; // Stores the image currently being processed
		Iterator<JToggleButton> it = galleryImages.iterator();
		
		while (it.hasNext()) {
			
			currentImage = it.next();
			if (currentImage.isSelected()) {
				imagePanel.remove(currentImage);
				it.remove();
			}
		}
		
		this.updateUI();
	}

}