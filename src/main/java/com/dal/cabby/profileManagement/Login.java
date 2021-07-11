package com.dal.cabby.profileManagement;

import com.dal.cabby.pojo.UserType;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Login {

    public Login(){

    }
    public boolean attemptLogin(UserType userType){
        Scanner sc = new Scanner(System.in);
        DB_Operations db_operations = new DB_Operations(userType);
        String userNameOrEmail = null;
        String password = null;
        System.out.print("\nEnter UserName or Email : ");
        userNameOrEmail = sc.nextLine();

        System.out.print("\nEnter Password : ");
        password = sc.nextLine();
        sc.close();

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
