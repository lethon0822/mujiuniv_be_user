package com.green.muziuniv_be_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MuziunivBeUserApplication {

    public static void main(String[] args) {

        SpringApplication.run(MuziunivBeUserApplication.class, args);
    }

}
