package ar.edu.unnoba.Proyecto.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LoginAspect {
    private final Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    @Before("execution(* ar.edu.unnoba.Proyecto.controller.AdministradorController.index(..))")
    public void logLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String timestamp = new SimpleDateFormat("yyyy|MM|dd HH|mm|ss").format(new Date());
            logger.info("Usuario logueado: " + username);
            logger.info("Fecha y hora de logueo: " + timestamp);
        }
    }
}