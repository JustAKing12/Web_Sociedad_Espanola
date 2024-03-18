package ar.edu.unnoba.Proyecto.controller;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.service.ActividadService;
import ar.edu.unnoba.Proyecto.service.EventoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class ImageController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ActividadService actividadService;

    // muestra la imagen dado un id
    @Transactional
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws SQLException {
        Evento evento = eventoService.get(id);
        byte [] imageBytes = null;
        imageBytes = evento.getImagen().getBytes(1,(int) evento.getImagen().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @Transactional
    @GetMapping("/display/actividad")
    public ResponseEntity<byte[]> displayActivityImage(@RequestParam("id") long id) throws SQLException {
        Actividad actividad = actividadService.get(id);
        byte [] imageBytes = null;
        imageBytes = actividad.getImage().getBytes(1,(int) actividad.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}
