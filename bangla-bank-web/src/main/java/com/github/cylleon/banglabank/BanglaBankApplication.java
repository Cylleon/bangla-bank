package com.github.cylleon.banglabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BanglaBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanglaBankApplication.class, args);
    }

}
