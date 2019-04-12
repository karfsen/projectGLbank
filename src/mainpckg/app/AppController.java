package mainpckg.app;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mainpckg.Client;
import mainpckg.Employee;
import mainpckg.Globals;
import mainpckg.database.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppController {

    public Label nameLabel;
    public Label eposition;
    public Button logoutB;
    public ComboBox client_dropdown;
    Employee person;

    public void initialize() throws SQLException {
        dropdown();
    }

    public void getEmployee(Employee employee){
        System.out.println(employee);
        nameLabel.setText(employee.fname+" "+employee.lname);
        eposition.setText(employee.position);
        person=employee;
    }

    public void logout(ActionEvent actionEvent) {
        person=null;
        Stage stage = (Stage) logoutB.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../login/sample.fxml"));
            Parent root1;
            root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("GLbanking banker's application");
            stage2.setScene(new Scene(root1));
            stage2.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO : :) :) :) :) :> :> :> :>
    public void dropdown() throws SQLException {
        Database db= Database.getInstance();
        ArrayList<Client> clients=db.getAllClients();
        ObservableList<String> olist=FXCollections.observableArrayList();
        for(int i=0;i<clients.size();i++){
            olist.add(clients.get(i).id+" "+clients.get(i).fname+" "+clients.get(i).lname);
        }
        System.out.println(clients.get(0).id);
        client_dropdown.setItems(olist);
    }
}
