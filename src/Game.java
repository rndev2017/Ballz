
/**  
* Game.java - contains the logic and graphics behind our remastered version of Ballz.  
* @author Rohan Nagavardhan
* @author Mourya Chimpiri
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

public class Game extends JPanel
    implements ActionListener, KeyListener, MouseListener
{
  private MovingBall ball;
  private Rectangle topBorder, leftBorder, rightBorder;
  private boolean showTitleScreen = true;
  private boolean playing = false;
  private boolean gameOver = false;
  private static int level;
  private int[] randomPattern1, randomPattern2, randomPattern3, randomPattern4,
      randomPattern5;
  private ArrayList<Box[]> box2d;
  private int inc;
  private final int topBorderScale = 15;
  private final int sideBorderScale = 20;
  private int movingBallInitialX;
  private int movingBallInitialY;

  /**
   * Constructs a GamePanel
   */
  public Game()
  {
    setBackground(Color.BLACK);
    inc = Box.height + 19;

    // listens to key presses and mouse
    setFocusable(true);
    addKeyListener(this);
    addMouseListener(this);

    // call step() 60 fps
    Timer t = new Timer(1000 / 30, this);
    t.start();
  }

  /**
   * initializes the Game screen with initial mouse position, borders, and one
   * row of boxes generates 5 random patterns that apply to the Box rows
   * 
   * @param screenSize
   */
  public void init(Dimension screenSize)
  {
    movingBallInitialX = (int) (screenSize.getWidth() / 4);
    movingBallInitialY = ((int) (screenSize.getHeight() / 1.2));

    setSize(screenSize.width / 2, screenSize.height);
    ball = new MovingBall(movingBallInitialX, movingBallInitialY);
    topBorder = new Rectangle(0, 0, (int) this.getSize().getWidth(),
        (int) this.getSize().getHeight() / topBorderScale);
    leftBorder = new Rectangle(0, 0,
        (int) super.getSize().getWidth() / sideBorderScale,
        (int) super.getSize().getHeight());
    rightBorder = new Rectangle(
        (int) super.getSize().getWidth()
            - (int) super.getSize().getWidth() / sideBorderScale,
        0, (int) super.getSize().getWidth() / sideBorderScale,
        (int) super.getSize().getHeight());
    randomPattern1 = generateRandomPlacement();
    randomPattern2 = generateRandomPlacement();
    randomPattern3 = generateRandomPlacement();
    randomPattern4 = generateRandomPlacement();
    randomPattern5 = generateRandomPlacement();
    level = 1;

    box2d = new ArrayList<Box[]>(); // initializes ArrayList of Box rows
    box2d.add(createArray(getPattern(), (int) super.getHeight() / 4));

  }

  public static int getLevel()
  {
    return level;
  }

  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    step();
  }

  public void step()
  {

    if (playing)
    {

      if ((int) ball.getY() < movingBallInitialY)
      {

        ball.move();
        collide();

      }

      else
      {
        ball.setBallDeltaX(0);
        ball.setBallDeltaY(0);
      }
    }

    repaint();

  }

  public void paintComponent(Graphics g)
  {

    super.paintComponent(g);
    if (showTitleScreen)
    {
      paintTitleScreen(g);
    }
    else if (playing)
    {
      ball.paint(g);

      // draws all the boxes
      for (Box[] b : box2d)
      {
        drawRow(b, g);
      }

      // draws the borders
      g.setColor(Color.gray);
      g.fillRect(0, 0, (int) this.getSize().getWidth(),
          (int) this.getSize().getHeight() / topBorderScale);
      g.fillRect(0, 0, (int) super.getSize().getWidth() / sideBorderScale,
          (int) super.getSize().getHeight());
      g.fillRect(
          (int) super.getSize().getWidth()
              - (int) super.getSize().getWidth() / sideBorderScale,
          0, (int) super.getSize().getWidth() / sideBorderScale,
          (int) super.getSize().getHeight());
      g.drawLine(0, movingBallInitialY + ball.getDiameter(), super.getWidth(),
          ball.getDiameter() + movingBallInitialY);

      // draws the score board
      g.setColor(Color.WHITE);
      g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
          (int) (Main.screenSize.getWidth() / 30)));
      g.drawString("Score: " + getLevel(),
          (int) (Main.screenSize.getWidth() / 5.5),
          (int) (Main.screenSize.getHeight() / 8));

    }
    else if (gameOver)
    {

      // displays the end of round score
      g.setColor(Color.WHITE);
      g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
          (int) (Main.screenSize.getWidth() / 30)));
      g.drawString("Your Score: " + getLevel(),
          (int) (Main.screenSize.getWidth() / 6.5),
          (int) (Main.screenSize.getHeight() / 5));

      // displays next instructions after end of game
      g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC,
          (int) (Main.screenSize.getWidth() / 35)));
      g.drawString("Press the space key to restart",
          (int) (Main.screenSize.getWidth() / 12),
          (int) (Main.screenSize.getHeight() / 2));
    }
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    if (showTitleScreen)
    {
      if (e.getKeyCode() == KeyEvent.VK_P)
      {
        showTitleScreen = false;
        playing = true;
      }
    }
    else if (playing)
    {
    }
    else if (gameOver)
    {
      if (e.getKeyCode() == KeyEvent.VK_SPACE)
      {
        gameOver = false;
        showTitleScreen = true;
        init(Main.screenSize); // reinitializes the board

      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent arg0)
  {
    if (playing)
    {
      int finalX = arg0.getX();
      int finalY = arg0.getY();
      ball.setBallDeltaY(finalY - (int) ball.getY());
      ball.setBallDeltaX(finalX - (int) ball.getX());

      ball.move();
      collide();

    }

  }

  /**
   * paints the title screen
   * 
   * @param Graphics
   *          g
   */
  public void paintTitleScreen(Graphics g)
  {
    // Sets the font of the title and sets it relative to the size of the JPanel
    // for
    // automatic resizing
    g.setColor(new Color(255, 102, 0)); // Dark Orange
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
        (int) (Main.screenSize.getWidth() / 12)));
    g.drawString("Ballz", (int) (Main.screenSize.getWidth() / 6.5),
        (int) (Main.screenSize.getHeight() / 5));

    // Sets the font of the instructions and sets it relative to the size of the
    // JPanel for automatic resizing
    g.setColor(Color.WHITE);
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
        (int) (Main.screenSize.getWidth() / 25)));
    g.drawString("Press 'P' to play.", (int) (Main.screenSize.getWidth() / 9.5),
        (int) (Main.screenSize.getHeight() / 2));

    howToPlay(g);
  }

  /**
   * displays the tutorial and instructions for the end user to follow
   * 
   * @param Graphics
   *          g
   */
  public void howToPlay(Graphics g)
  {
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,
        (int) (Main.screenSize.getWidth() / 50)));
    g.drawString("How to Play:", (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4));
    g.drawString("*Click close to the ball in the direction",
        (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4 + 40));
    g.drawString("you want the ball to go in order to hit the blocks",
        (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4 + 80));
    g.drawString("*The ball will hit the blocks",
        (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4 + 120));
    g.drawString("with the damage of the level number",
        (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4 + 160));
    g.drawString("*Prevent the blocks from reaching the ground.",
        (int) (Main.screenSize.getWidth() / 30),
        (int) (Main.screenSize.getHeight() / 4 + 200));
  }

  /**
   * creates an Box array with a random pattern
   * 
   * @param pattern
   * @param yPos
   * @return Box[]
   */
  public Box[] createArray(int[] pattern, int yPos)
  {
    Box[] arr = new Box[(int) super.getWidth() / (75 + 19)];
    for (int i = 0; i < arr.length;)
    {
      arr[pattern[i]] = new Box((pattern[i] * inc), yPos, getLevel() + 1);
      if (arr[pattern[i]] != null)
      {
        i++;
      }
    }
    return arr;
  }

  /**
   * generates an array of random positions where boxes will be drawn
   * 
   * @return int[]
   */
  public int[] generateRandomPlacement()
  {
    int[] randomPlacements = new int[(int) super.getWidth() / (75 + 19)];
    for (int i = 0; i < randomPlacements.length; i++)
    {
      randomPlacements[i] = ((int) (Math.random()
          * ((int) super.getWidth() / (75 + 19))));
    }
    return randomPlacements;
  }

  /**
   * draws the row
   * 
   * @param arr
   * @param Graphics
   *          g
   */
  public void drawRow(Box[] arr, Graphics g)
  {
    for (Box b : arr)
    {
      if (b != null)
      {
        b.paint(g);
      }
    }
  }

  /**
   * chooses a random number between 0 and 5 and picks which pattern to choose
   * 
   * @return randomPattern
   */
  public int[] getPattern()
  {
    int rndm = (int) (Math.random() * 5);
    if (rndm == 5)
    {
      return randomPattern5;
    }
    else if (rndm == 4)
    {
      return randomPattern4;
    }
    else if (rndm == 3)
    {
      return randomPattern3;
    }
    else if (rndm == 2)
    {
      return randomPattern2;
    }

    return randomPattern1;

  }
  /**
   * Assesses if the ball intersects the borders and the boxes
   * @return
   */
  public boolean collide()
  {
 //Does the ball hit the sides of the panel
    if (ball.intersects(rightBorder)||ball.intersects(leftBorder))
    {
      ball.setBallDeltaX(ball.getBallDeltaX() * -1);
      return true;
    }
 // Does the ball hit the top of the panel
    else if (ball.intersects(topBorder))
    {
      ball.setBallDeltaY(ball.getBallDeltaY() * -1);

      return true;
    }
  //Does the ball go below the start line
    else if (ball.getLocation().getY() >= movingBallInitialY)
    {
      ball.setLocation((int) ball.getLocation().getX(), movingBallInitialY);
      ball.setBallDeltaX(0);
      ball.setBallDeltaY(0);
      level++;
      Box.shiftRow(box2d);
      box2d.add(createArray(getPattern(), (int) super.getHeight() / 4));// Adds a new row of boxes at the top
      return true;
    }
    else
    {
      {
        // Checks if the ball hits any of the boxes and decreases health accordingly
        for (int i = 0; i < box2d.size(); i++)
        {
          int index = 0;
          for (Box b : box2d.get(i))
          {
            if (b != null)
            {

              if (ball.intersects(b))
              {
                Rectangle intersection = ball.intersection(b);
                int intersectionX = (int) intersection.getLocation().getX();
                int intersectionY = (int) intersection.getLocation().getY();
                // Does the ball hit the top border of the box
                if (intersectionY == b.getLocation().getY()
                    && intersectionX > b.getLocation().getX()
                    && intersectionX < b.getLocation().getX() + Box.width)
                {

                  ball.setBallDeltaY(ball.getBallDeltaY() * -1);
                  //Does the box have no health
                  if (box2d.get(i)[index].getHealth() - getLevel() > 0)
                  {
                    box2d.get(i)[index].setHealth(b.getHealth() - level);
                    box2d.get(i)[index].getColor(b.getHealth());// Changes color of box according to health
                  }
                  else
                  {
                    box2d.get(i)[index] = null;
                  }
                }
             // Does the ball hit the bottom border of the box
                else if (intersectionY == b.getLocation().getY() + Box.height
                    && (intersectionX > b.getLocation().getX()
                        && intersectionX < b.getLocation().getX() + Box.width))
                {
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
                }
             // Does the ball hit the middle of the box
                else if ((intersectionY > b.getLocation().getY()
                    && intersectionY < b.getLocation().getY() + Box.height)
                    && ((intersectionX > b.getLocation().getX()
                        && intersectionX < b.getLocation().getX()
                            + (Box.width) / 2)))
                {
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
                }
             // Does the ball hit the left border of the box
                else if (intersectionX == b.getLocation().getX()
                    && intersectionY < b.getLocation().getY() + Box.height
                    && intersectionY > b.getLocation().getY())
                {
                  ball.setBallDeltaX(ball.getBallDeltaX() * -1);
                  if (box2d.get(i)[index].getHealth() - getLevel() > 0)
                  {
                    box2d.get(i)[index].setHealth(b.getHealth() - level);
                    box2d.get(i)[index].getColor(b.getHealth());
                  }
                  else
                  {
                    box2d.get(i)[index] = null;
                  }
                }
             // Does the ball hit the right border of the box
                else if (intersectionX == b.getLocation().getX() + Box.width
                    && intersectionY < b.getLocation().getY() + Box.height
                    && intersectionY > b.getLocation().getY())
                {
                  ball.setBallDeltaX(ball.getBallDeltaX() * -1);
                  if (box2d.get(i)[index].getHealth() - getLevel() > 0)
                  {
                    box2d.get(i)[index].setHealth(b.getHealth() - level);
                    box2d.get(i)[index].getColor(b.getHealth());
                  }
                  else
                  {
                    box2d.get(i)[index] = null;
                  }
                }
             // Does the ball hit the edges of the box
                else
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
                }
                return true;
              }

            }
            index = index + 1;
          }
        }
        return false;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent e)
  {
  }
  @Override
  public void mousePressed(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }
}
