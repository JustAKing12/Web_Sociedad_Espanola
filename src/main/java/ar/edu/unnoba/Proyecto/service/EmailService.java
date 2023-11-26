package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Mensaje;
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
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Se crea una instancia de Logger usando el nombre de la clase
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    // Se configura el nivel de logging y el manejador de salida
    static {
        logger.setLevel(Level.INFO);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
    }

    // El método que envía el correo electrónico usando el objeto Mensaje
    public void enviarEmail(Mensaje mensaje) {

        // Se crea un objeto de tipo MimeMessage
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            // Se establecen los atributos del correo electrónico
            mail.setFrom(new InternetAddress(mensaje.getEmail()));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress("casilla@destino.com"));
            mail.setSubject("Consulta desde el formulario de contacto");
            mail.setText(mensaje.getCuerpo(), "utf-8", "html");

            // Se envía el correo electrónico
            mailSender.send(mail);

            // Se registra un mensaje de logging de nivel INFO
            logger.log(Level.INFO, "Correo electrónico enviado con éxito");

        } catch (MessagingException e) {
            // Se registra un mensaje de logging de nivel SEVERE con la traza de la excepción
            logger.log(Level.SEVERE, "Error al enviar el correo electrónico", e);
        }
    }
}