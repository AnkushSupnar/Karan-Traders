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
            return "/fxml/home/HomePage.fxml";
        }
    };
    abstract String getTitle();
    public abstract String getFxmlFile();
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
