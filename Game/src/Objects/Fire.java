package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entities.Player;
import Main.Game;
import Main.GameObject;
import Main.HUD;
import Main.Inventory;
import Other.ID;
import Other.SpriteSheet;

public class Fire extends GameObject {

	//Textures
	private SpriteSheet f;
	private BufferedImage fireImage;
	
	public static int fire;

	private int ticks = 0;
	private int d;
	
	public Fire(int x, int y, int width, int height, boolean near, boolean action, ID id) {
		super(x, y, width, height, id);
		this.near = near;
		this.action = action;

		f = new SpriteSheet(Game.fire_image);
		fireImage = f.grabImage1(93, 0, 101, 101);
		
		fire = 2700;
	}
	
	public void tick() {
		x -= Player.xOffSet;
		
		if (fire > -1) {
			fire--;
		}
		fire = Game.clamp(fire, 5400, 0);
		
		d = Player.x1 - x + 694;
		
		//Only does fire checks if player does not have a torch
		if (!Inventory.torch) {
			//Checking if player is too far from the fire or if the fire is out
			if (d >= 1200 || d <= -1200) {
				HUD.HEALTH -= 0.05;
			} else if (fire == 0) {
				HUD.HEALTH -= 0.07;
			}
		}
		//System.out.println(d);
		//System.out.println(fire);
		
		//Timer for displaying stoked fire message
		if (action) {
			ticks++;
			if (ticks == 100) {
				ticks = 0;
				action = false;
			}
		}
	}

	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 14);
		Font fnt1 = new Font("Century Gothic", 1, 12);
		Font fnt2 = new Font("Century Gothic", 1, 18);
		g.setFont(fnt);
		
		//Drawing fire
		//g.setColor(Color.GREEN);
		//g.fillRect(x, y, width, height);
		if (fire > 0) {
			fireImage = f.grabImage1(93, 0, 101, 101);
		} else {
			fireImage = f.grabImage1(204, 0, 96, 101);
		}
		g.drawImage(fireImage, x, y, null);
		
		//Fire indicator 
		g.setFont(fnt2);
		g.setColor(Color.orange);
		if (fire > 1200) {
			g.drawString("* The fire is hot *", 200, 60);
		} else if (fire > 600) {
			g.drawString("* The fire is getting smaller *", 200, 60);
		} else if (fire > 300) {
			g.drawString("* The fire is flickering *", 200, 60);
		} else if (fire > 100) {
			g.drawString("* The fire is on its last ember *", 200, 60);
		} else {
			g.drawString("* The fire fades to nothingness *", 200, 60);
		}
		
		//Timing how long the task message appears for
		if (action) {
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("You stoke the fire...", 10, 130);
		}

		//Displaying a box above the fire if the player is near it
		if (near) {
			g.setFont(fnt1);
			g.setColor(Color.white);
			g.fillRect(x - 25, y - 50, 150, 20);
			g.setColor(Color.black);
			g.drawRect(x - 25, y - 50, 150, 20);
			g.drawString("Press 'E' to stoke the fire.", x - 19, y - 35);
		}
		
		if (Inventory.torch) {
			g.setFont(fnt2);
			g.setColor(Color.red);
			g.drawString("* Torch is burning (" + Inventory.torchSeconds + " seconds remaining) *", 200, 40);
		} else {
			if (d >= 1200 || d <= -1200) {
				g.setFont(fnt2);
				g.setColor(Color.red);
				g.drawString("! You are losing health, return back to the fire !", 200, 40);
			} else if (fire == 0) {
				g.setFont(fnt2);
				g.setColor(Color.red);
				g.drawString("! You are losing health, stoke the fire !", 200, 40);
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
