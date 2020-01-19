package Main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Other.BufferedImageLoader;
import Other.STATE;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1550691097823471818L;
	
	//Dimensions of the window
	public static final int WIDTH = 1286, HEIGHT = 749;
	
	public static String title = "Surviving Samoil's Backyard";
	
	//Giving the game a thread to run on
	private Thread thread;
	private boolean running = false;
	
	//Other Stuff
	private Handler handler;
	private MouseInput input;
	private Inventory inv;
	private Menu menu;
	private Dead dead;
	private HUD hud;
	
	//Sprites
	public static BufferedImage game_background;
	public static BufferedImage sprite_sheet;
	public static BufferedImage small_tree;
	public static BufferedImage reg_tree;
	public static BufferedImage large_tree;
	public static BufferedImage fire_image;
	public static BufferedImage rock_image;
	public static BufferedImage metal_rock_image;
	public static BufferedImage bush_image;
	public static BufferedImage monster_sheet;
	
	//Game states
	public static STATE gameState = STATE.Menu;
	public static boolean pause = false;
	public static boolean openInv = false;
	
	//Constructor to make window appear when game object is created
	public Game() {
		handler = new Handler();
		inv = new Inventory();
		menu = new Menu();
		dead = new Dead(handler);
		hud = new HUD();
		
		//Textures
		BufferedImageLoader loader = new BufferedImageLoader();
		game_background = loader.loadImage("/background.png");
		sprite_sheet = loader.loadImage("/sprite_sheet.png");
		small_tree = loader.loadImage("/small_tree1.png");
		reg_tree = loader.loadImage("/reg_tree1.png");
		large_tree = loader.loadImage("/large_tree1.png");
		fire_image = loader.loadImage("/fire1.png");
		rock_image = loader.loadImage("/rock.png");
		metal_rock_image = loader.loadImage("/metal_rock.png");
		bush_image = loader.loadImage("/bush.png");
		monster_sheet = loader.loadImage("/ghost.gif");
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(input = new MouseInput(handler));
		
		new Window(WIDTH, HEIGHT, title, this);
	}

	public synchronized void start() {
		if (running) return;
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		if (!running) return;
		
		try {
			thread.join();
		} catch(Exception e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	//Game loop
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				tick();
				delta--;
			}
			
			if (running) {
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		input.tick();
		if (gameState == STATE.Game) {
			if (!pause) {
				handler.tick();
				hud.tick();
				inv.tick();
			}
		} else if (gameState == STATE.Menu || gameState == STATE.Help) {
			handler.tick();
			menu.tick();
		} else if (gameState == STATE.Dead) {
			handler.tick();
			dead.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(game_background, -640, -360, null);

		handler.render(g);
		
		if (gameState == STATE.Game) {
			hud.render(g);
			if (openInv) {
				inv.render(g);
			}
		} else if (gameState == STATE.Menu || gameState == STATE.Help) {
			menu.render(g);
		} else if (gameState == STATE.Dead) {
			dead.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	//Used for limiting values (collision detection on edges, health, etc.)
	public static int clamp(int var, int max, int min) {
		return Math.min(Math.max(min, var), max);
	}
	
	//Used for limiting values (collision detection on edges, health, etc.)
	public static double clamp(double var, int max, int min) {
		if (var >= max) {
			return max;
		} else if (var <= min) {
			return min;
		} else {
			return var;
		}
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
