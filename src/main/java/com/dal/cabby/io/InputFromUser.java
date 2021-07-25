package com.dal.cabby.io;

import java.util.Scanner;

/**
 * This class implements the Inputs interface. It will ask the user to enter
 * any input which is being asked by the application.
 */
public class InputFromUser implements Inputs {
    Scanner scanner;

    public InputFromUser() {
        scanner = new Scanner(System.in);
    }

    /**
     * @return return the integer input entered by user.
     */
    @Override
    public int getIntegerInput() {
        String value = scanner.nextLine();
        return Integer.parseInt(value);
    }

    /**
     * @return return the String input entered by user.
     */
    @Override
    public String getStringInput() {
        return scanner.nextLine();
    }

    /**
     * @return return the double input entered by user.
     */
    @Override
    public double getDoubleInput() {
        String value = scanner.nextLine();
        return Double.parseDouble(value);
    }

    /**
     * @return return the word input entered by user.
     */
    @Override
    public String getWordInput() {
        String value = scanner.next();
        return value;
    }
}
