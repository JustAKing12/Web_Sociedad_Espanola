package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Usuario;
import ar.edu.unnoba.Proyecto.service.EnviarMailService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnviarMailService enviarMailService;

    //*****************INDEX*****************

    @GetMapping("/index")
    public String index(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();
        model.addAttribute("user", sessionUser); //Se añade usuario para mostrar su nombre.
        return "administradores/index";
    }

    //*****************EVENTOS*****************

    @GetMapping("/eventos")
    public String eventos(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        VisitanteController.extractEventos(model, eventoService);
        model.addAttribute("user", sessionUser);
        return "administradores/eventos";
    }//FUNCIONALIDAD: muestra los eventos con los usuarios que los creó

    //La idea es que en la vista eventos, el usuario pueda tener la opción de crear y eliminar eventos.
    //Osea, se hace el diseño de eventos, apretas algo y redirige a eliminar (no es necesario vista)
    //o a nuevo (es necesario vista)

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventoService.delete(id);
        return "redirect:/administrador/eventos";
    }//FUNCIONALIDAD: elimina un evento por su id

    @GetMapping("/eventos/nuevo")
    public String nuevoEvento(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        Evento evento = new Evento();
        model.addAttribute("evento", evento); //el usuario debe introducir: titulo, descripcion, imagen
        model.addAttribute("user", sessionUser);
        return "administradores/nuevo-evento";
    }//FUNCIONALIDAD: muestra el formulario para crear un nuevo evento

    @PostMapping("/eventos/nuevo")
    public String crearEvento(Model model, Authentication authentication, @Valid Evento evento, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("evento", evento);
            model.addAttribute("user", sessionUser);
            return "administradores/nuevo-evento";
        }//Mantiene los datos que ingresó el usuario si vuelve al mismo html
        evento.setUsuario(usuarioService.buscarPorNombre(sessionUser.getUsername()));
        eventoService.save(evento);
        //enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido creado correctamente.");
        return "redirect:/administrador/eventos";
    }//FUNCIONALIDAD: procesa el formulario de creación de un nuevo evento y lo guarda

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String modificarEvento(Model model, Authentication authentication, @PathVariable Long id) {
        User sessionUser = (User) authentication.getPrincipal();

        Evento evento = eventoService.get(id);
        model.addAttribute("evento", evento);
        model.addAttribute("user", sessionUser);
        return "administradores/evento";
    }//FUNCIONALIDAD: muestra un evento específico con sus detalles y permite modificarlo

    @PostMapping("/evento/{id}")
    public String modificarEvento(Model model, Authentication authentication, @Valid Evento evento, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("evento", evento);
            model.addAttribute("user", sessionUser);
            return "administradores/evento";
        }//Mantiene los datos que ingresó el usuario, aunque fuera error, para luego corregirlos al ingresar de nuevo.
        eventoService.save(evento);
        //enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido modificado correctamente.");
        return "redirect:/administrador/eventos";
    }//FUNCIONALIDAD: procesa el formulario de modificación de un evento y guarda los cambios

    //*****************QUIENES SOMOS*****************

    @GetMapping("/quienes-somos")
    public String quienesSomos(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();
        model.addAttribute("user", sessionUser);
        return "administradores/quienes-somos";
    }

    //*****************CONTACTO*****************

    @GetMapping("/contacto")
    public String contacto(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();
        model.addAttribute("user", sessionUser);
        return "administradores/contacto";
    }

    //*****************USUARIOS*****************

    @GetMapping("/usuario/crear")
    public String crearUsuario(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("user", sessionUser);
        return "administradores/nuevo-usuario";
    }

    @PostMapping("/usuario/crear")
    public String crearUsuario(Model model, Authentication authentication, @Valid Usuario usuario, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("user", sessionUser);
            return "administradores/nuevo-usuario";
        }
        usuarioService.save(usuario);
        model.addAttribute("success", "El usuario ha sido creado correctamente.");
        return "redirect:/administrador/index";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(Model model, @PathVariable Long id) {
        long totalUsuarios = usuarioService.countUsuarios();
        if (totalUsuarios > 1) {
            usuarioService.delete(id);
            model.addAttribute("success", "El usuario ha sido eliminado correctamente.");
        } else {
            model.addAttribute("error", "No es posible eliminar el único usuario en la base de datos.");
        }
        return "redirect:/administrador/index";
    }
}