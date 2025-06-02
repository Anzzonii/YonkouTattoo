package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.Tatuaje;
import com.AntonioP.YonkouTattoo.repository.TatuajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CLASE QUE GESTIONA LOS METODOS QUE AFECTAN A LOS TATUAJES QUE SE VA A USAR EN LOS CONTROLADORES
 */
@Service
public class TatuajeService {
    private final TatuajeRepository tatuajeRepository;

    public TatuajeService(TatuajeRepository tatuajeRepository) {
        this.tatuajeRepository = tatuajeRepository;
    }

    public List<Tatuaje> listarTatuajes(){return tatuajeRepository.findAll();}

    public String guardarTatuaje(Tatuaje tatuaje){
        tatuajeRepository.save(tatuaje);
        return "tatuaje guardado correctamente";
    }

    public Optional<Tatuaje> getTatuajeById(Long id){
        return tatuajeRepository.findById(id);
    }

    public String borrarTatuaje(Tatuaje tatuaje){
        tatuajeRepository.delete(tatuaje);
        return "Tatuaje borrado correctamente";
    }
}
