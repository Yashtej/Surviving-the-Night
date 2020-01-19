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

public class Tree extends GameObject {
	
	//Texture
	private BufferedImage tree;
	private SpriteSheet t;
	private SpriteSheet t1;
	private SpriteSheet t2;
	
	//Respawn trees
	private int ticks = 0;
	public static boolean message = false;
	
	public Tree(int x, int y, int width, int height, boolean near, boolean action, ID id) {
		super(x, y, width, height, id);
		this.near = near;
		this.action = action;

		t = new SpriteSheet(Game.small_tree);
		t1 = new SpriteSheet(Game.reg_tree);
		t2 = new SpriteSheet(Game.large_tree);
		
		if (getID() == ID.SmallTree) {
			tree = t.grabImage(width, height);
		} else if (getID() == ID.RegTree) {
			tree = t1.grabImage(width, height);
		} else if (getID() == ID.LargeTree) {
			tree = t2.grabImage(width, height);
		}
	}

	public void tick() {
		x -= Player.xOffSet;
		
		//Tree respawn timer
		if (action) {
			ticks++;
			
			if (ticks == KeyInput.treeRespawnTime) {
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
		//If player cuts the tree
		if (action) {
			//Display message that tree has been cut
			if (message) {
				g.setColor(Color.white);
				g.drawString("You cut the tree...", 10, 100);
			}
		//Drawing the tree if it isn't cut
		} else {
			//g.setColor(Color.GREEN);
			//g.fillRect(x + 105, y, width - 200, height);
			g.drawImage(tree, x, y, null);
			
			//Displays box above player's head if they are near a tree
			if (near) {
				if (Inventory.axe) {
					g.setColor(Color.white);
					g.fillRect(x + 30, y + 110, 210, 20);
					g.setColor(Color.black);
					g.drawRect(x + 30, y + 110, 210, 20);
					g.drawString("Press 'E' to chop down the tree", x + 35, y + 126);
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 105, y, width - 200, height);
	}
}
