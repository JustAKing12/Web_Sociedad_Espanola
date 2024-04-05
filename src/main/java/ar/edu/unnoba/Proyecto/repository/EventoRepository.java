package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Page<Evento> findAll(Pageable pageable);
    Page<Evento> findEventoByTituloContainingIgnoreCase(String title, Pageable pageable);
}