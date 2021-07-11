package com.dal.cabby.io;

import java.util.Scanner;

public class InputFromUser implements Inputs {
    Scanner scanner;

    public InputFromUser() {
        scanner = new Scanner(System.in);
    }

    @Override
    public int getIntegerInput() {
        String value = scanner.nextLine();
        return Integer.parseInt(value);
    }

    @Override
    public String getStringInput() {
        return scanner.nextLine();
    }

    @Override
    public double getDoubleInput() {
        String value = scanner.nextLine();
        return Double.parseDouble(value);
    }
}
