package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//*****************NOTAS*****************
//En esta clase no es necesario definir más metodos, ya que no se necesita el usuario logueado.

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    @Autowired
    private EventoService eventoService;

    //*****************METODO PARA EVITAR DUPLICAR CODIGO*****************

    static void extractEventos(Model model, EventoService eventoService) {
        List<Evento> eventos = eventoService.getAll();
        Map<Evento, String> eventosConUsernames = new HashMap<>();
        for (Evento evento : eventos) {
            eventosConUsernames.put(evento, evento.getUsuario().getUsername());
        }
        model.addAttribute("eventos", eventosConUsernames);
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

    //*****************INICIO*****************

    @GetMapping("")
    public String redireccion(){
        return "redirect:/visitante/inicio";
    }

    @GetMapping("/inicio")
    public String inicio(){
        return "visitantes/inicio";
    }

    @GetMapping("/quienes-somos")
    public String historia(){
        return "visitantes/quienes-somos";
    }
    @GetMapping("/contacto")
    public String contacto(){
        return "visitantes/contacto";
    }

}