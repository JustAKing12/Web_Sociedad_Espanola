package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.repository.EventoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    @Transactional(readOnly = true)
    public Evento get(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }               //Transaccional puede ser utilizado cuando requiere mas de una operacion en la bd

    //mas de una operacion no sea atomica
    @Override
    @Transactional(readOnly = true)
    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Evento evento) {
        eventoRepository.save(evento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventoRepository.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Map<Evento, String> extractEventos() {
        List<Evento> eventos = getAll();
        Map<Evento, String> eventosConUsernames = new HashMap<>();
        for (Evento evento : eventos) {
            eventosConUsernames.put(evento, evento.getUsuario().getUsername());
        }
        return eventosConUsernames;
    }
}