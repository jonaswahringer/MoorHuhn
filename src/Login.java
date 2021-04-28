import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {
    GameWindow gameWindow;
    CheckLoginData cld;
    JLabel userLabel, passwordLabel, message;
    JTextField userNameText, passwordText;
    JButton submitButton;
    JPanel panel;
    String inputName, inputPassword;
    int checkedHighscore;
    String checkedUname;

    public Login() {
        super("Login System");
        this.setBounds(400,200,250,150);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initFile();

        //user label
        userLabel = new JLabel();
        userLabel.setText("Name :");
        userNameText = new JTextField();
        userNameText.setText("Please enter your name");

        // Password Label
        passwordLabel = new JLabel();
        passwordLabel.setText("Password :");
        passwordText = new JPasswordField();
        passwordText.setText("123456");
        // Submit
        submitButton = new JButton("SUBMIT");
        submitButton.addActionListener(new submitButtonListener());
        
        panel = new JPanel();
        panel.add(userLabel);
        panel.add(userNameText);
        panel.add(passwordLabel);
        panel.add(passwordText);

        message = new JLabel();
        panel.add(message);
        panel.add(submitButton);
        
        this.add(panel);

        this.setVisible(true);
    }

    public class submitButtonListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            getCredentials();
            cld = new CheckLoginData(inputName, inputPassword);
            checkedHighscore = cld.getCheckedHighscore();     
            checkedUname = cld.getCheckedUname();
            close();
        }

	}
    
    public void close() {
        this.dispose();
    }

    public void initFile() {

    }

    public String getFinalUsername() {
        return checkedUname;
    }

    public int getFinalHighscore() {
        return checkedHighscore;
    }



    public void getCredentials() {
        this.inputName = userNameText.getText();
        this.inputPassword = passwordText.getText();
    }
}
