import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnection {
    String url, user, pwd;
    Connection connection;
    Statement stm;
    ResultSet result;

    String[] userNames = new String[10];
    String[] userPasswords = new String[10];
    int[] highscores = new int[10];

    Boolean isSaved;
    int score, livesAvailable, currentAmmo, difficultyLevel;

    private static final String SQL_SERIALIZE_HASMAP = "INSERT INTO serialized(username, check_savestand, lives_available, score, current_ammo, lvl) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DESERIALIZE_HASHMAP = "SELECT * FROM serialized WHERE username=?";

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
        
        //get all users with every attribute
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

    public void alterSerializedUser(String username, HashMap<Integer, Object> objectToSerialize) {
        for(int h=1; h<=6; h++) {
            switch(h) {
                case 1: isSaved=(Boolean) objectToSerialize.get(1);
                break;
                case 2: livesAvailable=(int) objectToSerialize.get(2);
                break;
                case 3: score=(int) objectToSerialize.get(3);
                break;
                case 4: currentAmmo=(int) objectToSerialize.get(4);
                break;
                case 5: difficultyLevel = (int) objectToSerialize.get(5);
                break;
            }
        }
        try {
            connection=DriverManager.getConnection(url, user, pwd);
            String query = "UPDATE serialized SET check_savestand=?, lives_available=?, score=?, current_ammo=?, lvl=? WHERE username=?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setBoolean(1, isSaved);
            preparedStmt.setInt(2, livesAvailable);
            preparedStmt.setInt(3, score);
            preparedStmt.setInt(4, currentAmmo);
            preparedStmt.setInt(5, difficultyLevel);
            preparedStmt.setString(6, username);
            preparedStmt.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }

    public void createSerializedUser(String username, HashMap<Integer, Object> objectToSerialize) {
        for(int h=1; h<=6; h++) {
            switch(h) {
                case 1: isSaved=(Boolean) objectToSerialize.get(1);
                break;
                case 2: livesAvailable=(int) objectToSerialize.get(2);
                break;
                case 3: score=(int) objectToSerialize.get(3);
                break;
                case 4: currentAmmo=(int) objectToSerialize.get(4);
                break;
                case 5: difficultyLevel = (int) objectToSerialize.get(5);
                break;
            }
        }

        try {
            connection=DriverManager.getConnection(url, user, pwd);
            PreparedStatement pstmt = connection.prepareStatement(SQL_SERIALIZE_HASMAP);
            pstmt.setString(1, username);
            pstmt.setBoolean(2, isSaved);
            pstmt.setInt(3, livesAvailable);
            pstmt.setInt(4, score);
            pstmt.setInt(5, currentAmmo);
            pstmt.setInt(6, difficultyLevel);
            
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Java object serialized to database. Object: "+ objectToSerialize);
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    } 
    
    public void writeHashMap(String username, HashMap<Integer, Object> objectToSerialize) {
        if(checkIfUserSerialized(username)) {
            // System.out.println("ASKLDFALSKDFJASLFKDJ");
            alterSerializedUser(username, objectToSerialize);
        }
        else {
            System.out.println("ASKLDFALSKDFJASLFKDJ");
            createSerializedUser(username, objectToSerialize);
        }
    }

    public HashMap<Integer, Object> readHashMap(String username) {
        try {
            connection=DriverManager.getConnection(url, user, pwd);
            PreparedStatement pstmt = connection.prepareStatement(SQL_DESERIALIZE_HASHMAP);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            result = pstmt.executeQuery();

            if(result.getRow() == 1) {
                System.out.println("result: " + result.getBoolean("check_savestand"));
                isSaved = result.getBoolean("check_savestand");
                livesAvailable = result.getInt("lives_available");
                score = result.getInt("score");
                currentAmmo = result.getInt("current_ammo");
                difficultyLevel = result.getInt("lvl");
            }

            else {
                result.next();
                System.out.println("ROW" + result.getRow());
                System.out.println("result: " + result.getBoolean("check_savestand"));
                isSaved = result.getBoolean("check_savestand");
                livesAvailable = result.getInt("lives_available");
                score = result.getInt("score");
                currentAmmo = result.getInt("current_ammo");
                difficultyLevel = result.getInt("lvl");
            }
            
            result.close();
            pstmt.close();

        } catch (SQLException e) { 
            e.printStackTrace();
        }

        HashMap<Integer, Object> deSerializedMap = new HashMap<>();

        deSerializedMap.put(1, isSaved);
        deSerializedMap.put(2, livesAvailable);
        deSerializedMap.put(3, score);
        deSerializedMap.put(4, currentAmmo);
        deSerializedMap.put(5, difficultyLevel);
        
        return deSerializedMap;
    }

    public Boolean checkIfUserSerialized(String username) {
        ArrayList<String> serializedUnames = new ArrayList<>();
        Boolean checkFlag=false;
        int i=0;

        try {
            connection=DriverManager.getConnection(url, user, pwd);
            stm = connection.createStatement();
            result = stm.executeQuery("select * from serialized");

            while(result.next()) {
                serializedUnames.add(i, result.getString("username"));
                System.out.println(serializedUnames.get(i));
                i++;
        }
        } catch(SQLException e) {
            System.out.println(e);
        }
        
        for(String uname : serializedUnames) {
            if(uname.equals(username)) {
                checkFlag=true;
                break;
            }
        }

        return checkFlag;
    }
}
