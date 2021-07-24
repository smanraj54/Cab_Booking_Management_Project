package com.dal.cabby;

import com.dal.cabby.io.InputFromUser;
import com.dal.cabby.io.Inputs;
import com.dal.cabby.prelogin.PreLoginPage;

// Main starting class
public class Application {
    public static void main(String[] args) {
        try{
            Inputs inputs = new InputFromUser();
            PreLoginPage preLoginPage = new PreLoginPage(inputs);
            preLoginPage.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
