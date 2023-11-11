package lk.ijse.Controller;

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
import lk.ijse.Dto.AttendanceDto;
import lk.ijse.Dto.employeeDto;
import lk.ijse.Model.AttendanceModel;
import lk.ijse.Model.EmployeeManage_model;
import lk.ijse.Tm.attendanceTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceFormController {
    @FXML
    private ComboBox<String> cmbEmplooyeId;
    @FXML
    private TableView<attendanceTm> tblAttendance;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblTime;

    @FXML
    private RadioButton rAbstent;

    @FXML
    private RadioButton rbtnPresent;

    @FXML
    private AnchorPane rootNode;

    AttendanceModel attendanceModel = new AttendanceModel();

    EmployeeManage_model employeeManageModel = new EmployeeManage_model();
    private String attendanceStatus;

    public void initialize(){
        loadAllAttendanceDetails();
        setCellValueFactory();
        loadEmployeeIds();
        setDate();
        setTime();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllAttendanceDetails() {
        var model = new AttendanceModel();

        ObservableList<attendanceTm> obList = FXCollections.observableArrayList();

        try {
            List<AttendanceDto> dtoList = model.getAllAttendanceDetails();

            for (AttendanceDto dto : dtoList) {
                obList.add(
                        new attendanceTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getStatus()
                        )
                );
            }

          tblAttendance.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<employeeDto> idList = employeeManageModel.getAllEmployee();

            for (employeeDto dto : idList) {
                obList.add(dto.getEmp_id());
            }

            cmbEmplooyeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    private void setTime() {
        lblTime.setText(String.valueOf(LocalTime.now()));
    }

    public void rbtnPresentOnAction(ActionEvent actionEvent) {
        if(rbtnPresent.isSelected()){
            attendanceStatus="Present";
        }else{
            attendanceStatus="Absent";
        }
        System.out.println(attendanceStatus);
    }

    public void rbtnAbsentOnAction(ActionEvent actionEvent) {
        if(rAbstent.isSelected()){
            attendanceStatus="Present";
        }else{
            attendanceStatus="Absent";
        }
        System.out.println(attendanceStatus);
    }

    public void cmbEmployeeIdOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbEmplooyeId.getValue());
        try {
            employeeDto employeeDto = employeeManageModel.searchEmployee(id);
           lblEmployeeName.setText(employeeDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {


        String id = String.valueOf(cmbEmplooyeId.getValue());
        String name = lblEmployeeName.getText();
        String date = lblDate.getText();
        String time = lblTime.getText();
        String status =attendanceStatus;
        try {
           boolean isSaved = attendanceModel.saveDetails(new AttendanceDto(id,name,date,time,status));

           if (isSaved){
               new Alert(Alert.AlertType.INFORMATION,"Saved success!!").show();
               loadAllAttendanceDetails();
           }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }
}
