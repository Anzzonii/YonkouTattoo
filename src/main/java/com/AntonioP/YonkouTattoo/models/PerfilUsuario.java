package com.AntonioP.YonkouTattoo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil_usuario")
public class PerfilUsuario {

    @Id
    private String uid;

    private String nombre;
    private String telefono;
    private Boolean esTatuador;

    //Getters y Setters

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
}
