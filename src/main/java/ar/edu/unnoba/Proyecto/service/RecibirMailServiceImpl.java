package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RecibirMailServiceImpl implements RecibirMailService{
    @Autowired
    private JavaMailSender mailSender;

    // Inyectar la propiedad del correo electrónico del destinatario
    @Value("${spring.mail.username}")
    private String destinatario;

    // Método para enviar un correo electrónico al destinatario con el mensaje de contacto
    public void recibir(Mensaje mensaje) {
        // Crear un objeto de tipo SimpleMailMessage
        SimpleMailMessage correo = new SimpleMailMessage();

        // Establecer el remitente del correo con el correo electrónico del usuario que envió el formulario de contacto
        correo.setFrom(mensaje.getEmail());

        // Establecer el asunto del correo con el nombre del usuario que envió el formulario de contacto
        correo.setSubject(mensaje.getNombre());

        // Establecer el texto del correo con el cuerpo del mensaje de contacto
        correo.setText(mensaje.getCuerpo());

        // Establecer el destinatario del correo con el correo electrónico que se encuentra en el archivo application.properties
        correo.setTo(destinatario);


        System.out.println("Mensaje enviado correctamente...");

        // Usar el método send() del objeto JavaMailSender para enviar el correo electrónico
        mailSender.send(correo);
    }
}