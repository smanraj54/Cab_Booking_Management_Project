package com.dal.cabby.util;

import java.util.Scanner;

/*
    This class will contain all the common methods which can be reused across multiple
    places in the project.
 */
public class Common {


    // Return false if the input is outside the range of start and end.
    public static boolean isInputInvalid(int input, int start, int end) {
        return input < start || input > end;
    }

    // Show the input options and capture the input entered by the user and return it.
    public static int page1Options() {
        System.out.println("Enter input: \n1: Login\n2: Registration\n3: Forgot password?");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        return input;
    }
}
