package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Entities.Player;

public class Dead {

	private Handler handler;
	
	//Fading in death message
	private int alpha = 0;
	private int alpha1 = 0;
	
	public Dead(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		handler.clearAll();
		
		Player.xOffSet = 0;
		Player.x1 = 0;
		
		HUD.HEALTH = 100;
		HUD.HUNGER = 100;
		
		Inventory.axe = false;
		Inventory.woodPickaxe = false;
		Inventory.stonePickaxe = false;
		Inventory.knuckles = false;
		Inventory.torch = false;
		Inventory.wood = 3;
		Inventory.rock = 3;
		Inventory.metal = 0;
		Inventory.coal = 0;
		Inventory.berry = 0;
		Inventory.meat = 0;
		Inventory.healingSalve = 0;
		
		if (alpha < 255) {
			alpha++;
		}
		
		if (alpha >= 150) {
			if (alpha1 < 255) {
				alpha1++;
			}
		}
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 50);
		Font fnt1 = new Font("Century Gothic", 1, 40);
		Font fnt2 = new Font("Century Gothic", 1, 30);

		//Title
		g.setFont(fnt);
		g.setColor(new Color(186, 14, 2, alpha));
		g.drawString("Y O U   D I E D", 470, 230);
		g.fillRect(565, 250, 150, 3);

		//Time Survived
		g.setFont(fnt2);
		g.setColor(Color.gray);
		g.drawString("Time Survived: " + HUD.timeElapsed + " min.", 485, 300);
		
		//Return Button
		g.setFont(fnt1);
		g.setColor(new Color(66, 134, 244, alpha1));
		if (MouseInput.mouseOver(MouseInput.mx, MouseInput.my, 443, 354, 400, 75)) {
			g.drawRect(438, 327, 404, 79);
		}
		g.setColor(new Color(255, 255, 255, alpha1));
		g.drawRect(440, 329, 400, 75);
		g.setFont(fnt1);
		g.drawString("Return", 580, 380);
	}
}
