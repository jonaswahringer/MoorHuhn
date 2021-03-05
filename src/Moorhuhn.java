import java.awt.Image;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Moorhuhn {
	int x, y;
	Image img;
	Boolean isFlying = true;
	
	{
		img = new ImageIcon("moorhuhn.gif").getImage();
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

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}	
	
	public Boolean getIsFlying() {
		return isFlying;
	}

	public void setIsFlying(Boolean isFlying) {
		this.isFlying = isFlying;
	}
	
	public void move() {
		this.x++;
	}
	
	public void kill() {
		img = new ImageIcon("explode.gif").getImage();
	}
	
	class Animation extends JPanel implements Runnable{
		
		Moorhuhn moorhuhn;
		
		public Animation(Moorhuhn moorhuhn) {
			this.moorhuhn = moorhuhn;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isFlying) {
				moorhuhn.move();
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println("x: " + moorhuhn.getX() + " y: " + moorhuhn.getY());
			}
			
		}
		
	}
}
