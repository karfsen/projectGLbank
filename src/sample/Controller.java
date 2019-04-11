package sample;

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
import sample.App.AppController;
import sample.Database.Database;
import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    public Label wrong;
    public Button logBut;
    public ImageView img;
    Employee employee=null;
    public TextField name;
    public PasswordField pw;

    public void login(ActionEvent actionEvent) throws SQLException, IOException {
        employee=Database.checkUser(name.getText(),pw.getText());
        Stage dialogStage=new Stage();
        if(employee==null){
            wrong.setVisible(true);
        }
        else{
            wrong.setVisible(false);
            try {
                Node node = (Node) actionEvent.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("D:\\SOVY\\JAVA\\project_GLbank\\src\\sample\\App\\App.fxml"));
                Parent app = fxml.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(app));
                AppController appController = fxml.getController();
                appController.getEmployee(employee);
                stage1.show();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
