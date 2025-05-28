package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.service.CitaService;
import com.AntonioP.YonkouTattoo.service.PerfilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public Cita getCitaById(@PathVariable Long id){
        return citaService.getCitaById(id).get();
    }

    @PostMapping("/crear")
    public String crearCita(@RequestBody Cita cita){
        System.out.println(cita.getUsuario());
        return citaService.guardarCita(cita);
    }

    @GetMapping("/usuario/{uid}")
    public List<Cita> getCitaByUsuario(@PathVariable String uid){
        Optional<PerfilUsuario> perfil = perfilUsuarioService.getPerfilByUid(uid);

        if(perfil.isPresent()){
            PerfilUsuario perfilUsuario = perfil.get();

            return citaService.getCitaByPerfil(perfilUsuario);
        }

        return Collections.emptyList();
    }

    @GetMapping("/tatuador/{uid}")
    public List<Cita> getCitaByTatuador(@PathVariable String uid){
        Optional<PerfilUsuario> perfil = perfilUsuarioService.getPerfilByUid(uid);

        if(perfil.isPresent()){
            PerfilUsuario perfilUsuario = perfil.get();

            return citaService.getCitasByTatuador(perfilUsuario);
        }

        return Collections.emptyList();
    }

    @PutMapping("/citas/editar/{id}")
    public ResponseEntity<String> editarCita(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Cita> citaOpt = citaService.getCitaById(id); // Usa tu método de servicio

        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();

            // Campos que pueden ser modificados
            if (body.containsKey("estado")) {
                //cita.setAceptada(Boolean.parseBoolean(body.get("estado"))); // "aceptada" o "rechazada"
            }

            if (body.containsKey("descripcion")) {
                cita.setDescripcion(body.get("descripcion"));
            }

            if (body.containsKey("imagenUrl")) {
                //cita.setImagen_id(Long.valueOf(body.get("imagenUrl")));
            }

            // Agrega más campos si lo necesitas

            citaService.guardarCita(cita);
            return ResponseEntity.ok("Cita actualizada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cita no encontrada");
        }
    }


    @DeleteMapping("/borrar/{id}")
    public String borrarCita(@PathVariable Long id){
        Optional<Cita> cita = citaService.getCitaById(id);

        if(cita.isPresent()){
            citaService.borrarCita(cita.get());
        }

        return "citas";

    }

    @PostMapping("/rechazar")
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

    @PostMapping("/aceptar")
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

    @PostMapping("/completar")
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
