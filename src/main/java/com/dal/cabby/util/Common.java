package com.dal.cabby.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static void page1Options() {
        System.out.println("Enter input: \n1: Login\n2: Registration\n3: Forgot password? \n4. Exit");
    }

    public static void simulateCabTrip() {
        try {
            System.out.println("Trip started!");
            for (int i = 0; i < 5; i++) {
                System.out.println("Trip is going on....");
                Thread.sleep(1000);
            }
            System.out.println("Trip finished!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static Date parseDate(String dateStr, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    public static void main(String[] args) throws ParseException {
        Date date = parseDate("07/23/2021 14:23:23", "MM/dd/yyyy HH:mm");
        System.out.println("You travel date is " + date.toLocaleString());
    }
}
