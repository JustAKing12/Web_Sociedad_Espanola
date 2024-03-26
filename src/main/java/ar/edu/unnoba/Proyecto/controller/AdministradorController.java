package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Usuario;
import ar.edu.unnoba.Proyecto.service.ActividadService;
import ar.edu.unnoba.Proyecto.service.EnviarMailService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

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
    public String eventos(Model model,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "9") int size,
                          @RequestParam(required = false, defaultValue = "") String title) {

        Page<Evento> eventoPage = eventoService.getPageWithTitleFilter(page - 1, size, title);

        model.addAttribute("eventos", eventoPage);
        model.addAttribute("currentPage", page); // info de la pag actual para cambiar de pagina
        model.addAttribute("totalPages", eventoPage.getTotalPages()); // cant total de paginas
        model.addAttribute("searchText", title);
        return "administradores/eventos";
    }//FUNCIONALIDAD: muestra los eventos con los usuarios que los creó

    //La idea es que en la vista eventos, el usuario pueda tener la opción de crear y eliminar eventos.
    //Osea, se hace el diseño de eventos, apretas algo y redirige a eliminar (no es necesario vista)
    //o a nuevo (es necesario vista)

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, Model model) {
        eventoService.delete(id);
        model.addAttribute("mensaje", "El evento ha sido eliminado exitosamente.");
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
    public String crearEvento(Model model, Authentication authentication, @Valid @ModelAttribute("evento") Evento evento, BindingResult bindingResult) {
        User sessionUser = (User) authentication.getPrincipal();

        if (bindingResult.hasErrors()) {
            return "administradores/nuevo-evento";
        }

        evento.setUsuario(usuarioService.findByUserName(sessionUser.getUsername()));
        eventoService.save(evento);
        enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido creado correctamente.");
        return "redirect:/administrador/inicio";
    }//FUNCIONALIDAD: procesa el formulario de creación de un nuevo evento y lo guarda

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String modificarEvento(Model model, Authentication authentication, @PathVariable Long id) {

        User sessionUser = (User) authentication.getPrincipal();

        Evento evento = eventoService.get(id);
        model.addAttribute("evento", evento);
        model.addAttribute("user", sessionUser);
        model.addAttribute("mensaje", "los eventos se modificaran");
        return "administradores/evento";
    }//FUNCIONALIDAD: muestra un evento específico con sus detalles y permite modificarlo

    @PostMapping("/evento/{id}")
    //FUNCIONALIDAD: procesa el formulario de modificación de un evento y guarda los cambios
    public String modificarEvento(Model model, Authentication authentication, @Valid @ModelAttribute("evento") Evento evento, BindingResult result, @RequestParam("imagen") MultipartFile imagen) {
        User sessionUser = (User) authentication.getPrincipal();

        // guarda el evento existente
        Evento eventoExistente = eventoService.get(evento.getId());

        // Verifica si se cargo una nueva imagen
        if (!imagen.isEmpty()) {
            try {
                // Actualizar la imagen del evento
                eventoExistente.setImagen(imagen);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("evento", eventoExistente);
            model.addAttribute("user", sessionUser);
            return "administradores/evento";
        }

        eventoExistente.setUsuario(usuarioService.findByUserName(sessionUser.getUsername()));
        eventoExistente.setTitulo(evento.getTitulo());
        eventoExistente.setDescripcion(evento.getDescripcion());

        eventoService.save(eventoExistente);
        //enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido modificado correctamente.");
        return "redirect:/administrador/eventos";
    }

    //*****************ACTIVIDAD*****************

    @GetMapping("/actividades")
    public String actividades(Model model,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "9") int size,
                              @RequestParam(required = false, defaultValue = "") String title,
                              Authentication authentication) {
        User sessionUser = (User) authentication.getPrincipal();

        Page<Actividad> actividadPage = actividadService.getPageWithTitleFilter(page - 1, size, title);

        model.addAttribute("actividades", actividadPage);
        model.addAttribute("currentPage", page); // info de la pag actual para cambiar de pagina
        model.addAttribute("totalPages", actividadPage.getTotalPages()); // cant total de paginas
        model.addAttribute("user", sessionUser);
        model.addAttribute("searchText", title);
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
    public String crearActividad(Model model, @Valid @ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "administradores/nueva-actividad";
        }

        actividadService.save(actividad);
        model.addAttribute("success", "La actividad ha sido creado correctamente.");
        return "redirect:/administrador/inicio";
    }//FUNCIONALIDAD: procesa el formulario de creación de una nueva actividad y la guarda

    //*****************ACTIVIDAD (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/actividad/{id}")
    public String modificarActividad(Model model, Authentication authentication, @PathVariable Long id) {

        User sessionUser = (User) authentication.getPrincipal();

        Actividad actividad = actividadService.get(id);
        model.addAttribute("actividad", actividad);
        model.addAttribute("user", sessionUser);
        model.addAttribute("mensaje", "las actividades se modificaran");
        return "administradores/actividad";
    }//FUNCIONALIDAD: muestra una actividad específica con sus detalles y permite modificarla

    @PostMapping("/actividad/{id}")
    public String modificarActividad(Model model, @Valid @ModelAttribute("actividad") Actividad actividad, BindingResult bindingResult, @RequestParam("image") MultipartFile image) {

        if (bindingResult.hasErrors()) {
            return "administradores/actividades";
        }

        // guarda el evento existente
        Actividad actAModificar = actividadService.get(actividad.getId());

        if (!image.isEmpty()) {
            try {
                // Actualizar la imagen del evento
                actAModificar.setImage(image);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        actAModificar.setTitulo(actividad.getTitulo());
        actAModificar.setHorario(actividad.getHorario());
        actividadService.save(actividad);
        model.addAttribute("success", "La actividad ha sido modificada correctamente.");
        return "redirect:/administrador/actividades";
    }//FUNCIONALIDAD: procesa el formulario de modificación de una actividad y guarda los cambios

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
    public String crearUsuario(Model model, @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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


    @GetMapping("/usuario/{id}")
    public String modificarUsuario(Model model, Authentication authentication, @PathVariable Long id) {

        User sessionUser = (User) authentication.getPrincipal();

        Usuario usuario = usuarioService.get(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("user", sessionUser);
        model.addAttribute("modificar", 1);
        return "administradores/nuevo-usuario";
    }//FUNCIONALIDAD: muestra un usuario específico con sus detalles y permite modificarlo

    @PostMapping("/usuario/{id}")
    //FUNCIONALIDAD: procesa el formulario de modificación de un evento y guarda los cambios
    public String modificarUsuario(Model model, Authentication authentication, @Valid Usuario usuario, BindingResult result) {
        User sessionUser = (User) authentication.getPrincipal();


        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("user", sessionUser);
            model.addAttribute("modificar", 1);
            return "administradores/nuevo-usuario";
            //Mantiene los datos que ingresó el usuario, aunque fuera error, para luego corregirlos al ingresar de nuevo.
        }


        usuarioService.save(usuario);
        model.addAttribute("success", "El usuario ha sido modificado correctamente.");
        model.addAttribute("usuarios", usuarioService.getAll());
        model.addAttribute("borrar", 1);
        return "administradores/nuevo-usuario";
    }//FUNCIONALIDAD: procesa el formulario de modificación de un uusario y guarda los cambios



}
