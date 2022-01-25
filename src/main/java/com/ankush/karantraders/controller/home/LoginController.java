package com.ankush.karantraders.controller.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.service.UserService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.FxmlView;
import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

@Component
public class LoginController implements Initializable {

    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private Button btnCancel;
    @FXML private Button btnLogin;
    @FXML private ComboBox<String> cmbUserNames;
    @FXML private AnchorPane mainPane;
    @FXML private PasswordField txtPassword;
    @FXML private Hyperlink linkAddUser;
    @Autowired private AlertNotification alert;
    @Autowired private UserService userService;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       if(userService.getAllUserNames().size()==0)
       {
           linkAddUser.setVisible(true);
       }
       linkAddUser.setOnAction(e->stageManager.switchScene(FxmlView.ADDUSER));
       cmbUserNames.getItems().addAll(userService.getAllUserNames());
       btnLogin.setOnAction(e->login());
       btnCancel.setOnAction(e->System.exit(0));
        
    }
    private void login() {
        if(cmbUserNames.getValue()==null)
        {
            alert.showError("Select User Name");
            cmbUserNames.requestFocus();
            return;
        }
        if(txtPassword.getText().isEmpty())
        {
            txtPassword.requestFocus();
            alert.showError("Enter Your Password");
            return;
        }
        if(userService.getByUsername(cmbUserNames.getValue()).getPassword().equals(txtPassword.getText()))
        {
            alert.showSuccess("Login Success");
            stageManager.switchScene(FxmlView.HOME);
        }
        else
        alert.showError("Enter Correct Password");
    }
    
}
