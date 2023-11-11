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
import java.util.Optional;

public class LoggingFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtpassword;

    @FXML
    private JFXTextField txtusername;

    private LoggingModel loggingModel = new LoggingModel();

    private AdminModel adminModel = new AdminModel();

    public void btnCreateAccountOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Admin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Create Account Form");

    }
    private void navigateToDashBoard() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Liyo garment");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void btnLoggingOnAction(ActionEvent actionEvent)  {
        String userName = txtusername.getText();
        String password = txtpassword.getText();

        adminDto adminDto = new adminDto(userName, password);
        try {
            Optional<adminDto> u = loggingModel.searchUser(adminDto);
            if (u.isPresent()) {
                adminDto user1 = u.get();
                if (userName.equals(user1.getUsername()) && password.equals(user1.getPassword())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Welcome " + userName).showAndWait();
                    navigateToDashBoard();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"UserName or Password is incorrect Try Again!!").show();
            System.out.println(e);
        }
    }
}
