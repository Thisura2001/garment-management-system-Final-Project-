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
import java.util.regex.Pattern;

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

        try {

            String username = txtusername.getText();
            String password = txtpassword.getText();

            boolean isAdminValidate = validateAdmin(username,password);
            if (isAdminValidate) {
                boolean isSaved = adminModel.saveAdmin(new adminDto(username, password));

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Admin saved!").show();
                clearFields();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logging_form.fxml"));
                Parent rootNode = fxmlLoader.load();

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) this.rootNode.getScene().getWindow();
                stage.setTitle("Log in Page");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                 }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateAdmin(String username, String password) {
       boolean isUsernameValidate = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",username);
       if (!isUsernameValidate){
          new Alert(Alert.AlertType.ERROR,"Invalid Username !!").show();
          return false;
       }
       boolean isPasswordValidate = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\n",password);
       if (isPasswordValidate){
           new Alert(Alert.AlertType.ERROR,"Invalid Password!!").show();
           return false;
       }
       return true;
    }

    private void clearFields() {
        txtpassword.setText("");
        txtusername.setText("");
    }
}
