package com.dal.cabby;

import com.dal.cabby.dbHelper.DBHelper;
import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.prelogin.PreLoginPage;

import java.sql.SQLException;

// Main starting class
public class Application {
    public static void main(String[] args) {
        try{
            Inputs inputs = new InputFromUser();
            PreLoginPage preLoginPage = new PreLoginPage(inputs);
            preLoginPage.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBHelper dbHelper = null;
            try {
                dbHelper = DBHelper.getInstance();
                dbHelper.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
