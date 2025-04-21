package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.repository.PerfilUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilUsuarioService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public PerfilUsuarioService(PerfilUsuarioRepository perfilUsuarioRepository) {
        this.perfilUsuarioRepository = perfilUsuarioRepository;
    }

    public List<PerfilUsuario> listarPerfiles(){
        return perfilUsuarioRepository.findAll();
    }

    public String guardarPerfil(PerfilUsuario perfilUsuario){
        perfilUsuarioRepository.save(perfilUsuario);
        return "perfil guardado correctamente";
    }

    public Optional<PerfilUsuario> getPerfilById(String id){
        return perfilUsuarioRepository.findById(id);
    }

    public String borrarPerfil(PerfilUsuario perfilUsuario){
        perfilUsuarioRepository.delete(perfilUsuario);
        return "Perfil borrado correctamente";
    }
}
