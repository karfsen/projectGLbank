package mainpckg.database;

import mainpckg.Account;
import mainpckg.Client;
import mainpckg.Employee;
import mainpckg.Globals;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static final String checkUser="SELECT Employee.id as id,Employee.fname as fname,Employee.lname as lname,Positions.name as posname from LoginEmp INNER JOIN Employee on LoginEmp.ide=Employee.id INNER JOIN Positions on Employee.position=Positions.id where login like ? and password like ?";
    public static final String getClients="SELECT id,fname,lname from Client";
    public static final String getClientInfo="Select id,fname,lname,email from Client where id=?";
    public static final String getUserAccounts="SELECT * from Account where idc=?";
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

        ResultSet rs = sqlPreparedStatement.executeQuery();
        while (rs.next()) {
            id=rs.getInt("id");
            fname = rs.getString("fname");
            lname = rs.getString("lname");
            position = rs.getString("posname");
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
}
