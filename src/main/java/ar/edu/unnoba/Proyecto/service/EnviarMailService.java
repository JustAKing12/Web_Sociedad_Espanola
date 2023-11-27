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
public class EnviarMailService {
    // Inyectar la instancia de JavaMailSender
    @Autowired
    private JavaMailSender mailSender;

    // Inyectar la instancia de SubscriptorServiceImpl
    @Autowired
    private SubscriptorServiceImpl subscriptorService;

    // Inyectar la propiedad del correo electrónico del remitente
    @Value("${spring.mail.username}")
    private String remitente;

    // Método para enviar un correo electrónico a todos los subscriptores de un evento
    public void enviar(Evento evento) {
        // Crear un objeto de tipo SimpleMailMessage
        SimpleMailMessage mensaje = new SimpleMailMessage();

        // Establecer el remitente del mensaje con el correo electrónico que se encuentra en el archivo application.properties
        mensaje.setFrom(remitente);

        // Establecer el asunto del mensaje con el título del evento
        mensaje.setSubject(evento.getTitulo());

        // Establecer el texto del mensaje con un mensaje personalizado que informe que se ha creado un nuevo evento
        // Usar el método String.format() para crear el mensaje con los datos del objeto Evento
        String texto = String.format("Hola, te informamos que se ha creado o modificado un nuevo evento: %s. \n\nDescripción: %s. \n\nFecha: %s. \n\nPublicado por: %s.",
                evento.getTitulo(), evento.getDescripcion(), evento.getFecha(), evento.getUsuario().getUsername());
        mensaje.setText(texto);

        // Establecer los destinatarios del mensaje con los correos electrónicos de todos los subscriptores
        // Obtener la lista de Subscriptor que devuelve el método getAll() de la clase SubscriptorServiceImpl
        List<Subscriptor> subscriptores = subscriptorService.getAll();
        // Crear un array de String para almacenar los correos electrónicos de los subscriptores
        String[] destinatarios = new String[subscriptores.size()];
        // Recorrer la lista de Subscriptor y obtener el correo electrónico de cada uno con el método getEmail()
        // y asignarlo al array de String en la posición correspondiente
        int c = 0; // Variable para controlar el índice del array
        for (Subscriptor subscriptor : subscriptores) {
            destinatarios[c] = subscriptor.getEmail();
            c++; // Incrementar el índice del array
        }
        // Usar el método setTo() del objeto SimpleMailMessage para asignar el array de String como destinatarios
        mensaje.setTo(destinatarios);

        // Usar el método send() del objeto JavaMailSender para enviar el mensaje de correo electrónico
        mailSender.send(mensaje);
    }
}