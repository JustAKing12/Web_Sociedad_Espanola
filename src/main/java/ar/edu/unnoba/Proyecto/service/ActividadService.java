package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Actividad;

import java.util.List;

public interface ActividadService {

    Actividad get(Long id);
    List<Actividad> getAll();
    void save(Actividad actividad);
    void delete(Long id);
}
