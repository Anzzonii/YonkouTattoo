package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.*;

@Entity
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    @OneToOne
    @JoinColumn(name = "tatuaje_id", unique = true)
    private Tatuaje tatuaje;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Tatuaje getTatuaje() {
        return tatuaje;
    }

    public void setTatuaje(Tatuaje tatuaje) {
        this.tatuaje = tatuaje;
    }
}