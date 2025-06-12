package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.repository.PerfilUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CLASE QUE GESTIONA LOS METODOS QUE AFECTEN A LOS PERFILES DE USUARIO QUE SE VAYAN A UTILIZAR EN LOS CONTROLADORES
 */
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

    public Optional<PerfilUsuario> getPerfilById(Long id){
        return perfilUsuarioRepository.findById(id);
    }

    public String borrarPerfil(PerfilUsuario perfilUsuario){
        perfilUsuarioRepository.delete(perfilUsuario);
        return "Perfil borrado correctamente";
    }

    public Optional<PerfilUsuario> getPerfilByUid(String uid){
        return perfilUsuarioRepository.findByUid(uid);
    }
}
