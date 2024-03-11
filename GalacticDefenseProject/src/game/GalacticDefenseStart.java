package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * {@summary } Space Invaders: Galactic Defense introduces a thrilling twist on
 * the classic Space Invaders game, where players take on the role of a space
 * defender tasked with protecting their home planet from an invading alien
 * force. The game features intense action-packed gameplay, combining
 * traditional space shooter elements with strategic defense mechanics.
 * 
 * Players control a powerful spaceship equipped with advanced weapons, engaging
 * in epic battles against waves of alien invaders. They must navigate through a
 * hazardous space environment, dodging enemy attacks while unleashing
 * devastating firepower to repel the alien onslaught.
 * 
 * Space Invaders: Galactic Defense offers an immersive gaming experience,
 * featuring stunning visuals, dynamic gameplay mechanics, and challenging
 * levels that will keep players engaged for hours on end. Are you ready to
 * defend the galaxy and save humanity from the alien invasion? Let the battle
 * begin!
 * 
 * class extends the Polygon class and implements the Element and keyListener
 * interface, allowing it to be rendered and interacted with in the game.
 * 
 * @author Tong Zhan, Hayden Wong.
 */
class GalacticDefenseStart extends Game implements KeyListener, Element {
	private static final long serialVersionUID = -9216557405129453958L;
	private static final long BLINK_DURATION = 1000;
	private PlayerShip playerShip;
	private Border border1;
	private Border border2;
	private Obstacle obstacle1;
	private Obstacle obstacle2;
	private Obstacle obstacle3;
	private Timer blinkingTimer;
	private int rows = 12;
	private int cols = 2;
	private int score = 0;
	private long blinkStartTime;
	private ArrayList<Aliens> aliens;
	private static boolean gameStarted = false, GameOver = false, showStartText = true;

	/**
	 * Default Constructor: sets the start of the Galactic Defense game
	 */
	public GalacticDefenseStart() {
		super("Space Invaders : Galactic Defense", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		this.addKeyListener(playerShip);
		Point[] shipPoints = { new Point(20, 0), new Point(0, 40), new Point(10, 30), new Point(20, 40),
				new Point(30, 30), new Point(40, 40), new Point(20, 0) };
		playerShip = new PlayerShip(shipPoints, new Point(400, 480), 0);
		aliens = Aliens.createAliens(rows, cols);
		Point[] borderPoints = { new Point(0, 0), new Point(3, 0), new Point(0, 100000) };
		Point[] borderPoints2 = { new Point(0, 0), new Point(3, 0), new Point(0, 100000) };
		border1 = new Border(borderPoints, new Point(0, 0), 0);
		border2 = new Border(borderPoints2, new Point(770.49, 0), 0);
		Point[] obstaclePoints = { new Point(10, 0), new Point(30, 5), new Point(40, 15), new Point(35, 25),
				new Point(25, 35), new Point(10, 30), new Point(5, 20), new Point(0, 10) };
		Point[] obstacle2Points = { new Point(5, 0), new Point(30, 5), new Point(40, 20), new Point(35, 35),
				new Point(25, 35), new Point(5, 30), new Point(0, 20) };

		Point[] obstacle3Points = { new Point(0, 0), new Point(25, 5), new Point(40, 15), new Point(35, 30),
				new Point(20, 35), new Point(0, 30) };
		obstacle1 = new Obstacle(obstaclePoints, new Point(375, 300), 0);
		obstacle2 = new Obstacle(obstacle2Points, new Point(150, 300), 0);
		obstacle3 = new Obstacle(obstacle3Points, new Point(600, 300), 0);
	}

	/**
	 * Starts the blinking animation for the start/end text.
	 */
	public void startBlinking() {
		if (blinkingTimer != null) {
			blinkingTimer.cancel();
		}
		blinkingTimer = new Timer();
		blinkingTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				showStartText = !showStartText;
				repaint();
			}
		}, 0, BLINK_DURATION / 2);
	}

	/**
	 * This private inner class instantiates and processes the health system of the
	 * Player's ship. The bar begins by being filled by green colors, and as the
	 * ship's health decreases, it begins to be filled by red.
	 * 
	 * Max Health: 100 points. Game will end with a loss screen at 0.
	 */
	private static class HealthBar {
		private PlayerShip playerShip;

		public HealthBar(PlayerShip playerShip) {
			this.playerShip = playerShip;
		}

		public void paint(Graphics brush) {
			int healthBarX = 10;
			int healthBarY = 533;
			int healthBarWidth = 200;
			int healthBarHeight = 20;
			int currentHealth = playerShip.getHealth();
			int maxHealth = playerShip.getMaxHealth();
			int filledWidth = (int) ((double) currentHealth / maxHealth * healthBarWidth);
			brush.setColor(Color.WHITE);
			brush.drawRect(healthBarX - 1, healthBarY - 1, healthBarWidth + 2, healthBarHeight + 2);
			brush.setColor(Color.RED);
			brush.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
			brush.setColor(Color.GREEN);
			brush.fillRect(healthBarX, healthBarY, filledWidth, healthBarHeight);
			brush.setColor(Color.WHITE);
			brush.setFont(new Font("Arial", Font.BOLD, 18));
			brush.drawString("Health Bar", healthBarX + 5, healthBarY + healthBarHeight - 25);
			int healthPercentage = currentHealth;
			String percentageText = healthPercentage + "%";
			int textWidth = brush.getFontMetrics().stringWidth(percentageText);
			brush.drawString(percentageText, healthBarX + healthBarWidth - textWidth - 75,
					healthBarY + healthBarHeight - 4);
		}
	}

	/*
	 * Similarly to the HealthBar inner class, the Ammo Count processes and
	 * instantiates the Player Ship's total ammo count,
	 */
	private static class AmmoCount {
		private PlayerShip playerShip;

		public AmmoCount(PlayerShip playerShip) {
			this.playerShip = playerShip;
		}

		public void paint(Graphics brush) {
			int ammoX = 578;
			int ammoY = 533;
			int ammoWidth = 200;
			int ammoHeight = 20;
			int currentAmmo = playerShip.getAmmo();
			int maxAmmo = playerShip.getMaxAmmo();

			brush.setColor(Color.WHITE);
			brush.drawRect(ammoX - 1, ammoY - 1, ammoWidth + 2, ammoHeight + 2);
			brush.setColor(Color.LIGHT_GRAY);
			brush.fillRect(ammoX, ammoY, ammoWidth, ammoHeight);
			int filledWidth = (int) ((double) currentAmmo / maxAmmo * ammoWidth);
			brush.setColor(Color.BLUE);
			brush.fillRect(ammoX, ammoY, filledWidth, ammoHeight);

			brush.setColor(Color.WHITE);
			brush.setFont(new Font("Arial", Font.BOLD, 18));
			brush.drawString("Ammo Count", ammoX + 5, ammoY + ammoHeight - 25);
			if (!(currentAmmo > 30)) {
				brush.setColor(Color.red);
				brush.setFont(new Font("Arial", Font.BOLD, 18));
			}
			brush.drawString(currentAmmo + "/" + maxAmmo, ammoX + 75, ammoY + ammoHeight - 4);
		}
	}

	/**
	 * Starts and generates the entire game element on the GUI when the game is
	 * being run. All element specific mechanics and objects are drawn and initiated
	 * here. Also includes a segment for start/end screen.
	 * 
	 * @param brush The graphics element used for painting
	 * 
	 */
	public void paint(Graphics brush) {
		// Fill the background with black color
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// Check if the game has started
		if (!gameStarted) {
			// If not started, display the start screen
			brush.setColor(Color.green);
			brush.setFont(new Font("Arial", Font.BOLD, 46));
			brush.drawString("Space Invaders: Galactic Defense", 20, 200);
			brush.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 25));
			brush.drawString("Get ready for the gaming experience of a lifetime!", 98, 350);
			brush.setFont(new Font("Arial", Font.PLAIN, 20));

			// Blinking "Press Enter to play now" message
			if (showStartText) {
				brush.setColor(Color.yellow);
				brush.drawString("Press Enter to play now", 300, 500);
			}
		} else {
			/*
			 * If the game has started Check if there are still aliens remaining, and player
			 * ship has health and ammo.
			 */
			if (!aliens.isEmpty() && playerShip.getHealth() > 0 && playerShip.getAmmo() > 0) {
				// If conditions are met, continue the game

				// Move and paint aliens
				aliens.forEach(alien -> {
					alien.move();
					alien.paint(brush);
				});

				// Paint borders and obstacles
				border1.paint(brush);
				border2.paint(brush);
				obstacle1.move();
				obstacle1.paint(brush);
				obstacle2.move();
				obstacle2.paint(brush);
				obstacle3.move();
				obstacle3.paint(brush);
				playerShip.move();

				// Move and paint player's bullets
				Iterator<Bullets> iterator = playerShip.getBullets().iterator();
				ArrayList<Bullets> bulletsToRemove = new ArrayList<>();
				while (iterator.hasNext()) {
					Bullets bullet = iterator.next();
					bullet.move();
					bullet.paint(brush);

					// Check for collisions with aliens and obstacles
					for (Iterator<Aliens> alienIterator = aliens.iterator(); alienIterator.hasNext();) {
						Aliens alien = alienIterator.next();
						if (bullet.collides(alien)) {
							iterator.remove();
							alienIterator.remove();
							score++;
						}
						if (bullet.collides(obstacle1) || bullet.collides(obstacle2) || bullet.collides(obstacle3)) {
							bulletsToRemove.add(bullet);
						}
					}
				}
				playerShip.getBullets().removeAll(bulletsToRemove);

				// Move and paint alien bullets
				Iterator<Bullets> alienBulletIterator = Aliens.getBullets().iterator();
				ArrayList<Bullets> alienBulletsToRemove = new ArrayList<>();
				while (alienBulletIterator.hasNext()) {
					Bullets alienBullet = alienBulletIterator.next();
					if (alienBullet.collides(obstacle1) || alienBullet.collides(obstacle2)
							|| alienBullet.collides(obstacle3)) {
						alienBulletsToRemove.add(alienBullet);
					}
				}
				Aliens.getBullets().removeAll(alienBulletsToRemove);

				// Paint health bar and ammo count
				HealthBar healthBar = new HealthBar(playerShip);
				healthBar.paint(brush);

				AmmoCount ammoCount = new AmmoCount(playerShip);
				ammoCount.paint(brush);

				// Check for collision with enemy bullets
				if (playerShip.collides(Aliens.getBullets())) {
					playerShip.deductHealth(1);
					playerShip.toggleBlinking();
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							playerShip.toggleBlinking();
						}
					}, BLINK_DURATION);
				}

				// Check for collision with borders
				if (playerShip.collides(border1)) {
					double newX = Math.max(playerShip.position.x, border1.position.x);
					double newY = Math.max(playerShip.position.y, border1.position.y);
					playerShip.position = new Point(newX, newY);
					playerShip.paint(brush);
				}
				if (playerShip.collides(border2)) {
					double newX = Math.min(playerShip.position.x, border2.position.x);
					double newY = Math.max(playerShip.position.y, border2.position.y);
					playerShip.position = new Point(newX, newY);
					playerShip.paint(brush);
				}

				// Paint player ship and score
				playerShip.paint(brush);
				brush.setColor(Color.white);
				Font font = new Font("Arial", Font.BOLD, 25);
				brush.setFont(font);
				brush.drawString("Score: " + score, 10, 25);
				playerShip.paint(brush);

			} else {
				// If game over condition is met
				if (aliens.isEmpty()) {
					// If player wins
					if ((System.currentTimeMillis() - blinkStartTime) % 1000 < 500) {
						brush.setColor(Color.ORANGE);
					} else {
						brush.setColor(Color.white);
					}
					Font font = new Font("Arial", Font.BOLD, 100);
					brush.setFont(font);
					brush.drawString("You Win!", 170, 250);
					brush.setFont(new Font("Arial", Font.BOLD, 30));
					brush.drawString("Score: " + score + " / " + rows * cols, 300, 360);
					brush.setFont(new Font("Arial", Font.PLAIN, 20));
					brush.drawString("Press Escape to exit and Backspace to try again.", 195, 450);
					GameOver = true;

				} else if (playerShip.getHealth() <= 0 || playerShip.getAmmo() == 0) {
					// If player loses
					if ((System.currentTimeMillis() - blinkStartTime) % 1000 < 500) {
						brush.setColor(Color.red);
					} else {
						brush.setColor(Color.white);
					}
					brush.setFont(new Font("Arial", Font.BOLD, 100));
					brush.drawString("You Lost!", 170, 250);
					brush.setFont(new Font("Arial", Font.BOLD, 30));
					brush.drawString("Score: " + score + " / " + rows * cols, 300, 360);
					brush.setFont(new Font("Arial", Font.PLAIN, 20));
					brush.drawString("Press Escape to exit and Backspace to try again.", 195, 450);
					GameOver = true;

				}
				// If game over and game started, listen for backspace and escape keys
				if (GameOver && gameStarted) {
					addKeyListener(new KeyAdapter() {
						public void keyPressed(KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
								// Reset game if backspace is pressed
								GameOver = false;
								gameStarted = false;
								score = 0;
								playerShip.resetHealth();
								playerShip.setAmmo();
								aliens = Aliens.createAliens(rows, cols);
								startBlinking();
								repaint();
							} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
								// Exit game if escape is pressed
								System.exit(0);
							}
						}
					});
				}
			}
		}
	}

	/**
	 * Not needed since no direct keyboard input is needed in this game except
	 * movement keys, which are handled by the keyPressed method.
	 * 
	 * @param e registers the key strokes of the user
	 */
	public void keyTyped(KeyEvent e) {
		;
	}

	/**
	 * When a dedicated key for movement is pressed, the KeyEvent handler catches
	 * the input and moves the object.
	 * 
	 * @param e registers the key strokes of the user
	 */
	public void keyPressed(KeyEvent e) {
		if (!gameStarted) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				gameStarted = true;
				GameOver = false;
				score = 0;
				playerShip.resetHealth();
				playerShip.setAmmo();
				playerShip.resetBullets();
				aliens = Aliens.createAliens(rows, cols);
				startBlinking();
				repaint();
				repaint();
			}
		} else {
			if (!GameOver) {
				if (e.getKeyCode() == KeyEvent.VK_A) {
					playerShip.keyPressed(e);
				}

				if (e.getKeyCode() == KeyEvent.VK_D) {
					playerShip.keyPressed(e);
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					playerShip.keyPressed(e);
				}
			}
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
			playerShip.keyReleased(e);
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			playerShip.keyReleased(e);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			playerShip.keyReleased(e);
		}
	}

	/**
	 * Creates a game object and paints every object involved every tenth of a
	 * second.
	 * 
	 * @param args Passes through command line arguments
	 */
	public static void main(String[] args) {
		GalacticDefenseStart game = new GalacticDefenseStart();
		game.startBlinking();
		game.repaint();
	}

}