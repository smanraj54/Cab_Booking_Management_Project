package com.dal.cabby.driver;

import com.dal.cabby.util.Common;

import java.util.Scanner;

public class Driver {

    public Driver() {
        driverPage1();
    }

    private void driverPage1() {
        int input = Common.page1Options();
        if (Common.isInputInvalid(input, 1, 3)) {
            System.out.println("Invalid input: " + input);
            return;
        }
    }
}
