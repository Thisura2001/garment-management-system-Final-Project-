package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.employeeDto;
import lk.ijse.Dto.itemDto;
import lk.ijse.Tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<itemDto> getAllitems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<itemDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new itemDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public List<itemDto> getAllItemCodes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<itemDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new itemDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public itemDto searchItems(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item WHERE code = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        itemDto itemDto= null;

        if(resultSet.next()){
            String  code = resultSet.getString(1);
            String  description = resultSet.getString(2);
            int qty_on_hand = resultSet.getInt(3);
            String  unit_price = resultSet.getString(4);

            itemDto = new itemDto(code,description,qty_on_hand,unit_price);
        }
        return itemDto;
    }
    public boolean updateItem(List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            System.out.println("Item: " + tm);
            if(!updateQty(tm.getCode(), tm.getQty())) {
                return false;
            }
        }
        return true;
    }
    public boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty_on_hand = qty_on_hand - ? WHERE code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0;
    }
}
