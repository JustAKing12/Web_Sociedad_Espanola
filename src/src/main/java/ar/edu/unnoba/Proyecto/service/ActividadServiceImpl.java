package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Actividad;
import ar.edu.unnoba.Proyecto.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadServiceImpl implements ActividadService {

    private final ActividadRepository actividadRepository;

    @Autowired
    public ActividadServiceImpl(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Override
    public Actividad get(Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @Override
    public List<Actividad> getAll() {
        return actividadRepository.findAll();
    }

    @Override
    public void save(Actividad actividad) {
        actividadRepository.save(actividad);
    }

    @Override
    public void delete(Long id){
        actividadRepository.deleteById(id);
    }

    @Override
    public Page<Actividad> getPage(Pageable pageable) {
        return actividadRepository.findAll(pageable);
    }

    private Page<Actividad> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest) {
        return actividadRepository.findActividadByTituloContainingIgnoreCase(title, pageRequest);
    }

    @Override
    public Page<Actividad> getPageWithTitleFilter(int page, int size, String title) {

        PageRequest pageRequest = PageRequest.of(page, size);
        if (title != null && !title.isEmpty()) {
            return findByTitleContainingIgnoreCase(title, pageRequest);
        } else {
            return getPage(pageRequest);
        }
    }
}
