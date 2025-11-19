package com.nuclear.boomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoOmmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoOmmBackendApplication.class, args);
    }

}
