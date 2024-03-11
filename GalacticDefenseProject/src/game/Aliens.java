package game;

import java.awt.*;
import java.util.*;

/**
 * The Aliens class represents the alien entities in the game. Aliens are
 * polygonal shapes that move horizontally across the screen and periodically
 * shoot bullets at the player.
 * 
 * This class extends the Polygon class and implements the Element interface,
 * allowing aliens to be rendered and interacted with in the game.
 * 
 * @author Tong Zhan, Hayden Wong
 */
public class Aliens extends Polygon implements Element {
	private static ArrayList<Aliens> aliens;
	private static ArrayList<Bullets> bullets;
	private static final double SHOOT_PROBABILITY = 0.001;
	public static boolean moveRight = true;

	/**
	 * Constructs an Aliens object with the specified shape, position, and rotation.
	 * 
	 * @param inShape    An array of points defining the shape of the alien.
	 * @param inPosition The position of the alien in the game world.
	 * @param inRotation Not used in this game; ignored.
	 */
	public Aliens(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		bullets = new ArrayList<>();
	}

	/**
	 * Renders the alien and its bullets on the screen using the provided graphics
	 * context.
	 * 
	 * @param brush The graphics context used for painting.
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.magenta);
		int[] xValues = new int[this.getPoints().length];
		int[] yValues = new int[this.getPoints().length];
		for (int i = 0; i < this.getPoints().length; i++) {
			xValues[i] = (int) this.getPoints()[i].x;
			yValues[i] = (int) this.getPoints()[i].y;
		}
		brush.fillPolygon(xValues, yValues, this.getPoints().length);

		for (Bullets bullet : bullets) {
			bullet.paint(brush);
		}
	}

	/**
	 * Creates and returns a list of aliens with the specified number of rows and
	 * columns.
	 * 
	 * @param rows    The number of rows of aliens to create.
	 * @param columns The number of columns of aliens to create.
	 * 
	 * @return An ArrayList containing the created aliens.
	 */
	public static ArrayList<Aliens> createAliens(int rows, int columns) {
		aliens = new ArrayList<>(rows * columns);
		Point[] alienPoints = { new Point(0, 20), new Point(10, 0), new Point(20, 20), new Point(15, 30),
				new Point(10, 25), new Point(5, 30) };
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				aliens.add(new Aliens(alienPoints, new Point(160 + i * 50, 50 + j * 40), 180));
			}
		}
		return aliens;
	}

	/**
	 * Moves the aliens horizontally across the screen and periodically shoots
	 * bullets. The move method is responsible for updating the position of the
	 * aliens and their bullets in the game world.
	 */
	public void move() {
		// move each bullet downwards
		for (Bullets bullet : bullets) {
			bullet.position.y += 0.25;
		}

		// randomly determine if each alien should shoot.
		Random random = new Random();
		for (Aliens alien : aliens) {
			if (random.nextDouble() < SHOOT_PROBABILITY) {
				Point[] bulletPoints = { new Point(0, 0), new Point(20, 0), new Point(20, 20) };
				bullets.add(new Bullets(bulletPoints, 
						new Point(alien.position.x + 2.7, alien.position.y + 16), 0));
			}
		}

		// moves the aliens horizontally back and forth once it collides with the border
		if (moveRight) {
			this.position.x += 2;
			if (this.position.x > 750) {
				moveRight = !moveRight;
			}
		}
		if (!moveRight) {
			this.position.x -= 2;
			if (this.position.x < 0) {
				moveRight = !moveRight;
			}
		}
	}

	/**
	 * return method for bullets.
	 * 
	 * @return ArrayList of Bullets.
	 */
	public static ArrayList<Bullets> getBullets() {
		return bullets;
	}
}