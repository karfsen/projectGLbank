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
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import mainpckg.Account;
import mainpckg.Client;
import mainpckg.Employee;
import mainpckg.Globals;
import mainpckg.database.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppController {

    //Client info tab
    public Label nameLabel;
    public Label eposition;
    public Button logoutB;
    public ComboBox client_dropdown;
    public Label clientNameLabel;
    public Label mailLabel;
    public Label idLabel;

    //account tab
    public ComboBox acc_drop;
    public Label accBalLabel;
    public Label accNumLabel;
    public Label accIdLabel;
    public TabPane tabpane;

    //variables and lists storing data
    Employee person;
    ArrayList<Client> clients;
    ArrayList<Account> accounts;

    //function that initializes method dropdown(renders all clients into combobox )at the start of this window

    public void initialize() throws SQLException {
        dropdown();
    }

    //function that initializes employee from previous window

    public void getEmployee(Employee employee){
        System.out.println(employee);
        nameLabel.setText(employee.fname+" "+employee.lname);
        eposition.setText(employee.position);
        person=employee;
    }

    //function that provides logging off of employee , getting him to login window of application

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

    //Renders all clients into dropdown menu

    public void dropdown() throws SQLException {
        Database db= Database.getInstance();
        clients=db.getAllClients();
        ObservableList<String> olist=FXCollections.observableArrayList();
        for(int i=0;i<clients.size();i++){
            olist.add(clients.get(i).fname+" "+clients.get(i).lname);
        }
        client_dropdown.setItems(olist);
    }

    //method that gets info about user from database

    public void clientInfo() throws SQLException {
        Database db=Database.getInstance();
        Client selectedUser=db.getClientInfo(getIDofSelectedClient());
        clientNameLabel.setText(selectedUser.fname+" "+selectedUser.lname);
        mailLabel.setText(selectedUser.email);
        idLabel.setText(String.valueOf(selectedUser.id));
        tabpane.setVisible(true);
        AccDropDown();
    }

    //method for select id of picked item in dropdown menu

    public int getIDofSelectedClient() {
        System.out.println(client_dropdown.getSelectionModel().getSelectedIndex());
        int boxindex=client_dropdown.getSelectionModel().getSelectedIndex();
        return clients.get(boxindex).id;
    }

    public int getIDofSelectedAccount() {
        System.out.println(acc_drop.getSelectionModel().getSelectedIndex());
        int boxindex=acc_drop.getSelectionModel().getSelectedIndex();
        return accounts.get(boxindex).id;
    }

    //method for set up dropdown menu of accounts of selected user

    public void AccDropDown() throws SQLException {
        int clientID=getIDofSelectedClient();
        Database db=Database.getInstance();
        accounts=db.getAccsOfClient(clientID);
        ObservableList<String> olist=FXCollections.observableArrayList();
        for(int i=0;i<accounts.size();i++){
            olist.add(accounts.get(i).id+" "+accounts.get(i).AccNum);
        }
        acc_drop.setItems(olist);
    }

    public void AccInfo() throws SQLException {
        Database db=Database.getInstance();
        Account account=db.getSelectedAccOfClient(getIDofSelectedAccount());
        accIdLabel.setText(String.valueOf(account.id));
        accNumLabel.setText(account.AccNum);
        accBalLabel.setText(account.amount+" €");
    }


}
