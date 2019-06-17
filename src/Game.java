/**  
* Game.java - contains the logic and graphics behind our remastered version of Ballz.  
* @author  Rohan Nagavardhan & Mourya Chimpiri
* @version 1.0
*/

import java.awt.Color;
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
  private static int level = 1;
  private int[] randomPattern1, randomPattern2, randomPattern3, randomPattern4, randomPattern5;
  private ArrayList<Box[]> box2d;
  private int inc;
  private final int topBorderScale=15;
  private final int sideBorderScale=20;
  public Game() {
    setBackground(Color.BLACK);
    ball = new MovingBall(450,1000);
    inc = 119;
    randomPattern1 = generateRandomPlacement();
    randomPattern2 = generateRandomPlacement();
    randomPattern3 = generateRandomPlacement();
    randomPattern4 = generateRandomPlacement();
    randomPattern5 = generateRandomPlacement();
    
    box2d = new ArrayList<Box[]>(); // ArrayList of Box rows
    box2d.add(createArray(getPattern(), 500));

    setFocusable(true);
    addKeyListener(this);
    addMouseListener(this);
    Timer t = new Timer(1000 / 24, this);
    t.start();
  }
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    step();
  }

  public void step()
  {
    topBorder = new Rectangle(0, 0, (int) super.getSize().getWidth(),
        (int) super.getSize().getHeight() / topBorderScale);
    leftBorder = new Rectangle(0, 0,
        (int) super.getSize().getWidth() / sideBorderScale,
        (int) super.getSize().getHeight());
    rightBorder = new Rectangle(
        (int) super.getSize().getWidth()
            - (int) super.getSize().getWidth() / sideBorderScale,
        0, (int) super.getSize().getWidth() / sideBorderScale,
        (int) super.getSize().getHeight());
      ball.move();
      collide();
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
        ball.paintComponent(g);
        for (Box[] b : box2d) 
        {
          drawRow(b, g);
        }
    }
    else if (gameOver)
    {
    
    }
  }
  
  
  @Override
  public void keyPressed(KeyEvent e)
  {
    // TODO Auto-generated method stub
    if (showTitleScreen)
    {
      if (e.getKeyCode() == KeyEvent.VK_P)
      {
        showTitleScreen = false;
        playing = true;
        System.out.println("pressed");
        repaint();
      }
    }

    if (playing)
    {
    }

    if (gameOver)
    {
      if (e.getKeyCode() == KeyEvent.VK_SPACE)
      {
      gameOver = false;
        showTitleScreen = true;
      }
    }
  }

  @Override
   public void mouseClicked(MouseEvent arg0)
  {
    System.out.println("clicked");
    int finalX = arg0.getX();
    int finalY = arg0.getY();
    ball.setBallDeltaY(finalY-(int)ball.getY());
    if (finalX > ball.getLocation().getX())
    {
      ball.setBallDeltaX((int) (finalX - ball.getLocation().getX()));
      System.out.println(ball.getBallDeltaX());
      ball.move();
    }
    else
    {
      ball.setBallDeltaX((int) (ball.getLocation().getX() - finalX)*-1);
      System.out.println(ball.getBallDeltaX());
      ball.move();
    }
  }

  @Override
  public void mouseEntered(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mousePressed(MouseEvent arg0)
  {

  }

  @Override
  public void mouseReleased(MouseEvent arg1)
  {

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
  public void collide()
  {
   if (ball.intersects(leftBorder) || ball.intersects(rightBorder))
    {
      ball.setBallDeltaX(ball.getBallDeltaX() * -1);
    }
    if (ball.intersects(topBorder))
    {
      ball.setBallDeltaY(ball.getBallDeltaY() * -1);
    }
    else if((int) ball.getLocation().getY() >=400)
    {
      ball.setLocation(250,400);
      ball.setBallDeltaX(0);
      ball.setBallDeltaY(0);
    }
    else
    {
      for(int i=0;i<box2d.size(); i++)
      {
        for(Box b: box2d.get(i))
        {
          if(b!= null)
          {
            
          if(ball.intersects(b))
          {
            Rectangle intersection = ball.intersection(b);
            int intersectionX = (int)intersection.getLocation().getX();
            int intersectionY = (int) intersection.getLocation().getY();
            if(intersectionX==b.getLocation().getX() && intersectionY>= b.getLocation().getY() && intersectionY<=b.getLocation().getY() + 100)
            {
              ball.setBallDeltaX(ball.getBallDeltaX() * -1);
            }
            else if(intersectionX==b.getLocation().getX()+100 && intersectionY>=b.getLocation().getY() && intersectionY<=b.getLocation().getY()+100)
            {
              ball.setBallDeltaX(ball.getBallDeltaX() * -1);
            }
            else
            {
              ball.setBallDeltaY(ball.getBallDeltaY() * -1);
            }
            int newHealth = b.getHealth()-1;
            if(newHealth==0)
            {
              box2d.remove(b);
            }
            else
            {
              b.getColor(newHealth);
            }
          }
        }
      }
    }
   }   
  }
  
  public void paintTitleScreen(Graphics g) {
    // Sets the font of the title and sets it relative to the size of the JPanel for
    // automatic resizing
    g.setColor(new Color(255, 102, 0)); // Dark Orange
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 150));
    g.drawString("Ballz",(int)getWidth() / 3, (int) (getHeight() / 4));

    // Sets the font of the instructions and sets it relative to the size of the
    // JPanel for automatic resizing
    g.setColor(Color.WHITE);
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
    g.drawString("Press 'P' to play.", getWidth() / 3, (int) (getHeight() / 1.5));
  }

  public Box[] createArray(int[] pattern, int yPos) {
    Box[] arr = new Box[8];
    for (int i = 0; i < arr.length;) {
      arr[pattern[i]] = new Box((pattern[i] * inc) + 19, yPos);
      if (arr[pattern[i]] != null) {
        i++;
      }
    }
    return arr;
  }

  public int[] generateRandomPlacement() {
    int[] randomPlacements = new int[8];
    for (int i = 0; i < randomPlacements.length; i++) {
      randomPlacements[i] = (int) (Math.random() * 8);
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
}
