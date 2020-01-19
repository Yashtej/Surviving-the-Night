package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Other.STATE;

public class HUD {
	
	public static JPanel storyPanel;
	public static JTextArea storyText;
	
	//Survival Time
	public static int timeElapsed = 0; //In minutes
	private int ticks2 = 0;
	
	//Player stats
	public static double HEALTH = 100;
	public static int HUNGER = 100;
	
	//Hunger timer
	private int ticks = 0;
	//Other
	private int ticks1 = 0;
	
	public void tick() {
		HEALTH = Game.clamp(HEALTH, 100, 0);
		HUNGER = Game.clamp(HUNGER, 100, 0);
		
		//Survival time timer
		if (Game.gameState == STATE.Game) {
			if (!Game.pause) {
				ticks2++;
				if (ticks2 == 3600) {
					timeElapsed++;
					ticks2 = 0;
				}
			}
		}
		
		//Hunger goes down over time
		ticks++;
		if (ticks % 100 == 0) {
			HUD.HUNGER--;
		}
		
		//Health goes down if hunger is 0
		if (HUNGER == 0) {
			HEALTH -= 0.03;
		}
		
		//State becomes dead if player loses all health
		if (HEALTH == 0) {
			Game.gameState = STATE.Dead;
		}
		
		//If player takes random damage from collecting things
		if (KeyInput.damageMessage) {
			ticks1++;
			if (ticks1 == 120) {
				ticks1 = 0;
				KeyInput.damageMessage = false;
			}
		}
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("Century Gothic", 1, 12);
		Font fnt1 = new Font("Century Gothic", 1, 15);
		Font fnt2 = new Font("Century Gothic", 1, 14);
		Font fnt3 = new Font("Century Gothic", 1, 18);
		g.setFont(fnt);
		
		//Survival Time
		g.setFont(fnt3);
		g.setColor(Color.pink);
		g.drawString("Time Survived: " + timeElapsed + " min.", 800, 50);
		
		//Health Bar
		g.setFont(fnt1);
		g.setColor(Color.white);
		g.drawString("Health", 20, 15);
		g.setColor(Color.getHSBColor( (1f * (float)HEALTH) / 360, 1f, 1f));
		drawHeart(g, 20, 20, 48, 48);
		g.setColor(Color.black);
		g.setFont(fnt);
		g.drawString((int)HEALTH + "%", 30, 45);
		
		//Hunger Bar
		g.setFont(fnt1);
		g.setColor(Color.white);
		g.drawString("Hunger", 92, 15);
		g.drawOval(95, 21, 50, 50);
		g.setColor(Color.getHSBColor( (1f * (float)HUNGER) / 360, 1f, 1f));
		g.fillOval(95 + ((100 - HUNGER) / 4), 21 + ((100 - HUNGER) / 4), HUNGER / 2, HUNGER / 2);
		
		//Fire
		//g.setColor(Color.gray);
		//g.fillRect(220, 30, 200, 32);
		//g.setColor(Color.white);
		//g.drawRect(220, 30, 200, 32);
		//g.setColor(Color.GREEN);
		//g.fillRect(220, 30, Fire.fire / 27, 32);
		if (Game.pause) {
			g.setFont(fnt1);
			g.setColor(Color.red);
			g.drawString("P A U S E D", 1150, 50);
		}
		
		if (KeyInput.damageMessage) {
			g.setColor(Color.red);
			g.setFont(fnt2);
			g.drawString("You trip and take some damage...", 10, 200);
		}
	}
	
	public void drawHeart(Graphics g, int x, int y, int width, int height) {
	    int[] triangleX = {
	            x - 2*width/18,
	            x + width + 2*width/18,
	            (x - 2*width/18 + x + width + 2*width/18)/2};
	    int[] triangleY = { 
	            y + height - 2*height/3, 
	            y + height - 2*height/3, 
	            y + height };
	    g.fillOval(
	            x - width/12,
	            y, 
	            width/2 + width/6, 
	            height/2); 
	    g.fillOval(
	            x + width/2 - width/12,
	            y,
	            width/2 + width/6,
	            height/2);
	    g.fillPolygon(triangleX, triangleY, triangleX.length);
	}
}
