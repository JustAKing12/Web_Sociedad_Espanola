package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Suscriptor;
import ar.edu.unnoba.Proyecto.repository.SubscriptorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptorServiceImpl implements SubscriptorService {

    @Autowired
    private SubscriptorRepository subscriptorRepository;

    @Override
    @Transactional(readOnly = true)
    public Suscriptor get(Long id) {
        return subscriptorRepository.findById(id).orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Suscriptor> getAll() {
        return subscriptorRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Suscriptor suscriptor) {
        subscriptorRepository.save(suscriptor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        subscriptorRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return this.subscriptorRepository.existsByEmail(email);
    }
    public Suscriptor getByEmail(String email) {
        return this.subscriptorRepository.getByEmail(email);
    }
}