package com.thanhtan.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IdentityApplication {
//test
                public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }

}
