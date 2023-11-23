package lk.ijse.Controller;

import com.jfoenix.controls.JFXPasswordField;
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
import lk.ijse.mail.Mail_Logging;

import java.io.IOException;
import java.util.Optional;

public class LoggingFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXPasswordField txtPassword;

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
        String password = txtPassword.getText();

        adminDto adminDto = new adminDto(userName, password);
        try {
            Optional<adminDto> u = loggingModel.searchUser(adminDto);
            if (u.isPresent()) {
                adminDto user1 = u.get();
                if (userName.equals(user1.getUsername()) && password.equals(user1.getPassword())) {

                    Mail_Logging mail = new Mail_Logging();
                    mail.setMsg("WelCome !!  Your Account Logging is Successfully !!. ");
                    mail.setTo(txtusername.getText());
                    mail.setSubject("Successfully Logging Account !!");

                    Thread thread = new Thread(mail);
                    thread.start();
                    navigateToDashBoard();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"UserName or Password is incorrect Try Again!!").show();

                Mail_Logging mail = new Mail_Logging();
                mail.setMsg("Sorry !!  Your Account Logging is Failed Try again !!. ");
                mail.setTo(txtusername.getText());
                mail.setSubject("Failed to Logging Account !!");

                Thread thread = new Thread(mail);
                thread.start();

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"UserName or Password is incorrect Try Again!!").show();
        }
    }
}
