package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.service.ActividadService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    private final EventoService eventoService;

    private final SubscriptorService subscriptorService;

    private final ActividadService actividadService;


    @Autowired
    private VisitanteController(EventoService eventoService, SubscriptorService subscriptorService, ActividadService actividadService) {
        this.eventoService = eventoService;
        this.subscriptorService = subscriptorService;
        this.actividadService = actividadService;
    }

    //*****************INICIO*****************

    @GetMapping("")
    public String redireccion() {
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "visitantes/inicio";
    }

    //*****************EVENTOS*****************

    /*
    Cada vez que se ingrese a ver los eventos, se aparecerá de forma emergente en la misma pestaña
    un formulario para almacenar un nuevo subcriptor.
    */

    @GetMapping("/eventos")
    public String eventos(Model model,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "9") int size,
                          @RequestParam(required = false, defaultValue = "") String title) {

        Page<Evento> eventoPage = eventoService.getPageWithTitleFilter(page - 1, size, title);

        model.addAttribute("eventos", eventoPage);
        model.addAttribute("sub", new Subscriptor());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventoPage.getTotalPages());
        model.addAttribute("searchText", title);
        return "visitantes/eventos";
    }//FUNCIONALIDAD: Listado de todas los eventos
    /* PROBAR LO DE ARRIBA
        @GetMapping("/eventos")
        public String eventos(Model model, @PageableDefault(size = 9) Pageable pageable) {
            model.addAttribute("eventos", eventoService.getAll());//eventoService.extractEventos());
            model.addAttribute("sub", new Subscriptor());
            return "visitantes/eventos";
        }//FUNCIONALIDAD: Listado de todas los eventos
    */
    @PostMapping("/eventos")
    public String eventos(@ModelAttribute("sub") Subscriptor subscriptor) {
        subscriptorService.save(subscriptor);
        return "redirect:/visitante/eventos";
    }

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String evento(@PathVariable Long id, Model model) {

        Evento evento = eventoService.get(id);
        /* ver esto despues, Yo: quiere que veamos porque pasamos el nombre(revisar la vista como es que manejamos esa etiqueta enviada) */
        /* String username = evento.getUsuario().getUsername(); */
        model.addAttribute("evento", evento);
        /* model.addAttribute("username", username); */

        return "visitantes/evento";
    }//FUNCIONALIDAD: Mostrar en detalle un Evento

    //*****************CONTACTO*****************

    @GetMapping("/contacto")
    public String mostrarFormulario() {
        return "visitantes/contacto";
    }//FUNCIONALIDAD: muestra la vista de contacto con su formulario

//    @PostMapping("/contacto")
//    public String enviarMensaje(@ModelAttribute("mensaje") Mensaje mensaje) {
//        recibirMailService.recibir(mensaje);
//        return "redirect:/visitante/inicio";
   // }//FUNCIONALIDAD: Recibe el formulario de contacto y envía el correo electrónico

    @PostMapping("/contacto")
    public String recibirMensaje() {
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/historia")
    public String historia(){
        return "visitantes/historia";
    }

//    @GetMapping("/actividades")
//    public String actividades(Model model){
//        model.addAttribute("actividades", actividadService.getAll());
//        return "visitantes/actividades";
//    } //Lo mismo que arriva el chano hizo algo, probar que onda

    @GetMapping("/actividades")
    public String actividades(Model model,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "9") int size,
                              @RequestParam(required = false, defaultValue = "") String title) {

        Page<Actividad> actividadPage = actividadService.getPageWithTitleFilter(page - 1, size, title);

        model.addAttribute("actividades", actividadPage);
        model.addAttribute("currentPage", page); // info de la pag actual para cambiar de pagina
        model.addAttribute("totalPages", actividadPage.getTotalPages()); // cant total de paginas
        model.addAttribute("searchText", title);
        return "visitantes/actividades";
    }
}
