import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Stroke {

	public Set<Point> points;
	public Set<Point> reflectedPoints;
	private int brushSize; // Stores the brush size of the stroke
	private Color colour; // Stores the colour of the stroke
	private boolean reflected; // Stores if the stroke should be reflected
	
	/**
	 * Creates a new stroke drawn by the user with specified parameters
	 * 
	 * @param brushSize The current size of the brush in the application
	 * @param colour The colour of the point
	 * @param reflected If the point is being drawn as a result of being reflected from another point
	 */
	public Stroke(int brushSize, Color brushColour, boolean reflected) {
		this.colour = brushColour;
		this.brushSize = brushSize;
		this.reflected = reflected;
		this.points = new HashSet<Point>();
		if (reflected) this.reflectedPoints = new HashSet<Point>();
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
	 * Returns a boolean value indicating if this stroke is to be reflected
	 * 
	 * @return true if this was a reflected point, else false
	 */
	public boolean getReflected() {
		return reflected;
	}
	
}
