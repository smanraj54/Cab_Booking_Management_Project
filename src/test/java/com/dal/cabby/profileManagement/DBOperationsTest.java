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
        assertTrue(validation, "Username Validation failed from db");
    }

    @Test
    void dbContainsEmail() {

    }

    @Test
    void getEmailValue() {
    }

    @Test
    void entryRegistration() {
    }

    @Test
    void validateLoginUser() {
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