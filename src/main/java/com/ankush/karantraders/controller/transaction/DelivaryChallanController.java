package com.ankush.karantraders.controller.transaction;

import com.ankush.karantraders.config.SpringFXMLLoader;
import com.ankush.karantraders.controller.create.CreateSiteDialogController;
import com.ankush.karantraders.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class DelivaryChallanController implements Initializable {
    @Autowired @Lazy private StageManager stageManager;
    @Autowired private SpringFXMLLoader fxmlLoader;
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

        setProperties();

    }

    private void setProperties() {
        btnAddSite.setOnAction(e->{
            DialogPane pane =   fxmlLoader.getDialogPage("/fxml/create/CreateSiteDialog.fxml");
            CreateSiteDialogController dialog = fxmlLoader.getLoader().getController();
            Dialog<ButtonType> di = new Dialog<>();
            di.setDialogPane(pane);
            di.setTitle("Add New Site");
            //di.showAndWait();
            Optional<ButtonType> clickedButton = di.showAndWait();
            if(clickedButton.get()==ButtonType.FINISH){
               // customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
                //TextFields.bindAutoCompletion(txtCustomerName,customerNameProvider);
                //TextFields.bindAutoCompletion(txtSearchCustomer,customerNameProvider);
            }
        });
    }
}
