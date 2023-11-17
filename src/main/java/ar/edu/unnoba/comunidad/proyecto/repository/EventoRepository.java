package ar.edu.unnoba.comunidad.proyecto.repository;

import ar.edu.unnoba.comunidad.proyecto.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Evento findEventoByIdevento(Long id);
    void deleteEventoByIdevento(Long id);
}
