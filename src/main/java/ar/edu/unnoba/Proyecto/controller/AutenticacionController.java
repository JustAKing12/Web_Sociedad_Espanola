package ar.edu.unnoba.Proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autenticacion")
public class AutenticacionController {
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null){
            model.addAttribute("errorMensaje", "Usuario o contraseña incorrectos.");
        }
        return "autenticaciones/login";
    }//Luego de /login accede a /index (ver SecurityConfig)
}