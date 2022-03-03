package com.ankush.karantraders.controller.create;

import com.ankush.karantraders.view.StageManager;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
