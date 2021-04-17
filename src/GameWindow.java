import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

	JPanel pane;
	GamePanel mp;
	SettingsPanel sp;
	LostPanel lp;
	CardLayout cLay;
	int myScore;

	public GameWindow() {
		super("Moorhuhn");
		this.setBounds(100,100,1000,730);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		cLay = new CardLayout();
		
		pane = new JPanel();
		pane.setLayout(cLay);		
		
		JButton b = new JButton("Settings");
		b.setBounds(100, 0, 100, 30);
		b.setMinimumSize(new Dimension(30, 30));
		this.add(b);
		
		mp = new GamePanel(this);
		sp = new SettingsPanel(this);
		lp = new LostPanel(this, myScore);
		
		
		pane.add(mp, "Game");
		pane.add(sp, "Settings");
		pane.add(lp, "Lost");
		
		
		cLay.show(pane, "Game");
		
		this.setContentPane(pane);
		
		this.setVisible(true);
	}
	
	public void changeToSettings() {
		cLay.show(pane, "Settings");
	}
	
	public void changeToGame() {
		cLay.show(pane, "Game");
		mp.setFlying();
	}

	public void changeToLost(int score) {
		System.out.println("LOST PANEL");
		this.myScore = score;
		cLay.show(pane, "Lost");
	}

}
