package com.ankush.karantraders.controller.create;

import com.ankush.karantraders.data.entities.Customer;
import com.ankush.karantraders.data.entities.Site;
import com.ankush.karantraders.data.service.CustomerService;
import com.ankush.karantraders.data.service.SiteService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

@Component
public class CreateSiteDialogController implements Initializable {
    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private Button btnCheck;
    @FXML private DialogPane dialog;
    @FXML private TextField txtAddressLine,txtContact,txtCustomerNames,txtDistrict,txtSiteName;
    @FXML private TextField txtlternateContact,txtTaluka,txtVillage;
    private Button finish;

    @Autowired private CustomerService customerService;
    @Autowired private AlertNotification alert;
    @Autowired private SiteService siteService;
    private SuggestionProvider<String>customerNames;
    private Customer customer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customer  = null;
        finish =(Button) dialog.lookupButton(ButtonType.FINISH);
        btnCheck.setStyle("-fx-background-color:#5cb85c");
        customerNames = SuggestionProvider.create(customerService.getAllCustomerNames());
        TextFields.bindAutoCompletion(txtCustomerNames,customerNames);
        txtCustomerNames.setOnAction(e->{
            customer = customerService.getByCustomerName(txtCustomerNames.getText());
            setCustomer(customer);
        });
        btnCheck.setOnAction(e->add());
    }

    private void add() {
        if(!validate()) return;

        Site site = Site.builder()
                .addressline(txtAddressLine.getText())
                .contact(txtContact.getText())
                .district(txtDistrict.getText())
                .taluka(txtTaluka.getText())
                .village(txtVillage.getText())
                .sitename(txtSiteName.getText())
                .customer(customer)
                .build();
        int flag= siteService.saveSite(site);
        if(flag==1){
            alert.showSuccess("Site Saved Success");
            finish.fire();
        }
        System.out.println(site);
    }

    private boolean validate() {
        if(!txtCustomerNames.getText().isEmpty()){
            setCustomer(customerService.getByCustomerName(txtCustomerNames.getText()));
        }
        if(customer==null){
            alert.showError("Select Customer Name");
            txtCustomerNames.requestFocus();
            btnCheck.setStyle("-fx-background-color:#d9534f");
            return false;
        }

        if(txtSiteName.getText().isEmpty()){
            alert.showError("Enter Site Name");
            btnCheck.setStyle("-fx-background-color:#d9534f");
            txtSiteName.requestFocus();
            return false;
        }
        if(siteService.getByNameAndCustomerid(txtSiteName.getText(),customer.getId())==null){
            alert.showError("Site Name Already Exist with This Customer");
            txtSiteName.requestFocus();
            btnCheck.setStyle("-fx-background-color:#5cb85c");
            return false;
        }

        if(txtAddressLine.getText().isEmpty())txtAddressLine.setText("-");
        if(txtVillage.getText().isEmpty())txtVillage.setText("");
        if(txtTaluka.getText().isEmpty())txtTaluka.setText("-");
        if(txtDistrict.getText().isEmpty())txtDistrict.setText("-");
        if(txtContact.getText().isEmpty())txtContact.setText("-");
        if(txtlternateContact.getText().isEmpty())txtlternateContact.setText("");
        btnCheck.setStyle("-fx-background-color:#5cb85c");
        return true;
    }

    void setCustomer(Customer customer)
    {
        this.customer = customer;
        if(customer!=null) {
            txtContact.setText(customer.getContact());
            txtAddressLine.setText(customer.getAddressline());
            txtDistrict.setText(customer.getDistrict());
            txtlternateContact.setText(customer.getMobile());
            txtVillage.setText(customer.getVillage());
            txtTaluka.setText(customer.getTaluka());
        }
        else{
            txtContact.setText("");
            txtAddressLine.setText("");
            txtDistrict.setText("");
            txtlternateContact.setText("");
            txtVillage.setText("");
            txtTaluka.setText("");
        }
    }
}
