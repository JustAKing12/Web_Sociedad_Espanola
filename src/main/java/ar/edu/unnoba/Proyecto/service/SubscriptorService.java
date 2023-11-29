package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Suscriptor;

import java.util.List;

public interface SubscriptorService {
    Suscriptor get(Long id);
    List<Suscriptor> getAll();
    void save(Suscriptor suscriptor);
    void delete(Long id);
}