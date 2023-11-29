package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnviarMailServiceImpl implements EnviarMailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriptorServiceImpl subscriptorService;

    @Value("${spring.mail.username}")
    private String remitente;

    public void enviar(Evento evento) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(remitente);
        mensaje.setSubject(evento.getTitulo());
        String texto = String.format("Hola, te informamos que se ha creado o modificado un nuevo evento: %s. \n\nDescripción: %s. \n\nFecha: %s. \n\nPublicado por: %s.",
                evento.getTitulo(), evento.getDescripcion(), evento.getFecha(), evento.getUsuario().getUsername());
        mensaje.setText(texto);
        List<Subscriptor> subscriptores = subscriptorService.getAll();
        String[] destinatarios = new String[subscriptores.size()];
        int c = 0; // Variable para controlar el índice del array
        for (Subscriptor subscriptor : subscriptores) {
            destinatarios[c] = subscriptor.getEmail();
            c++;
        }
        mensaje.setTo(destinatarios);
        mailSender.send(mensaje);
    }
}