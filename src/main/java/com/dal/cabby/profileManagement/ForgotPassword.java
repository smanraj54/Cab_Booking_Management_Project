package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ForgotPassword {

    public boolean passwordUpdateProcess(Scanner sc, DB_Operations db_operations, UserType userType){
        boolean authenticationPass = false;
        String email = null;
        for(int t=0; t<3; t++) {
            System.out.print("\nEnter UserName or Email : ");
            String user = sc.next();
            sc.nextLine();
            email = db_operations.fetchEmailForAuthentication(user, userType);
            if(email!=null){
                authenticationPass = true;
                break;
            }
            System.err.println("Enter Correct Username or Email");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!authenticationPass){
            return false;
        }
        int tempPass = (int)(Math.random()*100000);
        try{
            SendEmail.sendEmail(email,
                    "Temporary password for RESET!!",
                    "<h2>Your Temporary Password is : "+tempPass+"</h2><p>Its advised not to share this email!!!</p>");
        }catch (Exception ee){
            System.out.println(ee);
        }

        authenticationPass = false;
        for(int t=0; t<3; t++) {
            if (validateTempPass(tempPass, sc)) {
                authenticationPass = true;
                break;
            }
            System.err.println("\n\t\tEnter Correct temporary password!!!!");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!authenticationPass){
            return false;
        }
        String newPass = getNewPassword(sc);

        if(newPass == null){
            return false;
        }
        db_operations.updateEmailPassword(email, newPass, userType);
        return true;
    }

    private boolean validateTempPass(int tempPass, Scanner sc){
        System.out.print("\nEnter temp password sent to your registered email : ");

        int enteredPass = -1;
        try{
            enteredPass = sc.nextInt();
        }catch(Exception e){
            System.err.print("Authentication Fail !!!!");
            return false;
        }finally {
            sc.nextLine();
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

    private String getNewPassword(Scanner sc){
        //System.out.print("\nEnter new Password : ");
        Registration registration = new Registration();
        String newPassword = registration.getPassword(sc, new ValidateInput());
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

}
