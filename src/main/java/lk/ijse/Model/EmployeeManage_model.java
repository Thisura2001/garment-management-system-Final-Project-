package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.employeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<employeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<employeeDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new employeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)
                    )
            );
        }
        return dtoList;
    }

    public employeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        employeeDto employeeDto = null;

        if(resultSet.next()){
            String  emp_id = resultSet.getString(1);
            String  name = resultSet.getString(2);
            String  address = resultSet.getString(3);
            Integer  tel = resultSet.getInt(4);

            employeeDto = new employeeDto(emp_id,name,address,tel);
        }
        return employeeDto;
    }
}
