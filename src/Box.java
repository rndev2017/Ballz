import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Box extends Rectangle {
  private int x, y;
  private int health;
  public static final int width = (int)Main.screenSize.width/45;
  public static final int height = (int)Main.screenSize.width/45;

  public Box(int xPos, int yPos,int healthh) {
    super(xPos, yPos, width, height);
    this.health = Game.getLevel()+1;
  }

  public void paint(Graphics g) {
    g.setColor(getColor(health));
    g.fillRect((int) getX(), (int) getY(), width, height);
  }

  public Color getColor(int health) {
    if (health%2==0) {
      return new Color(255, 204, 0); // produces a light orange color
    }

    else if (health%3==0) {
      return new Color(0, 255, 51);
    }
    
    else if(health%5==0)
    {
      return new Color(102, 0, 153);
    }
    else
    {
      return new Color(30,247,192);
    }
  }
  public static void shiftRow(ArrayList<Box[]> boxes) {
    for (Box[] row : boxes) {
      for (Box b : row) {
        if (b != null) {
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
    int newHealth = health-decrement;
    if(newHealth>0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
