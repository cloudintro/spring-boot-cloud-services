package com.cloudcode.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        // httpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers("/actuator/health").permitAll());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.POST).authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.PUT).authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.PATCH).authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.DELETE).authenticated());
        httpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.TRACE).authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return httpSecurity.build();
    }

}
