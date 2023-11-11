package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.adminDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LoggingModel {

    public Optional<adminDto> searchUser(adminDto adminDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM admin WHERE userName = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,adminDto.getUsername());
        preparedStatement.setString(2,adminDto.getPassword());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            String userName = resultSet.getString(1);
            String password = resultSet.getString(2);
            return Optional.of(new adminDto(userName,password));
        }
        return Optional.empty();
    }
}
