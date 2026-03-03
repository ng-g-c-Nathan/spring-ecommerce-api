package com.example.tiendita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
@Configuration
public class CorsConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. IMPORTANTE: Le decimos a Security que use nuestra config de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. IMPORTANTE: Desactivamos CSRF para poder hacer POST desde Angular/Postman
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Por ahora permitimos todo para que pruebes
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitimos el origen de Angular y dejamos abierto para pruebas
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200", "*"));

        // Métodos permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // Headers que Angular envía (Content-Type, Authorization, etc.)
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Si necesitas leer headers de respuesta en Angular
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // Si usas "*" en origins, esto debe ser false
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}