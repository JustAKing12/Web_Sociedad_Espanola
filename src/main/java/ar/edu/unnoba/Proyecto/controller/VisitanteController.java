package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.service.ActividadService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String eventos(Model model) {
        model.addAttribute("eventos", eventoService.getAll());//eventoService.extractEventos());
        model.addAttribute("sub", new Subscriptor());
        return "visitantes/eventos";
    }//FUNCIONALIDAD: Listado de todas los eventos

    @PostMapping("/eventos")
    public String eventos(@ModelAttribute("sub") Subscriptor subscriptor) {
        subscriptorService.save(subscriptor);
        return "redirect:/visitante/eventos";
    }

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String evento(@PathVariable Long id, Model model) {

        Evento evento = eventoService.get(id);
        /* ver esto despues */
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

    @PostMapping("/contacto")
    public String recibirMensaje() {
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/historia")
    public String historia() {
        return "visitantes/historia";
    }

    @GetMapping("/actividades")
    public String actividades(Model model) {
        model.addAttribute("actividades", actividadService.getAll());
        return "visitantes/inicio";
    }
}
