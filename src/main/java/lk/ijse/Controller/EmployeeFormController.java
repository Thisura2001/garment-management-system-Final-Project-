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
import lk.ijse.Dto.employeeDto;
import lk.ijse.Model.EmployeeManage_model;

import javafx.scene.control.TableColumn;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import lk.ijse.Tm.EmployeeTm;

public class EmployeeFormController {

    @FXML
    private TableView<EmployeeTm> tblEmployee;
    @FXML
    private TableColumn<?,?> colAddress;

    @FXML
    private TableColumn<?,?> colName;

    @FXML
    private TableColumn<?,?> colTel;

    @FXML
    private TableColumn<?,?> colid;

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

    private EmployeeManage_model employeeManageModel = new EmployeeManage_model();

    public void initialize(){
        setCellValueFactory();
        loadAllEmployee();
        genateNextEmployeeId();
    }

    private void genateNextEmployeeId() {
        try {
          String EmployeeId = employeeManageModel.genarateNextEmployeeId();
          txtid.setText(EmployeeId);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllEmployee() {
        var model = new EmployeeManage_model();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<employeeDto> dtoList = model.getAllEmployee();

            for (employeeDto employeeDto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                employeeDto.getEmp_id(),
                                employeeDto.getName(),
                                employeeDto.getAddress(),
                                employeeDto.getTel()
                        )
                );
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));

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

    public void btnAddonAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        String name = txtname.getText();
        String address = txtaddress.getText();
        Integer tel = Integer.valueOf(txttel.getText());
        try{
            boolean isValidate = validateEmployee(id,name,address);
            if (isValidate){
        boolean isAdd = employeeManageModel.addEmployee(new employeeDto(id,name,address,tel));

        if (isAdd) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully!!").show();
            loadAllEmployee();
            clearFields();
            genateNextEmployeeId();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Employee not Saved!!  Try again").show();
            System.out.println(e);
        }
    }

    private boolean validateEmployee(String id, String name, String address) {
        boolean isValidateEmployeeId = Pattern.matches("[E][\\d]{3,}",id);
        if (!isValidateEmployeeId){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Id").show();
            return false;
        }
        boolean isValidateEmployeeName = Pattern.matches("[A-Za-z ]+",name);
        if (!isValidateEmployeeName){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Name").show();
            return false;
        }
        boolean isValidateEmployeeAddress = Pattern.matches("[A-Za-z]{3,}",address);
        if (!isValidateEmployeeAddress){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Address").show();
            return false;
        }
        boolean isValidateEmployeeTel = Pattern.matches("[\\d]{9,}",txttel.getText());
        if (!isValidateEmployeeTel){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Tel").show();
            return false;
        }
        return true;
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtid.getText();

            boolean isDelete = employeeManageModel.deleteEmployee(id);

            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer delete Successfully!!").show();
                loadAllEmployee();
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
                loadAllEmployee();
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
           employeeDto employeeDto  = employeeManageModel.searchEmployee(id);
            if (employeeDto != null){
                txtid.setText(employeeDto.getEmp_id());
                txtname.setText(employeeDto.getName());
                txtaddress.setText(employeeDto.getAddress());
                txttel.setText(String.valueOf(employeeDto.getTel()));
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Employee not Found!!").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

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
