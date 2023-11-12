package lk.ijse.Model;

import javafx.scene.Parent;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.AttendanceDto;
import lk.ijse.Dto.customerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {
    public boolean saveDetails(AttendanceDto attendanceDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO attendance VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, attendanceDto.getId());
        preparedStatement.setString(2, attendanceDto.getName());
        preparedStatement.setString(3, attendanceDto.getDate());
        preparedStatement.setString(4, attendanceDto.getTime());
        preparedStatement.setString(5, attendanceDto.getStatus());
        return preparedStatement.executeUpdate() > 0;
    }

    public List<AttendanceDto> getAllAttendanceDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AttendanceDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new AttendanceDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return dtoList;
    }

    public boolean deleteDetails(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM attendance WHERE E_id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }
}
