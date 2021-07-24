package com.dal.cabby.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PredefinedInputsTest {

    @Test
    void testInputs() {
        PredefinedInputs predefinedInputs = new PredefinedInputs();
        predefinedInputs.add(34).add(33.44).add("hello");
        Assertions.assertEquals(34, predefinedInputs.getIntegerInput());
        Assertions.assertEquals(33.44, predefinedInputs.getDoubleInput());
        Assertions.assertEquals("hello", predefinedInputs.getStringInput());
    }
}