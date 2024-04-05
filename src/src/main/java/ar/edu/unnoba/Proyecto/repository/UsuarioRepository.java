package ar.edu.unnoba.Proyecto.repository;

import ar.edu.unnoba.Proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
