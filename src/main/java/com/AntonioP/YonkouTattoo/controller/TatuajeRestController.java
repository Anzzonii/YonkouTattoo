package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Estilos;
import com.AntonioP.YonkouTattoo.models.Tatuaje;
import com.AntonioP.YonkouTattoo.service.TatuajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tatuajes")
public class TatuajeRestController {

    @Autowired
    TatuajeService tatuajeService;

    @GetMapping()
    public List<Tatuaje> getTatuajes(){
        return tatuajeService.listarTatuajes();
    }

    @PostMapping("/crear")
    public String crearTatuaje(@RequestBody Tatuaje tatuaje){
        return tatuajeService.guardarTatuaje(tatuaje);
    }

    @PutMapping("/editar/{id}")
    public String editarTatuaje(@PathVariable String id, @RequestBody Map<String, String> body){

        Optional<Tatuaje> tatuaje = tatuajeService.getTatuajeById(Long.valueOf(id));

        if(tatuaje.isPresent()){
            Tatuaje tatuajeEditado = tatuaje.get();
            tatuaje.get().setEstilo(Estilos.valueOf(body.get("estilo")));
            tatuaje.get().setTitulo(body.get("titulo"));
            tatuajeService.guardarTatuaje(tatuajeEditado);
        }

        return "tatuajes";

    }

    @DeleteMapping("/borrar/{id}")
    public String borrarTatuaje(@PathVariable Long id) {
        Optional<Tatuaje> tatuaje = tatuajeService.getTatuajeById(id);

        if (tatuaje.isPresent()) {
            tatuajeService.borrarTatuaje(tatuaje.get());
        }

        return "tatuajes";
    }

}
