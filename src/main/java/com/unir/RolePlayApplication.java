package com.unir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

// TRAS ARRANCAR, ACCEDER A http://localhost:8080/api/
@SpringBootApplication
@EntityScan({"com.unir.character.model", "com.unir.adventure.model", "com.unir.auth.model"})
public class RolePlayApplication {
    public static void main(String[] args) {

        SpringApplication.run(RolePlayApplication.class, args);
    }
}