import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel implements Runnable, MouseListener {
	GameWindow gameWindow;
	BufferedImage background;
	BufferedImage heart;
	BufferedImage munition;
	ArrayList<Moorhuhn> moorhuhnArray = new ArrayList<>();
	ArrayList<Rectangle> hitboxArray = new ArrayList<>();
	Point point;
	Point click = new Point();
	int clickX = 0;
	int clickY = 0;
	int chickenCount;
	int[] xValues = new int[100];
	int[] yValues = new int[100];
	int speedValues[] = new int[100];
	int width, height;
	int difficultyLevel = 1;
	int livesAvailable = 3;
	Boolean isPlayerAlive = true;
	Boolean bulletFlag=true;
	int currentAmmo=3;
	int count = 3;
	Action keyListener;

	public GamePanel(GameWindow gameWindow) {
		this.setBounds(0, 100, 1000, 700);
		initFile();
		setAttributeValues();
		createChickens();
		spawnNewChickens();

		this.gameWindow = gameWindow;

		this.addMouseListener(this);

		Thread updateThread = new Thread(this);
		updateThread.start();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
		int x = 10;
		for (int i=0;i<livesAvailable;i++) {
			g.drawImage(heart,x,10,heart.getWidth(),heart.getHeight(),null);
			x = x + heart.getWidth() + 10;
		}
		int x1 = 1000 - munition.getWidth();
		for (int i=0;i<currentAmmo;i++) {
			g.drawImage(munition,x1,10,munition.getWidth(),munition.getHeight(),null);
			x1 = x1 - munition.getWidth() - 10;
		}
		for (Moorhuhn moorhuhn : moorhuhnArray) {
			g.drawImage(moorhuhn.getImg(), moorhuhn.getX(), moorhuhn.getY(), width, height, null);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Boolean bool = checkLives();
			if(bool==true) {
				try {
					Thread.sleep(0, 10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	//			System.out.println("updated");
	
				for (int i = 0; i < chickenCount; i++) {
					hitboxArray.get(i).setBounds(
							new Rectangle(moorhuhnArray.get(i).getX(), moorhuhnArray.get(i).getY(), width, height));
				}

				this.requestFocus();
				
				keyListener = new ActionKeyListener();
	
				this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ActionKey");
				this.getActionMap().put("ActionKey", keyListener);
	
				this.repaint();
			}
			else {
				System.out.println("Lost");
			}
		}
		
			
			

	}

	public class ActionKeyListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("ESC key got pressed!!!");
			System.out.println(moorhuhnArray.get(0).getIsFlying());
			for (int i = 0; i < chickenCount; i++) {
				moorhuhnArray.get(i).setIsFlying(false);
			}
			gameWindow.changeToSettings();

		}

	}

	public void initFile() {
		File background_file = new File("images/background.png");
		File heart_icon_file = new File("images/heart_icon.png");
		File munition_icon_file = new File("images/munition_icon.png"); 
		try {
			background = ImageIO.read(background_file);
			heart = ImageIO.read(heart_icon_file);
			munition = ImageIO.read(munition_icon_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAttributeValues() {
		chickenCount = 3;
		width = 100;
		height = 100;

		for (int i = 0; i < chickenCount; i++) {
			if (xValues[i] + 200 > xValues[i + 1]) {
				xValues[i] = ThreadLocalRandom.current().nextInt(-40, 30 + 1);
				yValues[i] = ThreadLocalRandom.current().nextInt(0+height, 700-height + 1);
				speedValues[i] = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			}

		}
	}

	public void createChickens() {
		if (difficultyLevel == 1) {
			for (int i = 0; i < chickenCount; i++) {
				moorhuhnArray.add(new Moorhuhn(xValues[i], yValues[i], speedValues[i]));
				hitboxArray.add(new Rectangle(xValues[i], yValues[i], width, height));
				Runnable animation = moorhuhnArray.get(i).new Animation(moorhuhnArray.get(i));
				Thread animationThread = new Thread(animation);
				animationThread.start();
			}
		}

	}
	
	public void spawnNewChickens() {
		new Thread(()->{
			while(true) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int x = ThreadLocalRandom.current().nextInt(-40, 30 + 1);
				int y = ThreadLocalRandom.current().nextInt(0+height, 700-height + 1);
				int speed = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				Moorhuhn newHuhn = new Moorhuhn(x, y, speed);
				moorhuhnArray.add(newHuhn);
				hitboxArray.add(new Rectangle(x, y, width, height));
				Runnable animation = newHuhn.new Animation(newHuhn);
				Thread animationThread = new Thread(animation);
				animationThread.start();
				chickenCount++;
				//System.out.println(chickenCount);
			}
		}).start();
	}

	public void setFlying() {
		for (int i = 0; i < chickenCount; i++) {
			moorhuhnArray.get(i).setIsFlying(true);
			Runnable animation = moorhuhnArray.get(i).new Animation(moorhuhnArray.get(i));
			Thread animationThread = new Thread(animation);
			animationThread.start();
		}
	}

	public Boolean checkLives() {
		if(livesAvailable == 0) {
			isPlayerAlive=false;
			//showYouLostScreen();
		}
		else {
			for (int i = 0; i < chickenCount; i++) {
				if (moorhuhnArray.get(i).getX()>1000) {
					if(moorhuhnArray.get(i).getIsFlying()==true) {
						System.out.println("Live gone ");
						moorhuhnArray.get(i).kill();
						moorhuhnArray.get(i).setIsFlying(false);
						livesAvailable--;
						
						//do front end stuff
					}
				}
			}
		}
		return isPlayerAlive;
	}

	public void checkAmmo() {
		

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (count > 0) {
					count--;
					System.out.println(count + "COUNT>0");
				}

				if (count == 0) {
					System.out.println(count + "COUNT");
					currentAmmo=3;
					count=3;
					return;
				}
					
				
				System.out.println(count);
			}
		};
		timer.schedule(task, 0, 1000);
		
		/*bulletFlag=true;
		new Thread(()->{
			label: {
				while(bulletFlag && currentAmmo < 3) {
					if(currentAmmo == 0) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(currentAmmo<3 && bulletFlag) {
							currentAmmo++;
							break label;
						}
					}
					else {
						System.out.println("normal i guess");
						// bulletFlag=false;
					}

				}
			}
		}).start();		*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(currentAmmo>0) {
			click = e.getPoint();
			for (int i = 0; i < chickenCount; i++) {
				if (hitboxArray.get(i).contains(click)) {
					System.out.println("Killed Chicken " + i);
					moorhuhnArray.get(i).kill();
					moorhuhnArray.get(i).setIsFlying(false);
					hitboxArray.get(i).setBounds(new Rectangle(0, 0, 0, 0));
					// hitboxArray.set(i, null);
				}
			}
			currentAmmo--;
			if(currentAmmo==0) {
				checkAmmo();
			}
			
		}
		else {
			System.out.println("No ammo available right now");
		}
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
