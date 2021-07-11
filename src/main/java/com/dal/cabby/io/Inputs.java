package com.dal.cabby.io;

/**
 * This interface has several methods which returns
 * inputs which can be either predefined or can be entered
 * by user in console.
 */
public interface Inputs {

    /**
     * @return integer input either from predefined or from user.
     */
    int getIntegerInput();

    /**
     * @return String input either from predefined or from user.
     */
    String getStringInput();

    /**
     * @return double input either from predefined or from user.
     */
    double getDoubleInput();
}
