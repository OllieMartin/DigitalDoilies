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

	public Point reflection;

	public StrokePoint(int x, int y) {
		this(x,y,0,0);
	}

	public StrokePoint(int x, int y, int reflectionX, int reflectionY) {
		super(x,y);
		this.reflection = new Point(reflectionX,reflectionY);
	}

}