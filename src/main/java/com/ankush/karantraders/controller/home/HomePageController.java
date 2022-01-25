package com.ankush.karantraders.controller.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.config.SpringFXMLLoader;
import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

@Component
public class HomePageController implements Initializable{

    @Autowired @Lazy
    private StageManager stageManager;
    @Autowired
    private SpringFXMLLoader fxmlLoader;
    @FXML private Button btnCustomers;
    @FXML private Button btnMenus;
    @FXML private Button btnOrders;
    @FXML private Button btnOverview;
    @FXML private Button btnPackages;
    @FXML private Button btnSettings;
    @FXML private Button btnSignout;
    @FXML private Label lblLogin;
    @FXML private BorderPane rootPane;

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

}