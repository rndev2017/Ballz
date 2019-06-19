import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/** A class that displays the methods used for moving the ball and changing it's direction
 *
*/
public class MovingBall extends Rectangle
{
  private int ballDeltaX;
  private int ballDeltaY;
  private final static int diameter = 15;
  public MovingBall(int initX, int initY)
  {
    super(initX,initY,diameter,diameter);
    ballDeltaX = 1; 
    ballDeltaY=1;
  }
  /**
   * // Moves ball by right ballDeltaX and up ballDeltaY
   */
  public void move()
  {
    super.translate(ballDeltaX, ballDeltaY);
  }
  public int getBallDeltaX()
  {
    return ballDeltaX;
  }
  public void setBallDeltaX(int ballDeltaX)
  {
    this.ballDeltaX = ballDeltaX;
  }
  public int getBallDeltaY()
  {
    return ballDeltaY;
  }
  public void setBallDeltaY(int ballDeltaY)
  {
    this.ballDeltaY = ballDeltaY;
  }
  /**
   * Paints ball
   * @param Graphics g
   */
  public void paint(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillOval((int)super.getLocation().getX(),(int)super.getLocation().getY(), diameter, diameter);
  }
  public int getDiameter()
  {
    return diameter;
  }
}
