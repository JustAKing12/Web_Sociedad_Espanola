package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}