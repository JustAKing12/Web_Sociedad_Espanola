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
            //return "redirect:/administrador/index"; PARECE QUE NO ME DEJA REDIRIGIR A ESA URL PORQUE "ADMINISTRADOR" NECESITA ESTAR AUTENTICADO Y POR LO VISTO EN NUESTRO LOGIN POR MAS QUE INGRESE EL USUARIO Y CONTRASEÑA CORRECTA NO LO ESTAMOS AUTENTICANDO
        } catch (AuthenticationException e){
            return "redirect:/autenticacion/login?error";
        }//FUNCINALIDAD: Valida que un usuario y su contraseña coincidan el la BD

    }


}