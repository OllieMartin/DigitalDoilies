import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

	private BufferedImage drawing; // Stores the current drawn image
	
	private Point mousePosition; // The current position of the mouse on the drawing panel, null if the mouse is elsewhere
	private Stroke currentStroke; // The current stroke being drawn if there is one, null if not
	private Stack<Stroke> strokes; // A stack of all the strokes that make up the drawing

	private int numberOfSectors; // The current number of sectors being used to draw
	private boolean showSectors; // True if the sector lines should be drawn, otherwise false

	private int brushSize; // The current size of the brush being used
	private Color brushColour; // The current colour of the brush being used

	private boolean reflect; // If new drawn points should be reflected within their respective sectors

	private static final int MINIMUM_PANEL_SIZE = 200; // The minimum width/height for the drawing panel object
	private static final Color DEFAULT_BACKGROUND_COLOUR = Color.BLACK; // The background colour to be used for the drawing panel
	private static final Color DEFAULT_BRUSH_COLOUR = Color.WHITE; // The default colour to be selected for the brush
	private static final int BRUSH_HOVER_TRANSPARENCY = 150; // The transparency factor for the brush hovering over the drawing panel

	/**
	 * Creates a new drawing panel with a specified number of sectors to begin with
	 * 
	 * @param defaultNumberOfSectors The number of sectors to be displayed by default
	 */
	public DrawingPanel(int initialSectors, int initialBrushSize) {

		// Setup panel
		
		this.setBackground(DEFAULT_BACKGROUND_COLOUR);
		
		this.brushColour = DEFAULT_BRUSH_COLOUR;
		this.brushSize = initialBrushSize;

		this.reflect = false;
		this.mousePosition = null;
		this.showSectors = true;
		
		this.strokes = new Stack<Stroke>();
		
		this.setMinimumSize(new Dimension(MINIMUM_PANEL_SIZE, MINIMUM_PANEL_SIZE));
		
		this.changeSectors(initialSectors);
		
		// Add listener for resizing of this drawing panel
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				refreshDrawing();
	        }
		});
		
		// Add mouse listeners for the panel
		
		this.addMouseMotionListener(new DrawingPanelMouseAdapter());
		this.addMouseListener(new DrawingPanelMouseAdapter());
	
		// Perform a full refresh of the drawing to be displayed
		
		this.refreshDrawing();
		
	}
	
	private void drawStroke(Stroke stroke) {
		
		Graphics2D g2;		
		StrokePoint lastPoint;
		
		if (stroke != null) {
			
			g2 = (Graphics2D) drawing.getGraphics();
			lastPoint = null;
			
			for (StrokePoint p : stroke.points) {
				
				g2.setColor(stroke.getColour());
				g2.setStroke(new BasicStroke(stroke.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

				for (int i = 0; i < numberOfSectors; i++) {
					
					if (lastPoint != null) {
						
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
						if (stroke.getReflected()) {
							g2.drawLine(getWidth()/2 - p.reflection.x, getHeight()/2 - p.reflection.y, getWidth()/2 - lastPoint.reflection.x, getHeight()/2 - lastPoint.reflection.y);
						}
						
					} else if (stroke.points.size() == 1) {
						
						g2.fillOval(getWidth()/2 - stroke.points.get(0).x - stroke.getBrushSize()/2, getHeight()/2 - stroke.points.get(0).y - stroke.getBrushSize()/2, stroke.getBrushSize(), stroke.getBrushSize());
						if (stroke.getReflected()) {
							g2.fillOval(getWidth()/2 - stroke.points.get(0).reflection.x - stroke.getBrushSize()/2, getHeight()/2 - stroke.points.get(0).reflection.y - stroke.getBrushSize()/2, stroke.getBrushSize(), stroke.getBrushSize());
						}
						
					}
					
					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				
				lastPoint = p;
			}
		}
	}
	
	private void drawSectors() {
		
		Graphics2D g2 = (Graphics2D) drawing.getGraphics();
		
		g2.setColor(DEFAULT_BRUSH_COLOUR);
		
		for (int i = 0; i < numberOfSectors; i++) {
			g2.drawLine(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight()/6);
			g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
		}
		
	}
	
	private void updateDrawing() {
		
		drawStroke(currentStroke);

		repaint();
	}
	
	private void refreshDrawing() {
		
		drawing = new BufferedImage(Math.max(this.getWidth(), 1), Math.max(this.getHeight(),1),  BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = (Graphics2D) drawing.getGraphics();
		
		// Set the correct background colour of the drawing
		g2.setColor(DEFAULT_BACKGROUND_COLOUR);
		g2.fillRect(0,0,drawing.getWidth(),drawing.getHeight());

		// Draw sector lines of that option is enabled
		if (showSectors) {
			drawSectors();
		}

		// Draw all the strokes to the drawing
		for (Stroke s : strokes) {
			drawStroke(s);
		}
		
		// Updates the drawing with any strokes currently being drawn and calls repaint()
		updateDrawing();
		
	}

	/**
	 * <p>Changes the number of sectors being used to draw to the specified value</p>
	 * 
	 * <p>This also re-draws the image entirely</p>
	 * 
	 * @param numberOfSectors
	 */
	public void changeSectors(int numberOfSectors) {

		this.numberOfSectors = numberOfSectors;
		refreshDrawing();
	}

	/**
	 * Toggles if the sector lines should be displayed and re-draws the image
	 */
	public void toggleSectors() {

		showSectors = !showSectors;
		refreshDrawing();
	}

	/**
	 * Gets the current size of brush being used
	 * 
	 * @return The size of the brush being used
	 */
	public int getBrushSize() {
		return brushSize;
	}

	/**
	 * Sets the size of the brush to the specified value
	 * 
	 * @param brushSize
	 */
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}

	/**
	 * Get the current colour of the brush
	 * 
	 * @return The current colour of the brush
	 */
	public Color getBrushColour() {
		return brushColour;
	}

	/**
	 * Sets the current colour of the brush
	 * 
	 * @param colour
	 */
	public void setBrushColour(Color colour) {
		this.brushColour = colour;
	}

	/**
	 * Toggles if subsequently drawn points should be reflected
	 */
	public void toggleReflection() {
		reflect = !reflect;
	}

	/**
	 * Clears all points from the image and causes a re-draw
	 */
	public void clearPoints() {
		
		while (!strokes.isEmpty()) {
			strokes.pop();
		}
		currentStroke = null;
		
		refreshDrawing();
	}

	/**
	 * Gets the number of sectors currently being used to draw on the image
	 * @return
	 */
	public int getSectors() {
		return numberOfSectors;
	}

	/**
	 * Undoes the last brush stroke and causes a re-draw
	 */
	public void undo() {
		
		if (!strokes.isEmpty() ) {
			strokes.pop();
			refreshDrawing();
		}
		
	}

	/**
	 * Creates an exact copy of the image being displayed in the drawing panel
	 * 
	 * @return An exact copy of the image being displayed in the drawing panel
	 */
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		paintComponent(image.getGraphics());
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(drawing, 0, 0, null);

		// Displays a transparent representation of the brush size and colour at the current mouse position
		if (mousePosition != null) {

			g2.setColor(new Color(getBrushColour().getRed(),getBrushColour().getGreen(),getBrushColour().getBlue(),BRUSH_HOVER_TRANSPARENCY));
			g2.fillOval(mousePosition.x - getBrushSize()/2,mousePosition.y -getBrushSize()/2,getBrushSize(),getBrushSize());

		}

	}

	/**
	 * <p>The listener to be used for the drawing panel. Acts as a mouse motion listener and mouse listener.</p>
	 * 
	 * <p>Allows strokes to be drawn when appropriate mouse actions are taken.
	 * Also updates the current location of the mouse when it is hovering over the panel.</p>
	 * 
	 * @author Oliver Martin (ojm1g16)
	 *
	 */
	private class DrawingPanelMouseAdapter extends MouseAdapter {
		
		// Draws a new point at the mouse location
		private void mouseDraw(MouseEvent e) {
			
			if (currentStroke == null) currentStroke = new Stroke(brushSize, brushColour, reflect);
			
			if (currentStroke.getReflected()) {
				currentStroke.points.add(new StrokePoint(getWidth()/2 - e.getX(), getHeight()/2 - e.getY(), e.getX() - getWidth()/2, getHeight()/2 - e.getY()));
			} else {
				currentStroke.points.add(new StrokePoint(getWidth()/2 - e.getX(), getHeight()/2 - e.getY()));
			}
			
		}
		
		// Finishes the current stroke being drawn and adds it to the stack
		private void finishStroke() {
			
			if (currentStroke != null) strokes.push(currentStroke);
			currentStroke = null;
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			super.mousePressed(e);
			mouseDraw(e);
			updateDrawing();
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			super.mouseDragged(e);
			mouseDraw(e);
			mousePosition = null;
			updateDrawing();
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			
			super.mouseReleased(e);
			finishStroke();
			updateDrawing();
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
			super.mouseMoved(e);
			mousePosition = e.getPoint();
			repaint();
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
			super.mouseExited(e);
			mousePosition = null;
			repaint();
			
		}
		
	}

}