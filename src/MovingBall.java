import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MovingBall extends Rectangle
{
  private int ballDeltaX;
  private int ballDeltaY;
  private static int diameter;
  public MovingBall(int initX, int initY)
  {
    super(initX,initY,diameter,diameter);
    ballDeltaX = 0; 
    ballDeltaY=0;
    diameter = 50;
  }
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
  public void paintComponent(Graphics g)
  {
    g.setColor(Color.WHITE);
    g.fillOval((int)super.getLocation().getX(),(int)super.getLocation().getY(), diameter, diameter);
  }
  public int getDiameter()
  {
    return diameter;
  }
}
