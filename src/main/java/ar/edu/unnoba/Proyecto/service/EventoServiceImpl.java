package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.repository.EventoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private NotifyService notifyService;

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
        evento.notifySubscribers(notifyService);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventoRepository.deleteById(id);
    }
}