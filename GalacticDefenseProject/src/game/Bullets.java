package game;

import java.awt.*;

/**
 * The Bullets Class handles the bullets fired from all player and NPC objects.
 * It sets the firing rate, draws the object, and assign them to the objects
 * required.
 * 
 * This class extends the Polygon class and implements the Element interface,
 * allowing it to be rendered and interacted with in the game.
 * 
 * @author Tong Zhan, Hayden Wong
 */
public class Bullets extends Polygon implements Element {
	private boolean shoot = false;

	/**
	 * Initial Constructor for the Bullets Class. Coordinates are handled through
	 * the Polygon class.
	 * 
	 * @param inShape    Coordinates of the Bullets object when drawn.
	 * @param inPosition Position of the Bullets Object. Center of the screen as a
	 *                   default.
	 * @param inRotation Not Applicable in Bullets. Ignored for this class.
	 */
	public Bullets(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Default constructor for the Bullets Class.
	 * 
	 */
	public Bullets(PlayerShip playerShip) {
		super(new Point[] { new Point(0, 0), new Point(5, 0), new Point(5, 5), new Point(0, 5) },
				new Point(playerShip.position.x + 5, playerShip.position.y - 12), 0);
	}

	/**
	 * Draws the bullet element in the game.
	 * 
	 * @param brush
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.red);
		int[] xValues = new int[this.getPoints().length];
		int[] yValues = new int[this.getPoints().length];
		for (int i = 0; i < this.getPoints().length; i++) {
			xValues[i] = (int) this.getPoints()[i].x;
			yValues[i] = (int) this.getPoints()[i].y;
		}
		brush.fillOval(xValues[0], yValues[0], 8, 8);
	}

	/**
	 * Method for moving the object when the movement keys are pressed.
	 * 
	 * Default movement range is two pixels.
	 */
	public void move() {
		this.position.y -= 5;
	}

	/**
	 * Determines if the shooting mechanism has been triggered, then sets it to the
	 * condition accordingly.
	 * 
	 * @param shoot true if firing, false if not.
	 */
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	/**
	 * 
	 * @return boolean variable shoot.
	 */
	public boolean getShoot() {
		return shoot;
	}

	/**
	 * Collision handler for bullets when it collies with the aliens. Searches
	 * through the Bullet ArrayList for any instance of bullet having the same
	 * coordinates as the alien, and returns true if conditions are met, false
	 * otherwise.
	 * 
	 * @param bullets Takes in an ArrayList of Bullets and searches it for any
	 *                instances of the Bullet object having the same coordinates as
	 *                the Alien object.
	 * 
	 * @return true if an alien collides with bullets, false otherwise.
	 */
	public boolean collides(Aliens alien) {
		Point[] points = getPoints();
		for (int i = 0; i < 3; i++) {
			if (alien.contains(points[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Collision handler for Bullet object. Searches through the Bullet ArrayList
	 * for any instance of bullet having the same coordinates as the obstacles, and
	 * returns true if conditions are met, false otherwise.
	 * 
	 * @param bullets Takes in an ArrayList of Bullets and searches it for any
	 *                instances of the Bullet object having the same coordinates as
	 *                the obstacles.
	 * 
	 * @return true if Player's ship collides with the coordinates of the obstacles,
	 *         false otherwise.
	 */
	public boolean collides(Obstacle obstacle) {
		Point[] points = getPoints();
		for (int i = 0; i < 3; i++) {
			if (obstacle.contains(points[i])) {
				return true;
			}
		}
		return false;
	}
}
