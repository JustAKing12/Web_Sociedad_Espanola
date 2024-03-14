package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActividadService {
    Actividad get(Long id);
    List<Actividad> getAll();
    void save(Actividad actividad);
    void delete(Long id);

    Page<Actividad> getPage(Pageable pageable);

    Page<Actividad> getPageWithTitleFilter(int page, int size, String title);
}