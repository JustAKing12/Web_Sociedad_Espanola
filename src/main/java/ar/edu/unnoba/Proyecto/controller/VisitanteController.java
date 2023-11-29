package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Mensaje;
import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.model.subscripcionEvento;
import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.RecibirMailService;
import ar.edu.unnoba.Proyecto.service.SubsEventService;
import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    private SubsEventService subsEventService;

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

    //************* Suscripcion(Hago que el subscritor se suscriba a un evento en especifico, lo hago asi porque asi se pidio en POO, despues vemos que entregamos en el proyecto final) **********
    //POR FAVOR CHANO NO ME BORRES ESTO

    @GetMapping("/subscriptor/nuevo/{id}")
    public String nuevoSubscriptor(@PathVariable Long id, Model model) {
        Evento evento = eventoService.get(id);
        Subscriptor subscriptor = new Subscriptor();

        model.addAttribute("evento", evento);
        model.addAttribute("subscriptor", subscriptor);
        model.addAttribute("nuevo", "nuevo");
        //String redirectUrl = String.format("redirect:/visitante/evento/%d", id);
        String redirectUrl = String.format("redirect:/visitante/evento/%d?nuevo=nuevo", id);

        return redirectUrl;
    }

    @PostMapping("/subscriptor/nuevo/{id}")
    public String crearEvento(Model model, @Valid Subscriptor subscriptor, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            model.addAttribute("evento", subscriptor);
           // model.addAttribute("user", sessionUser);
            return "administradores/nuevo-evento";
        }//Mantiene los datos que ingresó el usuario si vuelve al mismo html

        subscripcionEvento subEvent = new subscripcionEvento();
        subEvent.setEvento(eventoService.get(id));
        subEvent.setSubscriptor(subscriptor);
        subscriptorService.save(subscriptor);
        subsEventService.save(subEvent);


        //enviarMailService.enviar(evento);
        model.addAttribute("success", "El evento ha sido creado correctamente.");
        return "redirect:/visitante/eventos";
    }
//****************************************

    //*****************EVENTOS*****************

    @GetMapping("/eventos")
    public String eventos(Model model) {
        extractEventos(model, eventoService);
        return "visitantes/eventos";
    }//FUNCIONALIDAD: Listado de todas los eventos

    //*****************EVENTO (AL SELECCIONAR CLICKEANDO)*****************

    @GetMapping("/evento/{id}")
    public String evento(@PathVariable Long id, Model model, @RequestParam(name = "nuevo", required = false) String nuevo) {
        Evento evento = eventoService.get(id);
        String username = evento.getUsuario().getUsername();
        model.addAttribute("evento", evento);
        model.addAttribute("username", username);

        if (nuevo != null && !nuevo.isEmpty()) {
            model.addAttribute("nuevo", nuevo);
            return "visitantes/evento";
        }
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