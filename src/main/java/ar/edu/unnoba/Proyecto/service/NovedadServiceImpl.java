package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Novedad;
import ar.edu.unnoba.Proyecto.repository.NovedadRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovedadServiceImpl implements NovedadService {

    @Autowired
    private NovedadRepository novedadRepository;

    @Override
    @Transactional(readOnly = true)
    public Novedad get(Long id) {
        return novedadRepository.findById(id).orElse(null);
    }               //Transaccional puede ser utilizado cuando requiere mas de una operacion en la bd
                    //mas de una operacion no sea atomica
    @Override
    @Transactional(readOnly = true)
    public List<Novedad> getAll() {
        return novedadRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Novedad novedad) {
        novedadRepository.save(novedad);
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(Long id) {
        novedadRepository.deleteById(id);
    }
}