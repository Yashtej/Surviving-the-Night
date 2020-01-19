package Entities;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.Game;
import Main.GameObject;
import Main.Handler;
import Main.KeyInput;
import Other.ID;
import Other.SpriteSheet;

public class Player extends GameObject {
	
	private Handler handler;
	
	//Textures
	private SpriteSheet ss;
	private BufferedImage playerImage;
	
	//Movement animation
	public static int moveTick = 0;
	private int takeStep = 30;
	private boolean step = true;
	
	//Side scrolling stuff
	public static int xOffSet = 0;
	public static int x1 = 0;
	public static double velY = 0;
	
	//PvE
	public static boolean attack = false;
	public static boolean hit = false;
	public static boolean coolDown = false;
	public static boolean coolDownCounter = false;
	private int attackCooldown = 0;
	private int attackTicks = 0;
	
	public Player(int x, int y, int width, int height, ID id, Handler handler) {
		super(x, y, width, height, id);
		this.handler = handler;
		
		ss = new SpriteSheet(Game.sprite_sheet);
		playerImage = ss.grabImage(0, 0, 106, 114);
	}
	
	public void tick() {
		x1 += xOffSet;
		y += velY;
		
		//Bounds
		x1 = Game.clamp(x1, 1920, -1920);
		if (x1 == 1920) {
			xOffSet = -4;
		} else if (x1 == -1920) {
			xOffSet = 4;
		}
		
		if (xOffSet != 0 && !KeyInput.left && !KeyInput.right) {
			xOffSet = 0;
		}
		
		if (coolDownCounter) {
			attackCooldown++;
			if (attackCooldown == 16) {
				coolDown = true;
			} else if (attackCooldown == 40) {
				coolDown = false;
				coolDownCounter = false;
				attackCooldown = 0;
			}
		}
		
		//Animation Engine
		//Attack Animation
		if (attack) {
			if (!coolDown) {
				//Timing how long attack lasts for
				attackTicks++;
				if (attackTicks == 1) {
					Monster.hit = true;
				} else if (attackTicks == 15) {
					attackTicks = 0;
					attack = false;
				}
				
				if (KeyInput.fRight) {
					playerImage = ss.grabImage(5, 1, 106, 114);
				} else if (KeyInput.fLeft) {
					playerImage = ss.grabImage(5, 2, 106, 114);
				} else {
					playerImage = ss.grabImage(5, 1, 106, 114);
				}
			}
		//Movement Animation
		} else {
			if (!Game.pause) {
				//If player is not moving
				if (xOffSet == 0) {
					//Determine which way player should be facing
					if (KeyInput.fRight) {
						playerImage = ss.grabImage(2, 0, 106, 114);
					} else if (KeyInput.fLeft){
						playerImage = ss.grabImage(3, 2, 106, 114);
					} else {
						playerImage = ss.grabImage(0, 0, 106, 114);
					}
				//If player is moving right
				} else if (KeyInput.right) {
					moveTick++;
					if (moveTick > takeStep) {
						step = !step;
						moveTick = 0;
					}
					
					if (step) {
						playerImage = ss.grabImage(1, 0, 106, 114);
					} else {
						playerImage = ss.grabImage(3, 0, 106, 114);
					}
				//If player is moving left
				} else if (KeyInput.left) {
					moveTick++;
					if (moveTick > takeStep) {
						step = !step;
						moveTick = 0;
					}
					
					if (step) {
						playerImage = ss.grabImage(4, 2, 106, 114);
					} else {
						playerImage = ss.grabImage(2, 2, 106, 114);
					}
				}
				//System.out.println(moveTick);
			}
		}
		collision();
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 12);
		g.setFont(fnt);
		//g.setColor(Color.green);
		//g.fillRect(x, y, width, height);
		g.drawImage(playerImage, x - 24, y - 8, null);
		/*
		if (attack) {
			g.setColor(Color.WHITE);
			if (KeyInput.fRight) {
				g.fillRect(x + 63, y + 30, 20, 40);
			} else if (KeyInput.fLeft) {
				g.fillRect(x - 20, y + 30, 20, 40);
			} else {
				g.fillRect(x + 63, y + 30, 20, 40);
			}
		}
		*/
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	private Rectangle attackBounds() {
		if (KeyInput.fRight) {
			return new Rectangle(x + 63, y + 30, 20, 40);
		} else if (KeyInput.fLeft) {
			return new Rectangle(x - 20, y + 30, 20, 40);
		} else {
			return new Rectangle(x + 63, y + 30, 20, 40);
		}
	}
	
	private void collision() {
		for (int i=0;i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			//Floor Collision
			if (tempObject.getID() == ID.Floor) {
				if (getBounds().intersects(tempObject.getBounds())) {
					velY = 0;
					y = 422;
				} else {
					velY += 0.2;
				}
				
			//Fire Collision
			} else if (tempObject.getID() == ID.Fire) {
				if (getBounds().intersects(tempObject.getBounds())) {
					tempObject.setNear(true);
				} else {
					tempObject.setNear(false);
				}
				
			//Monster Collision
			} else if (tempObject.getID() == ID.Monster) {
				//Checking if player is near monster
				if (getBounds().intersects(tempObject.getBounds())) {
					tempObject.setNear(true);
				} else {
					tempObject.setNear(false);
				}
				//Checking if attack hit box is hitting monster
	 			if (attack) {
					if (attackBounds().intersects(tempObject.getBounds())) {
						hit = true;
					} else {
						hit = false;
					}
	 			}
	
			//Tree Collision	
			} else if (tempObject.getID() == ID.SmallTree || tempObject.getID() == ID.RegTree || tempObject.getID() == ID.LargeTree) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (tempObject.getAction()) {
						tempObject.setNear(false);
					} else {
						tempObject.setNear(true);
					}
				} else {
					tempObject.setNear(false);
				}
				
			//Rock Collision
			} else if (tempObject.getID() == ID.Rock) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (tempObject.getAction()) {
						tempObject.setNear(false);
					} else {
						tempObject.setNear(true);
					}
				} else {
					tempObject.setNear(false);
				}
				
			//Metal Rock Collision
			} else if (tempObject.getID() == ID.MetalRock) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (tempObject.getAction()) {
						tempObject.setNear(false);
					} else {
						tempObject.setNear(true);
					}
				} else {
					tempObject.setNear(false);
				}
				
			//Bush Collision
			} else if (tempObject.getID() == ID.Bush) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (tempObject.getAction()) {
						tempObject.setNear(false);
					} else {
						tempObject.setNear(true);
					}
				} else {
					tempObject.setNear(false);
				}
			}
		}
	}
}
