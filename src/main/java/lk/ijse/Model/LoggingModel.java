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
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("select * from admin where userName= ? and password = ?");
        ps.setString(1,adminDto.getUsername());
        ps.setString(2,adminDto.getPassword());
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            String userName = rs.getString(1);
            String password = rs.getString(2);
            return Optional.of(new adminDto(userName,password));
        }
        return Optional.empty();
    }
}
