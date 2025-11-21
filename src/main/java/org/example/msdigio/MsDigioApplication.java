package org.example.msdigio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDigioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDigioApplication.class, args);
    }

}
