package com.AntonioP.YonkouTattoo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CLASE DE CONFIGURACIÓN PARA CONTROLAR EL ACCESO A LAS APIS DEL BACKEND
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private FirebaseTokenFilter firebaseTokenFilter;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/citas",  "/api/citas/borrar/**",  //PERMISOS DE LAS CITAS
                                "/api/citas/tatuador/**", "/api/citas/borrar/**", "/api/citas/aceptar", "/api/citas/rechazar", "/api/citas/completar",

                                "/api/perfiles-usuario/editarRol/**",   //EDITAR ROLES DE UN USUARIO

                                "/api/tatuajes/crear",      //PERMISOS DE LOS TATUAJES
                                "/api/tatuajes/editar/**",
                                "/api/tatuajes/borrar"

                                ).hasAuthority("ROLE_TATUADOR")

                        .requestMatchers("/api/citas/crear", "/api/citas/usuario/**" //PERMISOS DE LAS CITAS
                                ).hasAuthority("ROLE_USER")

                        .requestMatchers(
                                "/api/citas/**", //CITAS
                                "/api/cloudinary/**", //CLOUDINARY (TODOS TIENEN PERMISOS DE SUBIR)

                                "/api/perfiles-usuario/editar/**"  //Permiso para poder editar su rol


                                ).hasAnyAuthority("ROLE_TATUADOR", "ROLE_USER")
                        .anyRequest().permitAll()   //INCLUYE FIREBASEAUTHCONTROLLER QUE NECESITA USARLO TODO EL MUNDO
                )
                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, FirebaseTokenFilter.class);

        return http.build();
    }

    //Configuracion de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}

