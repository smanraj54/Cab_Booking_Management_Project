package com.dal.cabby.profileManagement;

import com.dal.cabby.io.Inputs;

public class Logout implements ILogout {

    private final Inputs inputs;

    public Logout(Inputs inputs) {

        this.inputs = inputs;
    }
    /*
    This function will exit the application.
    Before exiting, it will ask for confirmation for one last time.
     */
    public boolean logout() {

        System.out.println("Are you sure you want to logout?(Please type y or yes to confirm or Any other keyword to cancel)");
        String input = inputs.getStringInput();

        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {

            System.out.println("Logged out successfully");

            return true;
        } else {

            return false;
        }
    }
}
