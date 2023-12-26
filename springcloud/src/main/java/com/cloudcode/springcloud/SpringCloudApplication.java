package com.cloudcode.springcloud;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@SpringBootApplication
public class SpringCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudApplication.class, args);
	}

	@GetMapping("/health")
	public Map<String, Object> health() {
		Map<String, Object> healthMap = new HashMap<>();
		healthMap.put("name", "demo service");
		healthMap.put("localDateTime", LocalDateTime.now());
		return healthMap;
	}
}
