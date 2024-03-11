package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The Border class represents a border element in the game, which is a
 * polygonal shape used to define the boundaries of the game environment.
 * Borders are typically used to prevent game objects from moving beyond certain
 * limits.
 * 
 * This class extends the Polygon class and implements the Element interface,
 * allowing it to be rendered and interacted with in the game.
 * 
 * @author Tong Zhan, Hayden Wong.
 */
public class Border extends Polygon implements Element {
	/**
	 * Constructs a Border object with the specified shape, position, and rotation.
	 * 
	 * @param inShape    An array of points defining the shape of the border.
	 * @param inPosition The position of the border in the game world.
	 * @param inRotation Not used in this game; ignored.
	 */
	public Border(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Renders the border on the screen using the provided graphics context.
	 * 
	 * @param brush The graphics context used for painting.
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		int[] xValues = new int[this.getPoints().length];
		int[] yValues = new int[this.getPoints().length];
		for (int i = 0; i < this.getPoints().length; i++) {
			xValues[i] = (int) this.getPoints()[i].x;
			yValues[i] = (int) this.getPoints()[i].y;
		}
		brush.fillPolygon(xValues, yValues, this.getPoints().length);
	}

}