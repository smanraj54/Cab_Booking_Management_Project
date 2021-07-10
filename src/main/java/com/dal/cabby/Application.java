package com.dal.cabby;

import com.dal.cabby.profileManagement.SendEmail;

// Main starting class
public class Application {
    public static void main(String[] args) {
//        PreLoginPage preLoginPage = new PreLoginPage();
//        preLoginPage.start();

//        Login login = new Login();
//        login.attemptLogin();
        try{
            SendEmail.sendEmail("software5408group15@gmail.com",
                    "Test email",
                    "<h2>Java Mail Example</h2><p>hi there!</p>");
        }catch (Exception ee){
            System.out.println(ee);
        }
    }
}
