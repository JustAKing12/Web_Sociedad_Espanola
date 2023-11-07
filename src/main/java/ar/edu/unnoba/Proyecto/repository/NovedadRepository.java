package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Long> {
}