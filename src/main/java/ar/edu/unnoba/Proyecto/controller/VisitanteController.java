package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Mensaje;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.RecibirMailService;
import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private SubscriptorService subscriptorService;

    @Autowired
    private RecibirMailService recibirMailService;

    //*****************INICIO*****************

    @GetMapping("")
    public String redireccion(){
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("subscriptor", new Subscriptor());
        return "visitantes/inicio";
    }

    @PostMapping("/inicio")
    public String inicio(@ModelAttribute Subscriptor subscriptor) {
        if (subscriptor != null) {
            subscriptorService.save(subscriptor);
        }
        return "redirect:/visitante/inicio";
    }

    //*****************EVENTOS*****************

    @GetMapping("/eventos")
    public String eventos(Model model) {
        extractEventos(model, eventoService);
        return "visitantes/eventos";
    }//FUNCIONALIDAD: Listado de todas los eventos

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
    public String enviarMensaje(@ModelAttribute("mensaje") @Valid Mensaje mensaje, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "visitantes/contacto"; // Se devuelve la vista "visitantes/contacto" con los errores
        }
        //recibirMailService.recibir(mensaje);
        return "visitantes/contacto";
    }//FUNCIONALIDAD: Recibe el formulario de contacto y envía el correo electrónico

    //*****************METODOS PARA EVITAR DUPLICAR CODIGO*****************

    static void extractEventos(Model model, EventoService eventoService) {
        List<Evento> eventos = eventoService.getAll();
        model.addAttribute("eventos", eventos);
//        Map<Evento, String> eventosConUsernames = new HashMap<>();
//        for (Evento evento : eventos) {
//            eventosConUsernames.put(evento, evento.getUsuario().getUsername());
//        }
//        model.addAttribute("eventos", eventosConUsernames);
    }

    @GetMapping("/historia")
    public String historia(){
        return "visitantes/historia";
    }
    @GetMapping("/actividades")
    public String actividades(){
        return "visitantes/actividades";
    }
}