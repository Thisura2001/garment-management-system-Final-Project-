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
import lk.ijse.Dto.employeeDto;
import lk.ijse.Model.EmployeeManage_model;

import java.io.IOException;

public class EmployeeFormController {
    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txttel;
    @FXML
    private AnchorPane rootNode;

    private EmployeeManage_model employeeManageModel = new EmployeeManage_model();
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void btnAddonAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        String name = txtname.getText();
        String address = txtaddress.getText();
        Integer tel = Integer.valueOf(txttel.getText());
        try{
        boolean isAdd = employeeManageModel.addEmployee(new employeeDto(id,name,address,tel));

        if (isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved Successfully!!").show();
            clearFields();
        }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Employee not Saved!!  Try again").show();
            System.out.println(e);
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();

            boolean isDelete = employeeManageModel.deleteEmployee(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer delete Successfully!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();
            String name =txtname.getText();
            String address =txtaddress.getText();
            Integer tel = Integer.valueOf(txttel.getText());

            boolean isUpdate = employeeManageModel.updateEmployee(new employeeDto(id,name,address,tel));

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Update Success!!").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            System.out.println(e);
        }
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
    }
    private void clearFields() {
        txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txttel.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
