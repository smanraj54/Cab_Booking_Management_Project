package com.dal.cabby.profileManagement;

import com.dal.cabby.io.PredefinedInputs;
import com.dal.cabby.pojo.UserType;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ForgotPasswordTest {

    private String name = "PasswordUpdate Test";
    private String email = "passwordupdate@dal.ca";
    private String userName = "passwordUpdate";
    private String password = "password123";
    private UserType userType = UserType.CUSTOMER;
    @Test

    void passwordUpdateProcess() {

        PredefinedInputs predefinedInputs1 = new PredefinedInputs();
        predefinedInputs1.add(userName).add("\n");

        ForgotPassword forgotPassword = new ForgotPassword(predefinedInputs1);
        predefinedInputs1.add(forgotPassword.getTempPass()).add("\n").add(password).add(password);

        IDBOperations idbOperations = new DBOperations(userType);
        String emailVal = idbOperations.fetchEmailForAuthentication(userName, userType);
        boolean validation = false;
        if(emailVal != null) {
            try {
                validation = forgotPassword.passwordUpdateProcess(userType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        if(emailVal == null){

            PredefinedInputs predefinedInputs2 = new PredefinedInputs();
            predefinedInputs2.add(name).add(email).add(userName).add(password).add(password);
            IRegistration iregistration = new Registration(predefinedInputs2);
            iregistration.registerUser(userType);
            predefinedInputs1.add(userName).add("\n").add(forgotPassword.getTempPass()).add("\n").add(password).add(password);

            try {
                validation = forgotPassword.passwordUpdateProcess(userType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        assertTrue(validation, "Username Validation failed from db");

    }
}