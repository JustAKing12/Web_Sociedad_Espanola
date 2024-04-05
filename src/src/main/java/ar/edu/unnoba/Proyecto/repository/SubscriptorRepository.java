package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Subscriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptorRepository extends JpaRepository<Subscriptor, Long> {
    /*
    @Bean
    Suscriptor findByEmail(String email); //devuelve un suscriptor que coincide con el email
    @Bean
    List<Suscriptor> findByNombreContaining(String nombre); //devuelve una lista de suscriptores que contienen el nombre
    */
}