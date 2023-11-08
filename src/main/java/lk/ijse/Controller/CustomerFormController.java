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
import lk.ijse.Model.CustomerManage_model;
import javafx.scene.control.TableColumn;
import lk.ijse.Tm.CustomerTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    @FXML
    private TableColumn<?,?> colAddress;

    @FXML
    private TableColumn<?,?> colId;

    @FXML
    private TableColumn<?,?> colName;

    @FXML
    private TableColumn<?,?> colTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txttel;
    private CustomerManage_model customerManageModel = new CustomerManage_model();
    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
    }

    private void loadAllCustomer() {
        var model = new CustomerManage_model();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<customerDto> dtoList = model.getAllCustomer();

            for (customerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTel()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cus_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cus_address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("cus_tel"));
        
    }



    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name =txtname.getText();
            String address =txtaddress.getText();
            String tel =txttel.getText();

            boolean isAdd = customerManageModel.addCustomer(new customerDto(id,name,address,tel));

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!!").show();
                loadAllCustomer();
                clearFields();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            throw new RuntimeException(e);
        }

    }

    private void clearFields() {
        txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txttel.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name =txtname.getText();
            String address =txtaddress.getText();
            String tel =txttel.getText();

            boolean isUpdate = customerManageModel.updateCustomer(new customerDto(id,name,address,tel));

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Update Success!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }

    public void btnDeleteonAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();

            boolean isDelete = customerManageModel.deleteCustomer(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer delete Successfully!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();

        try {
            customerDto customerDto = customerManageModel.searchCustomer(id);
            if (customerDto != null){
                txtid.setText(customerDto.getId());
                txtname.setText(customerDto.getName());
                txtaddress.setText(customerDto.getAddress());
                txttel.setText(customerDto.getTel());
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Customer not Found!!").show();
            }

        }catch (Exception e){
          new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
