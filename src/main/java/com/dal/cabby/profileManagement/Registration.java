package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import static java.lang.Thread.sleep;

public class Registration implements IRegistration {
    Inputs inputs;

    /*
        In the constructor we are setting the input interface and generating
        the temporary password which can be used in the forgot password function
     */
    public Registration(Inputs inputs) {

        this.inputs = inputs;
    }

    /*
        This method is used to register new user and update the dataNode which
        is further used in db operation to write into the db
     */
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

    /*
        this method get the password from the user and validate it with the
        input confirm password value
     */
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

    /*
        Fetching the confirm password input using PresentationLayer for validating the input with the
        password entered before to create the user in database later
     */
    private boolean confirmPassword(String password,
                                    ValidateInput validateInput) {

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

    /*
        This method is used to fetch the name from input using PresentationLayer and store it for
        further use
     */
    private String inputName() {

        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        return (inputs.getStringInput());
    }

    /*
        In this method the email is fetched from the user using
        PresentationLayer and validated with regex for syntax and also with db
        table entries to catch any duplicate entry using Database Layer
     */
    private String inputEmail(IDBOperations idbOperations, UserType userType,
                              ValidateInput validateInput) {
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

    /*
        In this method the username is taken as input from the user using
        PresentationLayer
     */
    private String inputUserName(IDBOperations idbOperations,
                                 UserType userType) {
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
