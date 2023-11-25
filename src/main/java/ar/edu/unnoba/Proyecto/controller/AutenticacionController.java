package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.service.UsuarioServiceImpl;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null){
            model.addAttribute("errorMensaje", "Usuario o contraseña incorrectos.");
        }
        return "autenticaciones/login";
    }//Luego de /login accede a /index (ver SecurityConfig)

    @PostMapping("/login")
    public String validar(String username, String password){  //Los nombre de los parametros deben ser iguales a la de los parametros "name" en el "login.html"


        // Esta es una forma de realizar la autenticación manualmente en Spring Security
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        // Establecer la autenticación en el contexto de Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "administradores/index";



    }

    public void authenticateUser(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationProvider.authenticate(authentication);
    }


}