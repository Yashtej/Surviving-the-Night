package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Other.STATE;

public class Menu {
	
	//Fading in title
	private int alpha = 0;
	private int alpha1 = 0;
	
	public void tick() {
		if (Game.gameState == STATE.Menu) {
			//Title
			if (alpha < 255) {
				alpha++;
			}
			
			if (alpha >= 100) {
				if (alpha1 < 255) {
					alpha1++;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 50);
		Font fnt1 = new Font("Century Gothic", 1, 40);
		Font fnt2 = new Font("Century Gothic", 1, 45);
		Font fnt3 = new Font("Century Gothic", 1, 35);
		Font fnt4 = new Font("Century Gothic", 1, 25);
		
		if (Game.gameState == STATE.Menu) {
			
			//Title
			g.setFont(fnt);
			g.setColor(new Color(255, 255, 255, alpha));
			g.drawString("Surviving", 535, 150);
			g.fillRect(565, 160, 150, 3);
			
			g.setFont(fnt2);
			g.setColor(new Color(66, 134, 244, alpha1));
			g.drawString("Samoil's Backyard", 440, 210);
			
			//Start
			g.setColor(new Color(66, 134, 244));
			if (MouseInput.mouseOver(MouseInput.mx, MouseInput.my, 443, 255, 400, 75)) {
				g.drawRect(438, 228, 404, 79);
			}
			g.setColor(Color.WHITE);
			g.drawRect(440, 230, 400, 75);
			g.setColor(Color.WHITE);
			g.setFont(fnt1);
			g.drawString("Start", 600, 285);
			
			//Help
			g.setColor(new Color(66, 134, 244));
			if (MouseInput.mouseOver(MouseInput.mx, MouseInput.my, 443, 340, 400, 75)) {
				g.drawRect(438, 313, 404, 79);
			}
			g.setColor(Color.WHITE);
			g.drawRect(440, 315, 400, 75);
			g.setColor(Color.WHITE);
			g.setFont(fnt1);
			g.drawString("Help", 595, 369);
			
			//Quit
			g.setColor(new Color(66, 134, 244));
			if (MouseInput.mouseOver(MouseInput.mx, MouseInput.my, 443, 425, 400, 75)) {
				g.drawRect(438, 398, 404, 79);
			}
			g.setColor(Color.WHITE);
			g.drawRect(440, 400, 400, 75);
			g.setColor(Color.WHITE);
			g.setFont(fnt1);
			g.drawString("Quit", 600, 451);
			
		} else if (Game.gameState == STATE.Help) {
			
			//Title
			g.setFont(fnt);
			g.setColor(new Color(66, 134, 244));
			g.drawString("Help", 120, 100);
			g.fillRect(125, 105, 70, 3);
			
			//Controls
			g.setFont(fnt3);
			g.setColor(Color.WHITE);
			g.drawString("Controls", 120, 200);
			g.fillRect(125, 205, 127, 3);
			g.setFont(fnt4);
			g.drawString("A - Move Left", 120, 240);
			g.drawString("D - Move Right", 120, 280);
			g.drawString("E - Use", 120, 320);
			g.drawString("F - Attack", 120, 360);
			g.drawString("W or Space - Jump", 120, 400);
			g.drawString("Q - Inventory", 120, 440);
			
			//Crafting
			g.setFont(fnt3);
			g.setColor(Color.WHITE);
			g.drawString("Crafting Recipes", 505, 200);
			g.fillRect(510, 205, 105, 3);
			g.fillRect(655, 205, 62, 3);
			g.fillRect(735, 205, 40, 3);
			g.setFont(fnt4);
			g.drawString("Axe - 2 Wood, 3 Rock", 505, 240);
			g.drawString("Wooden Pickaxe - 10 Wood", 505, 280);
			g.drawString("Stone Pickaxe - 5 Wood, 7 Rock", 505, 320);
			g.drawString("Knuckles - 5 Rock, 5 Metal", 505, 360);
			g.drawString("Healing Salve - 2 Berry, 1 Coal, 1 Meat", 505, 400);
			g.drawString("Torch - 5 Wood, 2 Coal", 505, 440);
			
			//Return Button
			g.setFont(fnt1);
			g.setColor(new Color(66, 134, 244));
			if (MouseInput.mouseOver(MouseInput.mx, MouseInput.my, 443, 600, 400, 75)) {
				g.drawRect(438, 573, 404, 79);
			}
			g.setColor(new Color(255, 255, 255));
			g.drawRect(440, 575, 400, 75);
			g.setFont(fnt1);
			g.drawString("Return", 580, 626);
		}
	}
}
