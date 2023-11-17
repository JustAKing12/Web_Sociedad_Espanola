package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Evento;

import java.util.List;

public interface EventoService {
    Evento get(Long id);
    List<Evento> getAll();
    void save(Evento evento);
    void delete(Long id);
}