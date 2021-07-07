package com.dal.cabby.profileManagement;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {

    public Registration(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n");
        System.out.print("\nEnter Name : ");
        String name = sc.nextLine();
        String email = "";

        for(;;){
            System.out.print("\nEnter Email : ");
            email = sc.nextLine();
            if(validateEmail(email)){
                break;
            }
            else{
                System.err.println("\t\tEnter Valid Email!!!!!");
            }
        }

        String userName = sc.next();

        sc.close();
    }

    public boolean validateEmail(String email){
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        Pattern VALIDATE_EMAIL_ADDRESS = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = VALIDATE_EMAIL_ADDRESS.matcher(email);
        return emailMatcher.find();

    }

}
