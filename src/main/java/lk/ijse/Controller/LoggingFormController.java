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

    public LoggingFormController(){
        setValues(super.saveDetail);
    }

    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CreateAccount_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Create Account Form");
        stage.centerOnScreen();
    }

    public void btnLoggingOnAction(ActionEvent actionEvent) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        // Replace this logic with your actual authentication mechanism.
        boolean isLoginSuccessful = authenticateUser(username, password);

        if (isLoginSuccessful) {
            // Login successful, you can open a new scene or perform other actions.
            showInfoAlert("Login Successful", "Welcome, " + username + "!");

            Parent rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setTitle("Liyo garment");
            stage.setScene(scene);

        } else {
            // Login failed, show an error message to the user.
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

    private void showInfoAlert(String title, String massage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    private boolean authenticateUser(String username, String password) {
        for (int i = 0; i < saveDetail.length; i++) {
            if (saveDetail[i][4].equals(username) && saveDetail[i][5].equals(password)) {
                System.out.println("Password correct");
                return true;
            }
        }
        // If no matching user was found
        System.out.println("Username or password incorrect");
        return false;
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
