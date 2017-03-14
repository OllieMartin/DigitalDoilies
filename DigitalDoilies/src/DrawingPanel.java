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
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

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
		strokes = new Stack<Stroke>();
		this.setBackground(DEFAULT_BACKGROUND_COLOUR);
		this.setMinimumSize(new Dimension(MINIMUM_PANEL_SIZE, MINIMUM_PANEL_SIZE));
		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if (currentStroke == null) currentStroke = new Stroke(brushSize, brushColour, reflect);

				if (currentStroke.getReflected()) {
					currentStroke.points.add(new StrokePoint(getWidth()/2 - e.getX(), getHeight()/2 - e.getY(), e.getX() - getWidth()/2, getHeight()/2 - e.getY()));
				} else {
					currentStroke.points.add(new StrokePoint(getWidth()/2 - e.getX(), getHeight()/2 - e.getY()));
				}

				mousePosition = null;
				repaint();
			}

		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if (currentStroke != null) strokes.push(currentStroke);
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

		StrokePoint lastPoint;

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
			lastPoint = null;

			for (StrokePoint p : s.points) {
				g2.setColor(s.getColour());
				g2.setStroke(new BasicStroke(s.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

				for (int i = 0; i < numberOfSectors; i++) {

					if (lastPoint != null) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
						if (s.getReflected()) {
							g2.drawLine(getWidth()/2 - p.reflection.x, getHeight()/2 - p.reflection.y, getWidth()/2 - lastPoint.reflection.x, getHeight()/2 - lastPoint.reflection.y);
						}
					}

					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				lastPoint = p;
			}
		}

		lastPoint = null;
		if (currentStroke != null) {
			for (StrokePoint p : currentStroke.points) {
				g2.setColor(currentStroke.getColour());
				g2.setStroke(new BasicStroke(currentStroke.getBrushSize(),BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

				for (int i = 0; i < numberOfSectors; i++) {

					if (lastPoint != null) {
						g2.drawLine(getWidth()/2 - p.x, getHeight()/2 - p.y, getWidth()/2 - lastPoint.x, getHeight()/2 - lastPoint.y);
						if (currentStroke.getReflected()) {
							g2.drawLine(getWidth()/2 - p.reflection.x, getHeight()/2 - p.reflection.y, getWidth()/2 - lastPoint.reflection.x, getHeight()/2 - lastPoint.reflection.y);
						}
					}


					g2.rotate(Math.PI*2/numberOfSectors, this.getWidth()/2,this.getHeight()/2);
				}
				lastPoint = p;
			}
		}

		if (mousePosition != null) {

			g2.setColor(new Color(getBrushColour().getRed(),getBrushColour().getGreen(),getBrushColour().getBlue(),150));
			g2.fillOval(mousePosition.x - getBrushSize()/2,mousePosition.y -getBrushSize()/2,getBrushSize(),getBrushSize());

		}

	}

}