
/**  
* Game.java - contains the logic and graphics behind our remastered version of Ballz.  
* @author  Rohan Nagavardhan & Mourya Chimpiri
* @version 1.0
*/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener
{
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;
	private int numBoxesToRemove;
	private int[] randomPlacements;
	private Ball ball;
	private int diameter = 50;

	public Game() 
	{
		setBackground(Color.BLACK);
		Ball ball = new Ball(getWidth()/2, getHeight());
		numBoxesToRemove = (int)(Math.random()*8);
		randomPlacements = new int[8];
		for(int i = 0; i < randomPlacements.length; i++)
		{
		  randomPlacements[i] = (int)(Math.random()*8);
		}
		System.out.println(Arrays.toString(randomPlacements));
		
	
		
		setFocusable(true);
		addKeyListener(this);
		Timer t = new Timer(1000 / 60, this);
		t.start();
	}

	public void actionPerformed(ActionEvent e) 
	{
		step();
	}

	public void step() 
	{
		if(playing){}
		repaint();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (showTitleScreen) 
		{
			paintTitleScreen(g);
			
		} 
		else if (playing) 
		{
			ball.paint();	
		}

		else if (gameOver) 
		{
		}
	}

	
	public int randInt(int min, int max) 
	{
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public void paintTitleScreen(Graphics g)
	{
		// Sets the font of the title and sets it relative to the size of the JPanel for
		// automatic resizing
		g.setColor(new Color(255, 102, 0)); //Dark Orange
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 150));
		g.drawString("Ballz", getWidth() / 3, (int) (getHeight() / 4));

		// Sets the font of the instructions and sets it relative to the size of the
		// JPanel for automatic resizing
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		g.drawString("Press 'P' to play.", getWidth() / 3, (int) (getHeight() / 1.5));
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
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub

	}
public void mouseClicked(MouseEvent arg0)
  {
    if(playing)
    {
      int finalX = arg0.getX();
      int finalY = arg0.getY();
      ball.setBallDeltaY(finalY-ball.getBallDeltaY());
      if (finalX > ball.getLocation().getX())
      {
        ball.setBallDeltaX(finalX-ball.getBallDeltaX());
      }
      else
      {
        ball.setBallDeltaX(ball.getBallDeltaX()-finalX);
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

}
