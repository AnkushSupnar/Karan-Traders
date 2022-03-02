package com.ankush.karantraders.controller.transaction;

import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Student Via Dialog");

    }
}
