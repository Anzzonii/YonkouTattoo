package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.service.CitaService;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CONTROLADOR QUE MANEJA LAS CITAS DE LA APLICACION
 */
@RestController
@RequestMapping("/api/citas")
public class CitaRestController {

    @Autowired
    CitaService citaService;

    @Autowired
    PerfilUsuarioService perfilUsuarioService;

    @GetMapping()
    public List<Cita> getCitas(){
        return citaService.listarCitas();
    }


    @GetMapping("/{id}") //TATUDOR Y USUARIOS
    public Cita getCitaById(@PathVariable Long id){
        return citaService.getCitaById(id).get();
    }

    @PostMapping("/crear") //SOLO USUARIOS
    public String crearCita(@RequestBody Cita cita){
        System.out.println(cita.getUsuario());
        return citaService.guardarCita(cita);
    }

    @GetMapping("/usuario/{uid}") //SOLO USUARIOS
    public List<Cita> getCitaByUsuario(@PathVariable String uid){
        Optional<PerfilUsuario> perfil = perfilUsuarioService.getPerfilByUid(uid);

        if(perfil.isPresent()){
            PerfilUsuario perfilUsuario = perfil.get();

            return citaService.getCitaByPerfil(perfilUsuario);
        }

        return Collections.emptyList();
    }

    @GetMapping("/tatuador/{uid}") //SOLO TATUADOR
    public List<Cita> getCitaByTatuador(@PathVariable String uid){
        Optional<PerfilUsuario> perfil = perfilUsuarioService.getPerfilByUid(uid);

        if(perfil.isPresent()){
            PerfilUsuario perfilUsuario = perfil.get();

            return citaService.getCitasByTatuador(perfilUsuario);
        }

        return Collections.emptyList();
    }


    @DeleteMapping("/borrar/{id}") //SOLO TATUADOR

    public String borrarCita(@PathVariable Long id){
        Optional<Cita> cita = citaService.getCitaById(id);

        if(cita.isPresent()){
            citaService.borrarCita(cita.get());
        }

        return "citas";

    }

    @PostMapping("/rechazar") //SOLO TATUADOR
    public ResponseEntity<String> rechazarCita(@RequestBody Map<String, Object> datos){
        try{
            Long idCita = Long.valueOf(datos.get("idCita").toString());
            String motivo = datos.get("motivoRechazo").toString();

            Optional<Cita> citaRechazar = citaService.getCitaById(idCita);

            if(citaRechazar.isPresent()){
                Cita citaRechazada = citaRechazar.get();

                citaService.enviarCorreoRechazo(motivo, citaRechazada);
                citaService.borrarCita(citaRechazada);

                return ResponseEntity.ok("No se ha rechazado la cita correctamente");

            }

            return ResponseEntity.internalServerError().body("Error: ");
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/aceptar") //SOLO TATUADOR
    public ResponseEntity<String> aceptarCita(@RequestBody Map<String,Object> datos){
        try{
            Long idCita = Long.valueOf(datos.get("idCita").toString());

            Optional<Cita> citaAceptar = citaService.getCitaById(idCita);

            if(citaAceptar.isPresent()){
                Cita citaAceptada = citaAceptar.get();

                citaAceptada.setEstado("Aceptada");
                citaService.guardarCita(citaAceptada);
                citaService.enviarCorreoAceptado(citaAceptada);

                return ResponseEntity.ok("Cita Aceptada");
            }
            return ResponseEntity.internalServerError().body("No se ha aceptado la cita correctamente");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/completar") //SOLO TATUADOR
    public ResponseEntity<String> completarCita(@RequestBody Map<String,Object> datos){
        try{
            Long idCita = Long.valueOf(datos.get("idCita").toString());

            Optional<Cita> citaCompletar = citaService.getCitaById(idCita);

            if(citaCompletar.isPresent()){
                Cita citaCompletada = citaCompletar.get();

                citaCompletada.setEstado("Completada");
                citaService.guardarCita(citaCompletada);
                citaService.enviarCorreoAceptado(citaCompletada);

                return ResponseEntity.ok("Cita Aceptada");
            }
            return ResponseEntity.internalServerError().body("No se ha aceptado la cita correctamente");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
