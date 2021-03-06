package mainpckg.newclient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainpckg.database.Database;


public class AddClientController {
    public TextField fname;

    public Label genPw;
    public Label genLog;
    public TextField lname;
    public TextField email;
    public Button createClientbutton;
    public Label filledfield;
    String username;
    String password;

    public void initialize(){
        username=generateLogin();
        password=generatePassword();
        genLog.setText(username);
        genPw.setText(password);
    }


    public String generateLogin(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
     }

    public String generatePassword(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 15; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public void generateNew(){
        username=generateLogin();
        password=generatePassword();
        genLog.setText(username);
        genPw.setText(password);
    }

    public void newClient() {
        Database db= Database.getInstance();
        if(fname.getText()!=""&&lname.getText()!=""&&email.getText()!="") {
            try {
                db.newClient(fname.getText(), lname.getText(), email.getText(), username, password);
                Stage stage = (Stage) createClientbutton.getScene().getWindow();
                filledfield.setVisible(false);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            filledfield.setVisible(true);
        }
    }
}
