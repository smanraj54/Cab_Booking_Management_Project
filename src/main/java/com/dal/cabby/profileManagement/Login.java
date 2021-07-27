package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;
import com.dal.cabby.util.ConsolePrinter;

import static java.lang.Thread.sleep;

public class Login implements ILogin {

    Inputs inputs;

    /*
        Login constructor initialising the input type as user defined or
        predefined
     */
    public Login(Inputs inputs) {
        this.inputs = inputs;
    }

    /*
        This method is used to attempt the login and generate a login request
        input as the userType
     */
    @Override
    public boolean attemptLogin(UserType userType) throws InterruptedException {

        IDBOperations db_operations = new DBOperations(userType);
        String userNameOrEmail;
        String password;

        userNameOrEmail = inputUserName();
        password = inputPassword();

        if (db_operations.validateLoginUser(userNameOrEmail, password, userType)) {
            return true;
        }

        ConsolePrinter.printErrorMsg("Login Failed due to invalid credentials.");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        return false;
    }

    /*
        This get the username as input
     */
    private String inputUserName() {

        System.out.print("\nEnter UserName or Email : ");

        return (inputs.getStringInput());

    }

    /*
        This get the password as input
     */
    private String inputPassword() {

        System.out.print("\nEnter Password : ");

        return (inputs.getStringInput());

    }

}
