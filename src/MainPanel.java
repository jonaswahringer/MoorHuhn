import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, MouseListener{
	
	BufferedImage background;
	ArrayList<Moorhuhn> moorhuhnArray = new ArrayList<Moorhuhn>();
	ArrayList<Rectangle> hitboxArray = new ArrayList<Rectangle>();
	int x = -40;
	int y = 0;
	int width = 130;
	int height = 142;
	Point point;
	int clickX = 0;
	int clickY;
	Point click = new Point();
	
	public MainPanel() {
		File file = new File("background.png");
		this.addMouseListener(this);
		try {
			background = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<10; i++) {
			moorhuhnArray.add(new Moorhuhn(x,y));
			hitboxArray.add(new Rectangle(x,y,width,height));
			y += 100;
			Runnable animation = moorhuhnArray.get(i).new Animation(moorhuhnArray.get(i));
			Thread animationThread = new Thread(animation);
			animationThread.start();
		}
		
		Runnable update = this;
		Thread updateThread = new Thread(update);
		updateThread.start();
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(background,0,0,background.getWidth(),background.getHeight(), null);
		for (Moorhuhn moorhuhn : moorhuhnArray) {
			g.drawImage(moorhuhn.getImg(),moorhuhn.getX(),moorhuhn.getY(),width ,height, null);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				TimeUnit.NANOSECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("updated");
			this.repaint();
			if(moorhuhnArray.get(0).getX() == 700) {
				moorhuhnArray.get(0).setIsFlying(false);
				moorhuhnArray.get(0).kill();
			}
			for(int i=0; i<10; i++) {
				hitboxArray.get(i).setBounds(new Rectangle(moorhuhnArray.get(i).getX(), moorhuhnArray.get(i).getY(), width, height));
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		click = e.getPoint();
		for(int i=0; i<10; i++) {
			if(hitboxArray.get(i).contains(click)) {
				System.out.println("Clicked " + i);
				moorhuhnArray.get(i).kill();
				moorhuhnArray.get(i).setIsFlying(false);
			}
		}
//		System.out.println("Click: x: " + e.getX() + " y: " + e.getY());
//		System.out.println("Pic: x: " + moorhuhnArray.get(0).getX() + " y: " + moorhuhnArray.get(0).getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
