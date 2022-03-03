package com.ankush.karantraders.controller.transaction;

import com.ankush.karantraders.view.StageManager;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DelivaryChallanController implements Initializable {
    @Autowired @Lazy private StageManager stageManager;
    @FXML private AnchorPane mainPane;
    @FXML private Button btnAdd;
    @FXML private Button btnAddCustomer;
    @FXML private Button btnAddSite;
    @FXML private Button btnClear;
    @FXML private MFXButton btnClearBill,btnHome,btnPrint,btnSave,btnShowAll,btnUpdateBill;
    @FXML private Button btnRemove;
    @FXML private Button btnSearch;
    @FXML private Button btnSearchSite;
    @FXML private Button btnUpdate;

    @FXML private MFXCheckbox chkGst;
    @FXML private ComboBox<String> cmbSite;
    @FXML private MFXLegacyComboBox<String> cmbUnit;
    @FXML private DatePicker date,dateSearch;

    @FXML private TableView<?> tableTr;
    @FXML private TableColumn<?, ?> colSr;
    @FXML private TableColumn<?, ?> colCode;
    @FXML private TableColumn<?, ?> colDescription;
    @FXML private TableColumn<?, ?> colUnit;
    @FXML private TableColumn<?, ?> colQuantity;
    @FXML private TableColumn<?, ?> colRate;
    @FXML private TableColumn<?, ?> colGst;
    @FXML private TableColumn<?, ?> colAmount;

    @FXML private TableView<?> tableChallan;
    @FXML private TableColumn<?, ?> colChallan;
    @FXML private TableColumn<?, ?> colCustomerName;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> ColGrand;

    @FXML private MFXTextField txtAmount,txtCode;
    @FXML private TextArea txtCustomerInfo;
    @FXML private TextField txtCustomerName,txtDescription;
    @FXML private MFXTextField txtDiscount,txtGrandTotal,txtGst,txtNetTotal,txtOther,txtQuantity,txtRate,txtSearchBillNo;
    @FXML private TextField txtSearchCustomer;
    @FXML private MFXTextField txtTotalGst,txtTransport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
