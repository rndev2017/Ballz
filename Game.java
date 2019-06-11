
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

public class Game extends JPanel implements ActionListener, KeyListener 
{
	private boolean showTitleScreen = true;
	private boolean playing = false;
	private boolean gameOver = false;
	private int ballX = 465;
	private int ballY = 1500;
	private int diameter = 50;

	public Game() 
	{
		setBackground(Color.BLACK);
		
		
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
		//repaint();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (showTitleScreen) 
		{
			paintTitleScreen(g);
			
		} 
		else if (playing) 
		{
			g.setColor(Color.WHITE);
			g.fillOval(ballX, ballY, diameter, diameter);
			g.drawLine(0, ballY + diameter, getHeight()/2, ballY + diameter);
			
			
			
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
			if (e.getKeyCode() == KeyEvent.VK_P) {
				showTitleScreen = false;
				playing = true;
			}
			repaint();

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
	
	public void drawBoxes(Box[] arr, Graphics g)
	{
		int numBoxesToRemove = randInt(1, arr.length-1);
		for(int box = 0; box < numBoxesToRemove; box++)
		{
			int boxToRemove = randInt(1, arr.length-1);
			arr[boxToRemove] = null;
		}
		
		for(Box b : arr)
		{
			if(b != null)
			{
				b.paintComponent(g);
			}
		}
	}
	
	public void shiftRow(Box[] arr, Graphics g)
	{
		g.setColor(Color.YELLOW);
		Box[] shift = new Box[arr.length];
		for(int i = 0; i < arr.length; i++)
		{
			shift[i] = arr[i];
		}
	
		
		for(Box b : shift)
		{
			if(b != null)
			{
				int currentY = (int) b.getY();
				b.setY(currentY + 119);
				b.paintComponent(g);
			}
		}
		
//		for(Box b : arr)
//		{
//			if(b != null)
//			{
//				g.setColor(Color.BLACK);
//				b.paintComponent(g);
//				
//			}
//		}
		
	}
	
	public Box[] createArray(int yPos)
	{
		Box[] newRow = new Box[8];
		int increment = 19; // creates an offset between the block1 and the block that comes after it
		for (int i = 0; i < newRow.length; i++) 
		{
			newRow[i] = new Box(increment, yPos);
			increment += 119;
		}
		return newRow;
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


}
