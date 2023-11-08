package lk.ijse.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.adminDto;
import lk.ijse.Model.AdminModel;
import lk.ijse.Model.LoggingModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoggingFormController extends CreateAccountFormController{

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtpassword;

    @FXML
    private JFXTextField txtusername;

    private LoggingModel loggingModel = new LoggingModel();

    private AdminModel adminModel = new AdminModel();

    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CreateAccount_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Create Account Form");

    }

    public void btnLoggingOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        adminDto dto = new adminDto(username, password);
        String pass = adminModel.searchAdmin(dto);

        if(pass.equals(password)){
            Parent rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setTitle("Liyo garment");
            stage.setScene(scene);
            stage.centerOnScreen();
        } else {
            showErrorAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void setValues(String[][] sd) {
        this.saveDetail = sd;
        for (int i = 0; i < saveDetail.length; i++) {
            for (int j = 0; j < saveDetail[i].length; j++) {
                System.out.println(saveDetail[i][j]+",");
            }
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try {
            String userName = txtusername.getText();
            String password = txtpassword.getText();

            boolean isAdd = loggingModel.AddAdmin(userName,password);

            if (isAdd){
                new Alert(Alert.AlertType.CONFIRMATION,"Admin saved successfully!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Try again!").show();
            throw new RuntimeException(e);
        }
    }
}
