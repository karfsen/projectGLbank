package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Database.Database;

import java.sql.SQLException;

public class Controller {
    public Label wrong;
    Employee employee=null;
    public TextField name;
    public PasswordField pw;

    public void login(ActionEvent actionEvent) throws SQLException {
        employee=Database.checkUser(name.getText(),pw.getText());
        if(employee==null){
            wrong.setVisible(true);
        }
        else{
            wrong.setVisible(false);

        }
    }
}
