package com.ankush.karantraders.controller.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.entities.User;
import com.ankush.karantraders.data.service.UserService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

@Component
public class AddUserController implements Initializable{
    @Autowired @Lazy
    private StageManager stageManager;
    @FXML private Button btnCancel;
    @FXML private Button btnCreate;
    @FXML private AnchorPane mainFrame;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtUserName;
    @Autowired private AlertNotification alert;
    @Autowired private UserService userServicee;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
        btnCreate.setOnAction(e->create());
        
    }
    private void create() {
        if(txtUserName.getText().isEmpty())
        {
            alert.showError("Enter User Name");
            txtUserName.requestFocus();
            return;
        }
        if(txtPassword.getText().isEmpty())
        {
            alert.showError("Enter Password");
            txtPassword.requestFocus();
            return;
        }
        if(txtConfirmPassword.getText().isEmpty())
        {
            alert.showError("Enter Confirm Password");
            txtConfirmPassword.requestFocus();
            return;
        }
        if(!txtPassword.getText().equals(txtConfirmPassword.getText()))
        {
            alert.showError("Both password not match");
            txtPassword.requestFocus();
            return;
        }
        if(userServicee.getByUsername(txtUserName.getText())!=null)
        {
            alert.showError("User Name Already Exist");
            txtUserName.requestFocus();
            return;
        }
        User user = User.builder()
        .password(txtPassword.getText())
        .username(txtUserName.getText())
        .build();
        if(userServicee.saveUser(user)==1)
        {
            alert.showSuccess("User Saved Success");
            
        }

    }



}
