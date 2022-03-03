package com.ankush.karantraders.controller.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.config.SpringFXMLLoader;
import com.ankush.karantraders.view.FxmlView;
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
    @FXML private Button btnAddCustomer,btnAddItem,btnDaillyBilling,btnDashboard;
    @FXML private Button btnDeliveryChallan,btnEditCustomer,btnEditItem,btnPayinvoice,btnPurchaseinvoice,
    btnViewBill,btnViewCUstomer,btnViewChallan,btnViewItem,btnViewinvoice,btnAddParty,btnEditParty,
    btnViewParty,btnAddBank,btnEditBank,btnViewBank;
    @FXML private Label lblLogin;
    @FXML private BorderPane rootPane;
    @FXML private AnchorPane centerPane;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnAddItem.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/create/AddItem.fxml"));
        });
        btnAddCustomer.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/create/AddCustomer.fxml"));
        });
        btnAddParty.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/create/PurchaseParty.fxml"));
        });
        btnAddBank.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/create/AddBank.fxml"));
        });
        btnDaillyBilling.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/transaction/Billing.fxml"));
        });
        btnPurchaseinvoice.setOnAction(e->{
            stageManager.switchScene(FxmlView.PURCHASE);
        });
        btnDeliveryChallan.setOnAction(e->{
            centerPane.getChildren().clear();
            centerPane.getChildren().addAll(getNode("/fxml/transaction/DelivaryChallan.fxml"));
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
