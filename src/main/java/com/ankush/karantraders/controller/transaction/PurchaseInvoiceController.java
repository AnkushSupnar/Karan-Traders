package com.ankush.karantraders.controller.transaction;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.entities.*;
import com.ankush.karantraders.data.service.BankService;
import com.ankush.karantraders.data.service.ItemService;
import com.ankush.karantraders.data.service.ItemStockService;
import com.ankush.karantraders.data.service.PurchasePartyService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;

import impl.org.controlsfx.skin.AutoCompletePopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
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
    @FXML private AnchorPane mainPane;
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
    @FXML private TableView<PurchaseTransaction> tableTr;
    @FXML private TextField txtAmount,txtCGst,txtCode,txtDescription,txtDiscount,txtGrandTotal,txtGst,txtHsn,txtInvoiceNo;
    @FXML private TextField txtMrp,txtNetTotal,txtOther,txtPackaging,txtPaid,txtPartyName;
    @FXML private TextField txtQuantity,txtRate,txtSGst,txtShiping,txtUnit;
    @FXML private TextField txtDiscountTotal;

    @Autowired
    private PurchasePartyService partyService;
    @Autowired AlertNotification alert;
    @Autowired private ItemService itemService;
    @Autowired private ItemStockService stockService;
    @Autowired private BankService bankService;
    private SuggestionProvider<String>partyNameProvider;
    private SuggestionProvider<String>itemNameProvider;
    private PurchaseParty party;
    private Item item;
    private ObservableList<PurchaseTransaction>trList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());

        AutoCompletionBinding<String> autoComplete = TextFields.bindAutoCompletion(this.txtPartyName,partyNameProvider);
        autoComplete.prefWidthProperty().bind(this.txtPartyName.widthProperty());

        //AutoCompletionTextFieldBinding<String> demo = new AutoCompletionTextFieldBinding<>(txtPartyName, partyNameProvider);

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
        tableTr.setItems(trList);
        btnSearch.setOnAction(e->searchParty());
        setTextProperties();
        btnAdd.setOnAction(e->add());
        btnUpdate.setOnAction(e->update());
        btnRemove.setOnAction(e->remove());
        btnSave.setOnAction(e->save());


    }

    private void save() {
        if(!validateBill()) return;

        PurchaseInvoice invoice = PurchaseInvoice.builder()
                .date(date.getValue())
                .bank(bankService.getByName(cmbBank.getValue()))
                .invoiceno(txtInvoiceNo.getText())
                .grand(Double.parseDouble(txtGrandTotal.getText()))
                .nettotal(Double.parseDouble(txtNetTotal.getText()))
                .other(Double.parseDouble(txtOther.getText()))
                .packaging(Double.parseDouble(txtPackaging.getText()))
                .paid(Double.parseDouble(txtPaid.getText()))
                .sgst(Double.parseDouble(txtSGst.getText()))
                .cgst(Double.parseDouble(txtCGst.getText()))
                .shiping(Double.parseDouble(txtShiping.getText()))
                .build();

        for(PurchaseTransaction tr:trList){
            tr.setId(null);
            tr.setInvoice(invoice);
            invoice.getTransaction().add(tr);
        }
        System.out.println(invoice);


    }

    private boolean validateBill() {
        if(date.getValue()==null)
        {
            alert.showError("Select Invoice Date");
            date.requestFocus();
            return false;
        }
        if(txtInvoiceNo.getText().isEmpty())
        {
            alert.showError("Enter Invoice No");
            txtInvoiceNo.requestFocus();
            return false;
        }
        if(party==null)
        {
            alert.showError("Select Party again");
            txtPartyName.requestFocus();
            return false;
        }
        if(trList.size()==0)
        {
            alert.showError("No Data to Save");
            return false;
        }
        if(cmbBank.getValue()==null){
            alert.showError("Select Payment Bank");
            cmbBank.requestFocus();
            return false;
        }
        if(txtPaid.getText().isEmpty())
        {
            txtPaid.setText(""+0.0f);
        }
        return true;
    }

    private void remove() {
        if (tableTr.getSelectionModel().getSelectedItem()!=null)
        {
            PurchaseTransaction tr = tableTr.getSelectionModel().getSelectedItem();
            trList.remove(tableTr.getSelectionModel().getSelectedIndex());
            txtNetTotal.setText(String.valueOf(Float.parseFloat(txtNetTotal.getText())-(tr.getRate()*tr.getQuantity())));
            txtDiscountTotal.setText(String.valueOf(Float.parseFloat(txtDiscountTotal.getText())-((tr.getRate()*tr.getQuantity())*(tr.getDiscount()/100))));
            txtCGst.setText(
                    String.valueOf(
                            Float.parseFloat(txtCGst.getText())-((tr.getRate()*tr.getQuantity())*(tr.getGst()/100)/2)
                    )
            );
            txtSGst.setText(
                    String.valueOf(
                            Float.parseFloat(txtSGst.getText())-((tr.getRate()*tr.getQuantity())*(tr.getGst()/100)/2)
                    )
            );
            calculateGrandTotal();
        }
    }

    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()!=null)
        {
            PurchaseTransaction tr = tableTr.getSelectionModel().getSelectedItem();
            txtCode.setText(tr.getCode());
            txtHsn.setText(String.valueOf(tr.getHsn()));
            txtDescription.setText(tr.getDescription());
            txtQuantity.setText(String.valueOf(tr.getQuantity()));
            txtUnit.setText(tr.getUnit());
            txtMrp.setText(String.valueOf(tr.getMrp()));
            txtDiscount.setText(String.valueOf(tr.getDiscount()));
            txtRate.setText(String.valueOf(tr.getRate()));
            txtGst.setText(String.valueOf(tr.getGst()));
            txtAmount.setText(String.valueOf(tr.getAmount()));
        }
    }

    private void setTextProperties() {
        
        txtCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                   // txtCode.setText(oldValue);
                }
                else
                {
                    if(!txtCode.getText().isEmpty())
                    {
                        findByCode(txtCode.getText());
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
       txtUnit.setOnAction(e->{
            if(!txtUnit.getText().isEmpty())
            {
                txtMrp.requestFocus();
            }
        });
       txtAmount.setOnAction(e->{
           calculateAmount();
           btnAdd.fire();
       });
       txtShiping.textProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                   txtShiping.setText(oldValue);
           }
       });
       txtShiping.setOnAction(e->{
           calculateGrandTotal();
            if(!txtShiping.getText().isEmpty())
                txtPackaging.requestFocus();
       });
        txtPackaging.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtPackaging.setText(oldValue);
            }
        });
        txtPackaging.setOnAction(e->{
            calculateGrandTotal();
            if(!txtPackaging.getText().isEmpty())
                txtOther.requestFocus();
        });
        txtOther.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtOther.setText(oldValue);
            }
        });
        txtOther.setOnAction(e->{
            calculateGrandTotal();
            if(!txtOther.getText().isEmpty())
                txtGrandTotal.requestFocus();
        });
        txtPaid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtPaid.setText(oldValue);
            }
        });
        txtPaid.setOnAction(e->{
            btnSave.requestFocus();
        });
        cmbBank.getItems().addAll(bankService.getAllBankNames());
       btnAdd.setOnAction(e->add());
       btnClear.setOnAction(e->clear());
    }
    private void add() {
        if(!validateItem()) return;
        PurchaseTransaction tr = PurchaseTransaction.builder().
                code(txtCode.getText())
                .description(txtDescription.getText().trim())
                .amount(Float.parseFloat(txtAmount.getText()))
                .hsn(Integer.parseInt(txtHsn.getText()))
                .discount(Float.parseFloat(txtDiscount.getText()))
                .gst(Float.parseFloat(txtGst.getText()))
                .unit(txtUnit.getText())
                .mrp(Float.parseFloat(txtMrp.getText()))
                .quantity(Float.parseFloat(txtQuantity.getText()))
                .rate(Float.parseFloat(txtRate.getText()))
                .build();
        System.out.println(tr);
        addInTrList(tr);
        clear();
    }
    private void clear(){
        txtCode.setText("");
        txtDiscount.setText("");
        txtHsn.setText("");
        txtDescription.setText("");
        txtQuantity.setText("");
        txtUnit.setText("");
        txtMrp.setText("");
        txtRate.setText("");
        txtGst.setText("");
        txtAmount.setText("");
    }
    private void addInTrList(PurchaseTransaction tr) {
        int index=-1;
        for(PurchaseTransaction t:trList)
        {
            if(
                    tr.getDescription().equalsIgnoreCase(t.getDescription())&&
                    tr.getCode().equalsIgnoreCase(t.getCode()) &&
                    tr.getRate().equals(t.getRate()) &&
                    tr.getDiscount().equals(t.getDiscount()) &&
                    tr.getMrp().equals(t.getMrp()))
            {
                index = trList.indexOf(t);
            }
        }
        if(index==-1)
        {
            tr.setId(trList.size()+1);
            trList.add(tr);
            tableTr.refresh();
        }
        else
        {
            trList.get(index).setQuantity(trList.get(index).getQuantity()+tr.getQuantity());
            trList.get(index).setAmount(trList.get(index).getAmount()+tr.getAmount());
            tableTr.refresh();
        }
        txtNetTotal.setText(
                String.valueOf(
                        Float.parseFloat(txtNetTotal.getText())+(tr.getRate()*tr.getQuantity())
                )
        );
        txtCGst.setText(
                String.valueOf(Float.parseFloat(txtCGst.getText())+
                        (tr.getRate()*tr.getQuantity())*(tr.getGst()/100)/2)
        );
        txtSGst.setText(
                String.valueOf(Float.parseFloat(txtSGst.getText())+
                        (tr.getRate()*tr.getQuantity())*(tr.getGst()/100)/2)
        );

        txtDiscountTotal.setText(
                String.valueOf(Float.parseFloat(txtDiscountTotal.getText())+
                        ( (tr.getRate()*tr.getQuantity())*(Float.parseFloat(txtDiscount.getText())/100))
                )
        );
        calculateGrandTotal();
    }
    private boolean validateItem() {
        if (txtDescription.getText().isEmpty()) {
            alert.showError("Enter Item Description");
            return false;
        }
        if (txtUnit.getText().isEmpty())
        {
            alert.showError("Enter Unit");
            return false;
        }
        if(txtAmount.getText().equals(""+0.0f) || txtAmount.getText().isEmpty())
        {
            alert.showError("Select Or Enter Item again!!!");
            return false;
        }
        return true;
    }
    private void findByCode(String code) {
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
        if(partyService.getPartyByName(txtPartyName.getText()).size()>0) {
            party = partyService.getPartyByName(txtPartyName.getText()).get(0);
            btnSearch.setStyle("-fx-background-color: green;-fx-text-fill:white ");
        }
        else {
            alert.showError("Party Not Found");
           btnSearch.setStyle("-fx-background-color: red; -fx-text-fill:white ");
        }
    }
    private void calculateAmount(){
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
    private void calculateGrandTotal(){
        if(txtNetTotal.getText().isEmpty()) txtNetTotal.setText(""+0.0f);
        if(txtCGst.getText().isEmpty())txtCGst.setText(""+0.0f);
        if(txtSGst.getText().isEmpty())txtSGst.setText(""+0.0f);
        if(txtShiping.getText().isEmpty())txtShiping.setText(""+0.0f);
        if(txtPackaging.getText().isEmpty())txtPackaging.setText(""+0.0f);
        if(txtOther.getText().isEmpty())txtOther.setText(""+0.0f);
        if(txtDiscountTotal.getText().isEmpty())txtDiscountTotal.setText(""+0.0f);

        txtGrandTotal.setText(
                String.valueOf(
                        Float.parseFloat(txtNetTotal.getText())+
                          Float.parseFloat(txtCGst.getText())+
                          Float.parseFloat(txtSGst.getText())+
                          Float.parseFloat(txtShiping.getText())+
                          Float.parseFloat(txtOther.getText())+
                          Float.parseFloat(txtPackaging.getText())-
                          Float.parseFloat(txtDiscountTotal.getText())
                )
        );
    }

    
}
