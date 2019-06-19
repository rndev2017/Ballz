
/**  
* Game.java - contains the logic and graphics behind our remastered version of Ballz.  
* @author  Rohan Nagavardhan & Mourya Chimpiri
* @version 1.0
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener {
	private MovingBall ball;
	private Rectangle topBorder, leftBorder, rightBorder;
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;
	private static int level = 1;
	private int[] randomPattern1, randomPattern2, randomPattern3, randomPattern4, randomPattern5;
	private ArrayList<Box[]> box2d;
	private int inc;
	private final int topBorderScale = 15;
	private final int sideBorderScale = 20;
	private int movingBallInitialX;
	private int movingBallInitialY;

	public Game() {
		setBackground(Color.BLACK);
		inc = Box.height + 19;
		

		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
	}

	public void init(Dimension screenSize) {
		movingBallInitialX = (int) (screenSize.getWidth() / 4);
		movingBallInitialY = ((int) (screenSize.getHeight())) - 500;

		setSize(screenSize.width / 2, screenSize.height);
		ball = new MovingBall(movingBallInitialX, movingBallInitialY);
		topBorder = new Rectangle(0, 0, (int) this.getSize().getWidth(),
				(int) this.getSize().getHeight() / topBorderScale);
		leftBorder = new Rectangle(0, 0, (int) super.getSize().getWidth() / sideBorderScale,
				(int) super.getSize().getHeight());
		rightBorder = new Rectangle(
				(int) super.getSize().getWidth() - (int) super.getSize().getWidth() / sideBorderScale, 0,
				(int) super.getSize().getWidth() / sideBorderScale, (int) super.getSize().getHeight());
		randomPattern1 = generateRandomPlacement();
		randomPattern2 = generateRandomPlacement();
		randomPattern3 = generateRandomPlacement();
		randomPattern4 = generateRandomPlacement();
		randomPattern5 = generateRandomPlacement();

		box2d = new ArrayList<Box[]>(); // ArrayList of Box rows
		box2d.add(createArray(getPattern(), (int)super.getHeight()/4));

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (playing) {
			step();
		}
	}

	public void step() {

		if (playing) {

			if ((int) ball.getY() < movingBallInitialY) {

				ball.move();
				collide();
				
			} else {
				ball.setBallDeltaX(0);
				ball.setBallDeltaY(0);

			}
		}
		repaint();

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (showTitleScreen) {
			paintTitleScreen(g);
		} else if (playing) {
			ball.paint(g);
			for (Box[] b : box2d) {
				drawRow(b, g);
			}
			g.setColor(Color.gray);
			g.fillRect(0, 0, (int) this.getSize().getWidth(), (int) this.getSize().getHeight() / topBorderScale);
			g.fillRect(0, 0, (int) super.getSize().getWidth() / sideBorderScale, (int) super.getSize().getHeight());
			g.fillRect((int) super.getSize().getWidth() - (int) super.getSize().getWidth() / sideBorderScale, 0,
					(int) super.getSize().getWidth() / sideBorderScale, (int) super.getSize().getHeight());

		} else if (gameOver) {

		}
	}

	public void startGame() {
		Timer t = new Timer(1000 / 30, this);
		t.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				showTitleScreen = false;
				playing = true;
				startGame();
			}
		}
		if (gameOver) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				gameOver = false;
				showTitleScreen = true;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (playing) {
			int finalX = arg0.getX();
			int finalY = arg0.getY();			
			ball.setBallDeltaY(finalY - (int) ball.getY());
			//ball.setBallDeltaY(50);
			if (finalX - (int) ball.getX() > 50) {
				ball.setBallDeltaX(ball.getDiameter() - 20);				
			} 
			else{
				ball.setBallDeltaX(finalX - (int) ball.getX());
			}
			ball.move();
			collide();
			
		}

	}
	
	    		 
	       
	public boolean collide() {
		if (ball.intersects(rightBorder)) {
			ball.setBallDeltaX(ball.getBallDeltaX() * -1);
			return true;
		} else if (ball.intersects(leftBorder)) {
			ball.setBallDeltaX(ball.getBallDeltaX() * -1);
			return true;
		} else if (ball.intersects(topBorder)) {
			ball.setBallDeltaY(ball.getBallDeltaY() * -1);

			return true;
		} else if (ball.getLocation().getY() >= movingBallInitialY) {
			ball.setLocation((int) ball.getLocation().getX(), movingBallInitialY);
			ball.setBallDeltaX(0);
			ball.setBallDeltaY(0);
			Box.shiftRow(box2d);
			box2d.add(createArray(getPattern(),(int) super.getHeight()/4));
			return true;
		} else {
			{
				for (int i = 0; i < box2d.size(); i++) {
					for (Box b : box2d.get(i)) {
						if (b != null) {

							if (ball.intersects(b)) {
								Rectangle intersection = ball.intersection(b);
								int intersectionX = (int) intersection.getLocation().getX();
								int intersectionY = (int) intersection.getLocation().getY();
								if (intersectionX == b.getLocation().getX() && intersectionY >= b.getLocation().getY()
										&& intersectionY <= b.getLocation().getY() + 100) {
									ball.setBallDeltaX(ball.getBallDeltaX() * -1);
								} else if (intersectionX == b.getLocation().getX() + 100
										&& intersectionY >= b.getLocation().getY()
										&& intersectionY <= b.getLocation().getY() + 100) {
									ball.setBallDeltaX(ball.getBallDeltaX() * -1);
									else if((intersectionX == b.getLocation().getX() + 100|| intersectionX==b.getLocation().getX())
                    &&(intersectionY == b.getLocation().getY()
                    || intersectionY == b.getLocation().getY() + 100))
                {
                  ball.setBallDeltaX(ball.getBallDeltaX() * -1);
                  ball.setBallDeltaY(ball.getBallDeltaY() * -1);
                  if (box2d.get(i)[index].getHealth() - getLevel() > 0)
                  {
                    box2d.get(i)[index].setHealth(b.getHealth() - level);
                    box2d.get(i)[index].getColor(b.getHealth());
                  }
                  else
                  {
                    box2d.get(i)[index] = null;
                  }
								} else {
									ball.setBallDeltaY(ball.getBallDeltaY() * -1);
								}
								int newHealth = b.getHealth() - level;
								if (newHealth == 0) {
									b = null;
								} else {
									b.getColor(newHealth);
								}
								return true;
							}
						}
					}
				}
				return false;
			}
		}
	}

	public void paintTitleScreen(Graphics g) {
		// Sets the font of the title and sets it relative to the size of the JPanel for
		// automatic resizing
		g.setColor(new Color(255, 102, 0)); // Dark Orange
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 150));
		g.drawString("Ballz", (int) getWidth() / 3, (int) (super.getHeight() / 4));

		// Sets the font of the instructions and sets it relative to the size of the
		// JPanel for automatic resizing
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		g.drawString("Press 'P' to play.", getWidth() / 3, (int) (getHeight() / 1.5));
	}

	public Box[] createArray(int[] pattern, int yPos) {
		Box[] arr = new Box[(int)super.getWidth()/(75 + 19)];
		for (int i = 0; i < arr.length;) {
			arr[pattern[i]] = new Box((pattern[i]*inc), yPos);
			if (arr[pattern[i]] != null) {
				i++;
			}
		}
		return arr;
	}

	public int[] generateRandomPlacement() {
		int[] randomPlacements = new int[(int)super.getWidth() / (75 + 19)];
		for (int i = 0; i < randomPlacements.length; i++) {
			randomPlacements[i] = ((int) (Math.random() * ((int)super.getWidth() / (75 + 19))));
		}
		return randomPlacements;
	}

	public void drawRow(Box[] arr, Graphics g) {
		for (Box b : arr) {
			if (b != null) {
				b.paint(g);
			}
		}
	}

	public int[] getPattern() {
		int rndm = (int) (Math.random() * 5);
		if (rndm == 5) {
			return randomPattern5;
		} else if (rndm == 4) {
			return randomPattern4;
		} else if (rndm == 3) {
			return randomPattern3;
		} else if (rndm == 2) {
			return randomPattern2;
		}

		return randomPattern1;

	}

	public static int getLevel() {
		return level;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
