import java.awt.Color;
import java.awt.Graphics;

public class Ball extends MovingBall
{
  private static int diameter;
  public Ball(int initX, int initY, int width, int height)
  {
    super(initX, initY, width, height);
    diameter = 20;
  }
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