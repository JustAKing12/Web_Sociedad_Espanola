package ar.edu.unnoba.comunidad.proyecto.service;

import ar.edu.unnoba.comunidad.proyecto.model.Evento;
import ar.edu.unnoba.comunidad.proyecto.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // guarda el evento en la bd
    void saveEvento(Evento e) {
        eventoRepository.save(e);
    }

    // borra el evento con la id
    void deleteEventoById(Long id) {
        eventoRepository.deleteEventoByIdevento(id);
    }

    // retorna el evento con la id
    Evento findById(Long id) {
        return eventoRepository.findEventoByIdevento(id);
    }
}
