package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;
import com.dal.cabby.pojo.UserType;

import static java.lang.Thread.sleep;

public class Login {

    Inputs inputs;
    public Login(Inputs inputs){
        this.inputs = inputs;
    }

    public boolean attemptLogin(UserType userType){
        IDBOperations db_operations = new DBOperations(userType);
        String userNameOrEmail;
        String password;
        System.out.print("\nEnter UserName or Email : ");
        userNameOrEmail = inputs.getStringInput();

        System.out.print("\nEnter Password : ");
        password = inputs.getStringInput();

        if(db_operations.validateLoginUser(userNameOrEmail, password, userType)){
            System.out.println("\n\t\tLogin Successful !!");
            return true;
        }

        System.err.println("\n\t\tLogin Failed !!");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;

    }

}
