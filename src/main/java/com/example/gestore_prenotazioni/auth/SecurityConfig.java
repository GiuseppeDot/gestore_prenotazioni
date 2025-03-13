package com.example.gestore_prenotazioni.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Abilita le annotazioni @PreAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // Gestisce gli errori di autenticazione

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Servizio per caricare i dettagli dell'utente

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Filtro per validare i token JWT

    // Configurazione della sicurezza
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabilita CSRF (non necessario per API REST)
                .authorizeHttpRequests(authorize -> authorize
                        // Endpoint pubblici (accessibili senza autenticazione)
                        .requestMatchers("/api/auth/**").permitAll() // Endpoint di autenticazione
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Accesso libero a Swagger
                        .requestMatchers("/api/rooms/**").hasRole("ADMIN") // Solo gli admin possono gestire le camere
                        .anyRequest().authenticated() // Tutti gli altri endpoint richiedono autenticazione
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Gestisce gli errori di autenticazione
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Non creare sessioni (API stateless)
                );

        // Aggiungi il filtro JWT per validare i token
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configurazione dell'AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configurazione dell'encoder per le password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt per crittografare le password
    }
}
