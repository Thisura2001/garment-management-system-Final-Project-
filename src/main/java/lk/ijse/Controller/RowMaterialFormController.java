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
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.rowMaterialDto;
import lk.ijse.Model.CustomerManage_model;
import lk.ijse.Model.MaterialManage_model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javafx.scene.control.TableColumn;
import lk.ijse.Tm.CustomerTm;
import lk.ijse.Tm.RowMaterialTm;

public class RowMaterialFormController {
    @FXML
    private TableView<RowMaterialTm> tblRowMaterial;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtunitPrice;

    @FXML
    private AnchorPane rootNode;

    private MaterialManage_model materialManageModel = new MaterialManage_model();

    public void initialize(){
        setCellValueFactory();
        loadAllMaterial();
        genarateNextRowMaterialId();
        tableListener();
    }

    private void tableListener() {
        tblRowMaterial.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, rowMaterialTm, t1) -> {
                    txtid.setText(t1.getRow_id());
                    txtname.setText(t1.getName());
                    txtunitPrice.setText(String.valueOf(t1.getUnit_price()));
                    txtqty.setText(String.valueOf(t1.getQty_on_hand()));
    });
    }

    private void genarateNextRowMaterialId() {
        try {
            String id =  materialManageModel.genarateNextMaterialId();
            txtid.setText(id);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllMaterial() {
        var model = new MaterialManage_model();

        ObservableList<RowMaterialTm> obList = FXCollections.observableArrayList();

        try {
            List<rowMaterialDto> dtoList = model.getAllMaterial();

            for (rowMaterialDto dto : dtoList) {
                obList.add(
                        new RowMaterialTm(
                                dto.getRow_id(),
                                dto.getName(),
                                dto.getUnitPrice(),
                                dto.getQty()
                        )
                );
            }

            tblRowMaterial.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("row_id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty_on_hand"));

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }

    public void btnAddMaterialOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        String name = txtname.getText();
        String unit_price = txtunitPrice.getText();
        Integer qty = Integer.valueOf(txtqty.getText());

        try {
            boolean isValidateMaterial = validateMaterial(id,name,unit_price,qty);
            if (isValidateMaterial){
            boolean isSaved = materialManageModel.saveMaterial(new rowMaterialDto(id,name,unit_price,qty));

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Material saved Success!!").show();
                    loadAllMaterial();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private boolean validateMaterial(String id, String name, String unitPrice, Integer qty) {
        boolean isValidateMaterialId = Pattern.matches("[R][\\d]{3,}",id);
        if (!isValidateMaterialId){
            new Alert(Alert.AlertType.ERROR,"Invalid Material Id").show();
            return false;
        }
        boolean isValidateMaterialName = Pattern.matches("[A-Za-z ]+",name);
        if (!isValidateMaterialName){
            new Alert(Alert.AlertType.ERROR,"Invalid Material Name").show();
            return false;
        }
        boolean isValidateMaterialUnitPrice = Pattern.matches("[\\d]{2,}",unitPrice);
        if (!isValidateMaterialUnitPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Material Unit Price").show();
            return false;
        }
        boolean isValidateMaterialQty = Pattern.matches("[\\d]{0,}",String.valueOf(qty));
        if (!isValidateMaterialQty){
            new Alert(Alert.AlertType.ERROR,"Invalid Material Qty").show();
            return false;
        }
        return true;
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();

        try {
            rowMaterialDto rowMaterialDto = materialManageModel.searchMaterial(id);
            if (rowMaterialDto != null){
                txtid.setText(rowMaterialDto.getRow_id());
                txtname.setText(rowMaterialDto.getName());
                txtunitPrice.setText(rowMaterialDto.getUnitPrice());
                txtqty.setText(String.valueOf(rowMaterialDto.getQty()));
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Customer not Found!!").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnUpdateMaterialOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String unit_price = txtunitPrice.getText();
            int qty_on_hand = Integer.valueOf(txtqty.getText());

            boolean isValidateMaterial = validateMaterial(id,name,unit_price,qty_on_hand);
            if (isValidateMaterial) {
                boolean isUpdate = materialManageModel.updateMaterial(new rowMaterialDto(id, name, unit_price, qty_on_hand));

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Material Update Successfully!!").show();
                    loadAllMaterial();
                    clearFields();
                }
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }

    public void btnDeleteMaterialOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        try {
            boolean isDeleted = materialManageModel.deleteMaterial(id);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Material delete Successfully").show();
                loadAllMaterial();
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
        txtqty.setText("");
        txtunitPrice.setText("");
    }
}
