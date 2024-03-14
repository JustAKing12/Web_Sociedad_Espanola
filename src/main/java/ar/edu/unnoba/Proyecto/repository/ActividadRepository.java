package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    Page<Actividad> findAll(Pageable pageable);

    Page<Actividad> findActividadByTituloContainingIgnoreCase(String title, Pageable pageable);
}
