package lk.ijse.Model;

import lk.ijse.Db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggingModel {
    public boolean AddAdmin(String userName, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO admin VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2,password);

        return preparedStatement.executeUpdate()>0;
    }
}
