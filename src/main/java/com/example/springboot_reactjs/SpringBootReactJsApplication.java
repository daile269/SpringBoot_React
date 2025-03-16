package com.example.springboot_reactjs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//        (exclude = {SecurityAutoConfiguration.class })
public class SpringBootReactJsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactJsApplication.class, args);
    }

}
