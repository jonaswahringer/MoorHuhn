public class CheckLoginData {
    String inputName, inputPassword;
    DatabaseConnection dbc;
    String uname;
    int hsc;

    public CheckLoginData(String n, String pw) {
        this.inputName = n;
        this.inputPassword = pw;

        getDataFromDB();
        checkCredentials();

    }

    public void getDataFromDB() {
        dbc = new DatabaseConnection();
    }

    public void checkCredentials() {
        String[] checkNames = dbc.getUserNames();
        String[] checkPW = dbc.getUserPasswords();
        int[] highscores = dbc.getHighscores();

        for(int i=0; i<checkNames.length; i++) {
            if(checkNames[i].equals(inputName) & checkPW[i].equals(inputPassword)) {
                setCheckedHighscore(highscores[i]);
                setCheckedUname(checkNames[i]);
                break;
            }
            else {
                dbc.createNewUser(inputName, inputPassword);
                setCheckedHighscore(0);
                setCheckedUname(inputName);
                break;
            }
        }
        
    }

    public void setCheckedHighscore(int hsc) {
        this.hsc = hsc;
    }

    public int getCheckedHighscore() {
       return hsc;
    }

    public void setCheckedUname(String uname) {
        this.uname = uname;
    }

    public String getCheckedUname() {
       return uname;
    }

}