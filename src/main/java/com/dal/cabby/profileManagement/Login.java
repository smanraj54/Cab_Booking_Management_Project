package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import static java.lang.Thread.sleep;

public class Login implements ILogin {

    Inputs inputs;

    public Login(Inputs inputs){
        this.inputs = inputs;
    }

    @Override
    public boolean attemptLogin(UserType userType) throws InterruptedException {

        IDBOperations db_operations = new DBOperations(userType);
        String userNameOrEmail;
        String password;

        userNameOrEmail = inputUserName();
        password = inputPassword();

        if(db_operations.validateLoginUser(userNameOrEmail, password, userType)){
            System.out.println("\n\t\tLogin Successful !!");
            return true;
        }

        System.err.println("\n\t\tLogin Failed !!");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        return false;

    }

    private String inputUserName(){

        System.out.print("\nEnter UserName or Email : ");

        return(inputs.getStringInput());

    }

    private String inputPassword(){

        System.out.print("\nEnter Password : ");

        return(inputs.getStringInput());

    }

}
