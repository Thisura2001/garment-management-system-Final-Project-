package lk.ijse.Model;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.rowMaterialDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialManage_model {
    public boolean updateMaterial(rowMaterialDto rowMaterialDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE  rowmaterial SET name = ?,unit_price = ?,qty_on_hand = ? WHERE row_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,rowMaterialDto.getName());
        preparedStatement.setString(2,rowMaterialDto.getUnitPrice());
        preparedStatement.setInt(3,rowMaterialDto.getQty());
        preparedStatement.setString(4,rowMaterialDto.getRow_id());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean saveMaterial(rowMaterialDto rowMaterialDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO rowmaterial VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,rowMaterialDto.getRow_id());
        preparedStatement.setString(2,rowMaterialDto.getName());
        preparedStatement.setString(3,rowMaterialDto.getUnitPrice());
        preparedStatement.setInt(4,rowMaterialDto.getQty());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteMaterial(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM rowmaterial WHERE row_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,id);

        return preparedStatement.executeUpdate() > 0;
    }

    public List<rowMaterialDto> getAllMaterial() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rowmaterial";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<rowMaterialDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new rowMaterialDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)
                    )
            );
        }
        return dtoList;
    }

    public rowMaterialDto searchMaterial(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rowmaterial WHERE row_id= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        rowMaterialDto materialDto = null;

        if(resultSet.next()){
            String  row_id = resultSet.getString(1);
            String  row_name = resultSet.getString(2);
            String  unit_price = resultSet.getString(3);
            int  qty = Integer.parseInt(resultSet.getString(4));

            materialDto = new rowMaterialDto(row_id,row_name,unit_price,qty);
        }
        return materialDto;
    }
}
