package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RecibirMailServiceImpl implements RecibirMailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public RecibirMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendSimpleMessage(Mensaje mensaje) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mensaje.getEmail());
        message.setTo(username);
        message.setSubject(mensaje.getNombre());
        message.setText(mensaje.getMensaje());
        javaMailSender.send(message);
    }
}