import java.io.Serializable;

import javax.swing.ImageIcon;

public class Bosshuhn extends Moorhuhn{
	
	public Bosshuhn(int x, int y, int speed) {
		super(x,y,speed);
		super.setImg(new ImageIcon("images/bosshuhn.gif").getImage());
	}

}
