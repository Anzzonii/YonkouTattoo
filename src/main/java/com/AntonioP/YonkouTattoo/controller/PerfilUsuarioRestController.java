package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfiles-usuario")
public class PerfilUsuarioRestController {

    @Autowired
    PerfilUsuarioService perfilService;

    @GetMapping()
    public List<PerfilUsuario> getPerfiles(){
        return perfilService.listarPerfiles();
    }

    @PostMapping("/crear")
    public String crearPerfil(@RequestBody PerfilUsuario perfil){
        return perfilService.guardarPerfil(perfil);
    }

    @PutMapping("/editar/{id}")
    public String eidtarPerfil(@PathVariable String id, @RequestBody Map <String, String> body) {
        Optional<PerfilUsuario> perfilUsuario = perfilService.getPerfilById(id);

        if(perfilUsuario.isPresent()){
            PerfilUsuario perfilEditado = perfilUsuario.get();

            //Realizar las ediciones a la cita
            perfilEditado.setNombre(body.get("nombre"));

            perfilService.guardarPerfil(perfilEditado);

        }

        return "perfiles";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarPerfil(@PathVariable String id){
        Optional<PerfilUsuario> perfil = perfilService.getPerfilById(id);

        if(perfil.isPresent()){
            perfilService.borrarPerfil(perfil.get());
        }

        return "perfiles";

    }
}
