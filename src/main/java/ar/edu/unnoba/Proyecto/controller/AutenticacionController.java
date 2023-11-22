package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.service.UsuarioServiceImpl;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null){
            model.addAttribute("errorMensaje", "Usuario o contraseña incorrectos.");
        }
        return "autenticaciones/login";
    }//Luego de /login accede a /index (ver SecurityConfig)

    @PostMapping("/validarUsuario")
    public String validar(String username, String password){  //Los nombre de los parametros deben ser iguales a la de los parametros "name" en el "login.html"
        try{
            usuarioService.validarUser(username,password);
            return "administradores/index";
            //return "redirect:/administrador/index";
        } catch (AuthenticationException e){
            return "redirect:/autenticacion/login?error";
        }//FUNCINALIDAD: Valida que un usuario y su contraseña coincidan el la BD

    }
    //HAY QUE ACLARAR QUE ESTAMOS IGNORANDO EL ".defaultSuccessUrl" DEL "securityConfig" AL MOMENTO DE LOGUEARNOS POR LO QUE NO SE SI REDIRIGIRNOS NOSOTROS MISMOS ESTA BIEN
//POR LO QUE PUEDO VER NOSOTROS ACCEDEMOS A "administradores/index" mediante la URL "/visitante/validarUsuario" y tenmos que hacerlo ingresando a "/administrador" cosa que no nos va a dejar hasta que estemos logueados, y hay que verificar si lo que estamos haciendo nos sirve para figurar como "logueados" para Spring

}