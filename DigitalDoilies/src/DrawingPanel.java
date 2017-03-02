import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {
	
	private int numberOfSectors;
	private Stack<List<Point>> points = new Stack<List<Point>>();
	private List<Point> currentStroke = new ArrayList<Point>();
	private boolean reflect;
	private int brushSize;
	
	public DrawingPanel(int defaultNumberOfSectors) {
		reflect = false;
		numberOfSectors = 0;
		brushSize = 5;
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(400,400));
		this.setMinimumSize(new Dimension(200,200));
		this.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				currentStroke.add(new Point(getWidth()/2 - e.getX(), getHeight()/2 - e.getY()));
				if (reflect) {
					currentStroke.add(new Point(e.getX() - getWidth()/2, getHeight()/2 - e.getY()));
				}
				repaint();
			}
			
		});
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				points.push(new ArrayList<Point>(currentStroke));
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
	
	public int getBrushSize() {
		return brushSize;
	}
	
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
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
		
		Stack<List<Point>> popped = new Stack<List<Point>>();
		List<Point> stroke = new ArrayList<Point>();
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.WHITE);
		
		for (int i = 0; i < numberOfSectors; i++) {
			//System.out.println(this.getWidth()/2 + " " + this.getHeight()/2+ " " + this.getWidth()/2+ " " + this.getHeight()/6);
			g.drawLine(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2, this.getHeight()/6);
			//System.out.println(Math.PI*2/numberOfSectors + " " +  this.getWidth()/2 + " " + this.getHeight()/2);
			
			while (!points.isEmpty()) {
				stroke = points.pop();
				for (Point p : stroke) {
					g2.fillOval(getWidth()/2 - p.x, getHeight()/2 - p.y, brushSize, brushSize);
				}
				popped.push(stroke);
			}
			
			for (Point p : currentStroke) {
				g2.fillOval(getWidth()/2 - p.x, getHeight()/2 - p.y, brushSize, brushSize);
			}
			
			while (!popped.isEmpty()) {
				stroke = popped.pop();
				points.push(stroke);
			}
			
			g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
			
		}
		
	}
	
}