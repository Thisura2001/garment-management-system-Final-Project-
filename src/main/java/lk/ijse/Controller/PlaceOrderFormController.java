package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.PlaceOrderDto;
import lk.ijse.Dto.customerDto;
import lk.ijse.Dto.itemDto;
import lk.ijse.Model.CustomerManage_model;
import lk.ijse.Model.ItemManage_model;
import lk.ijse.Model.OrderModel;
import lk.ijse.Model.PlaceOrder_model;
import lk.ijse.Tm.CartTm;
import lk.ijse.mail.Mail;
import lk.ijse.mail.Mail_Logging;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class PlaceOrderFormController {
    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerMail;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblPlaceOrder;

    @FXML
    private TextField txtqty;

    CustomerManage_model customerManageModel = new CustomerManage_model();
    ItemManage_model itemManageModel = new ItemManage_model();
    OrderModel orderModel = new OrderModel();
    PlaceOrder_model placeOrderModel = new PlaceOrder_model();
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        lblOrderDate.setText(String.valueOf(java.time.LocalDate.now()));
        loadAllCustomerIds();
        loadAllItemCodes();
        genateNextOrderId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void genateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }

    private void loadAllCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<customerDto> idList = customerManageModel.getAllcustomerId();

            for (customerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomerId.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<itemDto> idList = itemManageModel.getAllItemCodes();

            for (itemDto dto : idList) {
                obList.add(dto.getCode());
            }

            cmbItemCode.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddCartOnAction(actionEvent);
    }
    private int checkQty() {
        int currentQty = Integer.parseInt(lblQty.getText());
        int enterQty = Integer.parseInt(txtqty.getText());

        if (enterQty > currentQty) {
            new Alert(Alert.AlertType.WARNING, "Sorry Quantity not available..Please enter valid quantity").show();
            return 0;
        }
        return enterQty;
    }

    public void btnAddCartOnAction(ActionEvent actionEvent) {

        String code = cmbItemCode.getValue();
        String description = lblDescription.getText();
        int qty = checkQty();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double tot = unitPrice * qty;
        Button btn = new Button("Remove");

        if (qty == 0) {
            Mail_Logging mail = new Mail_Logging();
            mail.setMsg("Sorry !! We Don't have that much quantity we have only " + lblQty.getText() + " in stock. ");
            mail.setTo(lblCustomerMail.getText());
            mail.setSubject("Stock Alert !!");

            Thread thread = new Thread(mail);
            thread.start();
            return ;
        }else {

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                    if (colCode.getCellData(i).equals(code)) {
                        int col_qty = (int) colQty.getCellData(i);
                        qty += col_qty;
                        tot = unitPrice * qty;

                        obList.get(i).setQty(qty);
                        obList.get(i).setTot(tot);

                        calculateTotal();
                        tblPlaceOrder.refresh();
                        return;
                         }
                    }
                }
            }
            var cartTm = new CartTm(code, description, qty, unitPrice, tot, btn, qty * unitPrice);

            obList.add(cartTm);

            tblPlaceOrder.setItems(obList);
            calculateTotal();
            txtqty.clear();
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblPlaceOrder.refresh();
                calculateTotal();
            }
        });
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());
        String customerId = cmbCustomerId.getValue();
        String total = colTotal.getText();

        boolean isValidateOrder = validateOrder(orderId);
        if (isValidateOrder){

        List<CartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            CartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }

        var placeOrderDto = new PlaceOrderDto(orderId, date, customerId, cartTmList);
        try {

            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!!").show();

                Mail_Logging mail = new Mail_Logging();
                mail.setMsg("Your Order Successfully Placed !! \n\n Thank You... ");
                mail.setTo(lblCustomerMail.getText());
                mail.setSubject("Successfully Ordered");

                Thread thread = new Thread(mail);
                thread.start();
            }else {
                new Alert(Alert.AlertType.ERROR, "Sorry Your Order Failed!!").show();
                 }
            }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Order Failed!!").show();
            throw new RuntimeException(e);
             }
        }
    }

    private boolean validateOrder(String id) {
        boolean isOrderIdValidate = Pattern.matches("[O][\\d]{3,}", id);
        if (!isOrderIdValidate) {
            new Alert(Alert.AlertType.ERROR,"Invalid OrderId !!").show();
            return false;
        }
        return true;
    }

    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        String code = cmbItemCode.getValue();

        txtqty.requestFocus();
        try {
            itemDto dto = itemManageModel.searchItems(code);
            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQty.setText(String.valueOf(dto.getQty()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        String id = cmbCustomerId.getValue();
        try {
            customerDto customerDto = customerManageModel.searchCustomer(id);
            lblCustomerName.setText(customerDto.getName());
            lblCustomerMail.setText(customerDto.getAddress());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Parent rootNode = fxmlLoader.load();

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer_Form");
        stage.setScene(scene);
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {

        String id = lblOrderId.getText();

        InputStream resourceAsStream = getClass().getResourceAsStream("/view/Report/OrderDetail.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport, //compiled report
                        null,
                        DbConnection.getInstance().getConnection() //database connection
                );

        JasperViewer.viewReport(jasperPrint, false);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\thisu\\reports\\" + id + ".pdf");

        Mail mail = new Mail();
        mail.setMsg("Payment Success. ");
        mail.setTo(lblCustomerMail.getText());
        mail.setSubject("payment!");
        mail.setFile(new File("C:\\Users\\thisu\\reports\\"+id+".pdf"));



        Thread thread = new Thread(mail);
        thread.start();
    }
}