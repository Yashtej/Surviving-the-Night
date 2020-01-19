package Main;

import java.awt.Graphics;
import java.awt.Rectangle;

import Other.ID;

public abstract class GameObject {
	
	protected int x, y, width, height;
	protected double velX, velY;
	protected boolean near = false, action = false;
	protected ID id;
	
	public GameObject(int x, int y, int width, int height, ID id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	//Set & Get Methods
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public void setNear(boolean near) {
		this.near = near;
	}
	
	public void setAction(boolean action) {
		this.action = action;
	}
	
	public void setID(ID id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getVelX() {
		return velX;
	}
	
	public double getVelY() {
		return velY;
	}
	
	public boolean getNear() {
		return near;
	}
	
	public boolean getAction() {
		return action;
	}
	
	public ID getID() {
		return id;
	}
}
