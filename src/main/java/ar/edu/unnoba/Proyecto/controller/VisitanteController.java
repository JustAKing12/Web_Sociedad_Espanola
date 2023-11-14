package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Novedad;
import ar.edu.unnoba.Proyecto.service.NovedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/visitante")
public class VisitanteController {

    @Autowired
    private NovedadService novedadService;

    @GetMapping("/login")
    public String login() {
        return "administradores/login";
    }//Luego de /login accede a /main (ver SecurityConfig)

    @GetMapping("/actividades")
    public String actividades(Model model) {
        List<Novedad> novedades = novedadService.getAll();
        Map<Novedad, String> novedadesWithUsernames = new HashMap<>();
        for (Novedad novedad : novedades) {
            novedadesWithUsernames.put(novedad, novedad.getUsuario().getUsername());
        }
        model.addAttribute("novedades", novedadesWithUsernames);
        return "visitantes/actividades";
    }//FUNCIONALIDAD: Listado de todas las Novedades

    @GetMapping("/actividad/{id}")
    public String actividad(@PathVariable Long id, Model model) {
        Novedad novedad = novedadService.get(id);
        String username = novedad.getUsuario().getUsername();
        model.addAttribute("novedad", novedad);
        model.addAttribute("username", username);
        return "visitantes/actividad";
    }//FUNCIONALIDAD: Mostrar en detalle una Novedad
}