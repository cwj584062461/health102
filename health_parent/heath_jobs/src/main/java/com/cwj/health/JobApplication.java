package com.cwj.health;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class JobApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");

        System.in.read();
    }
}
