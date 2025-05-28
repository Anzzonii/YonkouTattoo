package com.AntonioP.YonkouTattoo.repository;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.models.Tatuaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    public List<Cita> getCitaByUsuario(PerfilUsuario usuario);

    public List<Cita> getCitaByTatuador(PerfilUsuario tatuador);

}
