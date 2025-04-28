package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Tatuaje;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
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

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @GetMapping()
    public List<Tatuaje> getTatuajes(){
        return tatuajeService.listarTatuajes();
    }

    @PostMapping("/crear")
    public String crearTatuaje(@RequestBody Tatuaje tatuaje){
        return tatuajeService.guardarTatuaje(tatuaje);
    }

    @PutMapping("/editar/{id}")
    public String editarTatuaje(@PathVariable Long id, @RequestBody Map<String, String> body){

        Optional<Tatuaje> tatuaje = tatuajeService.getTatuajeById(id);

        if(tatuaje.isPresent()){
            Tatuaje tatuajeEditado = tatuaje.get();
            tatuajeEditado.setTitulo(body.get("titulo"));
            tatuajeEditado.setEstilo(body.get("estilo"));
            tatuajeEditado.setTatuador(perfilUsuarioService.getPerfilById(Long.valueOf(body.get("tatuador_id"))).get());
            tatuajeEditado.setImagenId("imagen_id");

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
