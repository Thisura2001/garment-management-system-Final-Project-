package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.itemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemManage_model {
    public static boolean addItems(itemDto itemDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,itemDto.getCode());
        preparedStatement.setString(2,itemDto.getDescription());
        preparedStatement.setInt(3,itemDto.getQty());
        preparedStatement.setString(4,itemDto.getUnitPrice());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM item WHERE code = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateItems(itemDto itemDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE item SET description = ?,qty_on_hand = ? ,unit_price = ? WHERE code = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,itemDto.getDescription());
        preparedStatement.setInt(2,itemDto.getQty());
        preparedStatement.setString(3,itemDto.getUnitPrice());
        preparedStatement.setString(4,itemDto.getCode());

        return preparedStatement.executeUpdate()>0;
    }

}
