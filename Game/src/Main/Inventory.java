package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Inventory {
	
	public static boolean axe = false;
	public static boolean woodPickaxe = false;
	public static boolean stonePickaxe = false;
	public static boolean knuckles = false;
	public static boolean torch = false;
	public static int wood = 3;
	public static int rock = 3;
	public static int metal;
	public static int coal;
	public static int berry;
	public static int meat;
	public static int healingSalve;
	public static int traps;
	
	//Torch timer
	private int ticks = 0;
	private int torchTime = 3600;
	public static int torchSeconds = 60;
	
	public void tick() {
		//Torch timer
		if (torch) {
			ticks++;
			if (ticks == torchTime) {
				torch = false;
				torchSeconds = 60;
				ticks = 0;
			} else if (ticks % 60 == 0) {
				torchSeconds--;
			}
			//System.out.println(ticks);
		}
	}
	
	public void render(Graphics g) {
		int alpha = 40;
		int alpha1 = 60;
		Font fnt = new Font("Century Gothic", 1, 20);
		Font fnt1 = new Font("Century Gothic", 1, 17);
		
		//Outline
		g.setColor(Color.BLACK);
		g.drawRect(0, 525, Game.WIDTH, 195);
		
		//Background
		g.setColor(new Color(255, 255, 255, alpha));
		g.fillRect(0, 525, Game.WIDTH, 195);

// ---------------------- INVENTORY ----------------------
		//Title
		g.setColor(Color.BLUE);
		g.setFont(fnt);
		g.drawString("==[ Inventory ]==", 125, 550);
		
		//Axe
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		if (axe) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Axe", 112, 575);
		
		//Pickaxe
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		if (woodPickaxe) {
			if (stonePickaxe) {
				g.drawString("Stone Pickaxe", 61, 600);
			} else {
				g.drawString("Wood Pickaxe", 61, 600);
			}
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
			g.drawString("Wood Pickaxe", 61, 600);
		}
		
		//Knuckles
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		if (knuckles) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Knuckles", 88, 625);
		
		//Torch
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		if (torch) {
			g.setColor(Color.white);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Torch", 106, 650);
		
		//Wood
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Wood: " + wood, 93, 675);
		
		//Rock
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Rock: " + rock, 96, 700);
		
		//Metal
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Metal: " + metal, 255, 575);
		
		//Coal
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Coal: " + coal, 258, 600);
		
		//Berry
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Berries: " + berry, 250, 625);
		
		//Meat
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Meat: " + meat, 257, 650);
		
		//Healing Salve
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Healing Salves: " + healingSalve, 210, 675);
		
		//Traps
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawString("Traps: " + traps, 257, 700);

// ---------------------- CRAFTING ----------------------
		//Title
		g.setColor(Color.BLUE);
		g.setFont(fnt);
		g.drawString("==[ Crafting ]==", 980, 550);
		
		//Axe
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(850, 560, 200, 40);
		if (axe) {
			g.setColor(new Color(255, 255, 255, alpha1));
		} else {
			if (wood >= 2 && rock >= 3) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(new Color(255, 255, 255, alpha1));
			}
		}
		g.drawString("Craft Axe", 910, 586);
		
		//Pickaxes
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(850, 610, 200, 40);
		//Stone Pickaxe
		if (woodPickaxe) {
			if (stonePickaxe) {
				g.setColor(new Color(255, 255, 255, alpha1));
			} else {
				if (wood >= 5 && rock >= 7) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(new Color(255, 255, 255, alpha1));
				}
			}
			g.drawString("Craft Stone Pickaxe", 870, 637);
		//Wood Pickaxe
		} else {
			if (wood >= 10) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(new Color(255, 255, 255, alpha1));
			}
			g.drawString("Craft Wood Pickaxe", 868, 637);
		}
		
		//Knuckles
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(850, 660, 200, 40);
		if (knuckles) {
			g.setColor(new Color(255, 255, 255, alpha1));
		} else {
			if (rock >= 5 && metal >= 5) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(new Color(255, 255, 255, alpha1));
			}
		}
		g.drawString("Craft Knuckles", 892, 688);
		
		//Healing Salve
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(1060, 560, 200, 40);
		if (berry >= 2 && coal >= 1 && meat >= 1) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Craft Healing Salve", 1082, 586);
		
		//Trap
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(1060, 610, 200, 40);
		if (traps == 3) {
			g.setColor(new Color(255, 255, 255, alpha1));
		} else {
			if (wood >= 10 && rock >= 5 && meat >= 1) {
				//g.setColor(Color.WHITE);
				g.setColor(new Color(255, 255, 255, alpha1));
			} else {
				g.setColor(new Color(255, 255, 255, alpha1));
			}
		}
		g.drawString("Craft Trap", 1120, 636);
		
		//Torch
		g.setColor(Color.WHITE);
		g.setFont(fnt1);
		g.drawRect(1060, 660, 200, 40);
		if (torch) {
			g.setColor(Color.YELLOW);
		} else {
			if (wood >= 5 && coal >= 2) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(new Color(255, 255, 255, alpha1));
			}
		}
		g.drawString("Craft Torch", 1117, 688);
		
// ---------------------- FUNCTIONS ----------------------
		//Title
		g.setColor(Color.BLUE);
		g.setFont(fnt);
		g.drawString("==[ Functions ]==", 540, 550);
		
		//Eat
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawRect(522, 560, 200, 40);
		if (berry > 0) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Eat", 610, 587);
		
		//Heal
		g.setColor(Color.WHITE);
		g.setFont(fnt);
		g.drawRect(522, 610, 200, 40);
		if (healingSalve > 0) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(255, 255, 255, alpha1));
		}
		g.drawString("Heal", 602, 638);
	}
}

