package com.cloudcode.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Base64;

@SpringBootTest(classes = ApiGatewayApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApiGatewayApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Test
    public void health_status_200_Ok(@Value("${spring.application.name}") String appName) throws Exception {
        String token = ("Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes())));
        webTestClient.get().uri("/health").headers(httpHeaders -> httpHeaders.add("Authorization", token))
                .exchange().expectStatus().isOk()
                .expectBody().jsonPath("$.name").isEqualTo(appName);
    }

    @Test
    public void health_status_200_authorized() throws Exception {
        webTestClient.get().uri("/actuator/health").exchange().expectStatus().isOk();
    }

    @Test
    public void health_status_401_Unauthorized() throws Exception {
        webTestClient.get().uri("/health").exchange().expectStatus().isUnauthorized();
    }

}
