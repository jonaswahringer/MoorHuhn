import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    String url, user, pwd;
    Connection connection;
    Statement stm;
    ResultSet result;

    String[] userNames = new String[10];
    String[] userPasswords = new String[10];
    int[] highscores = new int[10];

    public DatabaseConnection() {
        url = "jdbc:mariadb://localhost:3306/moorhuhn";
        user="root";
        pwd="";
        int i=0;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            connection=DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        
        try {
            stm = connection.createStatement();
            result = stm.executeQuery("select * from user");
            while(result.next()) {
                userNames[i] = result.getString("username");
                userPasswords[i] = result.getString("userpassword");
                highscores[i] = result.getInt("highscore");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }     
        
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getUserNames() {
        return userNames;
    }

    public String[] getUserPasswords() {
        return userPasswords;
    }

    public int[] getHighscores() {
        return highscores;
    }

    public void alterHighscore(String username, int newHSC) {
        try {
            connection=DriverManager.getConnection(url, user, pwd);
            String query = "update user set highscore = ? where username = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, newHSC);
            preparedStmt.setString(2, username);
            preparedStmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }

    public void createNewUser(String username, String password) {
        try {
            connection=DriverManager.getConnection(url, user, pwd);
            String query = "insert into user(username,userpassword) values(?,?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }





}
