package mainpckg.database;

import mainpckg.*;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    public static final String checkUser="SELECT employee.id as id,employee.fname as fname,employee.lname as lname,positions.name as posname from loginemp INNER JOIN employee on loginemp.ide=employee.id INNER JOIN positions on employee.position=positions.id where login like ? and password like ?";
    public static final String getClients="SELECT id,fname,lname from client";
    public static final String getClientInfo="Select id,fname,lname,email from client where id=?";
    public static final String getUserAccounts="SELECT * from account where idc=?";
    public static final String getSelectedAccount="SELECT * from account where id=?";
    public static final String checkAccNum="SELECT * from account where accnum=?";
    public static final String insertAcc="INSERT into account VALUES(id,?,?,amount)";
    public static final String getAccCards="SELECT * from card WHERE ida like ?";
    public static final String updateMoney="UPDATE account set amount=amount+? where accnum like ?";
    public static final String insertintotrans="INSERT INTO transaction(idacc,recaccount,idemployee,transamount) values(?,?,?,?)";
    public static final String newClient="INSERT INTO client values(id,?,?,?)";
    public static final String newLogin="INSERT INTO loginclient values(id,?,?,MD5(?))";
    public static final String resetPassword="UPDATE loginclient SET password=MD5(?) WHERE login=?";
    public static final String selectuser="SELECT login from loginclient where idc=?";
    public static final String blockIB="INSERT INTO loginhistory(idl,success) VALUES((select id from loginclient where idc=?),NULL)";
    public static final String unblockIB="INSERT INTO loginhistory(idl,success) VALUES((select id from loginclient where idc=?),1)";
    public static final String get3Logins="SELECT * FROM loginhistory WHERE idl=(select id from loginclient where idc=?) ORDER BY logdate desc LIMIT 3";
    public static final String newCard="INSERT INTO card(ida,pin,active,expirem,expirey) VALUES(?,?,true,?,?)";
    public static final String newPIN="UPDATE card SET pin=? WHERE id=?";


    private static Database db = new Database();

    private Database(){

    }

    public static Database getInstance(){
        return db;
    }

    public static Employee checkUser(String username, String password) throws SQLException {
        int id = 0;
        String fname="";
        String lname="";
        String position="";
        
        Connection con= Globals.getConnection();
        PreparedStatement sqlPreparedStatement = null;

        sqlPreparedStatement = con.prepareStatement(checkUser);
        sqlPreparedStatement.setString(1,username);
        sqlPreparedStatement.setString(2,password);
        //System.out.println(sqlPreparedStatement);
        ResultSet rs = sqlPreparedStatement.executeQuery();
        while (rs.next()) {
            id=rs.getInt("id");
            fname = rs.getString("fname");
            lname = rs.getString("lname");
            position = rs.getString("posname");
            System.out.println(id+" "+fname+" "+position);
        }

        if(fname!=""&&id!=0&&lname!=""&&position!="") {

            System.out.println(id + " " + fname + " " + lname + " " + position);
            return new Employee(id, fname, lname, position);
        }
        else{
            return null;
        }
    }

    public Client getClientInfo(int id) throws SQLException {
        String fname="";
        String lname="";
        String email="";

        Connection con=Globals.getConnection();
        PreparedStatement sql=null;
        sql = con.prepareStatement(getClientInfo);
        sql.setString(1, String.valueOf(id));

        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            fname = rs.getString("fname");
            lname = rs.getString("lname");
            email = rs.getString("email");
        }
        if(fname!=""&&id!=0&&lname!=""&&email!="") {

            System.out.println(id + " " + fname + " " + lname + " " + email);
            return new Client(id, fname, lname, email);
        }
        else{
            return null;
        }
    }

    public ArrayList<Account> getAccsOfClient(int id) throws SQLException {
        int ida=0;
        int idc=0;
        String AccNum="";
        double amount=0.0;

        ArrayList<Account> accs=new ArrayList<>();
        Connection con=Globals.getConnection();
        PreparedStatement sql=null;
        try {
            sql = con.prepareStatement(getUserAccounts);
            sql.setInt(1, id);

            ResultSet rs = sql.executeQuery();

            while (rs.next()) {
                ida = rs.getInt("id");
                idc = rs.getInt("idc");
                AccNum = rs.getString("accnum");
                amount = rs.getDouble("amount");
                Account acc = new Account(ida, idc, AccNum, amount);
                accs.add(acc);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return accs;
    }

    public Account getSelectedAccOfClient(int id) throws SQLException {
        int ida=0;
        int idc=0;
        String AccNum="";
        double amount=0.0;
        Account acc=null;
        Connection con=Globals.getConnection();
        PreparedStatement sql=null;
        try {
            sql = con.prepareStatement(getSelectedAccount);
            sql.setInt(1, id);

            ResultSet rs = sql.executeQuery();

            while (rs.next()) {
                ida = rs.getInt("id");
                idc = rs.getInt("idc");
                AccNum = rs.getString("accnum");
                amount = rs.getDouble("amount");
                acc= new Account(ida, idc, AccNum, amount);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return acc;
    }

    public ArrayList<Client> getAllClients() throws SQLException {
        ArrayList<Client> clients=new ArrayList<>();
        Connection con= Globals.getConnection();
        PreparedStatement sqlPreparedStatement;

        try {
            sqlPreparedStatement = con.prepareStatement(getClients);
            ResultSet rs = sqlPreparedStatement.executeQuery();
            while (rs.next()) {
                int id=rs.getInt("id");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                Client client=new Client(id,fname,lname);
                clients.add(client);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }


    public String insertNewAccount(int idc,String AccNum){
        String result = null;
        if(AccNum.length()==10){
            Connection con= Globals.getConnection();
            PreparedStatement sqlPreparedStatement;
            try {
                sqlPreparedStatement = con.prepareStatement(checkAccNum);
                sqlPreparedStatement.setString(1,AccNum);
                ResultSet rs=sqlPreparedStatement.executeQuery();
                if(rs.next()){
                    result="Account number already exists!";
                }
                else{
                    PreparedStatement stmn=con.prepareStatement(insertAcc);
                    stmn.setInt(1,idc);
                    stmn.setString(2,AccNum);
                    stmn.execute();
                    result="New account: "+AccNum;
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        else{
            result="Long or short acc num";
        }
        return result;
    }

    public void updateAccMoney(int empID,int id,String AccNum,double money){

        Connection con= Globals.getConnection();
        PreparedStatement updatemoney;

        try {
            updatemoney = con.prepareStatement(updateMoney);
            updatemoney.setDouble(1,money);
            updatemoney.setString(2,AccNum);
            updatemoney.executeUpdate();
            PreparedStatement inserttrans=con.prepareStatement(insertintotrans);
            inserttrans.setInt(1,id);
            inserttrans.setInt(2,id);
            inserttrans.setInt(3,empID);
            inserttrans.setDouble(4,money);
            inserttrans.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Card> AllCards(int accountid){
        ArrayList<Card> cards=new ArrayList<>();

        Connection con= Globals.getConnection();
        PreparedStatement sqlPreparedStatement;

        try {
            sqlPreparedStatement = con.prepareStatement(getAccCards);
            sqlPreparedStatement.setInt(1,accountid);
            ResultSet rs = sqlPreparedStatement.executeQuery();
            while (rs.next()) {
                int id=rs.getInt("id");
                int  accid=accountid;
                String PIN = rs.getString("pin");
                boolean active  = rs.getBoolean("active");
                String m= String.valueOf(rs.getInt("expirem"));
                String y=String.valueOf(rs.getInt("expirey"));
                if(Integer.valueOf(m)<10) {
                    m="0"+m;
                }
                if(Integer.valueOf(y)<10){
                    y="0"+y;
                }
                String expireDate=m+"/"+y;
                Card card=new Card(id,accid,PIN,active,expireDate);
                cards.add(card);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public void newClient(String fname,String lname,String email,String login,String password){
        Connection con= Globals.getConnection();
        PreparedStatement addclient;

        try {
            addclient=con.prepareStatement(newClient, Statement.RETURN_GENERATED_KEYS);
            addclient.setString(1,fname);
            addclient.setString(2,lname);
            addclient.setString(3,email);
            addclient.executeUpdate();
            ResultSet rs=addclient.getGeneratedKeys();
            int key=0;
            if(rs.next()){
                key = rs.getInt(1);
            }
            PreparedStatement addLogin=con.prepareStatement(newLogin);
            addLogin.setInt(1,key);
            addLogin.setString(2,login);
            addLogin.setString(3,password);
            addLogin.execute();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectUserLogin(int idClient){
        String username=null;
        Connection con= Globals.getConnection();
        PreparedStatement selectusername;
        try{
            selectusername=con.prepareStatement(selectuser);
            selectusername.setInt(1,idClient);
            ResultSet rs = selectusername.executeQuery();
            while (rs.next()) {
                username= rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public void resetPW(String username,String pass){
        Connection con= Globals.getConnection();
        PreparedStatement resetpw;

        try {
            resetpw = con.prepareStatement(resetPassword);
            resetpw.setString(1, pass);
            resetpw.setString(2, username);
            System.out.println(resetpw);
            resetpw.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void blockIB(int idc){
        Connection con= Globals.getConnection();
        PreparedStatement blockInBank;
        try{
            blockInBank=con.prepareStatement(blockIB);
            blockInBank.setInt(1,idc);
            blockInBank.executeUpdate();
            System.out.println(blockInBank);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void unblockIB(int idc){


        Connection con= Globals.getConnection();
        PreparedStatement blockInBank;
        try{
            blockInBank=con.prepareStatement(unblockIB);
            blockInBank.setInt(1,idc);
            blockInBank.executeUpdate();
            System.out.println(blockInBank);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<LoginHistory> last3Logins(int idc){
        int id=0;
        int idl=0;
        String date="";
        String success;

        ArrayList<LoginHistory> loginHistories =new ArrayList<>();
        Connection con=Globals.getConnection();
        PreparedStatement sql=null;
        try {
            sql = con.prepareStatement(get3Logins);
            sql.setInt(1, idc);

            ResultSet rs = sql.executeQuery();

            while (rs.next()) {
                id=rs.getInt("id");
                idl=rs.getInt("idl");
                date=rs.getString("LogDate");
                success=rs.getString("Success");
                LoginHistory loghistory=new LoginHistory(id,idl,date,success);
                loginHistories.add(loghistory);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return loginHistories;
    }

    public void newCard(int ida,String PIN,int expirem,int expirey){
        Connection con= Globals.getConnection();
        PreparedStatement addclient;

        try{
            addclient=con.prepareStatement(newCard);
            addclient.setInt(1,ida);
            addclient.setString(2,PIN);
            addclient.setInt(3,expirem);
            addclient.setInt(4,expirey);
            addclient.executeUpdate();
            System.out.println("INSERTED NEW CARD");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void changePIN(String newPin,int idCard){
        Connection con= Globals.getConnection();
        PreparedStatement resetpw;

        try {
            resetpw = con.prepareStatement(newPIN);
            resetpw.setString(1, newPin);
            resetpw.setInt(2, idCard);
            System.out.println(resetpw);
            resetpw.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
