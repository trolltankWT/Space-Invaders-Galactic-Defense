package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Obstacle class represents asteroid objects within the game environment. It
 * rotates in a set point for the entire duration of the game. It serves as
 * shields for the aliens and Player ship bullets, in which the asteroid absorbs
 * the bullets completely.
 * 
 * This class extends the Polygon class and implements the Element interface,
 * allowing it to be drawn and interacted with in the game. Obstacles are static
 * elements in the game that do not move, but may have rotating animations.
 * 
 * @author Tong Zhan, Hayden Wong.
 */
public class Obstacle extends Polygon implements Element {

	/**
	 * Constructs a new Obstacle object with the specified shape, position, and
	 * rotation.
	 * 
	 * @param inShape    The array of points defining the shape of the obstacle.
	 * @param inPosition The position of the obstacle in the game world.
	 * @param inRotation The initial rotation angle of the obstacle.
	 */
	public Obstacle(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Paints the obstacle on the screen.
	 * 
	 * This method overrides the paint method defined in the Element interface. It
	 * sets the color of the obstacle to gray and fills the polygon defined by its
	 * points.
	 * 
	 * @param brush The graphics object used for painting.
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.gray);
		int[] xValues = new int[this.getPoints().length];
		int[] yValues = new int[this.getPoints().length];
		for (int i = 0; i < this.getPoints().length; i++) {
			xValues[i] = (int) this.getPoints()[i].x;
			yValues[i] = (int) this.getPoints()[i].y;
		}
		brush.fillPolygon(xValues, yValues, this.getPoints().length);
	}

	/**
	 * Moves the obstacle.
	 * 
	 * This method is responsible for updating the rotation of the obstacle. Each
	 * time it is called, it increments the rotation angle by 1 degree, causing the
	 * obstacle to rotate slowly over time.
	 */
	public void move() {
		this.rotation += 1;
	}

}