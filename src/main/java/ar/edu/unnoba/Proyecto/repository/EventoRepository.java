package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    /*
    @Bean
    List<Evento> findByTitulo(String titulo); //devuelve una lista de eventos que coinciden con el título
    @Bean
    List<Evento> findByDescripcionContaining(String descripcion); //devuelve una lista de eventos que contienen la descripción
    @Bean
    List<Evento> findByFechaBetween(Date inicio, Date fin); //devuelve una lista de eventos que están entre las fechas inicio y fin
    @Bean
    List<Evento> findByUsuario(Usuario usuario); //devuelve una lista de eventos que pertenecen al usuario
    */
}