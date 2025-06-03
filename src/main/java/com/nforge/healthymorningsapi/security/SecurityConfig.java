package com.nforge.healthymorningsapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // wyłączenie CSRF (opcjonalne)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll() // dostęp bez autoryzacji
                        .anyRequest().authenticated() // reszta wymaga zalogowania
                )
                .httpBasic(Customizer.withDefaults()); // proste uwierzytelnianie HTTP (możesz zastąpić JWT itp.)

        return http.build();
    }
}
