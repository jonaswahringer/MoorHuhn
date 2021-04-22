import java.awt.Image;
import java.io.File;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Moorhuhn implements Serializable {
	private Image img;
	private Boolean isFlying = true;
	private int x, y;
	private int speed;
	AudioInputStream audioIn=null;
	Clip clip=null;

	{
		img = new ImageIcon("images/moorhuhn.gif").getImage();
	}

	public Moorhuhn(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public Moorhuhn() {
		this.x = 0;
		this.y = 0;
		this.speed = 1;
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
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
	public void move() {
		this.x+=speed;
	}
	
	
	public void kill() {
		new Thread(()->{
			try {
				if(isFlying) {
					img = new ImageIcon("images/explode.gif").getImage();
					Thread.sleep(500);
					img.flush();
					img = null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		if(isFlying) playKillSound();
	}
	
	public void playKillSound() {
		try {
			audioIn = AudioSystem.getAudioInputStream(new File("sounds/kill.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
			clip.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	class Animation extends JPanel implements Runnable {
		
		Moorhuhn moorhuhn;
		
		public Animation(Moorhuhn moorhuhn) {
			this.moorhuhn = moorhuhn;
		}
		
		@Override
		public void run() {
			while(isFlying) {
				moorhuhn.move();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
