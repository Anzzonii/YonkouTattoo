package com.AntonioP.YonkouTattoo.repository;

import com.AntonioP.YonkouTattoo.models.Imagen;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    Imagen findByTatuajeId(Long tatuajeId);
}