package com.example.gestore_prenotazioni.security_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //non lo so nemmeno io cos'ho fatto qua
                .csrf(csrf -> csrf.disable())
                //
                 // Configurazione delle autorizzazioni
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Accesso pubblico a /api/auth/**
                        .requestMatchers("/api/rooms/**").permitAll() // Accesso pubblico a /api/rooms/**
                        .anyRequest().authenticated() // Tutte le altre richieste richiedono autenticazione
                )
                .httpBasic(withDefaults()); // Abilita l'autenticazione HTTP Basic

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}