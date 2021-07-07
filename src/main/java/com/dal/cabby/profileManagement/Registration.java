package com.dal.cabby.profileManagement;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Registration {

    public Registration(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        String name = sc.nextLine();
        String email = "";
        String password = "";
        String confirmPassword = "";
        String phoneNumber = "";
        String userName = "";
        ValidateInput validateInput = new ValidateInput();

        for(;;){
            System.out.print("\nEnter Email : ");
            email = sc.nextLine();

            if(validateInput.validateEmail(email)){
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

        System.out.print("\nEnter new Username : ");
        userName = sc.next();
        System.out.print("\nEnter Password : ");
        password = sc.nextLine();
        for(;;){
            System.out.print("\nConfirm above password : ");
            confirmPassword = sc.nextLine();
            if(validateInput.validateConfirmPassword(password, confirmPassword)){
                break;
            }
            else{
                System.err.println("\t\tConfirm password does't match !!!");
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        sc.close();
    }



}
