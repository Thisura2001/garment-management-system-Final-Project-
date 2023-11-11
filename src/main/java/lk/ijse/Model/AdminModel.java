package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.adminDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel {
    public boolean saveAdmin(adminDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO admin VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getUsername());
        pstm.setString(2, dto.getPassword());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
