package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import javax.mail.MessagingException;

import static java.lang.Thread.sleep;

public class ForgotPassword implements IForgotPassword {

    Inputs inputs;

    public ForgotPassword(Inputs inputs) {

        this.inputs = inputs;
    }

    @Override
    public boolean passwordUpdateProcess(UserType userType) throws InterruptedException, MessagingException {

        IDBOperations IDBOperations = new DBOperations(userType);
        boolean authenticationPass = false;

        String email = getEmailfromUser(IDBOperations, userType);

        if(email != null){
            authenticationPass = true;
        }
        if(!authenticationPass){
            return false;
        }

        int tempPass = generateTemporaryPassword(100000);

        if(!sendTemporaryPasswordViaEmail(email, tempPass)) {
            return false;
        }

        if(!checkTemporaryPass(tempPass)){
            return false;
        };

        String newPass = getNewPassword();

        if(newPass == null){
            return false;
        }
        IDBOperations.updateEmailPassword(email, newPass, userType);
        System.out.println("Password updated successfully");

        return true;
    }

    private boolean validateTempPass(int tempPass){

        System.out.print("\nEnter temp password sent to your registered email : ");

        int enteredPass = -1;
        try{
            enteredPass = inputs.getIntegerInput();
        }catch(Exception e){
            System.err.print("Authentication Fail !!!!");
            return false;
        }finally {
            inputs.getStringInput();
        }
        if(tempPass == enteredPass){
            System.out.println("\nAuthentication Passed");
        }
        else{
            System.err.println("\nAuthentication Failed !!!!");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return tempPass == enteredPass;
    }

    private String getNewPassword(){

        //System.out.print("\nEnter new Password : ");
        IRegistration IRegistration = new Registration(inputs);
        String newPassword = IRegistration.getPassword(new ValidateInput());
        if(newPassword == null){
            System.err.println("\nPassword Update Failed");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newPassword;
    }

    private String getEmailfromUser(IDBOperations idbOperations, UserType userType) throws InterruptedException {
        String email = null;
        for(int t=0; t<3; t++) {
            System.out.print("\nEnter UserName or Email : ");
            String user = this.inputs.getWordInput();
            this.inputs.getStringInput();
            email = idbOperations.fetchEmailForAuthentication(user, userType);
            if(email!=null){
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

    private int generateTemporaryPassword(int rangeOfPassword){
        if(rangeOfPassword<0 || rangeOfPassword > Integer.MAX_VALUE){
            throw new IndexOutOfBoundsException();
        }

        int tempPass = (int)(Math.random()*rangeOfPassword);
        return tempPass;
    }

    private boolean sendTemporaryPasswordViaEmail(String email, int tempPass) throws MessagingException {

        if(email == null){
            throw new NullPointerException();
        }

        try{
            SendEmail.sendEmail(email,
                    "Temporary password for RESET!!",
                    "<h2>Your Temporary Password is : "+tempPass+"</h2><p>Its advised not to share this email!!!</p>");
            return true;
        }
        catch (Exception ee){
            System.out.println(ee);
            throw ee;
        }
    }

    private boolean checkTemporaryPass(int tempPass ) throws InterruptedException {
        for(int t=0; t<3; t++) {
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
