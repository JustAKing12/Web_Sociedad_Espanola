package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Mensaje;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.RecibirMailServiceImpl;
import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private SubscriptorService subscriptorService;

    @Autowired
    private RecibirMailServiceImpl recibirMailService;

    //*****************INICIO*****************

    @GetMapping("")
    public String redireccion(){
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
        model.addAttribute("eventosconcreadores", eventoService.extractEventos());
        model.addAttribute("subscriptor", new Subscriptor());
        return "visitantes/eventos";
    }//FUNCIONALIDAD: Listado de todas los eventos

    @PostMapping("/eventos")
    public String eventos(@ModelAttribute Subscriptor subscriptor) {
        if (subscriptor != null) {
            subscriptorService.save(subscriptor);
        }
        return "redirect:/visitante/eventos";
    }

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String evento(@PathVariable Long id, Model model) {
        Evento evento = eventoService.get(id);
        String username = evento.getUsuario().getUsername();
        model.addAttribute("evento", evento);
        model.addAttribute("username", username);

        return "visitantes/evento";
    }//FUNCIONALIDAD: Mostrar en detalle un Evento

    //*****************CONTACTO*****************

    @GetMapping("/contacto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new Mensaje());
        return "visitantes/contacto";
    }//FUNCIONALIDAD: muestra la vista de contacto con su formulario

    @PostMapping("/contacto")
    public String enviarMensaje(Model model, @Valid Mensaje mensaje, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mensaje", mensaje);
            return "visitantes/contacto"; // Se devuelve la vista "visitantes/contacto" con los errores
        }
        //recibirMailService.recibir(mensaje);
        return "visitantes/contacto";
    }//FUNCIONALIDAD: Recibe el formulario de contacto y envía el correo electrónico

    @GetMapping("/historia")
    public String historia(){
        return "visitantes/historia";
    }

    @GetMapping("/actividades")
    public String actividades(){
        return "visitantes/actividades";
    }
}