import java.awt.Color;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MenuFrame extends JFrame {
	BufferedImage background;
	JLayeredPane backgroundPane, itemPane;
	JLabel gameTitle;
	JButton startGameButton, settingsButton, aboutButton;
	File audioFile, bgImageFile;
	AudioInputStream audioIn=null;
	Clip clip=null;
	
	
	public MenuFrame() {
		this.setBounds(300, 200, 350, 400);
		this.setResizable(false);
		
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initFile();
		
		
		gameTitle = new JLabel("Moorhuhn");
//		gameTitle.setBounds(400, 10, 250, 10);
		gameTitle.setBackground(Color.black);
		this.add(gameTitle);
		
//		this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new StartButtonListener());
		this.add(startGameButton);
		
//		this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new SettingsButtonListener());
//		this.add(settingsButton);		
		
		this.setVisible(true);
	}
	
	public void startGame() {
		GameWindow mw = new GameWindow();
		this.dispose();
//		mw.getContentPane().removeAll();
//		mw.add(new MainPanel());
//		mw.revalidate();
//		mw.repaint();
	}
	
	public void initFile() {
		bgImageFile = new File("images/background.png");
		try {
			background = ImageIO.read(bgImageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println("No Settings available yet");
			playClickSound();
			
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
