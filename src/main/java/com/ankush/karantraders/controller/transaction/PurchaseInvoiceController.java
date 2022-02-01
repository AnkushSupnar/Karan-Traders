package com.ankush.karantraders.controller.transaction;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
@Component
public class PurchaseInvoiceController implements Initializable {

    @Autowired
    private StageManager stageManager;

    @FXML private Button btnAdd,btnClear,btnClearBill,btnHome,btnPrint;
    @FXML private Button btnRemove,btnSave,btnSearch,btnUpdate,btnUpdateBill;
    @FXML private ComboBox<String> cmbBank;
    @FXML private TableColumn<?, ?> colAmount;
    @FXML private TableColumn<?, ?> colCode;
    @FXML private TableColumn<?, ?> colDescription;
    @FXML private TableColumn<?, ?> colDiscount;
    @FXML private TableColumn<?, ?> colGst;
    @FXML private TableColumn<?, ?> colHsn;
    @FXML private TableColumn<?, ?> colMrp;
    @FXML private TableColumn<?, ?> colQty;
    @FXML private TableColumn<?, ?> colRate;
    @FXML private TableColumn<?, ?> colSr;
    @FXML private TableColumn<?, ?> colUnit;
    @FXML private DatePicker date;
    @FXML private TableView<?> tableTr;
    @FXML private TextField txtAmount,txtCGst,txtCode,txtDescription,txtDiscount,txtGrandTotal,txtGst,txtHsn,txtInvoiceNo;
    @FXML private TextField txtMrp,txtNetTotal,txtOther,txtPackaging,txtPaid,txtPartyName;
    @FXML private TextField txtQuantity,txtRate,txtSGst,txtShiping,txtUnit;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        
    }
    
}
