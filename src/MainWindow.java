import javax.swing.JFrame;

public class MainWindow extends JFrame{

	public MainWindow() {
		super("Moorhuhn");
		this.setBounds(100,100,1000,700);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.add(new MainPanel());
		this.add(new MenuPanel());
		
		this.setVisible(true);
	}
}
