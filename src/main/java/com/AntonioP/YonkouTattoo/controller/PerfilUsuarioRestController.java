package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.service.CitaService;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import com.google.firebase.auth.FirebaseAuth;
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

    @Autowired
    CitaService citaService;

    @GetMapping()
    public List<PerfilUsuario> getPerfiles(){
        return perfilService.listarPerfiles();
    }

    @PostMapping("/crear")
    public String crearPerfil(@RequestBody PerfilUsuario perfil){
        return perfilService.guardarPerfil(perfil);
    }

    @GetMapping("/{uid}")
    public PerfilUsuario getPerfilByUid(@PathVariable String uid){
        Optional<PerfilUsuario> perfilUsuario = perfilService.getPerfilByUid(uid);

        if(perfilUsuario.isPresent()){
            return perfilUsuario.get();
        }

        return null;
    }

    @PutMapping("/editar/{id}")
    public String eidtarPerfil(@PathVariable Long id, @RequestBody Map <String, String> body) {
        Optional<PerfilUsuario> perfilUsuario = perfilService.getPerfilById(id);

        if(perfilUsuario.isPresent()){
            PerfilUsuario perfilEditado = perfilUsuario.get();

            //Realizar las ediciones al perfil
            perfilEditado.setNombre(body.get("nombre"));
            perfilEditado.setTelefono(body.get("telefono"));

            perfilService.guardarPerfil(perfilEditado);

        }

        return "perfiles";
    }

    @PutMapping("/editarRol/{id}")
    public String editarRolUsuario(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<PerfilUsuario> perfilUsuario = perfilService.getPerfilById(id);

        if (perfilUsuario.isPresent()) {
            PerfilUsuario perfilEditado = perfilUsuario.get();

            if (body.containsKey("esTatuador")) {
                boolean esTatuador = Boolean.parseBoolean(body.get("esTatuador"));
                perfilEditado.setEsTatuador(esTatuador);
                perfilService.guardarPerfil(perfilEditado);
            }
        }

        return "perfiles";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarPerfil(@PathVariable Long id){
        Optional<PerfilUsuario> perfil = perfilService.getPerfilById(id);

        if(perfil.isPresent()){

            List<Cita> citas = citaService.getCitaByPerfil(perfil.get());

            for(Cita cita : citas){
                citaService.borrarCita(cita);
            }

            perfilService.borrarPerfil(perfil.get());
        }

        return "perfiles";
    }
}
