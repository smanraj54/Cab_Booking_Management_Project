package com.dal.cabby;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    @Test
    public void testHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        Assertions.assertEquals("Hello world, the much awaited Cabby app is here..1", helloWorld.Hello(), "Hello world message is not correct");
    }
}
