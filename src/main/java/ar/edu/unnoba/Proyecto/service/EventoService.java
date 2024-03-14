package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EventoService {
    Evento get(Long id);
    List<Evento> getAll();
    void save(Evento evento);
    void delete(Long id);

    Page<Evento> getPage(Pageable pageable);

    Page<Evento> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest);
}