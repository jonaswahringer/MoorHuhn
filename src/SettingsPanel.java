import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SettingsPanel extends JPanel implements Runnable {
	
	GameWindow gameWindow;
	JPanel mainPanel, topPanel;
	Box box;
	BufferedImage background, restartImage, resumeImage, quitImage, quitSaveImage;
	JLayeredPane backgroundPane, itemPane;
	JLabel gameTitle, backgroundLabel;
	JButton resumeGameButton, settingsButton, aboutButton, restartGameButton, saveAndQuitButton, quitButton;
	File audioFile, bgImageFile;
	AudioInputStream audioIn=null;
	Clip clip=null;
	Action keyListener;
	
	public SettingsPanel(GameWindow gameWindow) {
		this.setBounds(100,100,1000,700);
		this.setLayout(null);
		initFile();
		
		this.gameWindow = gameWindow; 
		
		backgroundLabel = new JLabel();
		backgroundLabel.setIcon(new ImageIcon(background));
		
		resumeGameButton = new JButton(new ImageIcon(resumeImage));
		resumeGameButton.setBounds(400, 50, 200, 150);
		resumeGameButton.addActionListener(new resumeButtonListener());
		resumeGameButton.setBorder(BorderFactory.createEmptyBorder());
        resumeGameButton.setOpaque(false);
        resumeGameButton.setVisible(true);
		this.add(resumeGameButton);
		
		restartGameButton = new JButton(new ImageIcon(restartImage));
		restartGameButton.setBounds(400, 250, 200, 150);
		restartGameButton.addActionListener(new restartButtonListener());
		restartGameButton.setBorder(BorderFactory.createEmptyBorder());
        restartGameButton.setOpaque(false);
        restartGameButton.setVisible(true);
		this.add(restartGameButton);
		
		saveAndQuitButton = new JButton(new ImageIcon(quitSaveImage));
		saveAndQuitButton.setBounds(400, 450, 200, 150);
		saveAndQuitButton.addActionListener(new saveAndQuitButtonListener());
		saveAndQuitButton.setBorder(BorderFactory.createEmptyBorder());
        saveAndQuitButton.setOpaque(false);
        saveAndQuitButton.setVisible(true);
		this.add(saveAndQuitButton);
		
		quitButton = new JButton(new ImageIcon(quitImage));
		quitButton.setBounds(400, 600, 200, 150);
		quitButton.addActionListener(new quitButtonListener());	
		quitButton.setBorder(BorderFactory.createEmptyBorder());
        quitButton.setOpaque(false);
        quitButton.setVisible(true);
		this.add(quitButton);
			
		
		Thread settingsThread = new Thread(this);
		settingsThread.start();
				
		backgroundLabel.setBounds(0,0,1000,700);
		this.add(backgroundLabel);
		this.setVisible(true);
	}

	public void run() {
		while(true) {
			this.requestFocus();
			keyListener = new ActionKeyListener();		
			
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ActionKey");
			this.getActionMap().put("ActionKey", keyListener );
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
	}
	
	public class ActionKeyListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameWindow.changeToGame();
		}
	}
	
	class resumeButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			playClickSound();
			gameWindow.changeToGame();
	    }
		
	}
	
	class restartButtonListener implements ActionListener {
			
			public void actionPerformed(ActionEvent event) {
				playClickSound();
				//create new gamePanel object
				System.out.println("Restart");
		    }
			
		}
	
	class saveAndQuitButtonListener implements ActionListener {
		
		BufferedWriter bwr;

		public void actionPerformed(ActionEvent event) {
			gameWindow.mp.serialize();
			System.out.println("OBJCETS SHOULD BE SERIALIZED");
			playClickSound();
			System.exit(0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Quit with Save");
	    }
		
	}
	
	class quitButtonListener implements ActionListener {
		BufferedWriter bwr;

		public void actionPerformed(ActionEvent event) {
			playClickSound();
			setIsNotSerialized();
			System.exit(0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Quit");
	    }

		public void setIsNotSerialized() {
			File file = new File("files/checkSaveStand.txt");
  
			try {
				bwr = new BufferedWriter(new FileWriter(file));
				bwr.write("no");
				bwr.close();
				System.out.println("CHECK SAVE STAND SHOULD BE TRUE");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void initFile() {
		try {
			background = ImageIO.read(new File("images/background.png"));
			resumeImage = ImageIO.read(new File("images/resume.png"));
			restartImage = ImageIO.read(new File("images/restart.png"));
			quitSaveImage = ImageIO.read(new File("images/quit_and_save.png"));
			quitImage = ImageIO.read(new File("images/quit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playClickSound() {

		try {
			audioIn = AudioSystem.getAudioInputStream(new File("sounds/click2.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(6.0f);
			clip.start(); 
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}