package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tatuaje")
public class Tatuaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El estilo es obligatorio")
    private String estilo;

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private PerfilUsuario tatuador; //ID de firebase

    private Long imagen_id;

    private boolean diseno;

    //Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public PerfilUsuario getTatuador() {
        return tatuador;
    }

    public void setTatuador(PerfilUsuario tatuador) {
        this.tatuador = tatuador;
    }

    public Long getImagen_id() {
        return imagen_id;
    }

    public void setImagen_id(Long imagen_id) {
        this.imagen_id = imagen_id;
    }

    public boolean isDiseno() {
        return diseno;
    }

    public void setDiseno(boolean diseno) {
        this.diseno = diseno;
    }
}
