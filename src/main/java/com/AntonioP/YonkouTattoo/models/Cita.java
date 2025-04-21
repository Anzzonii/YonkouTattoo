package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private PerfilUsuario usuario;

    @ManyToOne
    @JoinColumn(name="tatuador_id")
    private PerfilUsuario tatuador;

    private LocalDateTime fechaHora;

    //Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PerfilUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(PerfilUsuario usuario) {
        this.usuario = usuario;
    }

    public PerfilUsuario getTatuador() {
        return tatuador;
    }

    public void setTatuador(PerfilUsuario tatuador) {
        this.tatuador = tatuador;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
