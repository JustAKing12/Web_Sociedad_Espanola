package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.model.Mensaje;
import ar.edu.unnoba.Proyecto.model.Suscriptor;

import ar.edu.unnoba.Proyecto.service.EventoService;
import ar.edu.unnoba.Proyecto.service.RecibirMailService;

import ar.edu.unnoba.Proyecto.service.SubscriptorService;
import ar.edu.unnoba.Proyecto.service.SubscriptorServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private SubscriptorServiceImpl subscriptorService;

    @Autowired
    private RecibirMailService recibirMailService;


    //*****************INICIO*****************

    @GetMapping("")
    public String redireccion(){
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(Model model) {
        model.addAttribute("subscriptor", new Suscriptor());
        return "visitantes/inicio";
    }

    @PostMapping("/inicio")
    public String inicio(@ModelAttribute Suscriptor suscriptor) {
        if (suscriptor != null) {
            subscriptorService.save(suscriptor);
        }
        return "redirect:/visitante/inicio";
    }

    //************* Suscripcion(Hago que el subscritor se suscriba a un evento en especifico, lo hago asi porque asi se pidio en POO, despues vemos que entregamos en el proyecto final) **********
    //POR FAVOR CHANO NO ME BORRES ESTO

    @GetMapping("/suscriptor/nuevo/{id}")
    public String nuevoSuscriptor(@PathVariable Long id, Model model) {
        Evento evento = eventoService.get(id);
        Suscriptor suscriptor = new Suscriptor();

        model.addAttribute("evento", evento);
        model.addAttribute("suscriptor", suscriptor);
        model.addAttribute("nuevo", "nuevo");
        //String redirectUrl = String.format("redirect:/visitante/evento/%d", id);

        return String.format("redirect:/visitante/evento/%d?nuevo=nuevo", id);
    }
    @Transactional
    @PostMapping("/suscriptor/nuevo/{id}")
    public String crearSuscriptor(Model model, @Valid Suscriptor suscriptor, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            model.addAttribute("suscriptor", suscriptor);
            return "administradores/nuevo-evento";
        }//Mantiene los datos que ingresó el usuario si vuelve al mismo html

        String email = suscriptor.getEmail();

        if (!subscriptorService.existsByEmail(email)) {
            subscriptorService.save(suscriptor);
            suscriptor = subscriptorService.get(suscriptor.getId());
        } else {
            suscriptor = subscriptorService.getByEmail(email);
        }


        Evento evento = (eventoService.get(id));


        evento.agregarSuscriptor(suscriptor);
        suscriptor.agregarEvento(evento);

        eventoService.save(evento);
        subscriptorService.save(suscriptor);

        //enviarMailService.enviar(evento);
        model.addAttribute("success", "Se ha suscrito al evento correctamente.");
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