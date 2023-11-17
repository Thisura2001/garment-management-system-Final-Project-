package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Model.CustomerManage_model;
import lk.ijse.Model.EmployeeManage_model;
import lk.ijse.Model.SupplierManage_model;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    public JFXButton btnsearch;
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lbltime;

    @FXML
    private BarChart<String,Number> BarChart;
    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private AnchorPane childPanel;

    CustomerManage_model customerManageModel = new CustomerManage_model();
    EmployeeManage_model employeeManageModel = new EmployeeManage_model();
    SupplierManage_model supplierManageModel = new SupplierManage_model();

   public  void initialize() throws SQLException {
       setDate();
       setTime();
       loadBarChart();
       getCustomerCount();
       getEmployeeCount();
       getSupplierCount();
   }

   public void getCustomerCount() throws SQLException {
       lblCustomerCount.setText(String.valueOf(customerManageModel.getCount()));
   }
   public void getEmployeeCount() throws SQLException {
    lblEmployeeCount.setText(String.valueOf(employeeManageModel.getEmployeeCount()));
   }
   public void getSupplierCount() throws SQLException {
    lblSupplierCount.setText(String.valueOf(supplierManageModel.getSupplierCount()));
   }

    private void loadBarChart() {

        XYChart.Series series = new XYChart.Series();

        series.setName("Income");
        series.getData().add(new XYChart.Data("Monday",8));
        series.getData().add(new XYChart.Data("Tuesday",12));
        series.getData().add(new XYChart.Data("Wednesday",10));
        series.getData().add(new XYChart.Data("Thursday",15));
        series.getData().add(new XYChart.Data("Friday",12));
        series.getData().add(new XYChart.Data("Saturday",8));
        series.getData().add(new XYChart.Data("Sunday",5));
        BarChart.getData().addAll(series);

    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    private void setTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = currentTime.format(formatter);
                    lbltime.setText(formattedTime);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void btnManageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer_Form");
        stage.setScene(scene);
    }

    public void btnManageEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Employee_Form");
        stage.setScene(scene);
    }

    public void btnManageItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/item_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Item_Form");
        stage.setScene(scene);
    }

    public void btnManageSupplierOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplier_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Supplier_Form");
        stage.setScene(scene);
    }

    public void btnManageReportOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/report_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("report_Form");
        stage.setScene(scene);

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/placeOrder_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("PlaceOrder_Form");
        stage.setScene(scene);
    }

    public void btnManageMaterialOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/rowMaterial_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Row Material_Form");
        stage.setScene(scene);
    }

    public void manageAttendaceOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Attendance_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Attendance_Form");
        stage.setScene(scene);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logging_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Logging_Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
