package Objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.GameObject;
import Other.ID;

public class Floor extends GameObject {
	
	public Floor(int x, int y, int width, int height, ID id) {
		super(x, y, width, height, id);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		//g.setColor(new Color(99, 80, 80));
		//g.fillRect(x, y, width, height);
		//g.setColor(new Color(121, 183, 89));
		//g.fillRect(x, y, width, height-140);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
