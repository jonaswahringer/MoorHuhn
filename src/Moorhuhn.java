import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Moorhuhn {
	int x, y;
	BufferedImage img;
	
	{
		File file = new File("flughuhn.png");
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Moorhuhn(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Moorhuhn() {
		this.x = 0;
		this.y = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}	
}
