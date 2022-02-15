package com.ankush.karantraders.controller.create;

import java.net.URL;
import java.util.ResourceBundle;

import com.ankush.karantraders.data.entities.Item;
import com.ankush.karantraders.data.service.ItemService;
import com.ankush.karantraders.view.AlertNotification;
import com.ankush.karantraders.view.StageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

@Component
public class AddItemController implements Initializable {

    @Autowired
    @Lazy
    private StageManager stageManager;
    @FXML
    private Button btnAdd, btnClear, btnHome, btnUpdate;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField txtCode,txtDescription,txtHsn,txtUnit, 
    txtSearchCode,txtSearchHsn,txtSerachDescription;
    @FXML
    private TableView<Item> table;
    @FXML
    private TableColumn<Item, Long> colCode;
    @FXML
    private TableColumn<Item, String> colDescription;
    @FXML
    private TableColumn<Item, Long> colHsn;
    @FXML
    private TableColumn<Item, Long> colSrNo;
    @FXML
    private TableColumn<Item, String> colUnit;
    
    @FXML private Button btnShowAll;
    

    @Autowired
    private ItemService itemService;
    @Autowired
    private AlertNotification alert;
    private SuggestionProvider<String> unitProvider;
    private ObservableList<Item> list = FXCollections.observableArrayList();
    Integer id;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colHsn.setCellValueFactory(new PropertyValueFactory<>("hsn"));
        colSrNo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        table.setItems(list);

        id = null;
        unitProvider = SuggestionProvider.create(itemService.getUiList());
        new AutoCompletionTextFieldBinding<>(txtUnit, unitProvider);
        txtCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtCode.setText(oldValue);
            }
        });
        txtHsn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtHsn.setText(oldValue);
            }
        });
        txtSearchCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtSearchCode.setText(oldValue);
            }
        });
        txtSearchHsn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,100}([\\.]\\d{0,6})?"))
                    txtSearchHsn.setText(oldValue);
            }
        });
        txtSearchCode.setOnAction(e->{
            list.clear();
            list.add(itemService.getByCode(txtSearchCode.getText()));
        });
        txtSearchHsn.setOnAction(e->{
            list.clear();
            list.addAll(itemService.getByHsn(Long.parseLong(txtSearchHsn.getText())));
        });
        txtSerachDescription.setOnAction(e->{
            list.clear();
            list.add(itemService.getByDescription(txtSerachDescription.getText()));
        });
        btnAdd.setOnAction(e -> add());
        btnClear.setOnAction(e->clear());
        btnShowAll.setOnAction(e->{
            list.clear();
            list.addAll(itemService.getAllItems());
        });
        btnUpdate.setOnAction(e->update());
        btnHome.setOnAction(e->mainPane.setVisible(false));

        
    }
    private void update() {
        if(table.getSelectionModel().getSelectedItem()==null) return;
        Item item = table.getSelectionModel().getSelectedItem();
        id = item.getId();
        txtCode.setText(String.valueOf(item.getCode()));
        txtHsn.setText(String.valueOf(item.getHsn()));
        txtDescription.setText(item.getDescription());
        txtUnit.setText(item.getUnit());
    }
    private void add() {
        if (!validate())
            return;
        Item item = Item.builder()
                .code(txtCode.getText())
                .description(txtDescription.getText().trim())
                .hsn(Long.parseLong(txtHsn.getText()))
                .unit(txtUnit.getText())
                .build();
                if(id!=null) 
                item.setId(id);
        int flag = itemService.save(item);
        if (flag == 1) {
            alert.showSuccess("Item Saved Success");
            addInList(item);
            clear();

        } else if (flag == 2) {
            alert.showSuccess("Item Update Success");
            addInList(item);
            clear();
        }
        else{
            alert.showError("Enrror in adding Item");
        }
    }
    private void addInList(Item item) {
        int index=-1;
        for(Item i:list)
        {
            if(i.getId().equals(item.getId()))
            {
                index=list.indexOf(i);
                break;
            }
        }
        if(index==-1)
        {
            item.setId(list.size()+1);
            list.add(item);
            table.refresh();
        }
        else{
            list.remove(index);
            list.add(index,item);
            table.refresh();
        }
    }


    private void clear() {
        txtCode.setText("");
        txtDescription.setText("");
        txtHsn.setText("");
        txtUnit.setText("");
        id=null;
    }


    private boolean validate() {
        if (txtCode.getText().isEmpty()) {
            alert.showError("Enter Item Code");
            txtCode.requestFocus();
            return false;
        }
        if (txtHsn.getText().isEmpty()) {
            txtHsn.requestFocus();
            alert.showError("Enter Item Hsn Code");
            return false;
        }
        if (txtDescription.getText().isEmpty()) {
            alert.showError("Enter Item Description");
            txtDescription.requestFocus();
            return false;
        }
        if (txtUnit.getText().isEmpty()) {
            alert.showError("Enter Item Unit");
            txtUnit.requestFocus();
            return false;
        }
        if(id==null && itemService.getByDescription(txtDescription.getText())!=null)
        {
            alert.showError("This Item Description Is Already Exist");
            txtDescription.requestFocus();
            return false;
        }
        if(id==null && itemService.getByCode(txtCode.getText())!=null)
        {
            alert.showError("This Item Code Is Already Exist");
            txtCode.requestFocus();
            return false;
        }
        return true;
    }

}
