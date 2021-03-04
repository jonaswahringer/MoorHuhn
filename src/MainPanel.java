import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	
	BufferedImage background;
	Moorhuhn eins = new Moorhuhn(10,10);
	
	public MainPanel() {
		File file = new File("background.png");
		try {
			background = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(background,0,0,background.getWidth(),background.getHeight(), null);
		g.drawImage(eins.getImg(),eins.getX(),eins.getY(),eins.getImg().getWidth(),eins.getImg().getHeight(), null);
	}

}
