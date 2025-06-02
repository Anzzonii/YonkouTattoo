package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Tatuaje;
import com.AntonioP.YonkouTattoo.service.CloudinaryService;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import com.AntonioP.YonkouTattoo.service.TatuajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CLASE PARA MANEJAR LOS TATUAJES Y DISEÑOS GUARDADOS EN LA APLICACION
 */
@RestController
@RequestMapping("/api/tatuajes")
public class TatuajeRestController {

    @Autowired
    TatuajeService tatuajeService;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping()
    public List<Tatuaje> getTatuajes(){
        return tatuajeService.listarTatuajes();
    }

    @GetMapping("/{id}")
    public Tatuaje getTatuajeById(@PathVariable Long id){
        Optional<Tatuaje> tatuaje = tatuajeService.getTatuajeById(id);

        if(tatuaje.isPresent()){
            return tatuaje.get();
        }

        return null;
    }

    @PostMapping("/crear")
    public String crearTatuaje(@RequestBody Tatuaje tatuaje){
        System.out.println(tatuaje);
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

            tatuajeService.guardarTatuaje(tatuajeEditado);
        }

        return "tatuajes";

    }

    @DeleteMapping("/borrar/{id}")
    public String borrarTatuaje(@PathVariable Long id) throws IOException {
        Optional<Tatuaje> tatuaje = tatuajeService.getTatuajeById(id);

        if (tatuaje.isPresent()) {
            Tatuaje tatu = tatuaje.get();
            cloudinaryService.deleteFileByUrl(cloudinaryService.deleteFileByUrl(tatu.getImagen()).toString());
            tatuajeService.borrarTatuaje(tatuaje.get());
        }

        return "tatuajes";
    }

}
