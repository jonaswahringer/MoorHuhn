import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, MouseListener, KeyListener {
	GameWindow gameWindow;
	Login loginData;
	DatabaseConnection dbCon;
	BufferedImage background;
	BufferedImage heart;
	BufferedImage munition;
	BufferedReader br;
	BufferedWriter bwr;
	HashMap<Integer, Object> serMap = new HashMap<>();
	ArrayList<Moorhuhn> moorhuhnArray = new ArrayList<>();
	ArrayList<Rectangle> hitboxArray = new ArrayList<>();
	Point point;
	Point click = new Point();
	Action keyListener;
	String savestandString;
	String userName;
	Boolean isPlayerAlive = true;
	Boolean bulletFlag=true;
	int chickenCount;
	int[] xValues = new int[100];
	int[] yValues = new int[100];
	int speedValues[] = new int[100];
	int width, height;
	int difficultyLevel=1;
	int livesAvailable = 3;
	int score=0;
	int currentAmmo=3;
	int count = 3;
	int userHighscore;
	long sleepTime=4000;
	

	public GamePanel(GameWindow gameWindow, Login login) {
		this.setBounds(0, 100, 1000, 700);

		this.loginData = login;
		userName = loginData.getFinalUsername();
		userHighscore = loginData.getFinalHighscore();

		dbCon = new DatabaseConnection();

		initFile();
		setAttributeValues();
		checkIfSerialized();
		createChickens();
		spawnNewChickens();

		this.gameWindow = gameWindow;
		this.addMouseListener(this);	
		this.addKeyListener(this);	

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

		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString("Score: " + Integer.toString(score), 450, 35);

		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.drawString("Level: " + Integer.toString(difficultyLevel), 450, 65);

		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		g.drawString("Highscore of " + userName+ ": " + Integer.toString(userHighscore), 450, 95);

	}

	@Override
	public void run() {
		while (true) {
			Boolean bool = checkLives();
			if(bool==true) {
				try {
					Thread.sleep(0, 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
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
				bool=checkLives();
				break;
				
			}
		}
			
	}

	public void checkIfSerialized() {
		dbCon = new DatabaseConnection();
		
		userName = loginData.getFinalUsername();
		userHighscore = loginData.getFinalHighscore();

		Boolean check = dbCon.checkIfUserSerialized(userName);
		if(check) {
			System.out.println("CHECK IS TRUE - DESERIALIZE");
			deSerialize();
		}
	}

	public void serialize() {
		serMap.put(1, livesAvailable);
		serMap.put(2, score);
		serMap.put(3, currentAmmo);
		serMap.put(4, difficultyLevel);

		dbCon.writeHashMap(userName, serMap);
		
	}

	private void deSerialize() {
		serMap = dbCon.readHashMap(userName);

		for(int h=1; h<=6; h++) {
			switch(h) {
				case 1: this.livesAvailable=(int) serMap.get(1);
				break;
				case 2: this.score=(int) serMap.get(2);
				break;
				case 3: this.currentAmmo=(int) serMap.get(3);
				break;
				case 4: this.difficultyLevel = (int) serMap.get(4);
				checkLvlUp();
				break;
			}
		}

    }

	public class ActionKeyListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("ESC pressed");
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
			e.printStackTrace();
		}
	}

	

	public void setAttributeValues() {
		chickenCount = 7;
		width = 100;
		height = 100;

		for (int i = 0; i < chickenCount; i++) {
			if (xValues[i] + 200 > xValues[i + 1]) {
				xValues[i] = ThreadLocalRandom.current().nextInt(-10, 10);
				yValues[i] = ThreadLocalRandom.current().nextInt(0+height, 700-height + 1);
				speedValues[i] = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			}
		}
	}

	public void createChickens() {
		for (int i = 0; i < chickenCount; i++) {
			moorhuhnArray.add(new Moorhuhn(xValues[i], yValues[i], speedValues[i]));
			hitboxArray.add(new Rectangle(xValues[i], yValues[i], width, height));
			Runnable animation = moorhuhnArray.get(i).new Animation(moorhuhnArray.get(i));
			Thread animationThread = new Thread(animation);
			animationThread.start();
		}
	}
	
	public void spawnNewChickens() {
		new Thread(()->{
			int i=0;
			while(true) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int x = ThreadLocalRandom.current().nextInt(-40, 10 + 1);
				int y = ThreadLocalRandom.current().nextInt(0+height, 700-height + 1);
				int speed = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				Moorhuhn newHuhn;
				if(i==10) {
					newHuhn = new Bosshuhn(x, y, speed);
				}
				else {
					newHuhn = new Moorhuhn(x, y, speed);
				}
				moorhuhnArray.add(newHuhn);
				hitboxArray.add(new Rectangle(x, y, width, height));
				Runnable animation = newHuhn.new Animation(newHuhn);
				Thread animationThread = new Thread(animation);
				animationThread.start();
				chickenCount++;
				i++;
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
			if(score>userHighscore) {
				dbCon.setValuesToDefault(userName);
				gameWindow.changeToLost(userName, score, true);
			}
			else {
				dbCon.setValuesToDefault(userName);
				gameWindow.changeToLost(userName, score, false);
			}
			
		}
		else {
			
			for (int i = 0; i < chickenCount; i++) {
				if (moorhuhnArray.get(i).getX()>1020){
					if(moorhuhnArray.get(i).getIsFlying()==true) {
						System.out.println("Live gone ");
						moorhuhnArray.get(i).kill();
						moorhuhnArray.get(i).setIsFlying(false);
						livesAvailable--;
					}
				}
			}
		}
		return isPlayerAlive;
	}

	public void checkLvlUp() {
		if(sleepTime>300) {
			if(score%10 == 0) {
				difficultyLevel++;
				sleepTime-=300;
			}
		}
		else {
			sleepTime=300;
		}	
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
					// System.out.println("Killed Chicken " + i);
					moorhuhnArray.get(i).kill();
					moorhuhnArray.get(i).setIsFlying(false);
					hitboxArray.get(i).setBounds(new Rectangle(0, 0, 0, 0));
					score++;
					checkLvlUp();
				}
			}
			currentAmmo--;			
		}
		else {
			System.out.println("No ammo available right now");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R){
            currentAmmo = 3;
        }		
	}

	@Override
	public void keyReleased(KeyEvent e) {		
	}

}
