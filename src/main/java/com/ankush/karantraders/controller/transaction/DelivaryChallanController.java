package com.ankush.karantraders.controller.transaction;

import com.ankush.karantraders.config.SpringFXMLLoader;
import com.ankush.karantraders.controller.create.CreateSiteDialogController;
import com.ankush.karantraders.data.entities.*;
import com.ankush.karantraders.data.service.CustomerService;
import com.ankush.karantraders.data.service.ItemService;
import com.ankush.karantraders.data.service.ItemStockService;
import com.ankush.karantraders.data.service.SiteService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class DelivaryChallanController implements Initializable {
    @Autowired
    @Lazy
    private StageManager stageManager;
    @Autowired
    private SpringFXMLLoader fxmlLoader;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private Button btnAddSite;
    @FXML
    private Button btnClear;
    @FXML
    private MFXButton btnClearBill, btnHome, btnPrint, btnSave, btnShowAll, btnUpdateBill;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSearchSite;
    @FXML
    private Button btnUpdate;

    @FXML
    private MFXCheckbox chkGst;
    @FXML
    private ComboBox<String> cmbSite;
    @FXML
    private MFXLegacyComboBox<String> cmbUnit;
    @FXML
    private DatePicker date, dateSearch;

    @FXML
    private TableView<ChallanTransaction> tableTr;
    @FXML
    private TableColumn<ChallanTransaction, Long> colSr;
    @FXML
    private TableColumn<ChallanTransaction, String> colCode;
    @FXML
    private TableColumn<ChallanTransaction, String> colDescription;
    @FXML
    private TableColumn<ChallanTransaction, String> colUnit;
    @FXML
    private TableColumn<ChallanTransaction, Float> colQuantity;
    @FXML
    private TableColumn<ChallanTransaction, Float> colRate;
    @FXML
    private TableColumn<ChallanTransaction, Float> colGst;
    @FXML
    private TableColumn<ChallanTransaction, Float> colAmount;

    @FXML
    private TableView<?> tableChallan;
    @FXML
    private TableColumn<?, ?> colChallan;
    @FXML
    private TableColumn<?, ?> colCustomerName;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> ColGrand;

    @FXML
    private MFXTextField txtAmount, txtCode;
    @FXML
    private TextArea txtCustomerInfo;
    @FXML
    private TextField txtCustomerName, txtDescription;
    @FXML
    private MFXTextField txtDiscount, txtGrandTotal, txtGst, txtNetTotal, txtOther, txtQuantity, txtRate, txtSearchBillNo;
    @FXML
    private TextField txtSearchCustomer;
    @FXML
    private MFXTextField txtTotalGst, txtTransport;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AlertNotification alert;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemStockService stockService;
    private Customer customer;
    private Site site;
    private Item item;
    private ItemStock stock;
    private SuggestionProvider<String> customerNameProvider, itemNameProvideer;
    private ObservableList<String> siteNames = FXCollections.observableArrayList();
    private ObservableList<ChallanTransaction> trList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProperties();
    }

    private void setProperties() {
        customer = null;
        site = null;
        item = null;
        btnAddSite.setOnAction(e -> {
            DialogPane pane = fxmlLoader.getDialogPage("/fxml/create/CreateSiteDialog.fxml");
            CreateSiteDialogController dialog = fxmlLoader.getLoader().getController();
            Dialog<ButtonType> di = new Dialog<>();
            di.setDialogPane(pane);
            di.setTitle("Add New Site");
            //di.showAndWait();
            Optional<ButtonType> clickedButton = di.showAndWait();
            if (clickedButton.get() == ButtonType.FINISH) {
                // customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
                //TextFields.bindAutoCompletion(txtCustomerName,customerNameProvider);
                //TextFields.bindAutoCompletion(txtSearchCustomer,customerNameProvider);
            }
        });
        customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
        itemNameProvideer = SuggestionProvider.create(itemService.getAllItemNames());
        cmbSite.getItems().addAll(siteNames);
        TextFields.bindAutoCompletion(txtDescription, itemNameProvideer);
        TextFields.bindAutoCompletion(txtCustomerName, customerNameProvider);

        colSr.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colGst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tableTr.setItems(trList);

        txtCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    // txtCode.setText(oldValue);
                } else {
                    if (!txtCode.getText().isEmpty()) {
                        findByCode(txtCode.getText());
                        setItem(item);
                    }
                }
            }
        });
        txtCode.setOnAction(e -> {
            if (!txtCode.getText().isEmpty())
                txtDescription.requestFocus();
        });
        txtDescription.setOnAction(e -> {
            if (!txtDescription.getText().isEmpty()) {
                setItem(itemService.getByDescription(txtDescription.getText()));
                txtQuantity.requestFocus();
            }
        });
        txtQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtQuantity.setText(oldValue);
                } else {

                }
            }
        });
        txtQuantity.setOnAction(e -> {
            if (!txtQuantity.getText().isEmpty() && !txtQuantity.getText().equalsIgnoreCase("" + 0.0f)) {
                calculateAmount();
                txtGst.requestFocus();
            }
        });
        txtCustomerName.setOnAction(e -> {
            if (!txtCustomerName.getText().isEmpty())
                setCustomer(customerService.getByCustomerName(txtCustomerName.getText()));
            btnSearchSite.setStyle("-fx-background-color:#d9534f");
        });
        txtGst.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtGst.setText(oldValue);
                }
            }
        });
        txtGst.setOnAction(e -> {
            if (!txtGst.getText().isEmpty()) {
                calculateAmount();
                txtAmount.requestFocus();
            }
        });
        txtAmount.setOnAction(e -> {
            btnAdd.requestFocus();
        });
        btnSearchSite.setOnAction(e -> searchSite());
        txtTransport.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtTransport.setText(oldValue);
                }
            }
        });
        txtTransport.setOnAction(e -> {
            if (!txtTransport.getText().isEmpty()) {
                calculateGrandTotal();
                txtOther.requestFocus();
            }
        });
        txtOther.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtOther.setText(oldValue);
                }
            }
        });
        txtOther.setOnAction(e -> {
            if (!txtOther.getText().isEmpty()) {
                calculateGrandTotal();
                txtDiscount.requestFocus();
            }
        });
        txtDiscount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtDiscount.setText(oldValue);
                }
            }
        });
        txtDiscount.setOnAction(e -> {
            if (!txtDiscount.getText().isEmpty()) {
                calculateGrandTotal();
                txtGrandTotal.requestFocus();
            }
        });
        chkGst.setOnAction(e -> {
            if (chkGst.isSelected()) {
                if (txtGst.getText().isEmpty() || txtGst.getText().equalsIgnoreCase("" + 0.0f)) {
                    alert.showError("Enter Gst Rate in percentage");
                    txtGst.requestFocus();
                    chkGst.setSelected(false);
                } else {
                    txtGst.setEditable(false);
                }
            } else {
                txtGst.setEditable(true);
            }

        });
        btnAdd.setOnAction(e -> add());
        btnClear.setOnAction(e -> clear());
        btnUpdate.setOnAction(e -> update());
        btnRemove.setOnAction(e -> remove());
        btnSave.setOnAction(e->save());

    }

    private void save() {
        if(!validateChallan())return;
        calculateGrandTotal();
        DeliveryChallan challan = DeliveryChallan.builder()
                .date(date.getValue())
                .discount(Float.parseFloat(txtDiscount.getText()))
                .grand(Float.parseFloat(txtGrandTotal.getText()))
                .nettotal(Float.parseFloat(txtNetTotal.getText()))
                .paid(0.0f)
                .other(Float.parseFloat(txtOther.getText()))
                .customer(customer)
                .transport(Float.parseFloat(txtTransport.getText()))
                .gst(Float.parseFloat(txtTotalGst.getText()))
                .site(site)
                .transactions(new ArrayList<>())
                .build();
        for(ChallanTransaction tr:trList){
            tr.setId(null);
            tr.setChallan(challan);
            challan.getTransactions().add(tr);
        }
        System.out.println(challan);
    }

    private boolean validateChallan() {
        if(trList.size()==0)
        {
            alert.showError("No Data to Save");
            return false;
        }
        if(date.getValue()==null)
        {
            alert.showError("Select Challan Date");
            date.requestFocus();
            return false;
        }
        if(customer==null)
        {
            alert.showError("Select Customer!");
            txtCustomerName.requestFocus();
            return false;
        }
        return true;
    }

    private void searchSite() {
        if (customer == null) {
            alert.showError("Select Custmer First");
            txtCustomerName.requestFocus();
            return;
        }
        if (cmbSite.getValue() == null) {
            alert.showError("Select Site Name or Click on Add New!");
            btnSearchSite.setStyle("-fx-background-color:#d9534f");
            return;
        }
        this.site = siteService.getByCustomerAndSiteName(customer.getId(), cmbSite.getValue());
        if (site != null) {
            btnSearchSite.setStyle("-fx-background-color:#5cb85c");
        } else
            btnSearchSite.setStyle("-fx-background-color:#d9534f");
    }
    private void setCustomer(Customer customer) {
        this.customer = customer;
        if (customer != null) {
            txtCustomerInfo.setText(
                    "Name: " + customer.getCustomername() +
                            "\nAddress: " + customer.getAddressline() +
                            "Village: " + customer.getVillage() +
                            " Taluka: " + customer.getTaluka() +
                            " District: " + customer.getDistrict() +
                            "\nContact: " + customer.getContact() +
                            " Alternate Contact: " + customer.getMobile()
            );
            cmbSite.getItems().clear();
            cmbSite.getItems().addAll(siteService.getSiteNameByCustomer(customer.getId()));
        } else {
            cmbSite.getItems().clear();
        }
    }
    private void findByCode(String code) {
        item = itemService.getByCode(code);
    }
    private void setItem(Item item) {
        if (null != item) {
            stock = stockService.getByItemId(item.getId());
            if (null != stock) {
                txtCode.setText(item.getCode());
                txtDescription.setText(item.getDescription());
                cmbUnit.setValue(item.getUnit());
                txtRate.setText(String.valueOf(stock.getRate()));
                txtGst.setText(String.valueOf(stock.getGst()));
            } else {
                txtCode.setText("");
                txtDescription.setText("");
                cmbUnit.setValue(null);
                txtRate.setText("");
                txtGst.setText("");
            }
        } else {
            txtCode.setText("");
            txtDescription.setText("");
            cmbUnit.setValue(null);
            txtRate.setText("");
            txtGst.setText("");
        }
    }
    void calculateAmount() {
        if (txtRate.getText().isEmpty()) txtRate.setText("" + 0.0f);
        if (txtQuantity.getText().isEmpty()) txtQuantity.setText("" + 0.0f);
        if (txtGst.getText().isEmpty()) txtGst.setText("" + 0.0f);
        txtAmount.setText(
                String.valueOf(
                        Float.parseFloat(txtQuantity.getText()) * Float.parseFloat(txtRate.getText()) + (
                                Float.parseFloat(txtQuantity.getText()) * Float.parseFloat(txtRate.getText()) * Float.parseFloat(txtGst.getText()) / 100)
                )
        );

    }
    private void add() {
        if (!validate()) return;
        ChallanTransaction tr = ChallanTransaction.builder()
                .code(txtCode.getText())
                .description(txtDescription.getText())
                .quantity(Float.valueOf(txtQuantity.getText()))
                .rate(Float.parseFloat(txtRate.getText()))
                .gst(Float.parseFloat(txtGst.getText()))
                .amount(Float.parseFloat(txtAmount.getText()))
                .unit(cmbUnit.getValue())
                .itemStock(stockService.getByItemId(item.getId()))
                .build();
        if (checkStock(tr.getItemStock(), tr.getQuantity()) == 0) return;
        addInTrList(tr);
        clear();
        txtCode.requestFocus();
        //System.out.println(tr);

    }
    private boolean validate() {
        if (txtAmount.getText().isEmpty() || txtAmount.getText().equalsIgnoreCase("" + 0.0f)) {
            alert.showError("Select Item again!");
            return false;
        }
        return true;
    }
    private int checkStock(ItemStock stock,float q){
        float qty=0.0f;
        for(ChallanTransaction tr:trList)
        {
            if(tr.getItemStock().getId().equals(stock.getId()))
            {
                qty = tr.getQuantity();
                break;
            }
        }

        qty=stock.getQuantity()-qty;
        System.out.println("old stock "+qty);
        if(q>qty){
            alert.showError("Less Quantity available in Stock"+qty);
            txtQuantity.requestFocus();
            txtQuantity.selectAll();
            return 0;
        }
        else{
            return 1;
        }
    }
    private void calculateGrandTotal() {
        if(txtNetTotal.getText().isEmpty()) txtNetTotal.setText(""+0.0f);
        if(txtTotalGst.getText().isEmpty()) txtTotalGst.setText(""+0.0f);
        if(txtTransport.getText().isEmpty()) txtTransport.setText(""+0.0f);
        if(txtOther.getText().isEmpty()) txtOther.setText(""+0.0f);
        if(txtDiscount.getText().isEmpty()) txtDiscount.setText(""+0.0f);
        txtGrandTotal.setText(String.valueOf(
                Float.parseFloat(txtNetTotal.getText())+
                        Float.parseFloat(txtTotalGst.getText())+
                        Float.parseFloat(txtTransport.getText())+
                        Float.parseFloat(txtOther.getText())-
                        Float.parseFloat(txtDiscount.getText())
        ));
    }
    private void addInTrList(ChallanTransaction tr) {
        int index=-1;
        tr.setId((long) (trList.size()+1));
        for(ChallanTransaction t:trList)
        {
            if(tr.getCode().equalsIgnoreCase(t.getCode()) &&
                    tr.getDescription().equalsIgnoreCase(t.getDescription()) &&
                    tr.getRate().equals(t.getRate()))
            {
                index=trList.indexOf(t);
                break;
            }
        }
        if(index==-1)
        {
            tr.setId((long) (trList.size()+1));
            trList.add(tr);
            tableTr.refresh();
        }
        else{
            trList.get(index).setQuantity(trList.get(index).getQuantity()+tr.getQuantity());
            trList.get(index).setAmount(trList.get(index).getAmount()+tr.getAmount());
            tableTr.refresh();
        }
        if(txtNetTotal.getText().isEmpty()) txtNetTotal.setText(""+0.0f);
        if(txtTotalGst.getText().isEmpty()) txtTotalGst.setText(""+0.0f);
        txtNetTotal.setText(String.valueOf(
                Float.parseFloat(txtNetTotal.getText())+(tr.getQuantity()*tr.getRate())
        ));
        txtTotalGst.setText(String.valueOf(
                Float.parseFloat(txtTotalGst.getText())+(tr.getAmount()-(tr.getQuantity()*tr.getRate()))
        ));
        calculateGrandTotal();
    }
    private void clear(){
        item = null;
        txtCode.setText("");
        txtDescription.setText("");
        txtDiscount.setText("");
        txtGst.setText("");
        txtQuantity.setText("");
        cmbUnit.getSelectionModel().clearSelection();
        txtAmount.setText(""+0.0f);
    }
    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)
            return;
        ChallanTransaction tr = tableTr.getSelectionModel().getSelectedItem();
        txtDescription.setText(tr.getDescription());
        setItem(itemService.getByDescription(tr.getDescription()));
        txtRate.setText(String.valueOf(tr.getRate()));
        txtQuantity.requestFocus();
    }
    private void remove() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)return;
        ChallanTransaction tr = tableTr.getSelectionModel().getSelectedItem();
        trList.remove(tableTr.getSelectionModel().getSelectedIndex());
        int i=0;
        for(ChallanTransaction t:trList)
        {
            trList.get(trList.indexOf(t)).setId((long) ++i);
        }
        tableTr.refresh();
        txtNetTotal.setText(
                String.valueOf(
                        Float.parseFloat(txtNetTotal.getText())-(tr.getRate()*tr.getQuantity())
                )
        );
        txtTotalGst.setText(String.valueOf(
                Float.parseFloat(txtTotalGst.getText())-(tr.getAmount()-(tr.getQuantity()*tr.getRate()))
        ));
        calculateGrandTotal();
    }
}
