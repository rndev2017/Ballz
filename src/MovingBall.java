import java.awt.Graphics;
import java.awt.Rectangle;

public class MovingBall extends Rectangle {
	private int objDeltaX;
	private int objDeltaY;

	public MovingBall(int initX, int initY, int width, int height) {
		super(initX, initY, width, height);
		objDeltaX = 0;
		objDeltaY = 0;
	}

	public void move() {
		super.translate(objDeltaX, objDeltaY);
	}

	public int getObjDeltaX() {
		return objDeltaX;
	}

	public void setBallDeltaX(int ballDeltaX) {
		this.objDeltaX = ballDeltaX;
	}

	public int getObjDeltaY() {
		return objDeltaY;
	}

	public void setObjDeltaY(int ballDeltaY) {
		this.objDeltaY = ballDeltaY;
	}
}