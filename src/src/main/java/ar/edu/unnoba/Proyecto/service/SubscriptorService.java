package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Subscriptor;

import java.util.List;

public interface SubscriptorService {
    Subscriptor get(Long id);
    List<Subscriptor> getAll();
    void save(Subscriptor subscriptor);
    void delete(Long id);
}