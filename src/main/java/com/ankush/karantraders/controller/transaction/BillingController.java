package com.ankush.karantraders.controller.transaction;

import com.ankush.karantraders.data.entities.Customer;
import com.ankush.karantraders.data.entities.Item;
import com.ankush.karantraders.data.entities.ItemStock;
import com.ankush.karantraders.data.entities.Transaction;
import com.ankush.karantraders.data.service.*;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.type.FloatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class BillingController implements Initializable {
   @Autowired
   @Lazy
   private StageManager stageManager;
   @FXML private TextField txtCustomerName,txtDescription;


    @FXML private TableColumn<?, ?> ColGrand;
    @FXML private Button btnAdd;
    @FXML private Button btnAddCustomer;
    @FXML private Button btnClear;
    @FXML private MFXButton btnClearBill,btnHome,btnPrint;
    @FXML private Button btnSearch,btnRemove,btnUpdate;
    @FXML private MFXButton btnSave,btnShowAll,btnUpdateBill;
    @FXML private MFXCheckbox chkGst;
    @FXML private MFXLegacyComboBox<String> cmbBank;
    @FXML private MFXLegacyComboBox<String> cmbUnit;
    @FXML private TableView<Transaction> tableTr;
    @FXML private TableColumn<Transaction,String> colCode;
    @FXML private TableColumn<Transaction,Float> colAmount;
    @FXML private TableColumn<Transaction,String> colDescription;
    @FXML private TableColumn<Transaction,Float> colQuantity;
    @FXML private TableColumn<Transaction,Float> colRate;
    @FXML private TableColumn<Transaction,Long> colSr;
    @FXML private TableColumn<Transaction,String> colUnit;
    @FXML private TableColumn<Transaction,Float> colGst;

    @FXML private TableColumn<?, ?> colBill;
    @FXML private TableColumn<?, ?> colCustomerName;
    @FXML private TableColumn<?, ?> colDate;
    @FXML private TableColumn<?, ?> colPaid;
    @FXML private DatePicker date,dateSearch;
    @FXML private TableView<?> tableBill;

    @FXML private MFXTextField txtAmount,txtCode;
    @FXML private TextArea txtCustomerInfo;
    @FXML private MFXTextField txtDiscount,txtGrandTotal,txtGst,txtNetTotal,txtOther,txtPaid,txtQuantity,txtRate;
    @FXML private MFXTextField txtSearchBillNo,txtSearchCustomer,txtTotalGst,txtTransport;

    @Autowired private ItemStockService stockService;
    @Autowired private ItemStockService itemStockService;
    @Autowired private CustomerService customerService;
    @Autowired private UserService userService;
    @Autowired private BankService bankService;
    @Autowired private ItemService itemService;
    @Autowired private AlertNotification alert;

    private SuggestionProvider<String>customerNameProvider;

    private SuggestionProvider<String>itemNameProvider;
    private ObservableList<List>bankNameList = FXCollections.observableArrayList();
    private ObservableList<Transaction>trList = FXCollections.observableArrayList();
    private Customer customer;
    private Item item;
    private ItemStock stock;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setProperties();

    }
    private void setProperties()
    {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colSr.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colGst.setCellValueFactory(new PropertyValueFactory<>("gst"));
     tableTr.setItems(trList);

     cmbBank.getItems().addAll(bankService.getAllBankNames());
     customerNameProvider = SuggestionProvider.create(customerService.getAllCustomerNames());
     TextFields.bindAutoCompletion(txtCustomerName,customerNameProvider);
     TextField text = txtCustomerName;
     TextFields.bindAutoCompletion(text,customerNameProvider);
     btnSearch.setOnAction(e->searchCustomer());

     cmbUnit.getItems().addAll(itemService.getUnitNames());
     itemNameProvider = SuggestionProvider.create(itemService.getAllItemNames());
     new AutoCompletionTextFieldBinding<>(txtDescription,itemNameProvider);
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
         if(!txtCode.getText().isEmpty())
             txtDescription.requestFocus();
     });

     txtDescription.setOnAction(e->{
         if(!txtDescription.getText().isEmpty())
         {
             setItem(itemService.getByDescription(txtDescription.getText()));
             txtQuantity.requestFocus();
         }
     });
     txtQuantity.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
             if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                 txtQuantity.setText(oldValue);
             }
             else
             {

             }
         }
     });
     txtQuantity.setOnAction(e->{
         calculateAmount();
         txtGst.requestFocus();
     });
     txtGst.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
             if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                 txtGst.setText(oldValue);
             }
         }
     });
     txtGst.setOnAction(e->{
        if(!txtGst.getText().isEmpty())
        {
            calculateAmount();
            txtAmount.requestFocus();
        }
    });
     txtAmount.setOnAction(e->{
         btnAdd.requestFocus();
     });
     txtTransport.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
             if (!newValue.matches("\\d{0,100}([\\.]\\d{0,4})?")) {
                 txtTransport.setText(oldValue);
             }
         }
     });
     txtTransport.setOnAction(e->{
         if(!txtTransport.getText().isEmpty()) {
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
     txtOther.setOnAction(e->{
         if(!txtOther.getText().isEmpty()){
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
     txtDiscount.setOnAction(e->{
         if(!txtDiscount.getText().isEmpty())
         {
             calculateGrandTotal();
             txtGrandTotal.requestFocus();
         }
     });
     btnAdd.setOnAction(e->add());
     btnClear.setOnAction(e->clear());
     btnUpdate.setOnAction(e->update());
     btnRemove.setOnAction(e->remove());

    }

    private void remove() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)return;
        Transaction tr = tableTr.getSelectionModel().getSelectedItem();
        trList.remove(tableTr.getSelectionModel().getSelectedIndex());
        int i=0;
        for(Transaction t:trList)
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

    private void update() {
        if(tableTr.getSelectionModel().getSelectedItem()==null)
            return;
        Transaction tr = tableTr.getSelectionModel().getSelectedItem();
        txtDescription.setText(tr.getDescription());
        setItem(itemService.getByDescription(tr.getDescription()));
        txtRate.setText(String.valueOf(tr.getRate()));
        txtQuantity.requestFocus();


    }
    private void add() {
        if(!validate()) return;
        Transaction tr = Transaction.builder()
                .code(txtCode.getText())
                .description(txtDescription.getText())
                .quantity(Float.valueOf(txtQuantity.getText()))
                .rate(Float.parseFloat(txtRate.getText()))
                .gst(Float.parseFloat(txtGst.getText()))
                .amount(Float.parseFloat(txtAmount.getText()))
                .unit(cmbUnit.getValue())
                .itemStock(stockService.getByItemId(item.getId()))
                .build();
        addInTrList(tr);
        clear();
        txtCode.requestFocus();
        System.out.println(tr);

    }
    private void addInTrList(Transaction tr) {
        int index=-1;
        tr.setId((long) (trList.size()+1));
        for(Transaction t:trList)
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
    private boolean validate() {
        if(txtAmount.getText().isEmpty() || txtAmount.getText().equalsIgnoreCase(""+0.0f))
        {
            alert.showError("Select Item again!");
            return false;
        }

        return true;
    }
    void calculateAmount(){
        if(txtRate.getText().isEmpty()) txtRate.setText(""+0.0f);
        if(txtQuantity.getText().isEmpty()) txtQuantity.setText(""+0.0f);
        if(txtGst.getText().isEmpty()) txtGst.setText(""+0.0f);

        txtAmount.setText(
                String.valueOf(
                        Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtRate.getText())+(
                                Float.parseFloat(txtQuantity.getText())*Float.parseFloat(txtRate.getText())*Float.parseFloat(txtGst.getText())/100)
                                )
        );

    }
    private void searchCustomer() {
     if(!txtCustomerName.getText().isEmpty()){
       customer = customerService.getByCustomerName(txtCustomerName.getText().trim());
       if(customer!=null)
       {
        btnSearch.setStyle("-fx-background-color: green;-fx-text-fill:white ");
        setCustomer(customer);

       }else {
        btnSearch.setStyle("-fx-background-color: red; -fx-text-fill:white ");
        setCustomer(customer);
       }
     }
 }
    private void findByCode(String code) {
        item  = itemService.getByCode(code);
    }
    private void setCustomer(Customer customer) {
     if(null!=customer)
     {
       txtCustomerInfo.setText("" +
               "Name:"+customer.getCustomername()+"\nAddress: "+customer.getAddressline()+
                       ","+customer.getVillage()+", "+customer.getTaluka()+", "+customer.getDistrict()+"\nContact: "+
               customer.getContact()+", "+customer.getMobile());
     }
     else
      txtCustomerInfo.setText("");
 }
    private void setItem(Item item) {
     if(null!=item)
     {
         stock = stockService.getByItemId(item.getId());
         if(null!=stock) {
             txtCode.setText(item.getCode());
             txtDescription.setText(item.getDescription());
             cmbUnit.setValue(item.getUnit());
             txtRate.setText(String.valueOf(stock.getRate()));
             txtGst.setText(String.valueOf(stock.getGst()));
         }
         else{
             txtCode.setText("");
             txtDescription.setText("");
             cmbUnit.setValue(null);
             txtRate.setText("");
             txtGst.setText("");
         }
     }else {
         txtCode.setText("");
         txtDescription.setText("");
         cmbUnit.setValue(null);
         txtRate.setText("");
         txtGst.setText("");
     }
 }
}
