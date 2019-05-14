package mainpckg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Globals {

    public static final String username="glbank";
    public static final String password="password";
    public static final String url = "jdbc:mysql://itsovy.sk:3306/glbank";

    public static Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DriverLoaded");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
