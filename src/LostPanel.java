import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LostPanel extends JPanel implements MouseListener {
    GameWindow gameWindow;
    JLabel backgroundLabel;
    BufferedImage background, restartImage;
    int score;
    JButton restartGameButton;
    
    public LostPanel(GameWindow gameWindow, int score) {
        this.setBounds(0, 100, 1000, 700);
        this.setLayout(null);
        initFile();

        this.gameWindow = gameWindow;
        this.score = score;

	    

		backgroundLabel = new JLabel("",new ImageIcon(background),JLabel.CENTER);
        
		this.addMouseListener(this);
        
        restartGameButton = new JButton(new ImageIcon(restartImage));
        restartGameButton.setBounds(400, 300, 200, 150);
		restartGameButton.addActionListener(new restartButtonListener());
        restartGameButton.setBorder(BorderFactory.createEmptyBorder());
        restartGameButton.setOpaque(false);
        restartGameButton.setVisible(true);
		this.add(restartGameButton);

        JButton label = new JButton(Integer.toString(score));
        label.setBounds(530, 300, 200, 150);
		label.addActionListener(new restartButtonListener());
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setOpaque(false);
        label.setVisible(true);
		this.add(label);

        JLabel yourScore = new JLabel(Integer.toString(score));
        this.add(yourScore);

        backgroundLabel.setBounds(0,0,1000,700);
        this.add(backgroundLabel);
        this.setVisible(true);
    }

    public void initFile() {
		try {
			background = ImageIO.read(new File("images/lost_screen.png"));
            restartImage = ImageIO.read(new File("images/start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.setColor(Color.black);
        g.drawString("You Lost", 0, 0);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString("Score: " + Integer.toString(score), 0, 0);
    }

    public class restartButtonListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
            System.out.println("Restart Game");
            //create new gamePanel object
		}

	}

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {        
    }

    @Override
    public void mouseReleased(MouseEvent e) {        
    }

    @Override
    public void mouseEntered(MouseEvent e) {   
    }

    @Override
    public void mouseExited(MouseEvent e) {        
    }   

}
