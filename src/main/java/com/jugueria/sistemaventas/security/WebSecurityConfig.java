package com.jugueria.sistemaventas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivamos CSRF para que el Logout funcione fácil con un simple enlace
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((requests) -> requests
                        // Permitir archivos estáticos (CSS, JS, Imágenes)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // --- REGLAS DE SEGURIDAD ACTUALIZADAS ---
                        // Todo lo que empiece con /cajero solo es para el rol CAJERO
                        .requestMatchers("/cajero/**").hasAnyAuthority("CAJERO", "ROLE_CAJERO")

                        // Todo lo que empiece con /mozo solo es para el rol MOZO
                        .requestMatchers("/mozo/**").hasAnyAuthority("MOZO", "ROLE_MOZO")

                        // Cualquier otra página requiere estar logueado
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll() // Usamos tu HTML de login personalizado
                        .successHandler(myAuthenticationSuccessHandler()) // Redirigir según el rol
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // A dónde ir tras cerrar sesión
                        .permitAll()
                );

        return http.build();
    }

    // Usamos contraseñas sin encriptar (texto plano) para facilitar el proyecto
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Lógica para decidir a dónde mandar al usuario después de loguearse
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();
            String role = authorities.iterator().next().getAuthority();

            // Verificamos si el rol contiene la palabra clave
            if (role.contains("CAJERO")) {
                response.sendRedirect("/cajero"); // Panel del Cajero
            } else if (role.contains("MOZO")) {
                response.sendRedirect("/mozo");   // Panel del Mozo
            } else {
                response.sendRedirect("/");
            }
        };
    }
}