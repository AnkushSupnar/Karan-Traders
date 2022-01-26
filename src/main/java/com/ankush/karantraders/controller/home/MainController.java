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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

@Component
public class MainController implements Initializable{

    @Autowired @Lazy
    private StageManager stageManager;
    @Autowired
    private SpringFXMLLoader fxmlLoader;
    @FXML private Button btnAddCustomer;
    @FXML private Button btnAddItem;
    @FXML private Button btnDaillyBilling;
    @FXML private Button btnDashboard;
    @FXML private Button btnDeliveryChallan;
    @FXML private Button btnEditCustomer;
    @FXML private Button btnEditItem;
    @FXML private Button btnPayinvoice;
    @FXML private Button btnPurchaseinvoice;
    @FXML private Button btnViewBill;
    @FXML private Button btnViewCUstomer;
    @FXML private Button btnViewChallan;
    @FXML private Button btnViewItem;
    @FXML private Button btnViewinvoice;
    @FXML private Label lblLogin;
    @FXML private BorderPane rootPane;
    @FXML private AnchorPane centerPane;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnAddItem.setOnAction(e->{
            centerPane.getChildren().addAll(getNode("/fxml/create/AddItem.fxml"));
        });
    }
    private Node getNode(String filePath)
    {
        //Node node = viewUtil.getPage("masterreport/TodayDashboard");
        Node node = fxmlLoader.getPage(filePath);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        return node;
    }
}
