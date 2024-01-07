package com.cloudcode.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET).permitAll());
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST).authenticated());
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PUT).authenticated());
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PATCH).authenticated());
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE).authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

}
