package com.AntonioP.YonkouTattoo.controller;

import com.AntonioP.YonkouTattoo.models.Imagen;
import com.AntonioP.YonkouTattoo.service.ImagenService;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Imagen> subirImagen(@RequestParam("file") MultipartFile file,
                                              @RequestParam("tatuajeId") Long tatuajeId) {
        try {
            Imagen imagen = imagenService.guardarImagen(file, tatuajeId);
            return ResponseEntity.ok(imagen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<Resource> devolverImagenPorId(@PathVariable Long id){
        try{
            Optional<Imagen> imagen = imagenService.getImagenById(id);

            if(imagen.isPresent()){
                Imagen imagenCogida = imagen.get();
                Resource recurso = imagenService.cargarImagenComoRecurso(imagenCogida.getNombreArchivo());
                return ResponseEntity.ok()
                        .contentType(MediaTypeFactory.getMediaType(recurso).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .body(recurso);
            }

            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
