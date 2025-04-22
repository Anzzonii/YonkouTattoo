package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "perfil_usuario")
public class PerfilUsuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El uid no puede ser nulo")
    private String uid;

    private String nombre;
    private String telefono;
    private Boolean esTatuador;

    //Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEsTatuador() {
        return esTatuador;
    }

    public void setEsTatuador(Boolean esTatuador) {
        this.esTatuador = esTatuador;
    }

    @Override
    public String toString() {
        return "PerfilUsuario{" +
                "uid='" + uid + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", esTatuador=" + esTatuador +
                '}';
    }
}
