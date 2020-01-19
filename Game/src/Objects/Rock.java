package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Game;
import Main.GameObject;
import Main.Inventory;
import Main.KeyInput;
import Other.ID;
import Other.SpriteSheet;

public class Rock extends GameObject {
	
	//Textures
	private SpriteSheet r;
	private BufferedImage rockImage;

	//Respawn rocks
	private int ticks = 0;
	public static boolean message = false;
	
	public Rock(int x, int y, int width, int height, boolean near, boolean action, ID id) {
		super(x, y, width, height, id);
		this.near = near;
		this.action = action;
		
		r = new SpriteSheet(Game.rock_image);
		rockImage = r.grabImage(width, height);
	}

	public void tick() {
		x -= Player.xOffSet;
		
		//Rock respawn timer
		if (action) {
			ticks++;
			
			if (ticks == KeyInput.rockRespawnTime) {
				ticks = 0;
				action = false;
			} else if (ticks == 100) {
				message = false;
			}
			//System.out.println(ticks);
		}
	}

	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 14);
		g.setFont(fnt);
		//If player mines the rock
		if (action) {
			g.setColor(null);
			//Display message that rock has been mined
			if (message) {
				g.setColor(Color.white);
				g.drawString("You mine the rock...", 10, 115);
			}
		} else {
			//g.setColor(Color.green);
			//g.fillRect(x, y, width, height);
			g.drawImage(rockImage, x, y, null);
			
			//Displays box above player's head if they are near a rock
			if (near) {
				if (Inventory.woodPickaxe) {
					g.setColor(Color.white);
					g.fillRect(x - 60, y - 70, 185, 20);
					g.setColor(Color.black);
					g.drawRect(x - 60, y - 70, 185, 20);
					g.drawString("Press 'E' to mine the rock", x - 49, y - 55);
				}
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

}
