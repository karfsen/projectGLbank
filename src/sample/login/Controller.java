package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.app.AppController;
import sample.database.Database;
import sample.Employee;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    public Label wrong;
    public Button logBut;
    public ImageView img;
    Employee employee=null;
    public TextField name;
    public PasswordField pw;

    public void login(ActionEvent actionEvent) throws SQLException{
        employee=Database.checkUser(name.getText(),pw.getText());
        if(employee==null){
            wrong.setVisible(true);
        }
        else{
            wrong.setVisible(false);
            Stage stage = (Stage) logBut.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../app/app.fxml"));
                Parent root1;
                root1 = (Parent) fxmlLoader.load();
                Stage stage2 = new Stage();
                stage2.setTitle("BANKERS APP");
                stage2.setScene(new Scene(root1));

                AppController acc;
                acc = fxmlLoader.getController();
                acc.getEmployee(employee);

                stage2.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
