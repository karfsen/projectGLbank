package mainpckg.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mainpckg.*;
import mainpckg.database.Database;
import mainpckg.newclient.AddClientController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


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
    public CheckBox newCardCheck;
    public Label newCardLabel;
    public TextField changePinInput;
    public Button submitChangePin;

    //transaction tab
    public TextField depositInput;
    public CheckBox depositCheck;
    public TextField withdrawInput;
    public CheckBox withdrawCheck;
    public ComboBox transactionDropdown;
    public Label notCheckedDep;
    public Label notCheckedWithdraw;
    public Label depZero;
    public Label witZero;
    public Label lowmoney;

    //IB tab
    public Label username;
    public CheckBox block;
    public CheckBox resetCheck;
    public Label wrongInput;


    int count=0;
    public String clientLogin;

    //variables and lists storing data
    Employee person = null;
    ArrayList<Client> clients;
    ArrayList<Account> accounts;
    ArrayList<Card> cards;

    //function that initializes method dropdown(renders all clients into combobox )at the start of this window

    public void initialize() throws SQLException {
        dropdown();
    }

    //function that initializes employee from previous window

    public void getEmployee(Employee employee) {
        System.out.println(employee);
        nameLabel.setText(employee.fname + " " + employee.lname);
        eposition.setText(employee.position);
        person = employee;
    }

    //function that provides logging off of employee , getting him to login window of application

    public void logout(ActionEvent actionEvent) {
        person = null;
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
        Database db = Database.getInstance();
        clients = db.getAllClients();
        ObservableList<String> olist = FXCollections.observableArrayList();
        for (int i = 0; i < clients.size(); i++) {
            olist.add(clients.get(i).fname + " " + clients.get(i).lname);
        }
        client_dropdown.setItems(olist);
    }

    //method that gets info about user from database

    public void clientInfo() throws SQLException {
        Database db = Database.getInstance();
        Client selectedUser = db.getClientInfo(getIDofSelectedClient());
        clientNameLabel.setText(selectedUser.fname + " " + selectedUser.lname);
        mailLabel.setText(selectedUser.email);
        idLabel.setText(String.valueOf(selectedUser.id));
        tabpane.setVisible(true);
        AccDropDown();
        dropdown();
        clientLogin=db.selectUserLogin(getIDofSelectedClient());
        username.setText(clientLogin);
        checkIfIBBlocked();
    }

    //method for select id of picked item in dropdown menu

    public int getIDofSelectedClient() {
        System.out.println(client_dropdown.getSelectionModel().getSelectedIndex());
        int boxindex = client_dropdown.getSelectionModel().getSelectedIndex();
        return clients.get(boxindex).id;
    }

    public int getIDofSelectedAccount() {
        //System.out.println(acc_drop.getSelectionModel().getSelectedIndex());
        int boxindex = acc_drop.getSelectionModel().getSelectedIndex();
        return accounts.get(boxindex).id;
    }

    public int getIDofSelectedCard() {
        int boxindex = card_drop.getSelectionModel().getSelectedIndex();
        return boxindex;
    }

    public int getIDofSelectedAccountInCards() {
        int boxindex = cardaccdrop.getSelectionModel().getSelectedIndex();
        System.out.println(accounts.get(boxindex).id);
        return accounts.get(boxindex).id;
    }

    //method for set up dropdown menu of accounts of selected user

    public void AccDropDown() throws SQLException {
        int clientID = getIDofSelectedClient();
        Database db = Database.getInstance();
        accounts = db.getAccsOfClient(clientID);
        ObservableList<String> olist = FXCollections.observableArrayList();
        for (int i = 0; i < accounts.size(); i++) {
            olist.add(accounts.get(i).id + " " + accounts.get(i).AccNum);
        }
        acc_drop.setItems(olist);
        cardaccdrop.setItems(olist);
        transactionDropdown.setItems(olist);

    }

    public void AccInfo() throws SQLException {
        Database db = Database.getInstance();
        Account account = db.getSelectedAccOfClient(getIDofSelectedAccount());
        accIdLabel.setText(String.valueOf(account.id));
        accNumLabel.setText(account.AccNum);
        double d=Math.floor(account.amount*100)/100;
        accBalLabel.setText(d+" €");
    }

    public void createAccount() throws SQLException {
        String AccNum;
        String rand = "";
        for (int i = 0; i < 10; i++) {
            Random r = new Random();
            rand = rand + r.nextInt(10);
        }
        AccNum = rand;
        int idc = getIDofSelectedClient();
        Database db = Database.getInstance();
        String rs = db.insertNewAccount(idc, AccNum);
        System.out.println(idc + " " + AccNum + " " + rs);
        newacc.setText(rs);
        AccDropDown();
    }

    public void CardDropdown() {
        Database db = Database.getInstance();
        cards = db.AllCards(getIDofSelectedAccountInCards());
        ObservableList<String> olist = FXCollections.observableArrayList();
        for (int i = 0; i < cards.size(); i++) {
            olist.add(String.valueOf(cards.get(i).getId()));
        }
        card_drop.setItems(olist);
    }

    public boolean checkPosition() {

        if (person.position.equals("boss")) {
            System.out.println(true);
            return true;
        } else {
            System.out.println(false);
            return false;
        }
    }

    public void cardInfo() {
        cardIdLabel.setText(String.valueOf(cards.get(getIDofSelectedCard()).getId()));
//        System.out.println(person.position);
//        System.out.println(cards.get(getIDofSelectedCard()));
        if (checkPosition() == true) {
            pinLabel.setText(cards.get(getIDofSelectedCard()).getPIN());
//            System.out.println(cards.get(getIDofSelectedCard()).getPIN());
        } else {
            pinLabel.setText("****");
        }
        activeLabel.setText(String.valueOf(cards.get(getIDofSelectedCard()).isActive()));
        expireLabel.setText(cards.get(getIDofSelectedCard()).getExpireDate());
    }

    public void createClient() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../newclient/newClient.fxml"));
            Parent root1;
            root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("GLbanking create new client");
            stage2.setScene(new Scene(root1));
            stage2.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void depositMoney() {
        double d = Math.floor((Double.parseDouble(depositInput.getText())) * 100.0) / 100.0;
        if(d>0) {
            try {
                System.out.println("deposit money input: "+d);
                    if (depositCheck.isSelected()) {
                        String accnum = accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).AccNum;
                        int id = accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).id;
                        Database db = Database.getInstance();
                        db.updateAccMoney(person.id, id, accnum, d);
                        notCheckedDep.setVisible(false);
                        depZero.setVisible(false);
                        AccInfo();
                    } else {
                        notCheckedDep.setVisible(true);
                    }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Deposit error occured");
            }
        }
        else{
            depZero.setVisible(true);
        }
    }

    public void withdrawMoney() {
        double d = Math.floor(-1 * (Double.parseDouble(withdrawInput.getText())) * 100.0) / 100.0;
        if (accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).amount-d > -60.0) {
            System.out.println("Withdraw money input: "+d);
            try {
                if (withdrawCheck.isSelected() == true) {
                    if (d > 0) {
                        d = -1*d;
                    }
                    System.out.println("Withdrawing "+d+" € from "+accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).amount+" from selected account");
                    String accnum = accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).AccNum;
                    int id = accounts.get(transactionDropdown.getSelectionModel().getSelectedIndex()).id;
                    System.out.println(accnum+" "+id);
                    Database db = Database.getInstance();
                    db.updateAccMoney(person.id, id, accnum, d);
                    notCheckedWithdraw.setVisible(false);
                    witZero.setVisible(false);
                    AccDropDown();
                    } else {
                        notCheckedWithdraw.setVisible(true);
                    }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Withdraw error occured");
            }
            lowmoney.setVisible(false);
        }
        else{
            System.out.println("Cannot go to more debt than -60");
            lowmoney.setVisible(true);
        }
    }

    public void newCard() {
        int accid=getIDofSelectedAccountInCards();
        System.out.println(accid);
        String PIN = "";
        int month= new Date().getMonth()+1;
        System.out.println(month);
        Calendar now=Calendar.getInstance();
        int year=now.get(Calendar.YEAR)+3-2000;
        System.out.println(year);
        for (int i = 0; i < 4; i++) {
            Random r = new Random();
            PIN = PIN + r.nextInt(10);
        }
        System.out.println(PIN);

        if(newCardCheck.isSelected()){
            Database db=Database.getInstance();
            db.newCard(accid,PIN,month,year);
            newCardLabel.setVisible(false);
            CardDropdown();
        }
        else{
            newCardLabel.setVisible(true);
        }
    }

    public void resetPW() {
        count++;
        if(count==1){
            resetCheck.setVisible(true);
        }
        if(resetCheck.isSelected()){
            count=0;
            AddClientController newpw = new AddClientController();
            String newpass=newpw.generatePassword();
            Database db=Database.getInstance();
            db.resetPW(username.getText(),newpass);
            resetCheck.setVisible(false);
        }
    }

    public void blockIB(){
        if(block.isSelected()){
            Database db= Database.getInstance();
            db.blockIB(getIDofSelectedClient());
            System.out.println("Blocked ACC");
        }
        else{
            System.out.println("unblocked acc");
            Database db=Database.getInstance();
            db.unblockIB(getIDofSelectedClient());
        }
    }

    public void checkIfIBBlocked(){
        Database db=Database.getInstance();
        ArrayList<LoginHistory> loghis=db.last3Logins(getIDofSelectedClient());
        System.out.println(loghis.get(0).isSuccess());
        if(loghis.get(0).isSuccess()=="1"){
            block.setSelected(false);
        }

        else if(loghis.get(0).isSuccess()==null){
            block.setSelected(true);
        }
        else if(loghis.get(0).isSuccess()=="0"){
            if(loghis.get(1).isSuccess()=="0"){
                if(loghis.get(2).isSuccess()=="0"){
                    block.setSelected(true);
                }
                else{
                    block.setSelected(false);
                }
            }
            else{
                block.setSelected(false);
            }
        }
        else{
            block.setSelected(false);
        }
    }

    public void changePin() {
        submitChangePin.setVisible(true);
        changePinInput.setVisible(true);
    }

    public void submitChangePin() {
        String PIN = changePinInput.getText();
        int idcard=cards.get(getIDofSelectedCard()).getId();

            if (PIN.matches("[0-9]+") && PIN.length() == 4){
                System.out.println(PIN);
                wrongInput.setVisible(false);
                Database db=Database.getInstance();
                db.changePIN(PIN,idcard);
            }
            else {
                wrongInput.setVisible(true);
            }
    }
}
