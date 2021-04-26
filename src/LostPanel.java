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
    JLabel backgroundLabel, scoreLabel;
    JButton restartGameButton;
    BufferedImage background, restartImage;
    String username;
    int score;

    
    public LostPanel(GameWindow gameWindow) {
        this.setBounds(0, 100, 1000, 700);
        this.setLayout(null);
        initFile();

        this.gameWindow = gameWindow;

		backgroundLabel = new JLabel("",new ImageIcon(background),JLabel.CENTER);
        
		this.addMouseListener(this);
        
        restartGameButton = new JButton(new ImageIcon(restartImage));
        restartGameButton.setBounds(400, 300, 200, 150);
		restartGameButton.addActionListener(new restartButtonListener());
        restartGameButton.setBorder(BorderFactory.createEmptyBorder());
        restartGameButton.setOpaque(false);
        restartGameButton.setVisible(true);
		this.add(restartGameButton);

        JButton label = new JButton();
        label.setBounds(530, 300, 200, 150);
		label.addActionListener(new restartButtonListener());
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setOpaque(false);
        label.setVisible(true);
		this.add(label);

        scoreLabel = new JLabel();
        scoreLabel.setBounds(400, 350, 200, 200);
        Font font = new Font("Courier", Font.BOLD,16);
        scoreLabel.setFont(font);
        this.add(scoreLabel);

        backgroundLabel.setBounds(0,0,1000,700);
        this.add(backgroundLabel);
        this.setVisible(true);
    }

    public void initFile() {
		try {
			background = ImageIO.read(new File("images/lost_screen.png"));
            restartImage = ImageIO.read(new File("images/restart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.setColor(Color.black);
        g.drawString("You Lost", 0, 0);
    }

    public void setFinalScore(String user, int myscore, Boolean isHighScore) {
        this.score=myscore;
        this.username=user; 

        if(isHighScore) {
            scoreLabel.setText("New Highscore: " + Integer.toString(score));
            alterUserHSC();
        }
        else {
            scoreLabel.setText("Your score: " + Integer.toString(score));
        }        
    }

    public void alterUserHSC() {
        DatabaseConnection alter_dbc = new DatabaseConnection();
        alter_dbc.alterHighscore(username, score);
    }

    public class restartButtonListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
            gameWindow.newGame();
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
