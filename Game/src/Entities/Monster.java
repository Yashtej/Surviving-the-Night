package Entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.Game;
import Main.GameObject;
import Main.HUD;
import Main.Inventory;
import Other.ID;
import Other.SpriteSheet;

public class Monster extends GameObject {

	//Textures
	private SpriteSheet ms;
	private BufferedImage monsterImage;
	
	//PvE
	private int ticks = 0;
	private boolean task = false, task1 = false, task2 = false;
	private boolean randomSpawn = false;
	private boolean killed = false;
	private Random r;
	private int spawnChance = 10000;
	public static boolean hit = false;
	
	public static int HP = 15;
	public static int DAMAGE = 5;
	
	public Monster(int x, int y, int width, int height, boolean near, boolean action, ID id) {
		super(x, y, width, height, id);
		this.near = near;
		this.action = action;
		
		ms = new SpriteSheet(Game.monster_sheet);
		
		r = new Random();
		
		velX = 3;
	}

	public void tick() {
		Game.clamp(HP, 15, 0);
		
		//Monster HP Checking
		if (HP == 0) {
			killed = true;
			action = false;
		}
		
		if (killed) {
			Inventory.meat++;
			x = 1380;
			HP = 15;
			killed = false;
		}
		
		//Fighting
		if (action) {
			if (Player.hit && hit) {
				if (Inventory.knuckles) {
					HP -= 5;
					hit = false;
				} else {
					HP -= 3;
					hit = false;
				}
			}
			
			if (near) {
				ticks++;
				if (ticks % 45 == 0) {
					HUD.HEALTH -= 6;
				}
			} else {
				ticks = 0;
			}
		}
		
		//MONSTER SPAWNING
		//For the monster, action is being used to determine whether or not the monster is alive
		//Spawning the monster if certain tasks are completed, only 1 monster will spawn at once
		if (!action) {
			//RANDOM MONSTER SPAWNING
			if (randomSpawn) {
				int spawn = r.nextInt(spawnChance);
				if (spawn >= 0 && spawn <= 5) {
					action = true;
				}
			}
			
			if (!task) {
				if (Inventory.woodPickaxe) {
					x = -150;
					HP = 15;
					action = true;
					task = true;
				}
			} else if (!task1) {
				if (Inventory.stonePickaxe) {
					x = 1380;
					HP = 15;
					randomSpawn = true;
					action = true;
					task1 = true;
				}
			} else if (!task2) {
				if (Inventory.knuckles) {
					x = -150;
					HP = 15;
					spawnChance = 7500;
					action = true;
					task2 = true;
				}
			}
		}
		
		//Monster's movement
		if (action) {
			if (x < 575) {
				x += velX;
				monsterImage = ms.grabImage(64, 100);
				//Flipping Image
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-monsterImage.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				monsterImage = op.filter(monsterImage, null);
			} else if (x > 655) {
				x -= velX;
				monsterImage = ms.grabImage(64, 100);
			}
			x -= Player.xOffSet;
		}
	}

	public void render(Graphics g) {
		if (action) {
			//g.setColor(Color.green);
			//g.fillRect(x, y, width, height);
			g.drawImage(monsterImage, x, y, null);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
