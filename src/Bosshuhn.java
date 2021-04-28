import java.io.Serializable;

import javax.swing.ImageIcon;

public class Bosshuhn extends Moorhuhn{
	
	{
		super.setImg(new ImageIcon("images/moorhuhn.gif").getImage());
	}
	
	public Bosshuhn(int x, int y, int speed) {
		super(x,y,speed);
	}

}
