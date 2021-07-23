package com.dal.cabby.profileManagement;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    private String name = "Manraj Singh";
    private String email = "msingh7_be15@thapar.edu";
    private String userName = "smanraj54";
    private String password = "manu123";
    private UserType userType = UserType.CUSTOMER;
    private String queryEmail = "Select * from %s where email = '%s'";

    @Test
    void attemptLogin() throws InterruptedException {
        PredefinedInputs predefinedInputs1 = new PredefinedInputs();
        predefinedInputs1.add(userName).add(password);
        ILogin ilogin = new Login(predefinedInputs1);
        boolean loggedIn = false;
        loggedIn = ilogin.attemptLogin(userType);

        if(!loggedIn){
            PredefinedInputs predefinedInputs2 = new PredefinedInputs();
            predefinedInputs2.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs2);
            iregistration.registerUser(userType);
            loggedIn = ilogin.attemptLogin(userType);
        }

        assertTrue(loggedIn, "Login failed with correct Credentials");

    }
}