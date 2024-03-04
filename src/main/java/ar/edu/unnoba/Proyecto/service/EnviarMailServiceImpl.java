package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnviarMailServiceImpl implements EnviarMailService {

    private final JavaMailSender mailSender;
    private final SubscriptorService subscriptorService;

    @Autowired
    public EnviarMailServiceImpl(JavaMailSender mailSender, SubscriptorService subscriptorService) {
        this.mailSender = mailSender;
        this.subscriptorService = subscriptorService;
    }

    @Value("${spring.mail.username}")
    private String remitente;

    public void enviar(Evento evento) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(remitente);
            helper.setSubject(evento.getTitulo());

            List<String> destinatarios = obtenerDestinatarios();
            helper.setTo(destinatarios.toArray(new String[0]));

            String htmlText = "<div style=\"text-align: center\">" +
                    "    <img src=\"cid:logo\" alt=\"Logo de La Sociedad Española de Junín\">" +
                    "    <p>¡Hola! Se te notifica de que se ha creado un nuevo evento en <strong>La Sociedad Española de Junín.</strong></p>" +
                    "    <br>" +
                    "    <p>Puedes visualizarlo aquí: <a href=\"http://localhost:8080/visitante/eventos\">http://localhost:8080/visitante/eventos</a></p>" +
                    "    <br>" +
                    "    <p>El evento se llama <strong>" + evento.getTitulo() + "</strong>, creado en la fecha <strong>" + evento.getFecha() + "</strong> por <strong>" + evento.getUsuario().getUsername() + "</strong>.</p>" +
                    "</div>";

            helper.setText(htmlText, true);
            helper.addInline("logo", new ClassPathResource("static/imagenes/eventosImg/logo.svg"));

            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            // Manejar la excepción de envío de correo (por ejemplo, registrar o notificar al administrador)
            // También puedes considerar reintentar el envío o notificar al usuario sobre el error.
            // Ejemplo: logger.error("Error al enviar el correo: " + e.getMessage());
        }
    }

    private List<String> obtenerDestinatarios() {
        List<Subscriptor> subscriptores = subscriptorService.getAll();
        return subscriptores.stream()
                .map(Subscriptor::getEmail)
                .collect(Collectors.toList());
    }
}