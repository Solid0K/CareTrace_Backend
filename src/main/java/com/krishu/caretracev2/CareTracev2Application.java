package com.krishu.caretracev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CareTracev2Application {

    public static void main(String[] args) {
        SpringApplication.run(CareTracev2Application.class, args);
    }

}
