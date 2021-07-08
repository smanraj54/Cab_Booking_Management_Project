package com.dal.cabby.profileManagement;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Login {

    public Login(){

    }
    public boolean attemptLogin(){
        Scanner sc = new Scanner(System.in);
        DB_Operations db_operations = new DB_Operations();
        System.out.print("\nEnter UserName : ");
        String userName = sc.nextLine();

        System.out.print("\nEnter Password : ");
        String  password = sc.nextLine();

        sc.close();

        if(db_operations.dbUserNameValidation(userName) && db_operations.validatePassword(userName,password)){
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
