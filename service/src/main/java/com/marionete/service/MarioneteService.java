package com.marionete.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marionete.backends.*;

@SpringBootApplication
public class MarioneteService {
    public static void main(String[] args) {
        AccountInfoMock.start();
        UserInfoMock.start();
        SpringApplication.run(MarioneteService.class, args);
    }
}
