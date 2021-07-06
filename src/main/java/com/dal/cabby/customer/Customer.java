package com.dal.cabby.customer;

import com.dal.cabby.util.Common;

import java.util.Scanner;

public class Customer {
    public Customer() {
        customerPage1();
    }

    private void customerPage1() {
        int input = Common.page1Options();
        if (Common.isInputInvalid(input, 1, 3)) {
            System.out.println("Invalid input: " + input);
            return;
        }
    }
}
