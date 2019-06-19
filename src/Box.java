import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/* A class that houses the methods for the box array and the boxes themselves*/
public class Box extends Rectangle
{
  private int x, y;
  private int health;
  public static final int width = (int) Main.screenSize.width / 45;
  public static final int height = (int) Main.screenSize.width / 45;

  public Box(int xPos, int yPos, int healthh)
  {
    super(xPos, yPos, width, height);
    this.health = Game.getLevel() + 1;// Sets health equal to one more than the
                                      // level number
  }
  /**
   * Paints the ball
   * @param g
   */
  public void paint(Graphics g)
  {
    g.setColor(getColor(health));
    g.fillRect((int) getX(), (int) getY(), width, height);
  }
  /**
   * Gets the color of the box according to the health
   * @param health
   * @return new color of the box
   */
  public Color getColor(int health) 
  {
    if (health == 1)
    {
      return new Color(250, 250, 250);// Makes the box white
    }
    else if (health % 5 == 0)
    {
      return new Color(255, 204, 0); // produces a light orange color
    }

    else if (health % 3 == 0)
    {
      return new Color(0, 255, 51);// Produces a bright green color
    }

    else if (health % 2 == 0)
    {
      return new Color(102, 0, 153);// Produces a dark purple color
    }
    else
    {
      return new Color(30, 247, 192);// Produces a sea green color
    }
  }
  /**
   * Shifts the arrays down 
   * @param boxes
   */
  public static void shiftRow(ArrayList<Box[]> boxes)
  {
    for (Box[] row : boxes)
    {
      for (Box b : row)
      {
        if (b != null)
        {
          b.translate(0, width + 25);
        }
      }
    }
  }

  public int getHealth()
  {
    return health;
  }

  public void setHealth(int health)
  {
    this.health = health;
  }

  public boolean newHealth(int startingHealth, int decrement)
  {
    int newHealth = health - decrement;
 // Is the box alive?
    if (newHealth > 0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
