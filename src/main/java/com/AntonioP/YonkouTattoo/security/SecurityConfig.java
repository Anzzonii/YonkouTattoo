package com.AntonioP.YonkouTattoo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/auth/index", "/**").permitAll()  // Permitir acceso público
                        /*.requestMatchers("/admin/**").hasRole("ADMIN")  // Solo ADMIN puede acceder a /admin
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // USER y ADMIN pueden acceder a /user
                        .anyRequest().authenticated()  // Requiere autenticación para todo lo demás*/
                )
                .formLogin(login -> login.disable()
                        /*.loginPage("/auth/login") // Página personalizada para el login
                        .defaultSuccessUrl("/auth/index", true) // Redirigir a la página principal tras un login exitoso
                        .permitAll()  // Permitir el acceso sin autenticación*/
                )
                /*.logout(logout -> logout
                        .logoutUrl("/logout")  // URL para hacer logout
                        .logoutSuccessUrl("/")  // Redirigir a la página principal tras el logout
                        .permitAll()  // Permitir el acceso sin autenticación
                )*/
                .csrf(csrf -> csrf.disable())  // Desactivar CSRF por simplicidad, aunque es importante para la seguridad
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error") // Página personalizada para errores de acceso denegado
                );
        return http.build();
    }
}

