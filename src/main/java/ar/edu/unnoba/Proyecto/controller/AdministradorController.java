package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Usuario;
import ar.edu.unnoba.Proyecto.service.ActividadService;
import ar.edu.unnoba.Proyecto.service.EnviarMailService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    private final EventoService eventoService;

    private final UsuarioService usuarioService;

    private final EnviarMailService enviarMailService;

    private final ActividadService actividadService;

    @Autowired
    public AdministradorController(EventoService eventoService, UsuarioService usuarioService, EnviarMailService enviarMailService, ActividadService actividadService) {
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
        this.enviarMailService = enviarMailService;
        this.actividadService = actividadService;
    }

    //*****************INDEX*****************

    @GetMapping("/inicio")
    public String index(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();
        model.addAttribute("user", sessionUser); //Se añade usuario para mostrar su nombre.
        return "administradores/inicio";
    }

    //*****************EVENTOS*****************

    @GetMapping("/eventos")
    public String eventos(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        model.addAttribute("eventosconcreadores", eventoService.getAll());
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
        evento.setUsuario(usuarioService.findByUserName(sessionUser.getUsername()));
        eventoService.save(evento);
        enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido creado correctamente.");
        return "redirect:/administrador/inicio";
    }//FUNCIONALIDAD: procesa el formulario de creación de un nuevo evento y lo guarda

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String modificarEvento(Model model, Authentication authentication, @PathVariable Long id) {

        /* if ((id >= eventoService.getAll().size()) || (id < 0)) {
            throw new EventoNotFoundException("Evento no encontrado con id: " + id);
        } */

        User sessionUser = (User) authentication.getPrincipal();

        Evento evento = eventoService.get(id);
        model.addAttribute("evento", evento);
        model.addAttribute("user", sessionUser);
        model.addAttribute("mensaje", "los eventos se modificaran");
        return "administradores/evento";
    }//FUNCIONALIDAD: muestra un evento específico con sus detalles y permite modificarlo

    @PostMapping("/evento/{id}")
    public String modificarEvento(Model model, Authentication authentication, @Valid Evento evento, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("evento", evento);
            model.addAttribute("user", sessionUser);
            return "administradores/evento";
            //Mantiene los datos que ingresó el usuario, aunque fuera error, para luego corregirlos al ingresar de nuevo.
        }

        evento.setUsuario(usuarioService.findByUserName(sessionUser.getUsername()));

        eventoService.save(evento);
        //enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido modificado correctamente.");
        return "redirect:/administrador/eventos";
    }//FUNCIONALIDAD: procesa el formulario de modificación de un evento y guarda los cambios


    //*****************ACTIVIDAD*****************

    @GetMapping("/actividades")
    public String actividades(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        model.addAttribute("actividades", actividadService.getAll());
        model.addAttribute("user", sessionUser);
        return "administradores/actividades";
    }//FUNCIONALIDAD: muestra las actividades

    @GetMapping("/actividades/eliminar/{id}")
    public String eliminarActividad(Model model, @PathVariable Long id) {

        actividadService.delete(id);
        model.addAttribute("succes", "La actividad se elimino con exito");
        return "redirect:/administrador/actividades";
    }//FUNCIONALIDAD: elimina una actividad por su id

    @GetMapping("/actividades/nuevo")
    public String nuevaActividad(Model model, Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        Actividad actividad = new Actividad();
        model.addAttribute("actividad", actividad); //el usuario debe introducir: titulo, descripcion y horarios de aterncion
        model.addAttribute("user", sessionUser);
        return "administradores/nueva-actividad";
    }//FUNCIONALIDAD: muestra el formulario para crear un nuevo evento

    @PostMapping("/actividades/nuevo")
    public String crearActividad(Model model, Authentication authentication, @Valid Actividad actividad, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("actividad", actividad);
            model.addAttribute("user", sessionUser);
            return "administradores/nueva-actividad";
        }//Mantiene los datos que ingresó el usuario si vuelve al mismo html

        actividadService.save(actividad);
        model.addAttribute("success", "La actividad ha sido creado correctamente.");
        return "redirect:/administrador/inicio";
    }//FUNCIONALIDAD: procesa el formulario de creación de una nueva actividad y la guarda

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/actividad/{id}")
    public String modificarActividad(Model model, Authentication authentication, @PathVariable Long id) {

        User sessionUser = (User) authentication.getPrincipal();

        Actividad actividad = actividadService.get(id);
        model.addAttribute("actividad", actividad);
        model.addAttribute("user", sessionUser);
        model.addAttribute("mensaje", "las actividades se modificaran");
        return "administradores/Actividad";
    }//FUNCIONALIDAD: muestra una actividad específica con sus detalles y permite modificarla

    @PostMapping("/actividad/{id}")
    public String modificarActividad(Model model, Authentication authentication, @Valid Actividad actividad, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();

        if (result.hasErrors()) {
            model.addAttribute("actividad", actividad);
            model.addAttribute("user", sessionUser);
            return "administradores/actividades";
        }//Mantiene los datos que ingresó el usuario, aunque fuera error, para luego corregirlos al ingresar de nuevo.


        actividadService.save(actividad);
        model.addAttribute("success", "La actividad ha sido modificada correctamente.");
        return "redirect:/administrador/actividades";
    }//FUNCIONALIDAD: procesa el formulario de modificación de una actividad y guarda los cambios




    //*****************Historia*****************

    @GetMapping("/historia")
    public String historia(){
        return "administradores/historia";
    }
//    @GetMapping("/quienes-somos")
//    public String quienesSomos(Model model, Authentication authentication) {
//        User sessionUser = (User) authentication.getPrincipal();
//        model.addAttribute("user", sessionUser);
//        return "historia";
//    }

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
        model.addAttribute("crear", 1);
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
        return "redirect:/administrador/inicio";
    }

    @GetMapping("/usuario/eliminar")
    public String eliminarUsuario(Model model, Authentication authentication){
        User sessionUser = (User) authentication.getPrincipal();

        model.addAttribute("usuarios", usuarioService.getAll());
        model.addAttribute("user", sessionUser);
        model.addAttribute("borrar", 1);
        return "administradores/nuevo-usuario";
    }

    @PostMapping("/usuarios/eliminar")
    public String eliminarUsuario(Model model, @RequestParam("nomUsuario") String nomUsuario) {
        Usuario usuario = usuarioService.findByUserName(nomUsuario); //consigo el usuario a traves de su nombre
        if (usuario != null) {
            long totalUsuarios = usuarioService.countUsuarios();
            if (totalUsuarios > 1) {
                usuarioService.delete(usuario.getId()); //obtengo el id del usuario y lo borro de la db
                model.addAttribute("success", "El usuario fue borrado correctamente.");
            } else {
                model.addAttribute("error", "Error, no se puede borrar al unico usuario de la base de datos.");
            }
        } else {
            model.addAttribute("error", "Error, no se encontro un usuario con ese nombre.");
        }
        return "redirect:/administrador/inicio";
    }
}