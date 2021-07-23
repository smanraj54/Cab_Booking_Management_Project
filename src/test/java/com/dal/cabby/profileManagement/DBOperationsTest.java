package com.dal.cabby.profileManagement;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DBOperationsTest {

    private String name = "Manraj Singh";
    private String email = "msingh7_be15@thapar.edu";
    private String userName = "smanraj54";
    private String password = "manu123";
    private UserType userType = UserType.CUSTOMER;
    private String queryEmail = "Select * from %s where email = '%s'";

    @Test
    void dbUserNameValidationTest() {
        IDBOperations idbOperations = new DBOperations(userType);
        boolean validation = idbOperations.dbUserNameValidation(userName);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.dbUserNameValidation(userName);
        }
        assertTrue(validation, "Username Validation failed from db");

    }

    @Test
    void dbContainsUserNameTest() {
        IDBOperations idbOperations = new DBOperations(userType);
        boolean validation = idbOperations.dbContainsUserName(userName, userType);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.dbContainsUserName(userName, userType);
        }
        assertTrue(validation, "db check for username failed");
    }

    @Test
    void dbContainsEmailTest() {

        IDBOperations idbOperations = new DBOperations(userType);
        boolean validation = idbOperations.dbContainsEmail(email, userType);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.dbContainsEmail(userName, userType);
        }
        assertTrue(validation, "db check for email failed");
    }

    @Test
    void getEmailValueTest() {

        IDBOperations idbOperations = new DBOperations(userType);
        String emailValue = idbOperations.getEmailValue(email, "email", userType, queryEmail);
        if(!emailValue.equals(email)){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            emailValue =idbOperations.getEmailValue(email, "email", userType, queryEmail);
        }

        assertTrue(email.equals(emailValue), "email extraction from db failed");
    }

    @Test
    void validateLoginUserTest() {

        IDBOperations idbOperations = new DBOperations(userType);
        boolean validation = idbOperations.validateLoginUser(userName, password, userType);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.validateLoginUser(userName, password, userType);
        }

        assertTrue(validation, "email extraction from db failed");

    }

    @Test
    void fetchEmailForAuthentication() {
    }

    @Test
    void updateEmailPassword() {
    }

    @Test
    void getTableName() {
    }

    @Test
    void getIDColumnName() {
    }
}