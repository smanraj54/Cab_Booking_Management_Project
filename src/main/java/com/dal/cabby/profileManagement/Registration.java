package com.dal.cabby.profileManagement;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Registration {

    public Registration(){


    }

    private boolean RegisterUser(){
        boolean registerSuccessful = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        String name = sc.nextLine();
        String email = "";
        String password = "";
        String confirmPassword = "";
        String userName = "";
        ValidateInput validateInput = new ValidateInput();
        DB_Operations db_operations = new DB_Operations();
        String userType = "";

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
                }
                if(registerSuccessful){
                    break;
                }
            }

        }

        registerSuccessful = false;
        for(int t=0; t<3; t++){
            System.out.print("\nEnter Email : ");
            email = sc.nextLine();

            if(!validateInput.validateEmail(email)){
                if(validateInput.validateEmail(email)){
                    registerSuccessful = true;
                    break;
                }
                else{
                    System.err.println("\t\tEnter Valid Email!!!!!");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        if(!registerSuccessful) {
            return false;
        }

        System.out.print("\nEnter new Username : ");
        userName = sc.next();

        System.out.print("\nEnter Password : ");
        password = sc.nextLine();
        registerSuccessful = false;

        for(int t=0; t<3; t++){
            System.out.print("\nConfirm above password : ");
            confirmPassword = sc.nextLine();
            if(validateInput.validateConfirmPassword(password, confirmPassword)){
                registerSuccessful = true;
                break;
            }
            else{
                System.err.println("\t\tConfirm password doesn't match !!!");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!registerSuccessful) {
            return false;
        }

        sc.close();
        DataNode dataNode = new DataNode(userName, name, email, password, userType);
        db_operations.entryRegistration(dataNode);

        return true;
    }



}
