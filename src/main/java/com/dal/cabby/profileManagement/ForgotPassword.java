package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import javax.mail.MessagingException;

import static java.lang.Thread.sleep;

public class ForgotPassword implements IForgotPassword {

    private final Inputs inputs;
    private final int tempPass;

    /*
        In the constructor we are setting the input interface and generating
        the temporary password which can be used in the forgot password function
     */
    public ForgotPassword(Inputs inputs) {

        this.inputs = inputs;
        tempPass = generateTemporaryPassword(100000) + 1;

    }

    public int getTempPass() {
        return tempPass;
    }

    /*
        This is the method used to update the password of a user in database
        after proper validation and authentication
     */
    @Override
    public boolean passwordUpdateProcess(UserType userType)
            throws InterruptedException, MessagingException {

        IDBOperations IDBOperations = new DBOperations(userType);
        boolean authenticationPass = false;

        String email = getEmailfromUser(IDBOperations, userType);

        if (email != null) {
            authenticationPass = true;
        }
        if (!authenticationPass) {
            return false;
        }

        if (!sendTemporaryPasswordViaEmail(email, tempPass)) {
            return false;
        }

        if (!checkTemporaryPass(tempPass)) {
            return false;
        }

        String newPass = getNewPassword();

        if (newPass == null) {
            return false;
        }
        IDBOperations.updateEmailPassword(email, newPass, userType);
        System.out.println("Password updated successfully");

        return true;
    }

    /*
        This method is used to validate the temporary password which is sent
        to the user on his email. If the password is incorrect three times
        then the user is not allowed to change the password
     */
    private boolean validateTempPass(int tempPass) {

        System.out.print("\nEnter temp password sent to your registered email : ");

        int enteredPass = -1;
        try {
            enteredPass = inputs.getIntegerInput();
        } catch (Exception e) {
            System.err.print("Authentication Fail !!!!");
            return false;
        } finally {
        }
        if (tempPass == enteredPass) {
            System.out.println("\nAuthentication Passed");
        } else {
            System.err.println("\nAuthentication Failed !!!!");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return tempPass == enteredPass;
    }

    /*
        This method is used to get entry of new password from the user
        and validate it with confirm password field.
     */
    private String getNewPassword() {

        //System.out.print("\nEnter new Password : ");
        IRegistration IRegistration = new Registration(inputs);
        String newPassword = IRegistration.getPassword(new ValidateInput());
        if (newPassword == null) {
            System.err.println("\nPassword Update Failed");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newPassword;
    }

    /*
        This method fetch the email id of the user from its userName. This
        method is coupled with the DBOperations class which runs the DBHelper
        class
     */
    private String getEmailfromUser(IDBOperations idbOperations, UserType userType) throws InterruptedException {
        String email = null;
        for (int t = 0; t < 3; t++) {
            System.out.print("\nEnter UserName or Email : ");
            String user = this.inputs.getWordInput();
            this.inputs.getStringInput();
            email = idbOperations.fetchEmailForAuthentication(user, userType);
            if (email != null) {
                break;
            }
            System.err.println("Enter Correct Username or Email");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return email;
    }

    /*
        This method is to generate Temporary password for the user to
        authenticate
     */
    private int generateTemporaryPassword(int rangeOfPassword) {

        if (tempPass > 0) {
            return tempPass;
        }

        if (rangeOfPassword < 0 || rangeOfPassword > Integer.MAX_VALUE) {
            throw new IndexOutOfBoundsException();
        }

        int tempPass = (int) (Math.random() * rangeOfPassword);
        return tempPass;
    }

    /*
        This method is used to send the email to the user with the temporary
        password for validation.
     */
    private boolean sendTemporaryPasswordViaEmail(String email, int tempPass) throws MessagingException {

        if (email == null) {
            throw new NullPointerException();
        }

        try {
            SendEmail.sendEmail(email,
                    "Temporary password for RESET!!",
                    "<h2>Your Temporary Password is : " + tempPass + "</h2><p>Its advised not to share this email!!!</p>");
            return true;
        } catch (Exception ee) {
            System.out.println(ee);
            throw ee;
        }
    }

    /*
        Testing teh temporary passowrd with the user input password
     */
    private boolean checkTemporaryPass(int tempPass) throws InterruptedException {
        for (int t = 0; t < 3; t++) {
            if (validateTempPass(tempPass)) {
                return true;
            }
            System.err.println("\n\t\tEnter Correct temporary password!!!!");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

}
