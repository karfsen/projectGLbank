package mainpckg.database;

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
        System.out.println(clients);
        return clients;
    }
}
