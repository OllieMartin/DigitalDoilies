import java.awt.Point;
/**
 * Extends Point to be able to hold reflected coordinates
 * 
 * @see Point
 * 
 * @author ojm1g16
 *
 */
@SuppressWarnings("serial")
public class StrokePoint extends Point {

	/**
	 * A point representing the reflection of this point.
	 * Made public for performance purposes when drawing.
	 */
	public Point reflection;

	/**
	 * Creates a new point with the given x and y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public StrokePoint(int x, int y) {
		this(x,y,0,0);
	}

	/**
	 * Creates a new point with the given x and y coordinates.
	 * Also adds x and y coordinates for the reflection of this point.
	 * 
	 * @param x
	 * @param y
	 * @param reflectionX
	 * @param reflectionY
	 */
	public StrokePoint(int x, int y, int reflectionX, int reflectionY) {
		super(x,y);
		this.reflection = new Point(reflectionX,reflectionY);
	}

}