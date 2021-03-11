import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, MouseListener, KeyListener {
	
	BufferedImage background;
	ArrayList<Moorhuhn> moorhuhnArray = new ArrayList<>();
	ArrayList<Rectangle> hitboxArray = new ArrayList<>();
	Point point;
	Point click = new Point();
	int clickX = 0;
	int clickY = 0;
	int chickenCount;
	int[] xValues = new int[20];
	int[] yValues = new int[20];
	int speedValues[] = new int[20];
	int width, height;
	
	public MainPanel() {
		this.setBounds(0, 100, 1000, 700);
		initFile();
		setAttributeValues();
		createChickens();
		
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(this);
		
		Thread updateThread = new Thread(this);
		updateThread.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
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
				Thread.sleep(0,10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("updated");
			
			for(int i=0; i<chickenCount; i++) {
				hitboxArray.get(i).setBounds(new Rectangle(moorhuhnArray.get(i).getX(), moorhuhnArray.get(i).getY(), width, height));
			}
			this.repaint();
		}
		
	}
	
	public void initFile() {
		File file = new File("images/background.png");
		try {
			background = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAttributeValues() {
		chickenCount=5;
		width = 100;
		height = 100;
		
		for(int i=0; i<chickenCount; i++) {
			if(xValues[i]+200 > xValues[i+1]) {
				xValues[i] = ThreadLocalRandom.current().nextInt(-40, 30 + 1);
				yValues[i] = ThreadLocalRandom.current().nextInt(0, 900 + 1);
				speedValues[i] = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			}
			
			
		}
	}
	
	public void createChickens() {
		for(int i=0; i<chickenCount; i++) {
			moorhuhnArray.add(new Moorhuhn(xValues[i],yValues[i], speedValues[i]));
			hitboxArray.add(new Rectangle(xValues[i],yValues[i],width,height));
			Runnable animation = moorhuhnArray.get(i).new Animation(moorhuhnArray.get(i));
			Thread animationThread = new Thread(animation);
			animationThread.start();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		click = e.getPoint();
		for(int i=0; i<chickenCount; i++) {
			if(hitboxArray.get(i).contains(click)) {
				System.out.println("Killed Chicken " + i);
				moorhuhnArray.get(i).kill();
				moorhuhnArray.get(i).setIsFlying(false);
				//hitboxArray.set(i, null);
			}
		}
		
//		System.out.println("Click: x: " + e.getX() + " y: " + e.getY());
//		System.out.println("Pic: x: " + moorhuhnArray.get(0).getX() + " y: " + moorhuhnArray.get(0).getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
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

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		System.out.println("asdfsdf");
		if(ke.getKeyCode()==27) {
			System.out.println("worked");
			MainWindow mw = new MainWindow();
			mw.getContentPane().removeAll();
			mw.add(new MenuPanel());
			mw.revalidate();
			mw.repaint();
			
		}
		else {
			System.out.println(ke.getKeyCode());
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

}
