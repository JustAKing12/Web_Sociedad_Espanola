package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Novedad;

import java.util.List;

public interface NovedadService {
    Novedad get(Long id);
    List<Novedad> getAll();
    void save(Novedad novedad);
    void delete(Long id);
}