package com.dal.cabby;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.prelogin.PreLoginPage;
import com.dal.cabby.profileManagement.Login;
import com.dal.cabby.profileManagement.Registration;

// Main starting class
public class Application {
    public static void main(String[] args) {
        try{
            Inputs inputs = new InputFromUser();
            DBHelper dbHelper = new DBHelper();
            dbHelper.initialize();
            PreLoginPage preLoginPage = new PreLoginPage(inputs, dbHelper);
            preLoginPage.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
