package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tatuaje")
public class Tatuaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El estilo es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estilos estilo;


    private String imagenId;          //UID de firebase

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private PerfilUsuario tatuador; //ID de firebase


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

    public Estilos getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilos estilo) {
        this.estilo = estilo;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public PerfilUsuario getTatuador() {
        return tatuador;
    }

    public void setTatuador(PerfilUsuario tatuador) {
        this.tatuador = tatuador;
    }
}
