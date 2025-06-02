package com.AntonioP.YonkouTattoo.security;

import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * FILTRO PARA EL TOKEN DE FIREBASE, ESTE SE EJECUTA PRIMERO, SI FALLA VA AL DE JWT Y SE EJECUTA UTILIZANDO COMO
 * CLASE ESTE FILTRO
  */
@Component
@Order(1)
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println();
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        System.out.println("Ejecutando FirebaseTokenFilter con token: " + token);

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();

            Optional<PerfilUsuario> perfilOpt = perfilUsuarioService.getPerfilByUid(uid);

            if (perfilOpt.isEmpty()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            PerfilUsuario perfil = perfilOpt.get();

            // Determinar rol
            String rol = perfil.getEsTatuador() ? "ROLE_TATUADOR" : "ROLE_USER";
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(uid, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (FirebaseAuthException e) {
            System.out.println("Token no valido, pasando al siguiente filtro");
        }

        filterChain.doFilter(request, response);
    }
}
