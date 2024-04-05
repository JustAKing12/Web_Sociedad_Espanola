package ar.edu.unnoba.Proyecto.service;

import ar.edu.unnoba.Proyecto.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsuarioService extends UserDetailsService {
    Usuario get(Long id);
    List<Usuario> getAll();
    void save(Usuario usuario);
    void delete(Long id);
    long countUsuarios();
    Usuario findByUserName(String username);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
