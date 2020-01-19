package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Game;
import Main.GameObject;
import Main.KeyInput;
import Other.ID;
import Other.SpriteSheet;

public class Bush extends GameObject {
	
	//Textures
	private SpriteSheet b;
	private BufferedImage bushImage;
	
	//Respawn bushes
	private int ticks = 0;
	public static boolean message = false;

	public Bush(int x, int y, int width, int height, boolean near, boolean action, ID id) {
		super(x, y, width, height, id);
		this.near = near;
		this.action = action;
		
		b = new SpriteSheet(Game.bush_image);
		bushImage = b.grabImage(width, height);
	}
	
	public void tick() {
		x -= Player.xOffSet;
		
		//Bush respawn timer
		if (action) {
			ticks++;
			
			if (ticks == KeyInput.bushRespawnTime) {
				ticks = 0;
				action = false;
			} else if (ticks == 100) {
				message = false;
			}
		}
	}

	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 14);
		g.setFont(fnt);
		
		//If player collects the bush
		if (action) {
			//Display message that bush has been collected
			if (message) {
				g.setColor(Color.white);
				g.drawString("You collect berries from the bush...", 10, 145);
			}
		} else { 
			//g.setColor(Color.green);
			//g.fillRect(x, y, width, height);
			g.drawImage(bushImage, x, y, null);

			//Displays box above player's head if they are near a bush
			if (near) {
				g.setColor(Color.white);
				g.fillRect(x - 35, y - 60, 185, 20);
				g.setColor(Color.black);
				g.drawRect(x - 35, y - 60, 185, 20);
				g.drawString("Press 'E' to collect the bush", x - 32, y - 45);
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
