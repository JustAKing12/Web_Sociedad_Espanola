package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.model.Evento;
import ar.edu.unnoba.Proyecto.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    @Transactional(readOnly = true)
    public Actividad get(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> getAll() {
        return actividadRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Actividad actividad) {
        actividadRepository.save(actividad);
    }

    @Override
    @Transactional
    public void delete(Long id){
        actividadRepository.deleteById(id);
    }
}
