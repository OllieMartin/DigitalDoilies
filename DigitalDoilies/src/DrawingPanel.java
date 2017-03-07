import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
	//
	
	private class DrawnPoint extends Point {
		
		private int brushSize;
		private Color colour;
		private boolean reflected;
		
		public DrawnPoint(int x, int y, int brushSize, Color colour, boolean reflected) {
			super(x,y);
			this.colour = colour;
			this.brushSize = brushSize;
			this.reflected = reflected;
		}
		
		public int getBrushSize() {
			return this.brushSize;
		}
		
		public Color getColour() {
			return this.colour;
		}
		
		public boolean getReflected() {
			return reflected;
		}
		
	}
	
	//
	
	private int numberOfSectors;
	private Stack<List<DrawnPoint>> points = new Stack<List<DrawnPoint>>();
	private List<DrawnPoint> currentStroke = new ArrayList<DrawnPoint>();
	private boolean reflect;
	private int brushSize;
	private Color brushColour;
	private boolean showSectors;
	
	public DrawingPanel(int defaultNumberOfSectors) {
		reflect = false;
		numberOfSectors = 0;
		brushSize = 5;
		brushColour = Color.WHITE;
		showSectors = true;
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400,400));
		this.setMinimumSize(new Dimension(200,200));
		this.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				currentStroke.add(new DrawnPoint(getWidth()/2 - e.getX(), getHeight()/2 - e.getY(), brushSize, brushColour, false));
				if (reflect) {
					currentStroke.add(new DrawnPoint(e.getX() - getWidth()/2, getHeight()/2 - e.getY(), brushSize, brushColour, true));
				}
				repaint();
			}
			
		});
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				points.push(new ArrayList<DrawnPoint>(currentStroke));
				currentStroke.clear();
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
		while (!points.isEmpty()) {
			points.pop();
		}
		currentStroke.clear();
		repaint();
	}
	
	public int getSectors() {
		return numberOfSectors;
	}
	
	public void undo() {
		if (!points.isEmpty() ) {
			
			points.pop();
			repaint();
			
		}
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
		
		while (!points.isEmpty()) {
			stroke = points.pop();
			popped.push(stroke);
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
		
	}
	
}