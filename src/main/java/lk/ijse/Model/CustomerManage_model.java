package lk.ijse.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.Customer_dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManage_model {

    public boolean addCustomer(Customer_dto customerDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,customerDto.getId());
        preparedStatement.setString(2,customerDto.getName());
        preparedStatement.setString(3,customerDto.getAddress());
        preparedStatement.setString(4,customerDto.getTel());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateCustomer(Customer_dto customerDto) throws SQLException {
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

    public Customer_dto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cust_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Customer_dto customerDto = null;

        if(resultSet.next()){
            String  cus_id = resultSet.getString(1);
            String  cus_name = resultSet.getString(2);
            String  cus_address = resultSet.getString(3);
            String  cus_tel = resultSet.getString(4);

            customerDto = new Customer_dto(cus_id,cus_name,cus_address,cus_tel);
        }
        return customerDto;
    }

    public List<Customer_dto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Customer_dto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Customer_dto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }
}
