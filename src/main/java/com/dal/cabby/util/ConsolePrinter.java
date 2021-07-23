package com.dal.cabby.util;

public class ConsolePrinter {
    private final static String RED_COLOR_CODE = "\u001B[31m";
    private final static String GREEN_COLOR_CODE = "\u001B[32m";
    private final static String RESET_COLOR_CODE = "\u001B[0m";

    public static void printErrorMsg(String errMsg) {
        String msg = String.format("%s ERROR: %s %s", RED_COLOR_CODE, errMsg, RESET_COLOR_CODE);
        System.out.println(msg);
    }

    public static void printSuccessMsg(String successMsg) {
        String msg = String.format("%s SUCCESS: %s %s", GREEN_COLOR_CODE, successMsg, RESET_COLOR_CODE);
        System.out.println(msg);
    }
}
