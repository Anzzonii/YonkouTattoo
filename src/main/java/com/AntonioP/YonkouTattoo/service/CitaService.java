package com.AntonioP.YonkouTattoo.service;

import com.AntonioP.YonkouTattoo.models.Cita;
import com.AntonioP.YonkouTattoo.models.PerfilUsuario;
import com.AntonioP.YonkouTattoo.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    @Autowired
    JavaMailSender mailSender;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas(){
        return citaRepository.findAll();
    }

    public String guardarCita(Cita cita){
        citaRepository.save(cita);
        return "cita guardada correctamente";
    }

    public Optional<Cita> getCitaById(Long id){
        return citaRepository.findById(id);
    }

    public String borrarCita(Cita cita){
        citaRepository.delete(cita);
        return "cita borrada correctamente";
    }

    public List<Cita> getCitaByPerfil(PerfilUsuario usuario){
        return citaRepository.getCitaByUsuario(usuario);
    }

    public List<Cita> getCitasByTatuador(PerfilUsuario tatuador){
        return citaRepository.getCitaByTatuador(tatuador);
    }

    public void enviarCorreoRechazo(String motivo, Cita cita){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(cita.getUsuario().getEmail());
        mensaje.setSubject("YONKOU TATTOO: Rechazo cita Yonkou Tattoo "+cita.getFecha());
        mensaje.setText("Buenas "+cita.getUsuario().getNombre()+", sentimos mucho que tu cita con fecha y hora: "+ cita.getFecha()+" "+cita.getHora()+" haya sido rechazada :( " +
                "\n\nTe dejamos el motivo del rechazo: " +
                "\n"+ motivo +
                "\n\n\n\nTe animamos a que nos vuelvas a hablar sin miedo, Un saludo ^^"
        );

        mensaje.setFrom("yonkoutattooapp@gmail.com");

        mailSender.send(mensaje);
    }

    public void enviarCorreoAceptado(Cita cita){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(cita.getUsuario().getEmail());
        mensaje.setSubject("YONKOU TATTOO: Cita Aceptada con fecha: "+cita.getFecha());
        mensaje.setText("Buenas "+cita.getUsuario().getNombre()+", tu cita con "+cita.getTatuador().getNombre()+" ha sido aceptada." +
                "\nFecha y Hora: "+cita.getFecha()+" "+cita.getHora()+
                "\nTatuador: "+cita.getTatuador().getNombre()+
                "\nPara confirmar la cita recuerda pagar la reserva antes de esta"+
                "\n\nUn saludo, nos vemos en el estudio ^^"
        );

        mensaje.setFrom("yonkoutattooapp@gmail.com");

        mailSender.send(mensaje);
    }

}
