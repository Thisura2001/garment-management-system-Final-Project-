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

import java.io.IOException;
import java.sql.SQLException;

public class AdminFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtFname;

    @FXML
    private JFXTextField txtLname;

    @FXML
    private JFXTextField txtetf;

    @FXML
    private JFXTextField txtnic;

    @FXML
    private JFXTextField txtpassword;

    @FXML
    private JFXTextField txtusername;

    private AdminModel adminModel =new AdminModel();

    public void btnCreateAccOnAction(ActionEvent actionEvent) throws IOException {

        String username = txtusername.getText();
        String password = txtpassword.getText();

        try {
            boolean isSaved = adminModel.saveAdmin(new adminDto(username,password));

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Admin saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logging_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Log in Page");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void clearFields() {
        txtpassword.setText("");
        txtusername.setText("");
    }
}
