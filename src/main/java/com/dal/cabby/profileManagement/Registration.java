package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import static java.lang.Thread.sleep;

public class Registration implements IRegistration {
    Inputs inputs;

    public Registration(Inputs inputs) {

        this.inputs = inputs;
    }

    @Override
    public boolean registerUser(UserType userType) {

        String name = inputName();
        String email = "";
        String password;
        String userName = "";

        ValidateInput validateInput = new ValidateInput();
        IDBOperations idbOperations = new DBOperations(userType);

        email = inputEmail(idbOperations, userType, validateInput);

        if (email == null) {
            return false;
        }

        userName = inputUserName(idbOperations, userType);

        if (userName == null) {
            return false;
        }

        password = getPassword(validateInput);

        if (password == null) {
            return false;
        }

        DataNode dataNode = new DataNode(userName, name, email, password,
                            userType);
        idbOperations.entryRegistration(dataNode);
        System.out.println("Registration successful");

        return true;
    }

    @Override
    public String getPassword(ValidateInput validateInput) {
        String password;
        boolean registerSuccessful = false;
        System.out.print("\nEnter Password : ");
        password = inputs.getStringInput();

        registerSuccessful = confirmPassword(password, validateInput);

        if (registerSuccessful) {

            return password;
        }

        return null;
    }

    private boolean confirmPassword(String password, ValidateInput validateInput){

        String confirmPassword;

        for (int t = 0; t < 3; t++) {
            System.out.print("\nConfirm above password : ");
            confirmPassword = inputs.getStringInput();
            if (validateInput.validateConfirmPassword(password,
                    confirmPassword)) {

                return true;
            } else {

                System.err.println("\t\tConfirm password doesn't match !!!");
                try {

                    sleep(100);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private String inputName(){

        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        return(inputs.getStringInput());
    }

    private String inputEmail(IDBOperations idbOperations, UserType userType, ValidateInput validateInput){
        String email;
        for (int t = 0; t < 3; t++) {
            System.out.print("\nEnter Email : ");
            email = inputs.getStringInput();

            if (!idbOperations.dbContainsEmail(email, userType)) {
                if (validateInput.validateEmail(email)) {
                    return email;
                } else {
                    System.err.println("\t\tEnter Valid Email!!!!!");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("\t\tEmail already Registered");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private String inputUserName(IDBOperations idbOperations, UserType userType){
        String userName;
        for (int t = 0; t < 3; t++) {
            System.out.print("\nEnter new Username : ");
            userName = inputs.getStringInput();
            if (!idbOperations.dbContainsUserName(userName, userType)) {
                return userName;

            } else {
                System.err.println("\t\tUsername already Taken!!!");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
