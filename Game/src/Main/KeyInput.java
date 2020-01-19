package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import Entities.Player;
import Objects.Bush;
import Objects.Fire;
import Objects.MetalRock;
import Objects.Rock;
import Objects.Tree;
import Other.ID;
import Other.STATE;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	
	//Randomizing respawn times and amount of items collected
	Random r = new Random();
	public static int treeRespawnTime;
	public static int rockRespawnTime;
	public static int metalRockRespawnTime;
	public static int bushRespawnTime;
	
	//Chance for player to take damage when they perform some actions
	private int damageChance;
	private int damageAmount = 2;
	private int maxChance = 10;
	public static boolean damageMessage = false;
	
	//Movement booleans
	public static boolean left = false;
	public static boolean right = false;
	public static boolean fLeft = false;
	public static boolean fRight = false;
	public static boolean standing = false;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		//Ensuring that key input only works when in the game
		if (Game.gameState == STATE.Game) {
			//Ensuring that key input only works when not paused
			if (!Game.pause) {
				//Ensuring that key input only works when inventory is not open
				if (!Game.openInv) {
					//Ensuring that key input only works when not attacking
					if (!Player.attack) {
						
						//Move
						if (key == KeyEvent.VK_A) {
							left = true;
							right = false;
							fLeft = true;
							fRight = false;
							Player.xOffSet = -4;
						} else if (key == KeyEvent.VK_D) {
							right = true;
							left = false;
							fRight = true;
							fLeft = false;
							Player.xOffSet = 4;
							
						//Jump
						} else if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W) {
							if (Player.velY == 0) {
								Player.velY = -5;
							}
							
						//Attack
						} else if (key == KeyEvent.VK_F) {
							if (!Player.coolDown) {
								Player.attack = true;
								Player.coolDownCounter = true;
							}
							
						//Use
						} else if (key == KeyEvent.VK_E) {
							//Looping through every object
							for (int i=0;i < handler.object.size();i++) {
								GameObject tempObject = handler.object.get(i);
		
								//Chopping down trees
								if (tempObject.getID() == ID.SmallTree || tempObject.getID() == ID.RegTree || tempObject.getID() == ID.LargeTree) {
									if (tempObject.near) {
										if (Inventory.axe) {
											tempObject.action = true;
											Tree.message = true;
											
											int max = 1200 ;
											int min = 600;
											int woodAmount = r.nextInt((3 - 1) + 1) + 1;
											treeRespawnTime = r.nextInt((max - min) + 1) + min;
											damageChance = r.nextInt(101);
											if (damageChance > 0 && damageChance <= maxChance) {
												damageMessage = true;
												HUD.HEALTH -= damageAmount;
											}
											
											Inventory.wood += woodAmount;
										}
									}
								//Mining rocks
								} else if (tempObject.getID() == ID.Rock) {
									if (tempObject.near) {
										if (Inventory.woodPickaxe) {
											tempObject.action = true;
											Rock.message = true;
											
											int max = 1800;
											int min = 1200;
											int rockAmount = r.nextInt((3 - 1) + 1) + 1;
											rockRespawnTime = r.nextInt((max - min) + 1) + min;
											damageChance = r.nextInt(101);
											if (damageChance > 0 && damageChance <= maxChance) {
												damageMessage = true;
												HUD.HEALTH -= damageAmount;
											}
											
											Inventory.rock += rockAmount;
											Inventory.coal++;
										}
									}
								//Mining metal rocks
								} else if (tempObject.getID() == ID.MetalRock) {
									if (Inventory.stonePickaxe) {
										if (tempObject.near) {
											tempObject.action = true;
											MetalRock.message = true;
											
											int max = 3600;
											int min = 1800;
											int rockAmount = r.nextInt((3 - 1) + 1) + 1;
											metalRockRespawnTime = r.nextInt((max - min) + 1) + min;
											damageChance = r.nextInt(101);
											if (damageChance > 0 && damageChance <= maxChance) {
												damageMessage = true;
												HUD.HEALTH -= damageAmount;
											}
											
											Inventory.rock += rockAmount;
											Inventory.metal++;
											Inventory.coal++;
										}
									}
								//Harvesting bushes
								} else if (tempObject.getID() == ID.Bush) {
									if (tempObject.near) {
										tempObject.action = true;
										Bush.message = true;
										
										int max = 1800;
										int min = 1200;
										int berryAmount = r.nextInt((3 - 1) + 1) + 1;
										bushRespawnTime = r.nextInt((max - min) + 1) + min;
										damageChance = r.nextInt(101);
										if (damageChance > 0 && damageChance <= maxChance) {
											damageMessage = true;
											HUD.HEALTH -= damageAmount;
										}
										
										Inventory.berry += berryAmount;
									}
								//Stoking fire
								} else if (tempObject.getID() == ID.Fire) {
									if (tempObject.near) {
									 	if (Fire.fire >= -1) {
											if (Inventory.wood >= 1) {
												tempObject.action = true;
												
												Inventory.wood--;
												Fire.fire += 600;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			//Inventory
			if (key == KeyEvent.VK_Q) {
				if (Game.gameState == STATE.Game) {
					 if (Game.openInv) {
						 Game.openInv = false;
					 } else {
						 Game.openInv = true;
					 }
				}
			}
			
			if (key == KeyEvent.VK_P) {
				if (Game.gameState == STATE.Game) {
					if (Game.pause) {
						Game.pause = false;
					} else {
						Game.pause = true;
					}
				}
			}
		}
	
		//Quit
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
				
		//Looping through every object
		for (int i=0;i < handler.object.size();i++) {
			GameObject temp = handler.object.get(i);
			
			//Key events for player
			if (temp.getID() == ID.Player) {

				if (key == KeyEvent.VK_A) {
					left = false;
					Player.moveTick = 0;
					if (right) {
						Player.xOffSet = 4;
					} else {
						Player.xOffSet = 0;
					}
				} else if (key == KeyEvent.VK_D) {
					right = false;
					Player.moveTick = 0;
					if (left) {
						Player.xOffSet = -4;
					} else {
						Player.xOffSet = 0;
					}
				}
				
				if (!left && !right) {
					Player.xOffSet = 0;
				}
			}
		}
	}
}
