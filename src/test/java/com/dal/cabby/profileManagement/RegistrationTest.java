package com.dal.cabby.profileManagement;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationTest {

    private String name = "Manraj Singh";
    private String email = "msingh7_be15@thapar.edu";
    private String userName = "smanraj54";
    private String password = "manu123";
    private UserType userType = UserType.CUSTOMER;
    private String queryEmail = "Select * from %s where email = '%s'";
    private String tableName = "customer";
    private String columnId = "cust_id";

    @Test
    void registerUser() {

        IDBOperations idbOperations = new DBOperations(userType);
        boolean validation = idbOperations.dbUserNameValidation(userName);
        if(!validation){
            PredefinedInputs predefinedInputs = new PredefinedInputs();
            predefinedInputs.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs);
            iregistration.registerUser(userType);
            validation = idbOperations.dbUserNameValidation(userName);
        }
        assertTrue(validation, "Registration of new user failed");
    }

    @Test
    void getPassword() {

        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add(password).add(password);

        Registration registration = new Registration(predefinedInputs);

        assertTrue(registration.getPassword(new ValidateInput()).equals(password), "Fetching password failed from the registration class getPassword method");
    }
}