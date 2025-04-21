package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas(){
        return citaRepository.findAll();
    }

    public String guardarCita(Cita cita){
        citaRepository.save(cita);
        return "cita guardada correctamente";
    }

    public Optional<Cita> getCitaById(Long id){
        return citaRepository.findById(id);
    }

    public String borrarCita(Cita cita){
        citaRepository.delete(cita);
        return "cita borrada correctamente";
    }
}
