package com.ankush.karantraders.controller.transaction;

import com.ankush.karantraders.data.entities.Customer;
import com.ankush.karantraders.data.service.CustomerService;
import com.ankush.karantraders.view.AlertNotification;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddCustomerDialog  implements Initializable {
    @FXML private DialogPane dialog;
    @FXML private TextField txtAddressLine;
    @FXML private TextField txtContact;
    @FXML private TextField txtCustomerNames;
    @FXML private TextField txtDistrict;
    @FXML private TextField txtTaluka;
    @FXML private TextField txtVillage;
    @FXML private TextField txtlternateContact;
    @FXML private Button btnCheck;
    Button finish;

    @Autowired private AlertNotification alert;
    @Autowired private CustomerService customerService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Student Via Dialog");
        System.out.println(dialog.getButtonTypes());
        //ButtonType finish = dialog.getButtonTypes().get(0);
        btnCheck.setOnAction(e->add());
        finish = (Button) dialog.lookupButton(ButtonType.FINISH);
        finish.setOnAction(e->{
        });
    }

    private void add() {
        if(!validate())return;
        Customer customer = Customer.builder()
                .addressline(txtAddressLine.getText())
                .customername(txtCustomerNames.getText())
                .contact(txtContact.getText())
                .district(txtDistrict.getText())
                .mobile(txtlternateContact.getText())
                .village(txtVillage.getText())
                .taluka(txtTaluka.getText())
                .build();
        if(customerService.saveCustomer(customer)==1)
        {
            alert.showSuccess("Customer Saved Success");
            finish.fire();
        }
    }

    private boolean validate() {
        if(txtCustomerNames.getText().isEmpty())
        {
            alert.showError("Enter Customer Names");
            txtCustomerNames.requestFocus();
            btnCheck.setStyle("-fx-background-color:#d9534f");
            return false;
        }
        if(txtAddressLine.getText().isEmpty()){
            txtAddressLine.setText("-");
        }
        if(txtVillage.getText().isEmpty()){ txtVillage.setText("-");}
        if(txtTaluka.getText().isEmpty()){txtTaluka.setText("-");}
        if(txtDistrict.getText().isEmpty()){txtDistrict.setText("-");}
        if(txtContact.getText().isEmpty()){
            alert.showError("Enter Customer Mobile no");
            txtContact.requestFocus();
            btnCheck.setStyle("-fx-background-color:#d9534f");
            return false;
        }
        if(txtlternateContact.getText().isEmpty()){txtlternateContact.setText("-");}
        btnCheck.setStyle("-fx-background-color:#5cb85c");
        return true;
    }
}
