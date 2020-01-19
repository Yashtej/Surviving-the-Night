package Main;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Entities.Monster;
import Entities.Player;
import Objects.Bush;
import Objects.Fire;
import Objects.Floor;
import Objects.MetalRock;
import Objects.Rock;
import Objects.Tree;
import Other.ID;
import Other.STATE;

public class MouseInput extends MouseAdapter {

	private Handler handler;
	
	//Object Arrays
	private Tree[] tree = new Tree[9];
	private Rock[] rock = new Rock[4];
	private MetalRock[] metalRock = new MetalRock[3];
	private Bush[] bush = new Bush[4];
	
	//Using to get position of the mouse within the frame
	private Point a;
	
	public static int mx;
	public static int my;
	
	public MouseInput(Handler handler) {
		this.handler = handler;
	}

	public void tick() {
		//Get position of the mouse within the frame
		a = Window.frame.getMousePosition();
		if (a != null) {
			mx = (int)a.getX();
			my = (int)a.getY();
		}
		//System.out.println(a);
	}
	
	public void mouseEnter(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//Mouse input for menu
		if (Game.gameState == STATE.Menu) {
			//Start Button
			if (mouseOver(mx, my, 440, 230, 400, 75)) {
				Game.gameState = STATE.Game;
				
				HUD.timeElapsed = 0;
				
				//Small Trees
				tree[0] = new Tree(305, 275, 250, 250, false, false, ID.SmallTree);
				tree[1] = new Tree(930, 275, 250, 250, false, false, ID.SmallTree);
				tree[2] = new Tree(1450, 275, 250, 250, false, false, ID.SmallTree);
				//Regular Trees
				tree[3] = new Tree(625, 250, 275, 275, false, false, ID.RegTree);
				tree[4] = new Tree(-500, 250, 275, 275, false, false, ID.RegTree);
				tree[5] = new Tree(1700, 250, 275, 275, false, false, ID.RegTree);
				//Large Trees
				tree[6] = new Tree(-50, 225, 300, 300, false, false, ID.LargeTree);
				tree[7] = new Tree(-1150, 225, 300, 300, false, false, ID.LargeTree);
				tree[8] = new Tree(1300, 225, 300, 300, false, false, ID.LargeTree);
				
				rock[0] = new Rock(300, 467, 75, 52, false, false, ID.Rock);
				rock[1] = new Rock(-550, 467, 75, 52, false, false, ID.Rock);
				rock[2] = new Rock(840, 467, 75, 52, false, false, ID.Rock);
				rock[3] = new Rock(1950, 467, 75, 52, false, false, ID.Rock);
				
				metalRock[0] = new MetalRock(-670, 452, 100, 69, false, false, ID.MetalRock);
				metalRock[1] = new MetalRock(-1200, 452, 100, 69, false, false, ID.MetalRock);
				metalRock[2] = new MetalRock(2300, 452, 100, 69, false, false, ID.MetalRock);
				
				bush[0] = new Bush(170, 455, 120, 70, false, false, ID.Bush);
				bush[1] = new Bush(-300, 455, 120, 70, false, false, ID.Bush);
				bush[2] = new Bush(1150, 455, 120, 70, false, false, ID.Bush);
				bush[3] = new Bush(2150, 455, 120, 70, false, false, ID.Bush);
				
				handler.addObject(tree[0]);
				handler.addObject(tree[1]);
				handler.addObject(tree[2]);
				handler.addObject(tree[3]);
				handler.addObject(tree[4]);
				handler.addObject(tree[5]);
				handler.addObject(tree[6]);
				handler.addObject(tree[7]);
				handler.addObject(tree[8]);
				handler.addObject(rock[0]);
				handler.addObject(rock[1]);
				handler.addObject(rock[2]);
				handler.addObject(rock[3]);
				handler.addObject(metalRock[0]);
				handler.addObject(metalRock[1]);
				handler.addObject(metalRock[2]);
				handler.addObject(bush[0]);
				handler.addObject(bush[1]);
				handler.addObject(bush[2]);
				handler.addObject(bush[3]);
				handler.addObject(new Floor(0, 520, Game.WIDTH, 200, ID.Floor));
				handler.addObject(new Fire(500, 440, 101, 101, false, false, ID.Fire));
				handler.addObject(new Monster(1380, 420, 64, 100, false, false, ID.Monster));
				handler.addObject(new Player(608, 422, 63, 99, ID.Player, handler));
			//Help Button
			} else if (mouseOver(mx, my, 440, 315, 400, 75)) {
				Game.gameState = STATE.Help;
			//Quit Button
			} else if (mouseOver(mx, my, 440, 400, 400, 75)) {
				System.exit(0);
			}
		//Mouse input for in game (using inventory)
		} else if (Game.gameState == STATE.Game) {
			if (!Game.pause) {
				if (Game.openInv) {
					//Craft Axe
					if (mouseOver(mx, my, 850, 560, 200, 40)) {
						if (!Inventory.axe) {
							if (Inventory.wood >= 2 && Inventory.rock >= 3) {
								Inventory.wood -= 2;
								Inventory.rock -= 3;
								Inventory.axe = true;
							}
						}
					//Craft Wood and Stone Pickaxe
					} else if (mouseOver(mx, my, 850, 610, 200, 40)) {
						if (Inventory.woodPickaxe) {
							if (!Inventory.stonePickaxe) {
								if (Inventory.wood >= 5 && Inventory.rock >= 7) {
									Inventory.wood -= 5;
									Inventory.rock -= 7;
									Inventory.stonePickaxe = true;
								}
							}
						} else {
							if (Inventory.wood >= 10) {
								Inventory.wood -= 10;
								Inventory.woodPickaxe = true;
							}
						}
					//Craft Knuckles
					} else if (mouseOver(mx, my, 850, 660, 200, 40)) {
						if (!Inventory.knuckles) {
							if (Inventory.rock >= 5 && Inventory.metal >= 5) {
								Inventory.rock -= 5;
								Inventory.metal -= 5;
								Inventory.knuckles = true;
							}
						}
					//Craft Healing Salve
					} else if (mouseOver(mx, my, 1060, 560, 200, 40)) {
						if (Inventory.berry >= 2 && Inventory.coal >= 1 && Inventory.meat >= 1) {
							Inventory.berry -= 2;
							Inventory.coal--;
							Inventory.meat--;
							Inventory.healingSalve++;
						}
					//Craft Trap
					} else if (mouseOver(mx, my, 1060, 610, 200, 40)) {
						if (Inventory.traps != 3) {
							if (Inventory.wood >= 10 && Inventory.rock >= 5 && Inventory.meat >= 1) {
								/*
								Inventory.wood -= 10;
								Inventory.rock -= 5;
								Inventory.meat--;
								Inventory.traps++;
								*/
							}
						}
					//Craft Torch
					} else if (mouseOver(mx, my, 1060, 660, 200, 40)) {
						if (!Inventory.torch) {
							if (Inventory.wood >= 5 && Inventory.coal >= 2) {
									Inventory.wood -= 5;
									Inventory.coal -= 2;
									Inventory.torch = true;
							}
						}
					//Eat
					} else if (mouseOver(mx, my, 522, 560, 200, 40)) {
						//Meat is eaten first
						if (Inventory.meat > 0) {
							HUD.HUNGER += 20;
							HUD.HEALTH += 3;
							Inventory.meat--;
						} else if (Inventory.berry > 0) {
							HUD.HUNGER += 5;
							HUD.HEALTH += 2;
							Inventory.berry--;
						}
					//Heal
					} else if (mouseOver(mx, my, 522, 610, 200, 40)) {
						if (Inventory.healingSalve > 0) {
							HUD.HEALTH += 10;
							
							Inventory.healingSalve--;
						}
					}
				}
			}
		} else if (Game.gameState == STATE.Dead) {
			if (mouseOver(mx, my, 440, 329, 400, 75)) {
				Game.gameState = STATE.Menu;
			}
		} else if (Game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 440, 575, 400, 75)) {
				Game.gameState = STATE.Menu;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public static boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < (x + width)) {
			if (my > y && my < (y + height)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
