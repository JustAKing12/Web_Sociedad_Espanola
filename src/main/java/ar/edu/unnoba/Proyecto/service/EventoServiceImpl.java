package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoServiceImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento get(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    @Override
    public void save(Evento evento) {
        eventoRepository.save(evento);
    }

    @Override
    public void delete(Long id) {
        eventoRepository.deleteById(id);
    }

    @Override
    public Page<Evento> getPage(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }


    private Page<Evento> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest) {
        return eventoRepository.findEventoByTituloContainingIgnoreCase(title, pageRequest);
    }

    @Override
    public Page<Evento> getPageWithTitleFilter(int page, int size, String title) {

        PageRequest pageRequest = PageRequest.of(page, size);
        if (title != null && !title.isEmpty()) {
            return findByTitleContainingIgnoreCase(title, pageRequest);
        } else {
            return getPage(pageRequest);
        }
    }

    @Override
    public byte[] getImageBytes(Long id) throws SQLException {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento != null) {
            return evento.getImagen().getBytes(1, (int) evento.getImagen().length());
        } else {
            System.out.println("Imagen no disponible para el evento con ID: " + id);
            return null;
        }
    }
}
