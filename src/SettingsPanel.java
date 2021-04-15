import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class SettingsPanel extends JPanel implements Runnable {
	
	GameWindow gameWindow;
	JPanel mainPanel, topPanel;
	Box box;
	BufferedImage background;
	JLayeredPane backgroundPane, itemPane;
	JLabel gameTitle;
	JButton resumeGameButton, settingsButton, aboutButton, saveGameButton, saveAndQuitButton, quitButton;
	File audioFile, bgImageFile;
	AudioInputStream audioIn=null;
	Clip clip=null;
	Action keyListener;
	
	public SettingsPanel(GameWindow gameWindow) {
		this.setBounds(100,100,1000,700);
		this.setBackground(Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initFile();
		
		
		this.gameWindow = gameWindow; 
		
//		topPanel = new JPanel();
//		topPanel.add(new JLabel("aslkdf"));
//		
//		this.add(mainPanel, BorderLayout.NORTH);
		
		mainPanel = new JPanel();
		mainPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		gameTitle = new JLabel("Moorhuhn");
		gameTitle.setBounds(400, 10, 250, 10);
		gameTitle.setBackground(Color.black);
		
		resumeGameButton = new JButton("Resume");
		resumeGameButton.setSize(new Dimension(200, 200));
		resumeGameButton.addActionListener(new ResumeButtonListener());
		
		saveGameButton = new JButton("Save Stand");
		saveGameButton.setSize(new Dimension(200, 200));
		saveGameButton.addActionListener(new saveButtonListener());
		
		saveAndQuitButton = new JButton("Quit with Save");
		saveAndQuitButton.setSize(new Dimension(200, 200));
		saveAndQuitButton.addActionListener(new saveAndQuitButtonListener());
		
		quitButton = new JButton("Quit");
		quitButton.setSize(new Dimension(200, 200));
		quitButton.addActionListener(new quitButtonListener());

		
		box = Box.createVerticalBox();
		box.add(resumeGameButton);
		box.add(Box.createVerticalStrut(70));
		
		box.add(saveGameButton);
		box.add(Box.createVerticalStrut(70));
		
		box.add(saveAndQuitButton);
		box.add(Box.createVerticalStrut(70));
		
		box.add(quitButton);
		box.add(Box.createVerticalStrut(70));
		
		mainPanel.add(box);
		this.add(mainPanel, BorderLayout.SOUTH);
		
		
		Thread threadForFocus = new Thread(this);
		threadForFocus.start();
				
		this.setVisible(true);
	}

	public void run() {
		while(true) {
			this.requestFocus();
			
			keyListener = new ActionKeyListener();		
			
			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ActionKey");
			this.getActionMap().put("ActionKey", keyListener );
		}
	}
	
	public class ActionKeyListener extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameWindow.changeToGame();
		}
	}
	
	class ResumeButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			playClickSound();
			gameWindow.changeToGame();
	    }
		
	}
	
	class saveButtonListener implements ActionListener {
			
			public void actionPerformed(ActionEvent event) {
				playClickSound();
				System.out.println("Save");
		    }
			
		}
	
	class saveAndQuitButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			playClickSound();
			System.exit(0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Quit with Save");
	    }
		
	}
	
	class quitButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			playClickSound();
			System.exit(0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Quit");
	    }
		
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