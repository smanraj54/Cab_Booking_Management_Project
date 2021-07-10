package com.dal.cabby.profileManagement;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Login {

    public Login(){

    }
    public boolean attemptLogin(){
        Scanner sc = new Scanner(System.in);
        DB_Operations db_operations = new DB_Operations();
        String userType = null;
        String userName = null;
        String password = null;
        boolean registerSuccessful = false;
        for(int t=0; t<3; t++){
            System.out.println("\nEnter UserType : ");
            System.out.print("press 1 for 'Admin' \n press 2 for 'customer' \n press 3 for 'driver' \n");
            int val = sc.nextInt();
            switch (val){
                case 1:{
                    userType = "Admin";
                    registerSuccessful = true;
                    break;
                }
                case 2:{
                    userType = "Customer";
                    registerSuccessful = true;
                    break;
                }
                case 3:{
                    userType = "driver";
                    registerSuccessful = true;
                    break;
                }
                default:{
                    System.err.println("\t\tinvalid UserType ... please enter correct userType");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }

            }

            if(registerSuccessful){
                break;
            }
        }
        if(!registerSuccessful) {
            return false;
        }
        sc.nextLine();
        System.out.print("\nEnter UserName or Email : ");
        userName = sc.nextLine();

        System.out.print("\nEnter Password : ");
        password = sc.nextLine();

        sc.close();

        if(db_operations.validateLoginUser(userName, password, userType)){
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
