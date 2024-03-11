package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * PlayerShip class handles the ship object that the player controls, where
 * health points, movement and shooting mechanics are implemented here.
 * 
 * 
 * This class extends the Polygon class and implements the Element and
 * keyListener interface, allowing it to be rendered and interacted within the
 * game.
 * 
 * @author Tong Zhan, Hayden Wong
 */
public class PlayerShip extends Polygon implements KeyListener, Element {
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean shot = false;
	private boolean isBlinking;
	private ArrayList<Bullets> bullets;
	private int health, ammo, stepSize = 5;
	private long blinkStartTime;
	private static final int MAX_HEALTH = 100;
	private static final int MAX_AMMO = 100;

	/**
	 * Initial Constructor for the PlayerShip Class. Coordinates are handled through
	 * the Polygon class.
	 * 
	 * @param inShape    Coordinates of the Ship object when drawn.
	 * @param inPosition Position of the Ship Object. Center of the screen as a
	 *                   default.
	 * @param inRotation Degree of the Ship Object when it is rotating. Not used.
	 */
	public PlayerShip(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		bullets = new ArrayList<>();
		this.health = MAX_HEALTH;
		this.ammo = MAX_AMMO;
	}

	/**
	 * Default constructor for the PlayerShip Class.
	 * 
	 */
	public PlayerShip() {
		super(null, null, 0);
		bullets = new ArrayList<>();
		this.health = MAX_HEALTH;
		this.ammo = MAX_AMMO;
	}

	/**
	 * Draws the PlayerShip element in the game.
	 * 
	 * @param brush
	 */
	public void paint(Graphics brush) {
		if (!isBlinking || (System.currentTimeMillis() - blinkStartTime) % 200 < 100) {
			brush.setColor(Color.white);
		} else {
			brush.setColor(Color.red);
		}
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
	 * Process Method for moving the object when the movement keys are pressed.
	 * 
	 * Default movement range is five pixels.
	 */
	public void move() {
		if (moveLeft) {
			double newX = position.x - stepSize;
			this.position.setX(newX);
		}

		if (moveRight) {
			double newX = position.x + stepSize;
			this.position.setX(newX);
		}
		if (shot) {
			for (Bullets bullet : bullets) {
				bullet.move();
			}
		}
	}

	/**
	 * Not needed since no direct keyboard input is needed in this game except
	 * movement keys, which are handled by the keyPressed method.
	 */
	public void keyTyped(KeyEvent e) {
		; // No implementation need.
	}

	/**
	 * When a dedicated key for movement is pressed, the KeyEvent handler catches
	 * the input and moves the object.
	 * 
	 * @param e registers the key strokes of the user
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			shot = true;
			Point[] bulletPoints = { new Point(0, 0), new Point(8, 0), new Point(8, 8) };
			bullets.add(new Bullets(bulletPoints, new Point(position.getX() + 9.5,
					position.getY() - 10), rotation));
			ammo--;
		}
	}

	/**
	 * When a dedicated key for movement is released from being pressed, the
	 * KeyEvent handler catches the input and stops moving the object.
	 * 
	 * @param e registers the key strokes of the user
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
		}
	}

	/**
	 * Collision handler for Player's ship. Searches through the Bullet ArrayList
	 * for any instance of bullet having the same coordinates as the Player's ship,
	 * and returns true if conditions are met, false otherwise.
	 * 
	 * @param bullets Takes in an ArrayList of Bullets and searches it for any
	 *                instances of the Bullet object having the same coordinates as
	 *                the Player's ship.
	 * 
	 * @return true if Player's ship collides with bullets of the Aliens, false
	 *         otherwise.
	 */
	public boolean collides(ArrayList<Bullets> bullets) {
		for (Bullets bullet : bullets) {
			for (Point point : this.getPoints()) {
				if (bullet.contains(point)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * return method for moveLeft
	 * 
	 * @return true if playerShip movedLeft, false otherWise.
	 */
	public boolean isMoveLeft() {
		return moveLeft;
	}

	/**
	 * return method for moveRight
	 * 
	 * @return true if playerShip moved right, false otherWise.
	 */
	public boolean isMoveRight() {
		return moveRight;
	}

	/**
	 * return method for bullets.
	 * 
	 * @return ArrayList of Bullets.
	 */
	public ArrayList<Bullets> getBullets() {
		return bullets;
	}

	/**
	 * Resets bullets by creating a new Bullet object.
	 */
	public void resetBullets() {
		bullets = new ArrayList<>();
	}

	/**
	 * getter method for the shot boolean. This boolean checks whether or not the
	 * Player has fired a shot.
	 * 
	 * @return true if shot, false otherwise.
	 */
	public boolean getShot() {
		return shot;
	}

	/**
	 * getter method for the Player's current ship health.
	 * 
	 * @return integer value of the health points the Player current has.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * getter method for the Player's max ship health.
	 * 
	 * @return integer value of the most health points the Player can have. 100 by
	 *         default.
	 */
	public int getMaxHealth() {
		return MAX_HEALTH;
	}

	/**
	 * resets the health of the Player's ship by setting it to the max health value.
	 */
	public void resetHealth() {
		health = MAX_HEALTH;
	}

	/**
	 * getter method for the Player's ship's current ammo count.
	 * 
	 * @return ammo count value in type integer.
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * getter method for the Player's ship's max ammo count.
	 * 
	 * @return max ammo count value in type integer. 100 by default.
	 */
	public int getMaxAmmo() {
		return MAX_AMMO;
	}

	/**
	 * setter method for the Player's ship's ammo count to max ammo value.
	 */
	public void setAmmo() {
		this.ammo = MAX_AMMO;
	}

	/**
	 * this method determines whether or not the ship has shot and sets it to the
	 * current shot variable.
	 * 
	 * @param shot true if ship has shot a bullet, false otherwise.
	 */
	public void setShot(boolean shot) {
		this.shot = shot;
	}

	/**
	 * this method takes in a new stepSize, or ship speed value, and sets it to the
	 * current speed value.
	 * 
	 * @param stepSize new ship speed.
	 */
	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}

	/**
	 * this method toggles the blinking effect of the Player ship by turning the
	 * isBlinking variable to false/true when called, and initiates the blink delay
	 * time.
	 */
	public void toggleBlinking() {
		isBlinking = !isBlinking;
		blinkStartTime = System.currentTimeMillis();
	}

	/**
	 * deducts the Player ship health by the new amount.
	 * 
	 * @param amount the amount of health deducted once the ship collides with a
	 *               bullet.
	 */
	public void deductHealth(int amount) {
		health -= amount;
	}
}
