import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class MenuPanel extends JLayeredPane {
	
	BufferedImage background;
	JLayeredPane backgroundPane, itemPane;
	JLabel gameTitle;
	JButton startGameButton, settingsButton, aboutButton;
	File audioFile, bgImageFile;
	AudioInputStream audioIn=null;
	Clip clip=null;

	
	public MenuPanel() {
		this.setBounds(100,100,1000,700);
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initFile();
		
		JLabel thumb = new JLabel();
		thumb.setIcon(new ImageIcon(background));		
		this.add(thumb, JLayeredPane.DEFAULT_LAYER);
		
		gameTitle = new JLabel("Moorhuhn");
//		gameTitle.setBounds(400, 10, 250, 10);
		gameTitle.setBackground(Color.black);
		gameTitle.setAlignmentX(CENTER_ALIGNMENT);
		this.add(gameTitle, JLayeredPane.PALETTE_LAYER);
		
		this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new StartButtonListener());
		startGameButton.setAlignmentX(CENTER_ALIGNMENT);
		this.add(startGameButton, JLayeredPane.PALETTE_LAYER);
		
		this.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new SettingsButtonListener());
		settingsButton.setAlignmentX(CENTER_ALIGNMENT);
		this.add(settingsButton, JLayeredPane.PALETTE_LAYER);		
		
		this.setVisible(true);
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
	
	
	public void startGame() {
		MainWindow mw = new MainWindow();
		mw.getContentPane().removeAll();
		mw.add(new MainPanel());
		mw.revalidate();
		mw.repaint();
	}

	class StartButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			startGame();
			playClickSound();
			
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
			clip.start(); 
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}





//Frame: Settings, StartButton, etc.
//Start Button pressed -> invoke mainPanel
//main panel esc -> invoke  menuPanel 


//evtl FX: click, deadhuhn...