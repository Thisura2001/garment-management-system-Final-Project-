package lk.ijse.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountFormController {
    public String[][] saveDetail = new String[2][6];
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
    void callArray(){
        for (int i = 0; i < saveDetail.length; i++) {
            int count = 0;
            for (int j = 0; j < saveDetail[i].length; j++) {
                if (saveDetail[i][j] != null) {
                    count++;
                }
            }
            if (count != 6) {
                saveDetail[i][0] = txtFname.getText();
                saveDetail[i][1] = txtLname.getText();
                saveDetail[i][2] = txtetf.getText();
                saveDetail[i][3] = txtnic.getText();
                saveDetail[i][4] = txtusername.getText();
                saveDetail[i][5] = txtpassword.getText();
                break;
            }
        }
    }

    public void btnCreateAccOnAction(ActionEvent actionEvent) throws IOException {
        callArray();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logging_form.fxml"));
        Parent rootNode = fxmlLoader.load();
        LoggingFormController loginFormController = fxmlLoader.getController();

        loginFormController.setValues(saveDetail);

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Log in Page");
        stage.setScene(scene);
        stage.show();
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logging_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Sign-Up Page");
        stage.setScene(scene);
    }
}
