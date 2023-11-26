package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NotifyService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = Logger.getLogger(NotifyService.class.getName());

    static {
        logger.setLevel(Level.INFO);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
    }

    public void notify(Subscriptor subscriptor, Evento evento) {
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            mail.setFrom(new InternetAddress("tuemail@gmail.com")); // Reemplaza con tu correo electrónico
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(subscriptor.getEmail()));
            mail.setSubject("Nueva notificación de evento: " + evento.getTitulo());
            mail.setText("Estimado " + subscriptor.getNombre() + ",\n\n"
                    + "Se ha creado un nuevo evento: " + evento.getTitulo() + "\n\n"
                    + "Detalles del evento: " + evento.getDescripcion(), "utf-8", "html");

            mailSender.send(mail);

            logger.log(Level.INFO, "Notificación por correo electrónico enviada con éxito a " + subscriptor.getEmail());

        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Error al enviar la notificación por correo electrónico a " + subscriptor.getEmail(), e);
        }
    }
}