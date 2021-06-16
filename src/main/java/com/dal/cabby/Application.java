package com.dal.cabby;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.dal.cabby")
public class Application {
    public static void main(String[] args) {
        System.out.println("Cabby app starts here11...");
        System.out.println("More implemention to come...stay tuned..");
        SpringApplication.run(Application.class, args);
    }
}