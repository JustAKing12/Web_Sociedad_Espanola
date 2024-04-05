package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Subscriptor;
import ar.edu.unnoba.Proyecto.repository.SubscriptorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptorServiceImpl implements SubscriptorService {

    private final SubscriptorRepository subscriptorRepository;

    @Autowired
    public SubscriptorServiceImpl(SubscriptorRepository subscriptorRepository) {
        this.subscriptorRepository = subscriptorRepository;
    }

    @Override
    public Subscriptor get(Long id) {
        return subscriptorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subscriptor> getAll() {
        return subscriptorRepository.findAll();
    }

    @Override
    public void save(Subscriptor suscriptor) {
        subscriptorRepository.save(suscriptor);
    }

    @Override
    public void delete(Long id) {
        subscriptorRepository.deleteById(id);
    }

/*   public boolean existsByEmail(String email) {
//        return this.subscriptorRepository.existsByEmail(email);
//    }
//
//    public Subscriptor getByEmail(String email) {
//        return this.subscriptorRepository.getByEmail(email);
//    }*/
}