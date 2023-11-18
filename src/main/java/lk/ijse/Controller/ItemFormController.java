package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.itemDto;
import lk.ijse.Model.ItemManage_model;
import javafx.scene.control.TableColumn;
import lk.ijse.Tm.EmployeeTm;
import lk.ijse.Tm.ItemTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {


    @FXML
    private TableColumn<?,?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colqtyOnHand;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtqtyOnHand;

    @FXML
    private TextField txtunitPrice;

    private ItemManage_model itemManageModel = new ItemManage_model();

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
        genarateNextItemId();
    }

    private void genarateNextItemId() {
        try {
            String id = itemManageModel.genarateNextItemId();
            txtid.setText(id);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllItems() {
        var model = new ItemManage_model();

        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<itemDto> dtoList = model.getAllitems();

            for (itemDto itemDto : dtoList) {
                obList.add(
                        new ItemTm(
                                itemDto.getCode(),
                                itemDto.getDescription(),
                                itemDto.getQty(),
                                itemDto.getUnitPrice()
                        )
                );
            }

            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("code"));
        colname.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty_on_hand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

    }

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
        String id = txtid.getText();

        try {
            itemDto itemDto = itemManageModel.searchItems(id);
            if (itemDto != null){
                txtid.setText(itemDto.getCode());
                txtname.setText(itemDto.getDescription());
                txtqtyOnHand.setText(String.valueOf(itemDto.getQty()));
                txtunitPrice.setText(itemDto.getUnitPrice());
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Item not Found!!").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void btnAddOnAction(ActionEvent actionEvent){
        try {
            String code = txtid.getText();
            String description =txtname.getText();
            Integer qtyOnHand = Integer.valueOf(txtqtyOnHand.getText());
            String unitPrice =txtunitPrice.getText();

            boolean isAdd = ItemManage_model.addItems(new itemDto(code,description,qtyOnHand,unitPrice));

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Saved!!").show();
                loadAllItems();
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
                loadAllItems();
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

            boolean isUpdate = itemManageModel.updateItems(new itemDto(code,description,qtyOnHand,unitPrice));

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Update Success!!").show();
                loadAllItems();
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
