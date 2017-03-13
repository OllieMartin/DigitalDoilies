import java.awt.Color;
import java.awt.geom.Path2D;

public class Stroke {

	public Path2D path; // Stores the path of points that makes up the stroke
	private int brushSize; // Stores the brush size of the point
	private Color colour; // Stores the colour of the point
	private boolean reflected; // Stores if the point was drawn as a result of being reflected
	
	public Stroke(Color brushColour, int brushSize, boolean reflectedStroke) {
		 this(new Path2D.Double(), brushColour, brushSize, reflectedStroke);
	}
	
	public Stroke(Path2D path, Color brushColour, int brushSize, boolean reflectedStroke) {
		 this.path = path;
	}
	
	/**
	 * Get the brush size of the stroke
	 * 
	 * @return The brush size of the stroke
	 */
	public int getBrushSize() {
		return this.brushSize;
	}
	
	/**
	 * Get the colour of the stroke
	 * 
	 * @return The colour of the stroke
	 */
	public Color getColour() {
		return this.colour;
	}
	
	/**
	 * Returns a boolean value indicating if this stroke is a reflected stroke
	 * 
	 * @return true if this was a reflected stroke, else false
	 */
	public boolean getReflected() {
		return reflected;
	}
	
}
