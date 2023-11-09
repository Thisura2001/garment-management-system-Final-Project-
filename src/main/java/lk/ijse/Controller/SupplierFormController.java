package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import lk.ijse.Dto.supplierDto;
import lk.ijse.Model.SupplierManage_model;
import javafx.scene.control.TableColumn;
import lk.ijse.Tm.supplierTm;

import java.io.IOException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private TableView<supplierTm> tblSupplier;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coltype;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txttel;

    @FXML
    private TextField txttype;

    private SupplierManage_model supplierManageModel = new SupplierManage_model();

    public void initialize(){
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        var model = new SupplierManage_model();

        ObservableList<supplierTm> obList = FXCollections.observableArrayList();

        try{
            List<supplierDto> dtoList = model.getAllSuppliers();

            for (supplierDto supplierDto:dtoList) {
                obList.add(
                        new supplierTm(
                        supplierDto.getSup_id(),
                        supplierDto.getName(),
                        supplierDto.getAddress(),
                        supplierDto.getEmail(),
                        supplierDto.getType()

                    )
                );
                tblSupplier.setItems(obList);

            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String address = txtaddress.getText();
            String tel = txttel.getText();
            String type = txttype.getText();

            boolean isAdd = supplierManageModel.SaveSupplier(new supplierDto(id,name,address,tel,type));

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier saved Successfully!!").show();
                loadAllSuppliers();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String address = txtaddress.getText();
            String email = txttel.getText();
            String type = txttype.getText();

            boolean isUpdate = supplierManageModel.UpdateSupplier(new supplierDto(id,name,address,email,type));

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"SupplierUpdate Successfully!!").show();
                loadAllSuppliers();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }

    @FXML
    void btndeleteSupplierOnAction(ActionEvent event) {

        String id = txtid.getText();

        try {
            boolean isDelete = supplierManageModel.deleteSupplier(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Delete Successfully!!").show();
                loadAllSuppliers();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }
    private void clearFields() {
        txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txttel.setText("");
        txttype.setText("");
    }
}
