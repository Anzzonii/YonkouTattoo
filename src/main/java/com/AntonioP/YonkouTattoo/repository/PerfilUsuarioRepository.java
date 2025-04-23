package com.AntonioP.YonkouTattoo.repository;

import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.models.Tatuaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {

    Optional<PerfilUsuario> findByUid(String uid);
}
