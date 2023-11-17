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

public class CustomerManage_model {

    public boolean addCustomer(customerDto customerDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,customerDto.getId());
        preparedStatement.setString(2,customerDto.getName());
        preparedStatement.setString(3,customerDto.getAddress());
        preparedStatement.setString(4,customerDto.getTel());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateCustomer(customerDto customerDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE customer SET name= ?,address = ? ,tel = ? WHERE cust_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,customerDto.getName());
        preparedStatement.setString(2,customerDto.getAddress());
        preparedStatement.setString(3,customerDto.getTel());
        preparedStatement.setString(4,customerDto.getId());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    public customerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cust_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        customerDto customerDto = null;

        if(resultSet.next()){
            String  cus_id = resultSet.getString(1);
            String  cus_name = resultSet.getString(2);
            String  cus_address = resultSet.getString(3);
            String  cus_tel = resultSet.getString(4);

            customerDto = new customerDto(cus_id,cus_name,cus_address,cus_tel);
        }
        return customerDto;
    }

    public List<customerDto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<customerDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new customerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public List<customerDto> getAllcustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<customerDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new customerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public String getCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM customer";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public char[] getSupplierCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM customer";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1).toCharArray();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public char[] getEmployeeCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM employee";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1).toCharArray();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateNextCustomerId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT cust_id FROM customer ORDER BY cust_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }

    private String splitCustomerId(Object currentCustomerId) {
        if(currentCustomerId != null) {
            String[] split = currentCustomerId.toString().split("C0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "C00" + id;
        } else {
            return "C001";
        }
    }
}
