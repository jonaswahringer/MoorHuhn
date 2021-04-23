import java.awt.CardLayout;
<<<<<<< HEAD


=======
import java.awt.Dimension;

>>>>>>> a01ff8a21d778a96f3b7518d3fa00a7a95b9936b
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
		this.setBounds(400,200,1000,730);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		cLay = new CardLayout();
		
		pane = new JPanel();
		pane.setLayout(cLay);		
		
		JButton b = new JButton("Settings");
		b.setBounds(100, 0, 100, 30);
		this.add(b);
		
		mp = new GamePanel(this);
		sp = new SettingsPanel(this);
		lp = new LostPanel(this, myScore);
		
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

	public void changeToLost(int score) {
		System.out.println("LOST PANEL");
		this.myScore = score;
		cLay.show(pane, "Lost");
	}

}
