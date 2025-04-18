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
}
