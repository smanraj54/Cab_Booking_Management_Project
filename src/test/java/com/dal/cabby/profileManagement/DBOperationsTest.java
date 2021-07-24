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
    private String tableName = "customer";
    private String columnId = "cust_id";

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
            validation = idbOperations.dbContainsEmail(email, userType);
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

        assertTrue(validation, "user validation failed with correct credentials");

    }

    @Test
    void fetchEmailForAuthenticationTest() {

        IDBOperations idbOperations = new DBOperations(userType);
        String emailForAuthentication = idbOperations.fetchEmailForAuthentication(userName, userType);
        if(!emailForAuthentication.equals(email)){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            emailForAuthentication = idbOperations.fetchEmailForAuthentication(userName, userType);
        }

        assertTrue(emailForAuthentication.equals(email), "email extraction using username failed from db");
    }

    @Test
    void updateEmailPasswordTest() {

        IDBOperations idbOperations = new DBOperations(userType);
        idbOperations.updateEmailPassword(email, password, userType);
        boolean validation = idbOperations.validateLoginUser(userName, password, userType);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.validateLoginUser(userName, password, userType);
        }

        assertTrue(validation, "password update failed");
    }

    @Test
    void getTableName() {
        IDBOperations idbOperations = new DBOperations(userType);
        String table = idbOperations.getTableName(userType);

        if(!table.equals(tableName)){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            table = idbOperations.getTableName(userType);
        }

        assertTrue(table.equals(tableName), "table name is wrong while fetching from db");
    }

    @Test
    void getIDColumnName() {

        IDBOperations idbOperations = new DBOperations(userType);
        String columnName = idbOperations.getIDColumnName(userType);

        if(!columnName.equals(columnId)){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            columnName = idbOperations.getIDColumnName(userType);
        }

        assertTrue(columnName.equals(columnId), "column id is wrong while fetching from db");
    }
}