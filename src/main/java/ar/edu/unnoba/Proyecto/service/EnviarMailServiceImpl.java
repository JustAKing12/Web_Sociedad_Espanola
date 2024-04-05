package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

            String text = getText(evento);
            helper.setText(text, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            // Manejar la excepción de envío de correo (por ejemplo, registrar o notificar al administrador)
            // También puedes considerar reintentar el envío o notificar al usuario sobre el error.
            // Ejemplo: logger.error("Error al enviar el correo: " + e.getMessage());
        }
    }

    private static String getText(Evento evento) {
        String eventoUrl = "http://localhost:8080/visitante/evento/" + evento.getId();

        return "<div style=\"text-align: center; background-color: #cdcdcd; padding: 10px;\">" +
                "    <p>¡Hola! Se te notifica de que se ha creado un nuevo evento en <strong>La Sociedad Española de Junín.</strong></p>" +
                "    <br>" +
                "    <p>Puedes visualizarlo aquí: <a style=\"color: #f00822;\" href=\"" + eventoUrl + "\">" + eventoUrl + "</a></p>" +
                "    <br>" +
                "    <p>El evento se llama <strong>" + evento.getTitulo() + "</strong>, creado en la fecha <strong>" + evento.getFecha() + "</strong> por <strong>" + evento.getUsuario().getUsername() + "</strong>.</p>" +
                "</div>" +
                "<div style=\"background-color: #d2b300; text-align: center; color: white; padding: 10px;\">" +
                "    <h1>Sociedad Española de Junín</h1>" +
                "</div>";
    }

    private List<String> obtenerDestinatarios() {
        List<Subscriptor> subscriptores = subscriptorService.getAll();
        return subscriptores.stream()
                .map(Subscriptor::getEmail)
                .collect(Collectors.toList());
    }
}
