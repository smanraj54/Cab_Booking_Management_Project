package com.dal.cabby;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", method = RequestMethod.GET)
public class HelloWorld {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String Hello() {
        return "Hello world, the much awaited Cabby app is here..";
    }
}
