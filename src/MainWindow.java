import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{

	public MainWindow() {
		super("Moorhuhn");
		this.setBounds(100,100,1000,700);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.add(new MenuPanel());
//		this.add(new MainPanel());
		
		this.setVisible(true);
	}
}
