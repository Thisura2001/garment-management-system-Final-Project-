package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.itemDto;
import lk.ijse.Dto.supplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierManage_model {
    public boolean SaveSupplier(supplierDto supplierDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,supplierDto.getSup_id());
        preparedStatement.setString(2,supplierDto.getName());
        preparedStatement.setString(3,supplierDto.getAddress());
        preparedStatement.setString(4,supplierDto.getEmail());
        preparedStatement.setString(5,supplierDto.getType());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean UpdateSupplier(supplierDto supplierDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE supplier SET name = ?,address = ?,email = ?,type = ? WHERE sup_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,supplierDto.getName());
        preparedStatement.setString(2,supplierDto.getAddress());
        preparedStatement.setString(3,supplierDto.getEmail());
        preparedStatement.setString(4,supplierDto.getType());
        preparedStatement.setString(5,supplierDto.getSup_id());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE sup_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        return preparedStatement.executeUpdate() > 0;
    }

    public List<supplierDto> getAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<supplierDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new supplierDto(
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

    public char[] getSupplierCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(sup_id) FROM supplier";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                char[] count = resultSet.getString(1).toCharArray();
                return count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String genarateNextSupplierId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT sup_id FROM supplier ORDER BY sup_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return splitsupplierId(resultSet.getString(1));
        }
        return splitsupplierId(null);
    }

    private String splitsupplierId(String currentSupplierId) {
        if(currentSupplierId != null) {
            String[] split = currentSupplierId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

    public supplierDto searchSupplier(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE sup_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        supplierDto supplierDto= null;

        if(resultSet.next()){
            String  sup_id = resultSet.getString(1);
            String  sup_name = resultSet.getString(2);
            String  sup_address = resultSet.getString(3);
            String  sup_mail = resultSet.getString(4);
            String  type = resultSet.getString(5);

            supplierDto= new supplierDto(sup_id,sup_name,sup_address,sup_mail,type);
        }
        return supplierDto;
    }
}
