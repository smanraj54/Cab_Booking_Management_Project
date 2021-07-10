package com.dal.cabby.profileManagement;

import java.util.Scanner;

public class Logout {

    /*
    This function will exit the application.
    Before exiting, it will ask for confirmation for one last time.
     */
    public void logout() {
        System.out.println("Are you sure you want to logout?(Please type y or yes to confirm or Any other keyword to cancel)");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
            System.out.println("Logged out successfully");
            System.exit(0);
        }
    }
}
