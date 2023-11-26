package lk.ijse.Controller;

import com.google.protobuf.StringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.employeeDto;
import lk.ijse.Dto.itemDto;
import lk.ijse.Dto.rowMaterialDto;
import lk.ijse.Model.ItemManage_model;
import lk.ijse.Model.MaterialManage_model;
import lk.ijse.Tm.EmployeeTm;
import lk.ijse.Tm.ItemTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ItemFormController {


    @FXML
    private TableColumn<?,?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private ComboBox<?> cmbMaterialId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

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
    private MaterialManage_model materialManageModel = new MaterialManage_model();

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
        genarateNextItemId();
        tableListener();
    }

    private void tableListener() {
        tblItem.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, itemTm, t1) -> {
                    txtid.setText(t1.getCode());
                    txtname.setText(t1.getDescription());
                    txtqtyOnHand.setText(String.valueOf(t1.getQty_on_hand()));
                    txtunitPrice.setText(String.valueOf(t1.getUnit_price()));
                });
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
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
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

            boolean isValidateItem = validateItem(code,description,qtyOnHand,unitPrice);
            if (isValidateItem){

            boolean isAdd = ItemManage_model.addItems(new itemDto(code,description,qtyOnHand,unitPrice));

            if (isAdd) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved!!").show();
                loadAllItems();
                clearFields();
                }
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }

    private boolean validateItem(String code, String description, Integer qtyOnHand, String unitPrice) {
        boolean isValidateItemId = Pattern.matches("[I][\\d]{3,}", code);
        if (!isValidateItemId){
            new Alert(Alert.AlertType.ERROR,"Item Id is not valid!!").show();
            return false;
        }
        boolean isValidateItemName = Pattern.matches("[A-Za-z]{3,}", description);
        if (!isValidateItemName){
            new Alert(Alert.AlertType.ERROR,"Item Name is not valid!!").show();
            return false;
        }
        boolean isValidateItemQty =Pattern.matches("[\\d]{0,}", String.valueOf(qtyOnHand));
        if (!isValidateItemQty){
            new Alert(Alert.AlertType.ERROR,"Item Qty is not valid!!").show();
            return false;
        }
        boolean isValidateItemUnitPrice = Pattern.matches("[\\d]{2,}", unitPrice);
        if (!isValidateItemUnitPrice){
            new Alert(Alert.AlertType.ERROR,"Item Unit Price is not valid!!").show();
            return false;
        }
        return true;
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

    public void cmbMaterialIdOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbMaterialId.getValue());
        try {
            rowMaterialDto materialDto = materialManageModel.searchMaterial(id);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }
}
