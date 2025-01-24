package com.seowoninfo.backend01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Backend01Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend01Application.class, args);
    }

}
