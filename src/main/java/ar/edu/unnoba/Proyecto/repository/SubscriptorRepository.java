package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Suscriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptorRepository extends JpaRepository<Suscriptor, Long> {
    boolean existsByEmail(String email);

    Suscriptor getByEmail(String email);
}