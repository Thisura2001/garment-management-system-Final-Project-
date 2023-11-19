package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public boolean saveOrderDetails(String orderId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getCode());
        pstm.setDouble(3, tm.getUnitPrice());
        pstm.setInt(4, tm.getQty());
        pstm.setDouble(5, tm.getTot());
        pstm.setDouble(6, tm.getSubTot());

        return pstm.executeUpdate() > 0;
    }
}
