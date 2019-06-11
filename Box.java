import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.glass.ui.Window.Level;



public class Box extends Rectangle
{
	private int x, y;
	private int health;
	public static final int length = 100;
	public static final int width = 100;
	
	public Box(int xPos, int yPos)
	{
		this.x = xPos;
		this.y = yPos;
		this.health = 15;
	}
	
	
	public double getX() {
		return x;
	}



	public void setX(int x) {
		this.x = x;
	}



	public double getY() {
		System.out.println(y);
		return y;
		
	}



	public void setY(int y) {
		this.y = y;
	}



	public void paintComponent(Graphics g)
	{
		g.setColor(getColor(health));
		g.fillRect(this.x, this.y, width, length);
	}
	
	public Color getColor(int health)
	{
		if(health <= 10)
		{
			return new Color(255, 204, 0); //produces a light orange color
		}
		
		if(health > 10 && health <= 25)
		{
			return new Color(0, 255, 51);
		}
		return null;
	}
	
	
	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}
	
	
	
	
	
	
	
  
}
