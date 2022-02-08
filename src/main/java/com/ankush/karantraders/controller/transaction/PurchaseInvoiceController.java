package com.ankush.karantraders.controller.transaction;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.entities.Item;
import com.ankush.karantraders.data.entities.ItemStock;
import com.ankush.karantraders.data.entities.PurchaseParty;
import com.ankush.karantraders.data.service.ItemService;
import com.ankush.karantraders.data.service.ItemStockService;
import com.ankush.karantraders.data.service.PurchasePartyService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
@Component
public class PurchaseInvoiceController implements Initializable {

    @Autowired
    @Lazy
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
    @Autowired
    private PurchasePartyService partyService;
    @Autowired AlertNotification alert;
    @Autowired private ItemService itemService;
    @Autowired private ItemStockService stockService;
    private SuggestionProvider<String>partyNameProvider;
    private SuggestionProvider<String>itemNameProvider;
    private PurchaseParty party;
    private Item item;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());
        new AutoCompletionTextFieldBinding<>(txtPartyName, partyNameProvider);
        itemNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
        new AutoCompletionTextFieldBinding<>(txtDescription,itemNameProvider);
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colGst.setCellValueFactory(new PropertyValueFactory<>("gst"));
        colHsn.setCellValueFactory(new PropertyValueFactory<>("hsn"));
        colMrp.setCellValueFactory(new PropertyValueFactory<>("mrp"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colSr.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        btnSearch.setOnAction(e->searchParty());
        setTextProperties();
    }
    private void setTextProperties() {
        
        txtCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                    txtCode.setText(oldValue);
                }
                else
                {
                    if(!txtCode.getText().isEmpty())
                    {
                        findByCode(Long.parseLong(txtCode.getText()));
                        setItem(item);
                    }
                }
            }
        });
        txtCode.setOnAction(e->{
            txtHsn.requestFocus();
        });
       txtHsn.setOnAction(e->txtDescription.requestFocus());
       txtDescription.setOnAction(e->{
           if(!txtDescription.getText().isEmpty()){
               item = itemService.getByDescription(txtDescription.getText());
               setItem(item);
               txtQuantity.requestFocus();
           }
       });
       txtQuantity.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtQuantity.setText(oldValue);
           }
       });
       txtQuantity.setOnAction(e->{
           txtUnit.requestFocus();
            calculateAmount();
       });
       txtMrp.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtMrp.setText(oldValue);
           }
       });
       txtMrp.setOnAction(e->{
           txtDiscount.requestFocus();
           calculateAmount();
       });
       txtDiscount.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtDiscount.setText(oldValue);
           }
       });
        txtDiscount.setOnAction(e->{
            txtRate.requestFocus();
            calculateAmount();
        });
       txtRate.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtRate.setText(oldValue);
           }
       });
       txtRate.setOnAction(e->{
           txtGst.requestFocus();
           calculateAmount();
       });
       txtGst.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtGst.setText(oldValue);
           }
       });
       txtGst.setOnAction(e->{
           txtAmount.requestFocus();
           calculateAmount();
       });


    }
    private void findByCode(Long code) {
         item  = itemService.getByCode(code);
        System.out.println("Got Item =>"+item);
    }
    private void setItem(Item item){
        if(item!=null){
            txtCode.setText(String.valueOf(item.getCode()));
            txtDescription.setText(item.getDescription());
            txtHsn.setText(String.valueOf(item.getHsn()));
            txtUnit.setText(item.getUnit());
            ItemStock stock = stockService.getByItemId(item.getId());
            if(stock!=null){
                txtMrp.setText(String.valueOf(stock.getMrp()));
                txtDiscount.setText(String.valueOf(stock.getDiscount()));
                txtRate.setText(String.valueOf(stock.getRate()));
                txtGst.setText(String.valueOf(stock.getGst()));
            }
        }
        else{
            txtDescription.setText("");
            txtHsn.setText("");
            txtUnit.setText("");
            txtMrp.setText("");
            txtDiscount.setText("");
            txtRate.setText("");
            txtGst.setText("");
        }
    }
    private void searchParty() {
        if(txtPartyName.getText().isEmpty())
        {
            txtPartyName.requestFocus();
            return;
        }
        if(partyService.getPartyByName(txtPartyName.getText()).size()>0)
        party = partyService.getPartyByName(txtPartyName.getText()).get(0);
        else
        alert.showError("Party Not Found");


    }
    private void calculateAmount()
    {
        if(txtQuantity.getText().isEmpty())txtQuantity.setText(""+0.0f);
        if(txtDiscount.getText().isEmpty()) txtDiscount.setText(""+0.0f);
        if(txtRate.getText().isEmpty())txtRate.setText(""+0.0f);
        if(txtGst.getText().isEmpty())txtGst.setText(""+18);
        float amount = Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtRate.getText());
        float gst =(amount*( Float.parseFloat(txtGst.getText())/100));
        float disc = (amount*(Float.parseFloat(txtDiscount.getText())/100));
        System.out.println("Amount="+amount+"\n gst= "+gst+"\ndisc = "+disc);
        txtAmount.setText(
                String.valueOf(
                        amount+gst-disc
                )
        );

    }
    
}
