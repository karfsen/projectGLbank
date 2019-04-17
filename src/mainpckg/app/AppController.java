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
import mainpckg.*;
import mainpckg.database.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


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
    public Label newacc;

    //card tab
    public ComboBox cardaccdrop;
    public ComboBox card_drop;
    public Label cardIdLabel;
    public Label pinLabel;
    public Label expireLabel;
    public Label activeLabel;
    public Button newClient;

    //variables and lists storing data
    Employee person=null;
    ArrayList<Client> clients;
    ArrayList<Account> accounts;
    ArrayList<Card> cards;

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
        dropdown();
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

    public int getIDofSelectedCard() {
        int boxindex=card_drop.getSelectionModel().getSelectedIndex();
        return boxindex;
    }

    public String getIDofSelectedAccountInCards() {
        int boxindex=cardaccdrop.getSelectionModel().getSelectedIndex();
        System.out.println(accounts.get(boxindex).id);
        return accounts.get(boxindex).AccNum;
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
        cardaccdrop.setItems(olist);

    }

    public void AccInfo() throws SQLException {
        Database db=Database.getInstance();
        Account account=db.getSelectedAccOfClient(getIDofSelectedAccount());
        accIdLabel.setText(String.valueOf(account.id));
        accNumLabel.setText(account.AccNum);
        accBalLabel.setText(Math.round(account.amount*100)/100+"  €");
    }

    public void createAccount() throws SQLException {
        String AccNum;
        String rand="";
        for(int i=0;i<10;i++){
            Random r=new Random();
            rand=rand+ r.nextInt(10);
        }
        AccNum=rand;
        int idc=getIDofSelectedClient();
        Database db=Database.getInstance();
        String rs=db.insertNewAccount(idc,AccNum);
        System.out.println(idc+" "+AccNum+" "+rs);
        newacc.setText(rs);
        AccDropDown();
    }

    public void CardDropdown() {
        Database db=Database.getInstance();
        cards=db.AllCards(getIDofSelectedAccountInCards());
        ObservableList<String> olist=FXCollections.observableArrayList();
        for(int i=0;i<cards.size();i++){
            olist.add(String.valueOf(cards.get(i).getId()));
        }
        card_drop.setItems(olist);
    }

    public boolean checkPosition(){

        if (person.position.equals("boss")) {
            System.out.println(true);
            return true;
        }
        else{
            System.out.println(false);
            return false;
        }
    }

    public void cardInfo() {
        cardIdLabel.setText(String.valueOf(cards.get(getIDofSelectedCard()).getId()));
//        System.out.println(person.position);
//        System.out.println(cards.get(getIDofSelectedCard()));
        if(checkPosition()==true){
            pinLabel.setText(cards.get(getIDofSelectedCard()).getPIN());
//            System.out.println(cards.get(getIDofSelectedCard()).getPIN());
        }
        else {
            pinLabel.setText("****");
        }
        activeLabel.setText(String.valueOf(cards.get(getIDofSelectedCard()).isActive()));
        expireLabel.setText(cards.get(getIDofSelectedCard()).getExpireDate());
    }

    public void createClient(ActionEvent actionEvent) {
        //TODO greeeeat thinks

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
}
