package com.WebApp.PokemonList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // disable CSRF so H2’s POST login form works
                .csrf(csrf -> csrf.disable())

                // allow frames (H2 console UI uses frames)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // allow all requests (or at least the console) without authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

}
