package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.Customer_dto;
import lk.ijse.Dto.Item_dto;
import lk.ijse.Model.ItemManage_model;
import lk.ijse.Tm.ItemTm;

import javax.swing.table.TableColumn;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblItem;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtqtyOnHand;

    @FXML
    private TextField txtunitPrice;

    private ItemManage_model itemManageModel = new ItemManage_model();
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void btnAddOnAction(ActionEvent actionEvent){
        try {
            String code = txtid.getText();
            String description =txtname.getText();
            Integer qtyOnHand = Integer.valueOf(txtqtyOnHand.getText());
            String unitPrice =txtunitPrice.getText();

            boolean isAdd = ItemManage_model.addItems(new Item_dto(code,description,qtyOnHand,unitPrice));

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Saved!!").show();
                clearFields();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();

            boolean isDelete = itemManageModel.deleteItem(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Item delete Successfully!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String code = txtid.getText();
            String description =txtname.getText();
            Integer qtyOnHand = Integer.valueOf(txtqtyOnHand.getText());
            String unitPrice =txtunitPrice.getText();

            boolean isUpdate = itemManageModel.updateItems(new Item_dto(code,description,qtyOnHand,unitPrice));

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Update Success!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }



    private void clearFields() {
        txtid.setText("");
        txtname.setText("");
        txtqtyOnHand.setText("");
        txtunitPrice.setText("");
    }

}
