package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import static java.lang.Thread.sleep;

public class Registration {
    Inputs inputs;
    public Registration(Inputs inputs) {
        this.inputs = inputs;
    }

    public boolean registerUser(UserType userType) {
        boolean registerSuccessful = false;
        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        String name = inputs.getStringInput();
        String email = "";
        String password = "";
        //String confirmPassword = "";
        String userName = "";
        ValidateInput validateInput = new ValidateInput();
        DBOperations db_operations = new DBOperations(userType);

        for (int t = 0; t < 3; t++) {
            System.out.print("\nEnter Email : ");
            email = inputs.getStringInput();

            if (!db_operations.dbContainsEmail(email, userType)) {
                if (validateInput.validateEmail(email)) {
                    registerSuccessful = true;
                    break;
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

        if (!registerSuccessful) {
            return false;
        }
        registerSuccessful = false;
        for (int t = 0; t < 3; t++) {
            System.out.print("\nEnter new Username : ");
            userName = inputs.getStringInput();
            if (!db_operations.dbContainsUserName(userName, userType)) {
                registerSuccessful = true;
                break;
            } else {
                System.err.println("\t\tUsername already Taken!!!");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!registerSuccessful) {
            return false;
        }

        password = getPassword(validateInput);

        if (password == null) {
            return false;
        }
        DataNode dataNode = new DataNode(userName, name, email, password,
                            userType);
        db_operations.entryRegistration(dataNode);
        System.out.println("Registration successful");
        return true;
    }

    public String getPassword(ValidateInput validateInput) {
        String password = null;
        String confirmPassword = null;
        boolean registerSuccessful = false;
        System.out.print("\nEnter Password : ");
        password = inputs.getStringInput();

        for (int t = 0; t < 3; t++) {
            System.out.print("\nConfirm above password : ");
            confirmPassword = inputs.getStringInput();
            if (validateInput.validateConfirmPassword(password,
                    confirmPassword)) {

                registerSuccessful = true;
                break;
            } else {

                System.err.println("\t\tConfirm password doesn't match !!!");
                try {

                    sleep(100);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
        if (registerSuccessful) {

            return password;
        }
        return null;
    }
}
