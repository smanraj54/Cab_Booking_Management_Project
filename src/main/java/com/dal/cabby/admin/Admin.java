package com.dal.cabby.admin;

import com.dal.cabby.util.Common;

public class Admin {

    public Admin() {
        adminPage1();
    }

    private void adminPage1() {
        int input = Common.page1Options();
        if (Common.isInputInvalid(input, 1, 3)) {
            System.out.println("Invalid input: " + input);
            return;
        }
    }
}
