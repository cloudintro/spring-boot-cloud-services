package com.cloudcode.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/health")
    public Map<String, Object> health(@Value("${spring.application.name}") String name) {
        Map<String, Object> healthMap = new HashMap<>();
        healthMap.put("name", name);
        healthMap.put("localDateTime", LocalDateTime.now());
        return healthMap;
    }
}
