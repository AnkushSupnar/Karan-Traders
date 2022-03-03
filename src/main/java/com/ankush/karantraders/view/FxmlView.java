package com.ankush.karantraders.view;

import java.util.ResourceBundle;

public enum FxmlView {
    LOGIN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Login.fxml";
        }
    },
    ADDUSER {
        @Override
        String getTitle() {
            return "Add User"; 
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/AddUser.fxml";
        }
    },
    HOME {
        @Override
        String getTitle() {
            return "Home Page"; 
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/home/Main.fxml";
        }
    },
    ADDITEM {
        @Override
        String getTitle() {
            return "Home Page"; 
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/create/AddItem.fxml";
        }
    },
    PURCHASE {
        @Override
        String getTitle() {
            return "Home Page"; 
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/PurchaseInvoice.fxml";
        }
    },
    BILLING {
        @Override
        String getTitle() {
            return "Billing Page";
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/Billing.fxml";
        }
    },
    CHALLAN {
        @Override
        String getTitle() {
            return "Delivery Challan";
            //getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/transaction/DelivaryChallan.fxml";
        }
    }
    ;
    abstract String getTitle();
    public abstract String getFxmlFile();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
