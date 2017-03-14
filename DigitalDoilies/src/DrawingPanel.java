import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

	////////////////////////////////////////
	
	/**
	 * Extends Point to represent a point drawn by the user
	 * 
	 * Contains information about the colour, brush size, and if this point is as a result of reflection
	 * 
	 * @see Point
	 * 
	 * @author ojm1g16
	 *
	 */
	private class DrawnPoint extends Point {
		
		private int brushSize; // Stores the brush size of the point
		private Color colour; // Stores the colour of the point
		private boolean reflected; // Stores if the point was drawn as a result of being reflected
		
		/**
		 * Creates a new point drawn by the user with specified parameters
		 * 
		 * @param x The x coordinate of the point
		 * @param y The y coordinate of the point
		 * @param brushSize The current size of the brush in the application
		 * @param colour The colour of the point
		 * @param reflected If the point is being drawn as a result of being reflected from another point
		 */
		public DrawnPoint(int x, int y, int brushSize, Color colour, boolean reflected) {
			super(x,y);
			this.colour = colour;
			this.brushSize = brushSize;
			this.reflected = reflected;
		}
		
		/**
		 * Get the brush size of the point
		 * 
		 * @return The brush size of the point
		 */
		public int getBrushSize() {
			return this.brushSize;
		}
		
		/**
		 * Get the colour of the point
		 * 
		 * @return The colour of the point
		 */
		public Color getColour() {
			return this.colour;
		}
		
		/**
		 * Returns a boolean value indicating if this point was drawn as a result of another point being reflected
		 * 
		 * @return true if this was a reflected point, else false
		 */
		public boolean getReflected() {
			return reflected;
		}
		
	}
	
	////////////////////////////////////////
	
	//private Stack<List<DrawnPoint>> points = new Stack<List<DrawnPoint>>(); // The image represented as a stack of drawn 'strokes' which are in turn stored as lists of DrawnPoints
	//private List<DrawnPoint> currentStroke = new ArrayList<DrawnPoint>(); // The current stroke being drawn by the user represented as a list of DrawnPoints
	private Point mousePosition; // The current position of the mouse on the drawing panel, null if the mouse is elsewhere
	private Stack<Stroke> strokes;
	private Stroke currentStroke;
	
	private int numberOfSectors; // The current number of sectors being used to draw
	private boolean showSectors; // True if the sector lines should be drawn, otherwise false
	
	private int brushSize; // The current size of the brush being used
	private Color brushColour; // The current colour of the brush being used
	
	private boolean reflect; // If new drawn points should be reflected within their respective sectors
	
	private static final int DEFAULT_BRUSH_SIZE = 5; // The default size of the brush
	private static final int MINIMUM_PANEL_SIZE = 200; // The minimum width/height for the drawing panel object
	private static final Color DEFAULT_BACKGROUND_COLOUR = Color.BLACK; // The background colour to be used for the drawing panel
	private static final Color DEFAULT_BRUSH_COLOUR = Color.WHITE; // The default colour to be selected for the brush
	
	/**
	 * Creates a new drawing panel with a specified number of sectors to begin with
	 * 
	 * @param defaultNumberOfSectors The number of sectors to be displayed by default
	 */
	public DrawingPanel(int defaultNumberOfSectors) {
		
		reflect = false;
		mousePosition = null;
		brushSize = DEFAULT_BRUSH_SIZE;
		brushColour = DEFAULT_BRUSH_COLOUR;
		showSectors = true;
		this.setBackground(DEFAULT_BACKGROUND_COLOUR);
		this.setMinimumSize(new Dimension(MINIMUM_PANEL_SIZE, MINIMUM_PANEL_SIZE));
		this.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if (currentStroke == null) currentStroke = new Stroke(brushSize, brushColour, reflect);
				currentStroke.points.add(new Point(getWidth()/2 - e.getX(), getHeight()/2 - e.getY()));
				if (currentStroke.getReflected()) {
					currentStroke.reflectedPoints.add(new Point(e.getX() - getWidth()/2, getHeight()/2 - e.getY()));
				}
				mousePosition = null;
				repaint();
			}
			
		});
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				strokes.push(currentStroke);
				currentStroke = null;
				repaint();
			}
			
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				mousePosition = e.getPoint();
				repaint();
			}
			
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				mousePosition = null;
				repaint();
			}
			
		});
		changeSectors(defaultNumberOfSectors);
	}
	
	public void changeSectors(int numberOfSectors) {
		
		this.numberOfSectors = numberOfSectors;
		
		repaint();
	}
	
public void toggleSectors() {
		
		showSectors = !showSectors;
		repaint();
	}
	
	public int getBrushSize() {
		return brushSize;
	}
	
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
		repaint();
	}
	
	public Color getBrushColour() {
		return brushColour;
	}
	
	public void setBrushColour(Color colour) {
		this.brushColour = colour;
		repaint();
	}
	
	public void toggleReflection() {
		reflect = !reflect;
		repaint();
	}
	
	public void clearPoints() {
		while (!strokes.isEmpty()) {
			strokes.pop();
		}
		currentStroke = null;
		repaint();
	}
	
	public int getSectors() {
		return numberOfSectors;
	}
	
	public void undo() {
		if (!strokes.isEmpty() ) {
			
			strokes.pop();
			repaint();
			
		}
	}
	
	public Image getImage() {
		BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		paintComponent(image.getGraphics());
		return image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Stack<List<DrawnPoint>> popped = new Stack<List<DrawnPoint>>();
		List<DrawnPoint> stroke = new ArrayList<DrawnPoint>();
		Point lastPoint;
		Point previousLastPoint;
		boolean reflectedStroke;
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		
		if (showSectors) {
			for (int i = 0; i < numberOfSectors; i++) {
				g2.drawLine(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight()/6);
				g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
			}
		}
		
		for (Stroke s : strokes) {
			stroke = popped.pop();
			lastPoint = null;
			previousLastPoint = null;
			reflectedStroke = false;
			for (Point p : s.points) {
				g2.setColor(p.getColour());
				g2.setStroke(new BasicStroke(p.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				if (p.getReflected()) reflectedStroke = true;
				for (int i = 0; i < numberOfSectors; i++) {
					if (lastPoint != null && reflectedStroke == false) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
					} else if (lastPoint != null && previousLastPoint != null && reflectedStroke) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - previousLastPoint.x, getHeight()/2 - previousLastPoint.y);
					}
					/*if (mousePosition != null) {
						
						g2.setColor(new Color(getBrushColour().getRed(),getBrushColour().getGreen(),getBrushColour().getBlue(),150));
						g2.fillOval(mousePosition.x - getBrushSize()/2,mousePosition.y -getBrushSize()/2,getBrushSize(),getBrushSize());
					
					}*/
					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				if (lastPoint != null) {
					previousLastPoint = lastPoint;
				}
				lastPoint = p;
			}
			points.push(stroke);
		}
		
		
		while (!popped.isEmpty()) {
			stroke = popped.pop();
			lastPoint = null;
			previousLastPoint = null;
			reflectedStroke = false;
			for (DrawnPoint p : stroke) {
				g2.setColor(p.getColour());
				g2.setStroke(new BasicStroke(p.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				if (p.getReflected()) reflectedStroke = true;
				for (int i = 0; i < numberOfSectors; i++) {
					if (lastPoint != null && reflectedStroke == false) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
					} else if (lastPoint != null && previousLastPoint != null && reflectedStroke) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - previousLastPoint.x, getHeight()/2 - previousLastPoint.y);
					}
					/*if (mousePosition != null) {
						
						g2.setColor(new Color(getBrushColour().getRed(),getBrushColour().getGreen(),getBrushColour().getBlue(),150));
						g2.fillOval(mousePosition.x - getBrushSize()/2,mousePosition.y -getBrushSize()/2,getBrushSize(),getBrushSize());
					
					}*/
					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				if (lastPoint != null) {
					previousLastPoint = lastPoint;
				}
				lastPoint = p;
			}
			points.push(stroke);
		}	
			
			lastPoint = null;
			previousLastPoint = null;
			reflectedStroke = false;
			for (DrawnPoint p : currentStroke) {
				g2.setColor(p.getColour());
				g2.setStroke(new BasicStroke(p.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				if (p.getReflected()) reflectedStroke = true;
				for (int i = 0; i < numberOfSectors; i++) {
					if (lastPoint != null && reflectedStroke == false) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
					} else if (lastPoint != null && previousLastPoint != null && reflectedStroke) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - previousLastPoint.x, getHeight()/2 - previousLastPoint.y);
					}
					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				if (lastPoint != null) {
					previousLastPoint = lastPoint;
				}
				lastPoint = p;
			}
			
			if (mousePosition != null) {
				
				g2.setColor(new Color(getBrushColour().getRed(),getBrushColour().getGreen(),getBrushColour().getBlue(),150));
				g2.fillOval(mousePosition.x - getBrushSize()/2,mousePosition.y -getBrushSize()/2,getBrushSize(),getBrushSize());
			
			}
		
	}
	
}