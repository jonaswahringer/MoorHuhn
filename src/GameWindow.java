import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
	JPanel pane;
	GamePanel mp;
	SettingsPanel sp;
	LostPanel lp;
	Login loginData;
	CardLayout cLay;

	public GameWindow(Login login) {
		super("Moorhuhn");
		this.setBounds(400,200,1000,730);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.loginData = login;

		cLay = new CardLayout();
		
		pane = new JPanel();
		pane.setLayout(cLay);		
		
		JButton b = new JButton("Settings");
		b.setBounds(100, 0, 100, 30);
		this.add(b);
		
		mp = new GamePanel(this, loginData);
		sp = new SettingsPanel(this);
		lp = new LostPanel(this);
		
		pane.add(mp, "Game");
		pane.add(sp, "Settings");
		pane.add(lp, "Lost");
		
		cLay.show(pane, "Game");
		
		this.setContentPane(pane);
		// this.setUndecorated(true);
		this.setVisible(true);
	}
	
	public void changeToSettings() {
		cLay.show(pane, "Settings");
	}
	
	public void changeToGame() {
		cLay.show(pane, "Game");
		mp.setFlying();
	}

	public void changeToLost(String user, int score, Boolean isHighScore) {
		lp.setFinalScore(user, score, isHighScore);
		cLay.show(pane, "Lost");
	}

	public void newGame() {
		mp = new GamePanel(this, loginData);
		cLay.show(pane, "Game");
	}

}
