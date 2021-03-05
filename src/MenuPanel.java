import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	
	JLabel gameTitle;
	JButton settingsButton, aboutButton;
	
	public MenuPanel() {
		this.setBounds(0, 0, 1000, 30);
		this.setBackground(Color.white);
		
		gameTitle = new JLabel("Moorhuhn");
		gameTitle.setBounds(400, 10, 250, 10);
		gameTitle.setBackground(Color.black);
		this.add(gameTitle);
		
		this.setVisible(true);
	}

}
