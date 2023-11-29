package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.model.subscripcionEvento;
import org.springframework.beans.factory.annotation.Autowired;
import ar.edu.unnoba.Proyecto.repository.SubsEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SubsEventService {

    @Autowired
    private SubsEventRepository subsEventRepository;


    @Transactional(readOnly = true)
    public subscripcionEvento get(Long id) {
        return subsEventRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<subscripcionEvento> getAll() {
        return subsEventRepository.findAll();
    }


    @Transactional
    public void save(subscripcionEvento subsEvent) {
        subsEventRepository.save(subsEvent);
    }


    @Transactional
    public void delete(Long id) {
        subsEventRepository.deleteById(id);
    }
}
