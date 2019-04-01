package com.marinho.event.driven.example.callcenterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
public class CallCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallCenterApplication.class, args);
    }
}
