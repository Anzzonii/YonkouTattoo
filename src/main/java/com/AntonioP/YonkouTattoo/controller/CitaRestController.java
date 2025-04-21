package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaRestController {

    @Autowired
    CitaService citaService;

    @GetMapping()
    public List<Cita> getCitas(){
        return citaService.listarCitas();
    }

    @PostMapping("/crear")
    public String crearCita(@RequestBody Cita cita){
        return citaService.guardarCita(cita);
    }

    @PutMapping("/editar/{id}")
    public String editarCita(@PathVariable Long id, @RequestBody Map<String, String> body){
        Optional<Cita> cita = citaService.getCitaById(id);

        if(cita.isPresent()){
            Cita citaEditada = cita.get();

            //Realizar las ediciones a la cita
            citaEditada.setFechaHora(LocalDateTime.parse(body.get("fechaHora")));

            citaService.guardarCita(citaEditada);

        }

        return "citas";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarCita(@PathVariable Long id){
        Optional<Cita> cita = citaService.getCitaById(id);

        if(cita.isPresent()){
            citaService.borrarCita(cita.get());
        }

        return "citas";

    }
}
