package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.employeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeManage_model {
    public boolean addEmployee(employeeDto employeeDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, employeeDto.getEmp_id());
        preparedStatement.setString(2, employeeDto.getName());
        preparedStatement.setString(3, employeeDto.getAddress());
        preparedStatement.setInt(4, employeeDto.getTel());

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean updateEmployee(employeeDto employeeDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET name= ?,address = ? ,tel = ? WHERE emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,employeeDto.getName());
        preparedStatement.setString(2,employeeDto.getAddress());
        preparedStatement.setInt(3,employeeDto.getTel());
        preparedStatement.setString(4,employeeDto.getEmp_id());

        return preparedStatement.executeUpdate()>0;
    }

}
