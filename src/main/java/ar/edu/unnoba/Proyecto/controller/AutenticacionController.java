package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "autenticaciones/login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        try {
            UserDetails userDetails = usuarioService.loadUserByUsername(username);

            if (userDetails != null && username.equals(userDetails.getUsername()) &&
                    passwordEncoder.matches(password, userDetails.getPassword())) {
                return "redirect:/administrador/index";
            } else {
                return "redirect:/autenticacion/login?error";
            }
        } catch (UsernameNotFoundException e) {
            return "redirect:/autenticacion/login?error";
        }
    }
}