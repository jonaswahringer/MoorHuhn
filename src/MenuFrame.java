import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuFrame extends JFrame {
	Login loginData;
	Options optionsPanel;
	BufferedImage backgroundImage, startImage, settingsImage, loginImage;
	JLabel title, background;
	JButton startGameButton, settingsButton, loginButton;
	File audioFile, bgImageFile, startImageFile, settingsImageFile, loginImageFile;
	AudioInputStream audioIn=null;
	Clip clip=null;
	Menu menu;
	
	public MenuFrame() {
		this.setBounds(100,100,1000,730);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initFile();

		JPanel p1 = new JPanel();
		p1.setLayout(null);

		JLabel background=new JLabel("",new ImageIcon(backgroundImage),JLabel.CENTER);
		
		JLabel title = new JLabel("Moorhuhn");
		p1.add(title);

		//Buttons
		// startGameButton = new JButton(new ImageIcon("heart_icon.png"));
		
		startGameButton = new JButton(new ImageIcon(startImage));
		startGameButton.setContentAreaFilled(false);
		startGameButton.setBounds(400,250,200,150);
		startGameButton.setBorder(BorderFactory.createEmptyBorder());
		startGameButton.setOpaque(false);
		startGameButton.addActionListener(new StartButtonListener());
		startGameButton.setVisible(true);
		
		// settingsButton = new JButton(new ImageIcon("heart_icon.png"));
		settingsButton = new JButton(new ImageIcon(settingsImage));
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorder(BorderFactory.createEmptyBorder());
		settingsButton.setBounds(415,400,75,82);
		settingsButton.setOpaque(false);	
		settingsButton.addActionListener(new SettingsButtonListener());
		settingsButton.setVisible(true);

		loginButton = new JButton(new ImageIcon(loginImage));
		loginButton.setContentAreaFilled(false);
		loginButton.setBorder(BorderFactory.createEmptyBorder());
		loginButton.setBounds(520,400,75,82);
		loginButton.setOpaque(false);	
		loginButton.addActionListener(new LoginButtonListener());
		loginButton.setVisible(true);
		

		p1.add(startGameButton);
		p1.add(settingsButton);
		p1.add(loginButton);
		
		background.setBounds(0,0,1000,700);
		p1.add(background);

		p1.setVisible(true);
		// Menu.setVisible(true);
		this.add(p1);
		this.setVisible(true);
	}
	
	public void initFile() {
		bgImageFile = new File("images/mh.png");
		startImageFile = new File("images/start.png");
		settingsImageFile = new File("images/settings.png");
		loginImageFile = new File("images/login.png");

		try {
			backgroundImage = ImageIO.read(bgImageFile);
			startImage = ImageIO.read(startImageFile);
			settingsImage = ImageIO.read(settingsImageFile);
			loginImage = ImageIO.read(loginImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startGame() {
		new GameWindow(loginData);
		this.dispose();
	}

	class StartButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			playClickSound();
			startGame();			
	    }
		
	}
	
	class SettingsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			optionsPanel = new Options();
			playClickSound();
		}		
	}

	class LoginButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			playClickSound();
			loginData = new Login();		
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
