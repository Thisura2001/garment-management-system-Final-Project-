package lk.ijse.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbconnection;

    private Connection connection;

    private  DbConnection(){

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/garment","root","1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static DbConnection getInstance() throws SQLException{
        if(dbconnection == null) {
           dbconnection = new DbConnection();
            return dbconnection;
        } else {
            return dbconnection;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

