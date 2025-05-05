package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.Imagen;
import com.AntonioP.YonkouTattoo.models.Tatuaje;
import com.AntonioP.YonkouTattoo.repository.ImagenRepository;
import com.AntonioP.YonkouTattoo.repository.TatuajeRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagenService {

    private final ImagenRepository imagenRepository;
    private final TatuajeRepository tatuajeRepository;

    private final String uploadDir = "uploads/";

    public ImagenService(ImagenRepository imagenRepository, TatuajeRepository tatuajeRepository) {
        this.imagenRepository = imagenRepository;
        this.tatuajeRepository = tatuajeRepository;
    }

    public Imagen guardarImagen(MultipartFile file, Long tatuajeId) throws IOException {
        Tatuaje tatuaje = tatuajeRepository.findById(tatuajeId)
                .orElseThrow(() -> new IllegalArgumentException("Tatuaje no encontrado"));

        // Si ya hay una imagen asociada, eliminarla
        Optional<Imagen> existente = Optional.ofNullable(imagenRepository.findByTatuajeId(tatuajeId));
        existente.ifPresent(imagenRepository::delete);

        // Guardar el archivo en disco
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // Crear y guardar la entidad
        Imagen imagen = new Imagen();
        imagen.setNombreArchivo(fileName);
        imagen.setTatuaje(tatuaje);

        return imagenRepository.save(imagen);
    }

    public Imagen obtenerImagenPorTatuaje(Long tatuajeId) {
        return imagenRepository.findByTatuajeId(tatuajeId);
    }

    public Resource cargarImagenComoRecurso(String fileName) throws IOException {
        Path path = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("No se pudo leer la imagen: " + fileName);
        }

        return resource;
    }
}

