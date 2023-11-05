package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.Customer_dto;
import lk.ijse.Model.CustomerManage_model;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerFormController {
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

            boolean isAdd = customerManageModel.addCustomer(new Customer_dto(id,name,address,tel));

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!!").show();
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

            boolean isUpdate = customerManageModel.updateCustomer(new Customer_dto(id,name,address,tel));

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
            Customer_dto customerDto = customerManageModel.searchCustomer(id);

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
}
