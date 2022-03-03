package com.ankush.karantraders.controller.transaction;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.entities.*;
import com.ankush.karantraders.data.service.*;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.FxmlView;
import com.ankush.karantraders.view.StageManager;

import impl.org.controlsfx.skin.AutoCompletePopup;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import net.bytebuddy.asm.Advice;
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

    @FXML private TableView<PurchaseInvoice> tableBill;
    @FXML private TableColumn<PurchaseInvoice, String> colPartyName;
    @FXML private TableColumn<?, ?> colPaid;
    @FXML private TableColumn<?, ?> colInvoiceNo;
    @FXML private TableColumn<?, ?> colId;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> colBillAmount;

    @FXML private Button btnSearchAll;
    @FXML private DatePicker dateSearch;
    @FXML private TextField txtSearchInvoice;
    @FXML private TextField txtSearchInvoiceno;
    @FXML private TextField txtSearchParty;


    @Autowired
    private PurchasePartyService partyService;
    @Autowired AlertNotification alert;
    @Autowired private ItemService itemService;
    @Autowired private ItemStockService stockService;
    @Autowired private BankService bankService;
    @Autowired private PurchaseInvoiceService purchaseService;

    private SuggestionProvider<String>partyNameProvider;
    private SuggestionProvider<String>itemNameProvider;
    private PurchaseParty party;
    private Item item;
    private ObservableList<PurchaseTransaction>trList = FXCollections.observableArrayList();
    private ObservableList<PurchaseInvoice>billList = FXCollections.observableArrayList();
    Long id;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        id=null;
        partyNameProvider = SuggestionProvider.create(partyService.getAllPartyNames());

        AutoCompletionBinding<String> autoComplete = TextFields.bindAutoCompletion(this.txtPartyName,partyNameProvider);
        autoComplete.prefWidthProperty().bind(this.txtPartyName.widthProperty());

        AutoCompletionBinding<String> autoCompleteParty = TextFields.bindAutoCompletion(this.txtSearchParty,partyNameProvider);
        autoCompleteParty.prefWidthProperty().bind(this.txtSearchParty.widthProperty());


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

        colPartyName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getParty().getName()));
        colPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceno"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colBillAmount.setCellValueFactory(new PropertyValueFactory<>("grand"));
        dateSearch.setValue(LocalDate.now());
        billList.addAll(purchaseService.getByDate(dateSearch.getValue()));
        tableBill.setItems(billList);

        btnSearch.setOnAction(e->searchParty());
        setTextProperties();
        btnAdd.setOnAction(e->add());
        btnUpdate.setOnAction(e->update());
        btnRemove.setOnAction(e->remove());
        btnSave.setOnAction(e->save());
        btnClearBill.setOnAction(e->clearBill());
        btnUpdateBill.setOnAction(e->updateBIll());

        txtSearchParty.setOnAction(e->{
            if(!txtSearchParty.getText().isEmpty()){
                    billList.clear();
                    billList.addAll(purchaseService.getByPartyName(txtSearchParty.getText()));
            }
        });
        txtSearchInvoice.setOnAction(e->{
            if(purchaseService.getById(Long.parseLong(txtSearchInvoice.getText()))!=null) {
                billList.clear();
                billList.add(purchaseService.getById(Long.parseLong(txtSearchInvoice.getText())));
            }
            else{
                alert.showError("No Invoice found with id "+txtSearchInvoice.getText());
            }
        });
        txtSearchInvoiceno.setOnAction(e->{
            if(!txtSearchInvoiceno.getText().isEmpty())
            {
                billList.clear();
                billList.addAll(purchaseService.getByInvoiceNo(txtSearchInvoiceno.getText()));
            }
        });
        btnSearchAll.setOnAction(e->{
            billList.clear();
            billList.addAll(purchaseService.getAllBill());
        });
        dateSearch.setOnAction(e->{
            if(dateSearch.getValue()!=null){
                billList.clear();
                billList.addAll(purchaseService.getByDate(dateSearch.getValue()));
            }
        });
        btnHome.setOnAction(e->stageManager.switchScene(FxmlView.HOME));

    }
    private void updateBIll() {
        PurchaseInvoice invoice = tableBill.getSelectionModel().getSelectedItem();
      if(invoice!=null){
            date.setValue(invoice.getDate());
            txtInvoiceNo.setText(invoice.getInvoiceno());
            txtPartyName.setText(invoice.getParty().getName());
            btnSearch.fire();

            trList.clear();
            trList.addAll(invoice.getTransaction());
        txtNetTotal.setText(String.valueOf(invoice.getNettotal()));
        txtDiscountTotal.setText(String.valueOf(invoice.getDiscount()));
        txtCGst.setText(String.valueOf(invoice.getCgst()));
        txtSGst.setText(String.valueOf(invoice.getSgst()));
        txtShiping.setText(String.valueOf(invoice.getShiping()));
        txtPackaging.setText(String.valueOf(invoice.getPackaging()));
        txtOther.setText(String.valueOf(invoice.getOther()));
        calculateGrandTotal();
        txtPaid.setText(String.valueOf(invoice.getPaid()));
        cmbBank.setValue(invoice.getBank().getName());
        id = invoice.getId();
      }
    }
    private void clearBill() {
        id=null;
        trList.clear();
        txtInvoiceNo.setText("");
        txtNetTotal.setText(""+0.0f);
        txtDiscountTotal.setText(""+0.0f);
        txtCGst.setText(""+0.0f);
        txtSGst.setText(""+0.0f);
        txtShiping.setText(""+0.0f);
        txtPackaging.setText(""+0.0f);
        txtOther.setText(""+0.0f);
        calculateGrandTotal();
        party = null;
        date.setValue(LocalDate.now());
        cmbBank.getSelectionModel().clearSelection();
        txtPaid.setText(""+0.0f);
        txtPartyName.setText("");
    }
    private void save() {
        if(!validateBill()) return;

        PurchaseInvoice oldInvoice = null;
        int stockFlag=1;

        PurchaseInvoice invoice = PurchaseInvoice.builder()
                .date(date.getValue())
                .bank(bankService.getByName(cmbBank.getValue()))
                .party(party)
                .invoiceno(txtInvoiceNo.getText())
                .grand(Double.parseDouble(txtGrandTotal.getText()))
                .nettotal(Double.parseDouble(txtNetTotal.getText()))
                .other(Double.parseDouble(txtOther.getText()))
                .packaging(Double.parseDouble(txtPackaging.getText()))
                .paid(Double.parseDouble(txtPaid.getText()))
                .sgst(Double.parseDouble(txtSGst.getText()))
                .cgst(Double.parseDouble(txtCGst.getText()))
                .discount(Double.parseDouble(txtDiscountTotal.getText()))
                .shiping(Double.parseDouble(txtShiping.getText()))
                .build();
        if(id!=null)
        {
            oldInvoice = purchaseService.getById(id);
            invoice.setId(id);
        }
        for(PurchaseTransaction tr:trList){
            if(oldInvoice!=null && stockService.getByItemId(itemService.getByCodeAndDescription(tr.getCode(),tr.getDescription()).getId()).getQuantity()<tr.getQuantity())
            {
                stockFlag=0;
            }
            tr.setId(null);
            tr.setInvoice(invoice);
            invoice.getTransaction().add(tr);
        }

        if(oldInvoice!=null && getStockFlag(oldInvoice)==0)
        {
            alert.showError("You can't edit this invoice! Item Already Sold");
            return;
        }

        int flag = purchaseService.savePurchaseInvoice(invoice);
        if(flag==1)
        {
            alert.showSuccess("Purchase Invoice Saved Success");
            addInStock(invoice.getTransaction());
            billList.clear();
            billList.addAll(purchaseService.getByDate(LocalDate.now()));
            clearBill();
        }
        else if(flag==2)
        {
            //reduce old stock
            reduceInStock(oldInvoice.getTransaction());
            addInStock(invoice.getTransaction());
            clearBill();
            billList.clear();
            billList.addAll(purchaseService.getByDate(LocalDate.now()));
            alert.showSuccess("Invoice Updated Success");
        }
    }
    private int getStockFlag(PurchaseInvoice oldInvoice) {

        for(PurchaseTransaction tr: oldInvoice.getTransaction()) {
            if (stockService.getByItemId(itemService.getByCodeAndDescription(tr.getCode(), tr.getDescription()).getId()).getQuantity() < tr.getQuantity()) {
                return 0;
            }
        }
        return 1;
    }
    private void addInStock(List<PurchaseTransaction> transaction) {
        for(PurchaseTransaction tr:transaction)
        {
            Item item =itemService.getByCodeAndDescription(tr.getCode(),tr.getDescription());
            if(item==null) {
                item = Item.builder()
                        .code(tr.getCode())
                        .hsn(tr.getHsn())
                        .description(tr.getDescription())
                        .unit(tr.getUnit())
                        .build();
                itemService.save(item);
            }
            ItemStock stock = ItemStock.builder()
                    .gst(tr.getGst())
                    .discount(tr.getDiscount())
                    .mrp(tr.getMrp())
                    .quantity(tr.getQuantity())
                    .rate(tr.getRate())
                    .item(item).build();
            stockService.save(stock);
        }
    }
    private void reduceInStock(List<PurchaseTransaction> transaction) {
        for(PurchaseTransaction tr:transaction)
        {
            Item item =itemService.getByCodeAndDescription(tr.getCode(),tr.getDescription());
            ItemStock stock = stockService.getByItemId(item.getId());
            stock.setQuantity(tr.getQuantity());
            stockService.reduceStock(stock);
        }
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
        txtSearchInvoice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtSearchInvoice.setText(oldValue);
            }
        });
        cmbBank.getItems().addAll(bankService.getAllBankNames());
       btnAdd.setOnAction(e->add());
       btnClear.setOnAction(e->clear());
    }
    private void add() {
        if(!validateItem()) return;
        calculateAmount();
        PurchaseTransaction tr = PurchaseTransaction.builder().
                code(txtCode.getText())
                .description(txtDescription.getText().trim())
                .amount(Float.parseFloat(txtAmount.getText()))
                .hsn(Long.parseLong(txtHsn.getText()))
                .discount(Float.parseFloat(txtDiscount.getText()))
                .gst(Float.parseFloat(txtGst.getText()))
                .unit(txtUnit.getText())
                .mrp(Float.parseFloat(txtMrp.getText()))
                .quantity(Float.parseFloat(txtQuantity.getText()))
                .rate(Float.parseFloat(txtRate.getText()))
                .build();

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
//            txtDescription.setText("");
//            txtHsn.setText("");
//            txtUnit.setText("");
//            txtMrp.setText("");
//            txtDiscount.setText("");
//            txtRate.setText("");
//            txtGst.setText("");
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
