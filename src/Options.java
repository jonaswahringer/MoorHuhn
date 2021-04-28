import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options extends JFrame{

	GameWindow gameWindow;
	int volume;
	JLabel lblVolume = new JLabel("Volume");
	JPanel panel = new JPanel();
	JSlider sliderVolume = new JSlider(0,100,1);
	JLabel lblColor = new JLabel("<html><body><br>Moorhuhn Color</body></html>");
	
	public Options() {
		super("Options");
		this.setBounds(400,200,250,300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        sliderVolume.setPaintLabels(true);
                 
        Hashtable<Integer, Object> position = new Hashtable<>();
        position.put(0, new JLabel("0%"));
        position.put(25, new JLabel("25%"));
        position.put(50, new JLabel("50%"));
        position.put(75, new JLabel("75%"));
        position.put(100, new JLabel("100%"));

        sliderVolume.setLabelTable(position); 
        
        sliderVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
              setVolume(sliderVolume.getValue());
              System.out.println(sliderVolume.getValue());
            }
          });
        
        panel.add(lblVolume);
        panel.add(sliderVolume);
        panel.add(lblColor);
        
        this.add(panel);
        this.setVisible(true);
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int getVolume() {
		return this.volume;
	}
}
