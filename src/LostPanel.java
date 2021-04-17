import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;

public class LostPanel extends JPanel implements MouseListener {
    GameWindow gameWindow;
    private BufferedImage background;
    int score;
    
    
    public LostPanel(GameWindow gameWindow, int score) {
        this.setBounds(0, 100, 1000, 700);
        
        this.gameWindow = gameWindow;
        this.score = score;

		this.addMouseListener(this);
        initFile();
        
        this.repaint();

        this.setVisible(true);
    }

    public void initFile() {
		File background_file = new File("images/background.png");
		try {
			background = ImageIO.read(background_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.setColor(Color.black);
        g.drawString("You Lost", 0, 0);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Score: " + Integer.toString(score), 0, 0);
    }

    public class ActionKeyListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("ESC");
			gameWindow.changeToSettings();

		}

	}

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    

}
