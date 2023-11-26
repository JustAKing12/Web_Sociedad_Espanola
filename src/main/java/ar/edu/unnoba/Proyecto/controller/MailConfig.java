package ar.edu.unnoba.Proyecto.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); //para gmail
        mailSender.setPort(587); //Gmail tiene puerto 587
        mailSender.setUsername("tuemail@gmail.com"); //el correo que va a recibir mensajes sobre la vista contactos o envia notificaciones
        mailSender.setPassword("tucontraseña"); //su contraseña
        return mailSender;
    }
}