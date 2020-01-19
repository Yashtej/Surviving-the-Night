package Other;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage sprite;
	
	public SpriteSheet(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height) {
		return sprite.getSubimage(col * 106, row * 114, width, height);
	}
	
	public BufferedImage grabImage(int width, int height) {
		return sprite.getSubimage(0, 0, width, height);
	}
	
	public BufferedImage grabImage1(int x, int y, int width, int height) {
		return sprite.getSubimage(x, y, width, height);
	}
}
