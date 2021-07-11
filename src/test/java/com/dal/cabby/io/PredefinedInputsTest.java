package com.dal.cabby.io;

import org.junit.jupiter.api.Test;

class PredefinedInputsTest {

    @Test
    void getIntegerInput() {
        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add(34).add(3344).add("hello");
        System.out.println(predefinedInputs.getIntegerInput());
        System.out.println(predefinedInputs.getIntegerInput());
    }

    @Test
    void getStringInput() {
        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add("hello").add("gello");
        System.out.println(predefinedInputs.getStringInput());
        System.out.println(predefinedInputs.getStringInput());
    }

    @Test
    void getDoubleInput() {
        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add(45.09).add("gello");
        System.out.println(predefinedInputs.getDoubleInput());
        System.out.println(predefinedInputs.getStringInput());
    }

    @Test
    void add() {
        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add(45.09).add("gello").add(23).add(34.56).add("helll");
        System.out.println(predefinedInputs.getDoubleInput());
        System.out.println(predefinedInputs.getStringInput());
    }
}