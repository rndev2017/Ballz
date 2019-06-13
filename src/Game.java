
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
import java.util.Arrays;
import java.util.Random;

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
  private int numBoxesToRemove;
  private int[] randomPlacements;
  private int diameter = 5;
  Timer t;
  public Game() {
    setBackground(Color.BLACK);
    ball = new MovingBall(getWidth() / 2, getHeight());
    numBoxesToRemove = (int) (Math.random() * 8);
    randomPlacements = new int[8];
    for (int i = 0; i < randomPlacements.length; i++) {
      randomPlacements[i] = (int) (Math.random() * 8);
    }
    System.out.println(Arrays.toString(randomPlacements));

    setFocusable(true);
    addKeyListener(this);
    Timer t = new Timer(1000 / 60, this);
    t.start();
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

  public void collide()
  {
    if(ball.intersects(leftBorder)||ball.intersects(rightBorder))// if the ball hits the sides, make the x velocity negated
    {
      ball.setBallDeltaX(ball.getBallDeltaX()*-1);
    }
    if(ball.intersects(topBorder))// if ball hits top, negate y velocity
    {
      ball.setBallDeltaY(ball.getBallDeltaY()*-1);
    }
//    for (int i = 0; i < boxes.length; i++)
//    {
//      for (int j = 0; j < boxes[i].length; j++)
//      {
//        
//        if (ball.intersects(boxes[i][j]))
//        {
//
//        }
//      }
//    }
  }

  @Override
  public void mouseClicked(MouseEvent arg0)
  {
    if (playing)
    {
      int finalX = arg0.getX();
      int finalY = arg0.getY();
      ball.setBallDeltaY(finalY - ball.getBallDeltaY());
      if (finalX > ball.getLocation().getX())
      {
        ball.setBallDeltaX((int) (finalX - ball.getLocation().getX()));
      }
      else
      {
        ball.setBallDeltaX((int) (ball.getLocation().getX() - finalX));
      }
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

  public void mouseDragged(MouseEvent e)
  {
  }

  public void actionPerformed(ActionEvent e)
  {
    topBorder = new Rectangle(0, 0, (int) super.getSize().getWidth(),
        (int) super.getSize().getHeight());
    leftBorder = new Rectangle(0, 0, (int) super.getSize().getWidth(),
        (int) super.getSize().getHeight());
    rightBorder = new Rectangle(
        (int) super.getSize().getWidth()
            - (int) super.getSize().getWidth(),
        0, (int) super.getSize().getWidth(),
        (int) super.getSize().getHeight());
//    ball.move();
  }

  public void paintComponent(Graphics g)
  {

    super.paintComponent(g);
    g.setColor(Color.RED);

    if (showTitleScreen)
    {

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
      g.drawString("Ballz", 165, 100);
      g.setColor(Color.RED);

      g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

      g.drawString("Press 'P' to play.", 175, 400);
    }
    else if (playing)
    {
      
      ball.paintComponent(g);
    } // add the boxes and the ball at the bottom }
    else if (gameOver)
    {
      g.setColor(Color.RED);
      g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
      g.drawString("Ballz", 165, 100);
    }
  }
  public int randInt(int min, int max) 
  {
    Random rand = new Random();
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
  }
  public Box [] createArray(int yPos)
  {
    Box[] arr = new Box[8];
    int inc = 119;
    for(int i = 0; i < randomPlacements.length;)
    {
      arr[randomPlacements[i]] = new Box(randomPlacements[i]*inc, yPos);
      if(arr[randomPlacements[i]] != null)
      {
        i++;
      }
    }
    return arr;
  }
  
}
