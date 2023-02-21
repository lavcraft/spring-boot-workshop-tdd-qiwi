package com.governance.embassy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.ResponseEntity.ok;

@SpringBootApplication
public class EmbassyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbassyServiceApplication.class, args);
    }

}
