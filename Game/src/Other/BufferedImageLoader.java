package Other;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	
	BufferedImage buffered_image;
	
	public BufferedImage loadImage(String path) {
		try {
			buffered_image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffered_image;
	}
}
